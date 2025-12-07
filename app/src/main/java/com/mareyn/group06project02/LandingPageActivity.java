package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.User;
import com.mareyn.group06project02.databinding.ActivityLandingPageBinding;
import com.mareyn.group06project02.viewHolders.UserAdapter;
import com.mareyn.group06project02.viewHolders.UserViewModel;

public class LandingPageActivity extends AppCompatActivity {
  private static String LANDING_PAGE_ACTIVITY_USERNAME = "landing-page-activity";
  private static String LANDING_PAGE_ACTIVITY_USER_ID = "landing-page-activity-userId";
  private ActivityLandingPageBinding binding;
  private ChoreScoreRepository repository;
  private User loggedInUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // repository assigned here for intent test purposes
    repository = ChoreScoreRepository.getRepository(getApplication());

    var username = getIntent().getStringExtra(LANDING_PAGE_ACTIVITY_USERNAME);
    int userId = getIntent().getIntExtra(LANDING_PAGE_ACTIVITY_USER_ID, -1);

    Log.e(ChoreLogger.ID, "THIS IS THE USER ID: " + username);

    // binding.goToAdminPageButton.setOnClickListener(view -> {
    //   var intent = AdminControlsActivity.adminControlsActivityIntentFactory(getApplicationContext(), userId);
    //   startActivity(intent);
    // });

    // Initialized recycler view/adapter
    RecyclerView recyclerView = binding.activeFamilyGroupsRecyclerView;
    final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff(), getApplicationContext(), user3 -> {
      if (loggedInUser == null) {
        Log.i(ChoreLogger.ID, "Logged in user not loaded yet");
        return;
      }
      Log.i(ChoreLogger.ID, "CLICKED THIS: " + user3.getUsername());
      if (user3.isAdmin()) {
        var intent = ParentChoreDisplayActivity.parentChoreDisplayActivityIntentFactory(
          getApplicationContext(), loggedInUser.getUsername(), loggedInUser.getUserId(), user3.getUsername(), user3.getUserId(), loggedInUser.isAdmin());
        startActivity(intent);
      } else {
        if (user3.getUsername().equals(loggedInUser.getUsername()) || loggedInUser.isAdmin()) {
          var intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(
            getApplication(),
            loggedInUser.getUsername(),
            loggedInUser.getUserId(),
            user3.getUsername(),
            user3.getUserId(),
            true
          );
          startActivity(intent);
        } else {
          var intent = ChildChoreDisplayActivity.ChildTaskDisplayActivityIntentFactory(
            getApplication(),
            loggedInUser.getUsername(),
            loggedInUser.getUserId(),
            user3.getUsername(),
            user3.getUserId(),
            false
          );
          startActivity(intent);
        }
      }
    });
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    // Loads User info for Landing Page
    repository.getUserByUsername(username, user -> {
      runOnUiThread(() -> {
        if (user == null) {

          binding.landingPageTitle.setText("User not found");
          return;
        } else {
          loggedInUser = user;
          repository.getUsersByGroupId(user.getFamilyId(), users -> {
          });

          Log.e(ChoreLogger.ID, "Is admin: " + user.isAdmin());

          binding.landingPageTitle.setText("Welcome, " + user.getUsername());
          if (!user.getEmail().isEmpty()) {
            binding.emailText.setText("Email: " + user.getEmail());
          } else {
            binding.emailText.setText("Email: " + "N/A");
          }
          repository.getGroupById(user.getFamilyId(), group -> {
            binding.whatFamilyText.setText("You are in GROUP: " + group.getName());
          });
          if (user.isAdmin()) {
            binding.goToAdminPageButton.setVisibility(View.VISIBLE);
            binding.adminStatus.setText("You ARE an admin");
          } else {
            binding.adminStatus.setText("You ARE NOT an admin");
          }

          var usersViewModel = new ViewModelProvider(this).get(UserViewModel.class);
          usersViewModel.getUsersWithGroupId(loggedInUser.getFamilyId()).observe(this, users -> {
            adapter.submitList(users);
          });
        }
      });
    });

    // To Admin Page
    binding.goToAdminPageButton.setOnClickListener(view -> {
      var intent = AdminControlsActivity.adminControlsActivityIntentFactory(getApplicationContext(), loggedInUser.getUsername(), loggedInUser.getUserId());
      startActivity(intent);
    });

    // To login page
    binding.logoutButton.setOnClickListener(view -> {
      Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    });

    // To Fun Page
    binding.landingPageTitle.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        Intent intent = FunActivity.funActivityIntentFactory(getApplicationContext(), username, userId);
        startActivity(intent);
        return false;
      }
    });
  }

  static Intent landingPageActivityIntentFactory(Context context, String username, int userId) {
    Intent intent = new Intent(context, LandingPageActivity.class);
    intent.putExtra(LANDING_PAGE_ACTIVITY_USER_ID, userId);
    intent.putExtra(LANDING_PAGE_ACTIVITY_USERNAME, username);
    return intent;
  }
}
