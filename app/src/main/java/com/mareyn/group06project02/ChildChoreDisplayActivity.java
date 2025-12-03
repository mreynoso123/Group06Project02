package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.databinding.ActivityChildTaskDisplayBinding;
import com.mareyn.group06project02.viewHolders.ChoreAdapter;
import com.mareyn.group06project02.viewHolders.ChoreViewModel;

public class ChildChoreDisplayActivity extends AppCompatActivity {
  private static final String CHILD_DISPLAY_USER_ID = "com.mareyn.group06project02.CHILD_DISPLAY_USER_ID";
  private static final String CHILD_DISPLAY_USERNAME = "com.mareyn.group06project02.CHILD_DISPLAY_USERNAME";

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

    // Get UserId
    loggedInUserId = getIntent().getIntExtra(CHILD_DISPLAY_USER_ID, -1);

    // Get Username
    username = getIntent().getStringExtra(CHILD_DISPLAY_USERNAME);

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

    // // Completed Chores Recycler View
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
      Log.d("TEST", "Error reading value from scoreEditView");
      return false;
    }
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  static Intent ChildTaskDisplayActivityIntentFactory(Context context, String username, int userId) {
    Intent intent = new Intent(context, ChildChoreDisplayActivity.class);
    intent.putExtra(CHILD_DISPLAY_USERNAME, username);
    intent.putExtra(CHILD_DISPLAY_USER_ID, userId);
    return intent;
  }
}
