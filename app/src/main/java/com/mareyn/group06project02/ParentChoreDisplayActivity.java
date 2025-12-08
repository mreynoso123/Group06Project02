package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Chore;
import com.mareyn.group06project02.database.entities.User;
import com.mareyn.group06project02.databinding.ActivityParentChoreDisplayBinding;
import com.mareyn.group06project02.viewHolders.ChoreAdapter;
import com.mareyn.group06project02.viewHolders.ChoreViewModel;

public class ParentChoreDisplayActivity extends AppCompatActivity {

  private static final String PARENT_DISPLAY_PREVIOUS_USER_ID = "com.mareyn.group06project02.PARENT_DISPLAY_PREVIOUS_USER_ID";
  private static final String PARENT_DISPLAY_PREVIOUS_USERNAME = "com.mareyn.group06project02.PARENT_DISPLAY_PREVIOUS_USERNAME";
  private static final String PARENT_DISPLAY_USER_ID = "com.mareyn.group06project02.PARENT_DISPLAY_USER_ID";
  private static final String PARENT_DISPLAY_USERNAME = "com.mareyn.group06project02.PARENT_DISPLAY_USERNAME";
  private static final String PARENT_DISPLAY_CAN_EDIT = "com.mareyn.group06project02.PARENT_DISPLAY_CAN_EDIT";

  private ActivityParentChoreDisplayBinding binding;
  // need next line of code?
  private ChoreScoreRepository repository;
  private ChoreViewModel choreViewModel;

  private String choreTitle = "";
  private String dueDate = "";
  private String choreDescription = "";
  private String assignTo = "";
  private int score = 0;

  // need the following info?
  private int userId;
  private String username;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityParentChoreDisplayBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    repository = ChoreScoreRepository.getRepository(getApplication());
    userId = getIntent().getIntExtra(PARENT_DISPLAY_USER_ID, -1);
    username = getIntent().getStringExtra(PARENT_DISPLAY_USERNAME);
    String previousUsername = getIntent().getStringExtra(PARENT_DISPLAY_PREVIOUS_USERNAME);
    int previousUserId = getIntent().getIntExtra(PARENT_DISPLAY_PREVIOUS_USER_ID, -1);

    if (!getIntent().getBooleanExtra(PARENT_DISPLAY_CAN_EDIT, false)) {
      binding.createNewChoreButton.setVisibility(View.GONE);
    }

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // All Active Chores Recycler View
    choreViewModel = new ViewModelProvider(this).get(ChoreViewModel.class);

    // The following instantiates the recycler view
    RecyclerView recyclerView = binding.activeChoresRecyclerView;
    final ChoreAdapter adapter = new ChoreAdapter(new ChoreAdapter.ChoreDiff());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    choreViewModel.getAllActiveChores().observe(this, chores -> {
      adapter.submitList(chores);
    });

    binding.createNewChoreButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        resetChoreDetails();
        if (!getChoreInformation()) {
          toastMaker("Empty Field");
        } else {
          insertChore();
        }
      }
    });

    binding.goBackButton2.setOnClickListener(view -> {
      Intent intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), previousUsername, previousUserId);
      startActivity(intent);
    });
  }

  private void resetChoreDetails() {
    choreTitle = "";
    dueDate = "";
    choreDescription = "";
    assignTo = "";
    score = 0;
  }

  private boolean isEmpty() {
    if (choreTitle.isEmpty() || dueDate.isEmpty() || choreDescription.isEmpty() || assignTo.isEmpty()) {
      resetChoreDetails();
      return false;
    } else if (score == 0) {
      return false;
    } else {
      return true;
    }
  }

  private boolean getChoreInformation() {
    choreTitle = binding.choreTitleEditText.getText().toString();
    dueDate = binding.dueDateEditView.getText().toString();
    choreDescription = binding.choreDescriptionEditView.getText().toString();
    assignTo = binding.usernameEditView.getText().toString();
    try {
      score = Integer.parseInt(binding.scoreEditView.getText().toString());
    } catch (NumberFormatException e) {
      Log.d(ChoreLogger.ID, "TEST: Error reading value from scoreEditView");
      return false;
    }
    return isEmpty();
  }

  private void insertChore() {
    LiveData<User> userObserver = repository.getUserByUserName(assignTo);
    userObserver.observe(this, user -> {
      if (user != null) {
        Chore chore = new Chore(user.getUserId(), dueDate, choreTitle, choreDescription, score);
        repository.insertChore(chore);
      } else {
        toastMaker("Assigned to user does not exist");
      }
    });
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  static Intent parentChoreDisplayActivityIntentFactory(Context context, String previousUsername, int previousUserId, String username, int userId, boolean canEdit) {
    Intent intent = new Intent(context, ParentChoreDisplayActivity.class);
    intent.putExtra(PARENT_DISPLAY_PREVIOUS_USERNAME, previousUsername);
    intent.putExtra(PARENT_DISPLAY_PREVIOUS_USER_ID, previousUserId);
    intent.putExtra(PARENT_DISPLAY_USERNAME, username);
    intent.putExtra(PARENT_DISPLAY_USER_ID, userId);
    intent.putExtra(PARENT_DISPLAY_CAN_EDIT, canEdit);
    return intent;
  }
}
