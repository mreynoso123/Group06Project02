package com.mareyn.group06project02;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Group;
import com.mareyn.group06project02.database.entities.User;
import com.mareyn.group06project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  public static final String ACTION_LOGIN_SUCCESS = "com.mareyn.group06project02.ACTION_LOGIN_SUCCESS";
  private static final int REQUEST_POST_NOTIFICATIONS = 1001;

  private ActivityLoginBinding binding;
  private ChoreScoreRepository repository;
  private EditText hiddenEditText;
  private Button hiddenCreateButton;
  private Button hideLoginButton;
  private static boolean initializeDataBase = false;
  @SuppressLint("UseSwitchCompatOrMaterialCode")
  private Switch hiddenSwitch;
  private int defaultGroupId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.POST_NOTIFICATIONS
      ) != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(
          this,
          new String[]{Manifest.permission.POST_NOTIFICATIONS},
          REQUEST_POST_NOTIFICATIONS
        );
      }
    }

    repository = ChoreScoreRepository.getRepository(getApplication());

    // Create initial test data.
    // Initialize demo data once per app run. Password changes persist until the app is restarted.
    if (!initializeDataBase) {
      repository.deleteAllUsers();
      Log.e("Initializing database", repository.toString());

      var testGroup1 = new Group();
      testGroup1.setName("test-group");
      repository.insertGroup(testGroup1);
      defaultGroupId = testGroup1.getGroupId();
      var testUser1 = new User(testGroup1.getGroupId(), "parent1", "password", "", true);
      var testUser2 = new User(testGroup1.getGroupId(), "child1", "password", "", false);
      var testUser3 = new User(testGroup1.getGroupId(), "child2", "password", "", false);
      repository.insertUser(testUser1, testUser2, testUser3);

      var testGroup2 = new Group();
      testGroup2.setName("test-group2");
      repository.insertGroup(testGroup2);
      var testUser7 = new User(testGroup2.getGroupId(), "parent2", "password", "", true);
      var testUser8 = new User(testGroup2.getGroupId(), "child7", "password", "", false);
      repository.insertUser(testUser7, testUser8);

      // Required for the demo video.
      var testUser9 = new User(testGroup1.getGroupId(), "testuser1", "testuser1", "", false);
      var testUser10 = new User(testGroup1.getGroupId(), "admin2", "admin2", "", true);
      repository.insertUser(testUser9, testUser10);

      initializeDataBase = true;
    }

    hiddenEditText = findViewById(R.id.hiddenEmailAddressEditText);
    hiddenSwitch = findViewById(R.id.adminSwitch);
    hiddenCreateButton = findViewById(R.id.createButton);
    hideLoginButton = findViewById(R.id.loginButton);

    // this is for the login button
    binding.loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        verifyUser();
      }
    });

    // this is for the create account text
    binding.createAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (hiddenEditText.getVisibility() == View.GONE && hiddenSwitch.getVisibility() == View.GONE) {
          hiddenEditText.setVisibility(View.VISIBLE);
          hiddenSwitch.setVisibility(View.VISIBLE);
          hiddenCreateButton.setVisibility((View.VISIBLE));
          hideLoginButton.setVisibility(View.GONE);

          binding.createButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              String newUsername = binding.userNameLoginEditText.getText().toString();
              String newPassword = binding.passwordLoginEditText.getText().toString();
              String newEmailAddress = binding.hiddenEmailAddressEditText.getText().toString();
              boolean isAdmin = binding.adminSwitch.isChecked();

              if (newUsername.isBlank() || newPassword.isBlank() || newEmailAddress.isBlank()) {
                toastMaker("Please fill in all fields");
                return true;
              }

              repository.getUserByUsername(newUsername, user -> {
                runOnUiThread(() -> {
                  if (user != null) {
                    toastMaker("Username already exists");
                    return;
                  }

                  var newUser = new User(defaultGroupId, newUsername, newPassword, newEmailAddress, isAdmin);
                  repository.insertUser(newUser);
                  toastMaker("Account created for " + newUsername);
                  Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                  startActivity(intent);
                });
              });

              Log.e("NEW USER", "Creating a new user account");
              return true;
            }
          });
        } else {
          hiddenEditText.setVisibility(View.GONE);
          hiddenSwitch.setVisibility(View.GONE);
          hiddenCreateButton.setVisibility(View.GONE);
          hideLoginButton.setVisibility(View.VISIBLE);
        }
      }
    });

    // this is for the forgot password which should open up ForgotPasswordActivity.java
    binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = ForgotPasswordActivity.forgotPasswordIntentFactory(getApplicationContext());
        startActivity(intent);
      }
    });
  }

  private void verifyUser() {
    String username = binding.userNameLoginEditText.getText().toString();

    if (username.isEmpty()) {
      toastMaker("Username should not be blank");
    }

    LiveData<User> userObserver = repository.getUserByUserName(username);
    userObserver.observe(this, user -> {
      if (user != null) {
        String password = binding.passwordLoginEditText.getText().toString();
        if (password.equals(user.getPassword())) {

          // this is for the push notification
          Intent broadcastIntent = new Intent(getApplicationContext(), LoginSuccessReceiver.class);
          broadcastIntent.setAction(ACTION_LOGIN_SUCCESS);
          broadcastIntent.putExtra("message", "Welcome " + username);
          sendBroadcast(broadcastIntent);

          var intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), username, user.getUserId());
          startActivity(intent);
        } else {
          toastMaker("Invalid password");
          binding.passwordLoginEditText.setSelection(0);
        }
      } else {
        toastMaker(String.format("%s is not a valid username. ", username));
        binding.userNameLoginEditText.setSelection(0);
      }
    });
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  static Intent loginIntentFactory(Context context) {
    return new Intent(context, LoginActivity.class);
  }
}
