package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mareyn.group06project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // this is for the login button
    binding.loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Use this one when the database is implemented
        // startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
        Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), 0);
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
