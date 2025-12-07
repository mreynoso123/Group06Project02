package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
  private ActivityAdminControlsBinding binding;
  private ChoreScoreRepository repository = ChoreScoreRepository.getRepository(getApplication());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityAdminControlsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    binding.backToLogin.setOnClickListener((view) -> {
      var userId = getIntent().getIntExtra(ADMIN_CONTROLS_USER_ID, -1);
      repository.getUserById2(userId).observe(this, user -> {
        var intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), user.getUsername(), user.getUserId());
        startActivity(intent);
      });
    });

    var usersDeleteViewModel = new ViewModelProvider(this).get(ListUserDeleteViewModel.class);

    RecyclerView recyclerView = binding.adminControlsRecyclerView;
    final ListUserDeleteAdapter adapter = new ListUserDeleteAdapter(new ListUserDeleteAdapter.UserDiff(), getApplicationContext(), user -> {
      Log.e(ChoreLogger.ID, "clicked!");
      repository.deleteUserByUsername(user.getUsername());
    });
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    var userId = getIntent().getIntExtra(ADMIN_CONTROLS_USER_ID, -1);
    repository.getUserById2(userId).observe(this, (user) -> {
      repository.getNormalUsersByGroup(user.getFamilyId()).observe(this, users -> {
        adapter.submitList(users);
      });
    });
  }

  static Intent adminControlsActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, AdminControlsActivity.class);
    intent.putExtra(ADMIN_CONTROLS_USER_ID, userId);
    return intent;
  }
}
