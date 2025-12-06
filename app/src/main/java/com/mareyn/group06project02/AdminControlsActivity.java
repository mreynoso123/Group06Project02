package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mareyn.group06project02.databinding.ActivityAdminControlsBinding;

public class AdminControlsActivity extends AppCompatActivity {
  static private String ADMIN_CONTROLS_USER_ID = "ADMIN_CONTROLS_USER_ID";
  private ActivityAdminControlsBinding binding;

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
      var intent = LoginActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    });
  }

  static Intent adminControlsActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, AdminControlsActivity.class);
    intent.putExtra(ADMIN_CONTROLS_USER_ID, userId);
    return intent;
  }
}
