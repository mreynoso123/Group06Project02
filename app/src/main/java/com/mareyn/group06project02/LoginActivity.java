package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.Group;
import com.mareyn.group06project02.database.entities.User;
import com.mareyn.group06project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Create initial test data.
    {
      ChoreScoreRepository repository = ChoreScoreRepository.getRepository(getApplication());
      Log.e("Initializing database", repository.toString());

      var testGroup1 = new Group();
      testGroup1.setName("test-group");
      repository.insertGroup(testGroup1);

      var testUser1 = new User(testGroup1.getGroupId(), "parent1", "password", 1, "");
      var testUser2 = new User(testGroup1.getGroupId(), "child1", "password", 0, "");
      var testUser3 = new User(testGroup1.getGroupId(), "child2", "password", 0, "");
      repository.insertUser(testUser1, testUser2, testUser3);

      var testGroup2 = new Group();
      testGroup2.setName("test-group2");
      repository.insertGroup(testGroup2);
      var testUser7 = new User(testGroup2.getGroupId(), "parent2", "password", 1, "");
      var testUser8 = new User(testGroup2.getGroupId(), "child7", "password", 0, "");
      repository.insertUser(testUser7, testUser8);

      // Required for the demo video.
      var testUser9 = new User(testGroup1.getGroupId(), "testuser1", "testuser1", 0, "");
      var testUser10 = new User(testGroup1.getGroupId(), "admin2", "admin2", 1, "");
      repository.insertUser(testUser9, testUser10);
    }

    // this is for the login button
    binding.loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Use this one when the database is implemented
        // startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
        // Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
        var intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), binding.userNameLoginEditText.getText().toString());
        startActivity(intent);
      }
    });

    // this is for the create account text
    binding.createAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      }
    });

    // this is for the forgot password which should open up ForgotPasswordActivity.java
    binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        // getIntent().putExtra();
        startActivity(ForgotPasswordActivity.forgotPasswordIntentFactory(getApplicationContext()));
      }
    });
  }

  private void verifyUser() {
    String username = binding.userNameLoginEditText.getText().toString();

    if (username.isEmpty()) {
      toastMaker("Username should not be blank");
      return;
    }

    // below is used for the GymLog database
    // LiveData<User> userObserver = repository.getUserByUserName(username);
    // userObserver.observe(this, user -> {
    //   if (user != null) {
    //     String password = binding.passwordLoginEditText.getText().toString();
    //     if (password.equals(user.getPassword())) {
    //       startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
    //     } else {
    //       toastMaker("Invalid password");
    //       binding.passwordLoginEditText.setSelection(0);
    //     }
    //   } else {
    //     toastMaker(String.format("%s is not a valid username. ", username));
    //     binding.userNameLoginEditText.setSelection(0);
    //   }
    // });

  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  static Intent loginIntentFactory(Context context) {
    return new Intent(context, LoginActivity.class);
  }
}
