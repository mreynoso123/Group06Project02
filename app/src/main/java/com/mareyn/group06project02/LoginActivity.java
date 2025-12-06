package com.mareyn.group06project02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Group;
import com.mareyn.group06project02.database.entities.User;
import com.mareyn.group06project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;
  private ChoreScoreRepository repository;
  private EditText hiddenEditText;
  private static boolean initializeDataBase = false;
  @SuppressLint("UseSwitchCompatOrMaterialCode")
  private Switch hiddenSwitch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    EdgeToEdge.enable(this);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    repository = ChoreScoreRepository.getRepository(getApplication());

    // Create initial test data.
    // Initialize demo data once per app run. Password changes persist forever
    Log.e("Initializing database...", repository.toString());
    repository.deleteAllUsers();
    repository.deleteAllGroups();
    repository.deleteAllChores();
    var testGroup1 = new Group("test-group");
    repository.insertGroupIfNotExists(testGroup1);

    var testUser1 = new User(testGroup1.getGroupId(), "parent1", "password", "", true);
    var testUser2 = new User(testGroup1.getGroupId(), "child1", "password", "", false);
    var testUser3 = new User(testGroup1.getGroupId(), "child2", "password", "", false);
    repository.insertUserIfNotExists(testUser1);
    repository.insertUserIfNotExists(testUser2);
    repository.insertUserIfNotExists(testUser3);

    var testGroup2 = new Group("test-group2");
    repository.insertGroupIfNotExists(testGroup2);
    var testUser7 = new User(testGroup2.getGroupId(), "parent2", "password", "", true);
    var testUser8 = new User(testGroup2.getGroupId(), "child7", "password", "", false);
    repository.insertUserIfNotExists(testUser7);
    repository.insertUserIfNotExists(testUser8);

    // Required for the demo video.
    var testUser9 = new User(testGroup1.getGroupId(), "testuser1", "testuser1", "", false);
    var testUser10 = new User(testGroup1.getGroupId(), "admin2", "admin2", "", true);
    repository.insertUserIfNotExists(testUser9);
    repository.insertUserIfNotExists(testUser10);

    // For testing.
    // repository.getUserByUserName("testuser1").observe(this, user -> {
    //   startActivity(LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), user.getUsername(), user.getUserId()));
    // });

    hiddenEditText = findViewById(R.id.hiddenEmailAddressEditText);
    hiddenSwitch = findViewById(R.id.adminSwitch);

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
        } else {
          hiddenEditText.setVisibility(View.GONE);
          hiddenSwitch.setVisibility(View.GONE);
        }
      }
    });

    // this is for the forgot password which should open up ForgotPasswordActivity.java
    binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = ForgotPasswordActivity.forgotPasswordIntentFactory(getApplicationContext());
        // getIntent().putExtra();
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
