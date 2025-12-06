package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mareyn.group06project02.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
  public static final String TAG = "CHORE_SCORE";

  private ActivityMainBinding binding;
  // MAIN_ACTIVITY_USER_ID is a key to retrieve a value to be a user id
  private static final String MAIN_ACTIVITY_USER_ID = "com.mareyn.group06project02.MAIN_ACTIVITY_USER_ID";
  private static final int LOGGED_OUT = -1;
  private int loggedInUserId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    // var apiClient = new ApiClient();
    // var service = apiClient.createService(MyServerApi.class);
    // var response = service.newFamily("edwin-family", "hunter123", "Edwin's Family");
    // System.out.println(response);

    loginUser();
    if (loggedInUserId == -1) {
      Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
      startActivity(intent);
    }
  }

  private void loginUser() {
    /// /TODO: create login method.
    loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
  }

  static Intent mainActivityIntentFactory(Context context, int userId) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
    return intent;
  }
}
