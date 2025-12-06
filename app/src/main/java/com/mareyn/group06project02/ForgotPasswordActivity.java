package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
  private static final int LOGGED_OUT = -1;
  private static final String LOGIN_ACTIVITY_USER_ID = "com.mareyn.group06project02.LOGIN_ACTIVITY_USER_ID";
  private ActivityForgotPasswordBinding binding;
  private ChoreScoreRepository repository;
  private int loggedInUserId = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    repository = ChoreScoreRepository.getRepository(getApplication());

    binding.submitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        verifyUser();
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
      }
    });
  }

  private void verifyUser() {
    String username = binding.userNameLoginEditText.getText().toString();
    // String emailAddress = binding.emailAddressEditText.getText().toString();
    String newPassword = binding.resetPasswordEditText.getText().toString();

    if (username.isEmpty()) {
      toastMaker("Please enter a username");
      return;
    }
    if (newPassword.isEmpty()) {
      toastMaker("Please enter a password");
      return;
    }

    repository.getUserByUsername(username, user -> {
      runOnUiThread(() -> {
        if (user == null) {
          toastMaker("Invalid username");
          return;
        }

        user.setPassword(newPassword);
        repository.updateUser(user);
        toastMaker(String.format("Password updated for %s", username));
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
      });
    });
  }

  // Helper method for logic testing
  static boolean isUsernameAndEmailEmpty(String username, String email) {
    return username.isEmpty() && email.isEmpty();
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  // onCreateOptionsMenu initializes and inflates the menu resource
  // (defined in an XML file) into the provided Menu object.
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.logout_menu, menu);
    return true;
  }

  // and onPrepareOptionsMenu are to display the "Go back" link to return to the login page
  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuItem item = menu.findItem(R.id.logoutMenuItem);
    item.setVisible(true);
    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(@NonNull MenuItem item) {
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
        return true;
      }
    });
    return true;
  }

  private void loginPage() {
    loggedInUserId = LOGGED_OUT;
    getIntent().putExtra(LOGIN_ACTIVITY_USER_ID, loggedInUserId);
  }

  static Intent forgotPasswordIntentFactory(Context context) {
    return new Intent(context, ForgotPasswordActivity.class);
  }
}
