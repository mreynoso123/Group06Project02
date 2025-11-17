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

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.databinding.ActivityLandingPageBinding;

public class LandingPageActivity extends AppCompatActivity {
  private static String LANDING_PAGE_ACTIVITY_USER_ID = "landing-page-activity";
  private ActivityLandingPageBinding binding;
  private ChoreScoreRepository repository = ChoreScoreRepository.getRepository(getApplication());
  ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
    EdgeToEdge.enable(this);
    // setContentView(R.layout.activity_landing_page);
    setContentView(binding.getRoot());
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    var username = getIntent().getStringExtra(LANDING_PAGE_ACTIVITY_USER_ID);
    Log.e("USER-ID", "THIS IS THE USER ID: " + username);

    binding.logoutButton.setOnClickListener(view -> {
      Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    });

    repository.getUserByUsername(username, user -> {
      runOnUiThread(() -> {
        if (user == null) {
          return;
        }
        Log.e("TESTING", "Is admin: " + user.isAdmin());

        binding.landingPageTitle.setText("Welcome, " + user.getUsername());
        if (user.isAdmin()) {
          binding.goToAdminPageButton.setVisibility(View.VISIBLE);
          binding.adminStatus.setText("You ARE an admin");
        } else {
          binding.adminStatus.setText("You ARE NOT an admin");
        }

        binding.goToAdminPageButton.setOnClickListener(view -> {
          var intent = AdminControlsActivity.adminControlsActivityIntentFactory(getApplicationContext(), user.getUserId());
          startActivity(intent);
        });
      });
    });
  }

  static Intent landingPageActivityIntentFactory(Context context, String username) {
    Intent intent = new Intent(context, LandingPageActivity.class);
    intent.putExtra(LANDING_PAGE_ACTIVITY_USER_ID, username);
    return intent;
  }
}
