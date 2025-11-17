package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.databinding.ActivityParentChoreDisplayBinding;
import com.mareyn.group06project02.viewHolders.ChoreAdapter;
import com.mareyn.group06project02.viewHolders.ChoreViewModel;

public class ParentChoreDisplayActivity extends AppCompatActivity {

  ActivityParentChoreDisplayBinding binding;
  // need next line of code?
  private ChoreScoreRepository repository;
  private ChoreViewModel choreViewModel;

  // need the following info?
  private int loggedInUserId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parent_chore_display);

    choreViewModel = new ViewModelProvider(this).get(ChoreViewModel.class);

    // The following instantiates the recycler view
    RecyclerView recyclerView = binding.activeChoresRecyclerView;
    final ChoreAdapter adapter = new ChoreAdapter(new ChoreAdapter.ChoreDiff());
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    repository = ChoreScoreRepository.getRepository(getApplication());

    choreViewModel.getAllChoresByUserId(loggedInUserId).observe(this, chores -> {
      adapter.submitList(chores);
    });
  }

  static Intent parentChoreDisplayActivityIntentFactory(Context context) {
    return new Intent(context, ParentChoreDisplayActivity.class);
  }
}
