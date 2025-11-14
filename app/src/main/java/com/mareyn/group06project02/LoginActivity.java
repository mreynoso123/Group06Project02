package com.mareyn.group06project02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.mareyn.group06project02.database.ChoreScoreRepository;
import com.mareyn.group06project02.database.entities.User;
import com.mareyn.group06project02.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;
  private ChoreScoreRepository repository;
  private EditText hiddenEditText;
  @SuppressLint("UseSwitchCompatOrMaterialCode")
  private Switch hiddenSwitch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    repository = ChoreScoreRepository.getRepository(getApplication());

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
          startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserId()));
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
