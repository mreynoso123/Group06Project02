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
import com.mareyn.group06project02.databinding.ActivityAdminControlsBinding;
import com.mareyn.group06project02.viewHolders.ListUserDeleteAdapter;
import com.mareyn.group06project02.viewHolders.ListUserDeleteViewModel;

public class AdminControlsActivity extends AppCompatActivity {
  static private String ADMIN_CONTROLS_USER_ID = "ADMIN_CONTROLS_USER_ID";
  static private String ADMIN_CONTROLS_USERNAME = "ADMIN_CONTROLS_USERNAME";
  private ActivityAdminControlsBinding binding;
  private ChoreScoreRepository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminControlsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    repository = ChoreScoreRepository.getRepository(getApplication());
    var userId = getIntent().getIntExtra(ADMIN_CONTROLS_USER_ID, -1);
    var username = getIntent().getStringExtra(ADMIN_CONTROLS_USERNAME);

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // Back to Landing Page
    binding.backToLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        var intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), username, userId);
        startActivity(intent);
      }
    });

    var usersDeleteViewModel = new ViewModelProvider(this).get(ListUserDeleteViewModel.class);

    RecyclerView recyclerView = binding.adminControlsRecyclerView;
    final ListUserDeleteAdapter adapter = new ListUserDeleteAdapter(new ListUserDeleteAdapter.UserDiff(), getApplicationContext(), user -> {
      Log.e(ChoreLogger.ID, "clicked!");
      repository.deleteUserByUsername(user.getUsername());
    });
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    repository.getUserById2(userId).observe(this, (user) -> {
      repository.getNormalUsersByGroup(user.getFamilyId()).observe(this, users -> {
        adapter.submitList(users);
      });
    });
  }

  static Intent adminControlsActivityIntentFactory(Context context, String username, int userId) {
    Intent intent = new Intent(context, AdminControlsActivity.class);
    intent.putExtra(ADMIN_CONTROLS_USER_ID, userId);
    intent.putExtra(ADMIN_CONTROLS_USERNAME, username);
    return intent;
  }
}
