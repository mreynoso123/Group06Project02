package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mareyn.group06project02.AgifyAPI.Agify;
import com.mareyn.group06project02.AgifyAPI.AgifyApiClient;
import com.mareyn.group06project02.AgifyAPI.AgifyApiService;
import com.mareyn.group06project02.databinding.ActivityFunBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FunActivity extends AppCompatActivity {

  private static final String FUN_USER_ID = "com.mareyn.group06project02.FUN_USER_ID";
  private static final String FUN_USERNAME = "com.mareyn.group06project02.FUN_USERNAME";
  private ActivityFunBinding binding;
  private int loggedInUserId;
  private String username;
  private String name;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityFunBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    // Get UserId
    loggedInUserId = getIntent().getIntExtra(FUN_USER_ID, -1);
    // Get Username
    username = getIntent().getStringExtra(FUN_USERNAME);

    binding.backButton.setOnClickListener(view -> {
      Intent intent = LandingPageActivity.landingPageActivityIntentFactory(getApplicationContext(), username, loggedInUserId);
      startActivity(intent);
    });

    binding.EstimateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        name = binding.nameEditText.getText().toString();
        if (name.isEmpty()) {
          toastMaker("Empty Name");
        } else {
          AgifyApiService apiService = AgifyApiClient.getAgifyAPiService();
          Call<Agify> call = apiService.getAgeFromName(name);
          call.enqueue(new Callback<Agify>() {
            @Override
            public void onResponse(Call<Agify> call, Response<Agify> response) {
              if (response.isSuccessful() && response.body() != null) {
                Agify agifyAge = response.body();
                if (agifyAge != null && agifyAge.age != null) {
                  binding.ageTextView.setText(agifyAge.age.toString());
                } else {
                  toastMaker("Unrecognizable Name");
                }
              }
            }

            @Override
            public void onFailure(Call<Agify> call, Throwable throwable) {
              toastMaker("AgifyAPI Error");
            }
          });
        }
      }
    });
  }

  private void toastMaker(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

  static Intent funActivityIntentFactory(Context context, String username, int userId) {
    Intent intent = new Intent(context, FunActivity.class);
    intent.putExtra(FUN_USER_ID, userId);
    intent.putExtra(FUN_USERNAME, username);
    return intent;
  }
}
