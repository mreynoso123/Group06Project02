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
import com.mareyn.group06project02.databinding.ActivityChildTaskDisplayBinding;
import com.mareyn.group06project02.viewHolders.ChoreAdapter;
import com.mareyn.group06project02.viewHolders.ChoreViewModel;

public class ChildChoreDisplayActivity extends AppCompatActivity {
  private static final String CHILD_DISPLAY_PREVIOUS_USER_ID = "com.mareyn.group06project02.CHILD_DISPLAY_PREVIOUS_USER_ID";
  private static final String CHILD_DISPLAY_PREVIOUS_USERNAME = "com.mareyn.group06project02.CHILD_DISPLAY_PREVIOUS_USERNAME";
  private static final String CHILD_DISPLAY_USER_ID = "com.mareyn.group06project02.CHILD_DISPLAY_USER_ID";
  private static final String CHILD_DISPLAY_USERNAME = "com.mareyn.group06project02.CHILD_DISPLAY_USERNAME";
  private static final String CHILD_DISPLAY_CAN_EDIT = "com.mareyn.group06project02.CHILD_DISPLAY_CAN_EDIT";

  private ActivityChildTaskDisplayBinding binding;
  private ChoreScoreRepository repository;
  private ChoreViewModel choreViewModel, choreViewModelCompletedChores;
  private int loggedInUserId;
  private String username;
  private int choreId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityChildTaskDisplayBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Get UserId
    loggedInUserId = getIntent().getIntExtra(CHILD_DISPLAY_USER_ID, -1);

    // Get Username
    username = getIntent().getStringExtra(CHILD_DISPLAY_USERNAME);
    binding.usernameText.setText(username);

    // Active Chores Recycler View
    choreViewModel = new ViewModelProvider(this).get(ChoreViewModel.class);

    // The following instantiates the recycler view
    RecyclerView recyclerView = binding.childActiveChoresRecyclerView;
    final ChoreAdapter adapter = new ChoreAdapter(new ChoreAdapter.ChoreDiff());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    repository = ChoreScoreRepository.getRepository(getApplication());
    setTotalScore();

    choreViewModel.getActiveChoresByUserId(loggedInUserId).observe(this, chores -> {
      adapter.submitList(chores);
    });

    // Completed Chores Recycler View
    choreViewModelCompletedChores = new ViewModelProvider(this).get(ChoreViewModel.class);

    RecyclerView recyclerView2 = binding.childCompletedChoresRecyclerView;
    final ChoreAdapter adapter2 = new ChoreAdapter(new ChoreAdapter.ChoreDiff());
    recyclerView2.setAdapter(adapter2);
    recyclerView2.setLayoutManager(new LinearLayoutManager(this));
    choreViewModelCompletedChores.getCompletedChoresByUserId(loggedInUserId).observe(this, chores -> {
      adapter2.submitList(chores);
    });

    binding.choreCompletionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!getChoreId()) {
          toastMaker("Invalid Chore ID");
        } else {
          repository.updateChoreStatus(choreId);
          setTotalScore();
        }
      }
    });

    binding.goBackButton.setOnClickListener(view -> {
      var user = getIntent().getStringExtra(CHILD_DISPLAY_PREVIOUS_USERNAME);
      var id = getIntent().getIntExtra(CHILD_DISPLAY_PREVIOUS_USER_ID, -1);
      Intent intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), user, id);
      startActivity(intent);
    });

    // If we only want to view chores of a user
    if (!getIntent().getBooleanExtra(CHILD_DISPLAY_CAN_EDIT, false)) {
      binding.choreIdEditText.setVisibility(View.GONE);
      binding.choreCompletionButton.setVisibility(View.GONE);
    }
  }

  // Sets total score based on all completed chores respective to logged in user
  public void setTotalScore() {
    LiveData<Integer> total = repository.getTotalScoreCompletedChoresById(loggedInUserId);
    total.observe(this, totScore -> {
      int value = (totScore != null) ? totScore : 0;
      binding.totalScoreTextView.setText(String.valueOf(value));
    });
  }

  // Receives value in EditText
  // Returns true if choreId exists in database and stores value in class variable
  public boolean getChoreId() {
    try {
      choreId = Integer.parseInt(binding.choreIdEditText.getText().toString());
      return true;
    } catch (NumberFormatException e) {
      Log.d(ChoreLogger.ID, "Error reading value from scoreEditView");
      return false;
    }
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  public static Intent ChildTaskDisplayActivityIntentFactory(Context context, String previousUsername, int previousUserId, String username, int userId, boolean canEdit) {
    Intent intent = new Intent(context, ChildChoreDisplayActivity.class);
    intent.putExtra(CHILD_DISPLAY_PREVIOUS_USERNAME, previousUsername);
    intent.putExtra(CHILD_DISPLAY_PREVIOUS_USER_ID, previousUserId);
    intent.putExtra(CHILD_DISPLAY_USERNAME, username);
    intent.putExtra(CHILD_DISPLAY_USER_ID, userId);
    intent.putExtra(CHILD_DISPLAY_CAN_EDIT, canEdit);
    return intent;
  }
}
