package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mareyn.group06project02.databinding.ActivityForgotPasswordBinding;


public class ForgotPasswordActivity extends AppCompatActivity {
    private static final int LOGGED_OUT = -1;
    private static final String LOGIN_ACTIVITY_USER_ID = "com.mareyn.group06project02.LOGIN_ACTIVITY_USER_ID";
    private ActivityForgotPasswordBinding binding;
    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
                //this needs to check the filled username/email address against the database
                // to ensure they correlate to an existing user.
                // The user needs to provide either a username or email address or both
                // else toastmaker notifies "please enter a valid username or email address"
                // The reset password field will update the password but will not care if
                // the existing password is entered.

            }
        });

    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();
        String emailAddress = binding.emailAddressEditText.getText().toString();
        String password = binding.resetPasswordEditText.getText().toString();

        if (username.isEmpty() && emailAddress.isEmpty()) {
            toastMaker("Please enter a username or email address");
            return;
        }

        if (password.isEmpty()) {
            toastMaker("Please enter a password");
            return;
        }

        if (username != null || emailAddress != null) {
            //TODO: check the fields against the database to see if the user is valid
            if (username || emailAddress) {
                //TODO: update the existing user password to the one provided
            }

        } else {
            toastMaker("Not a valid user");
            binding.userNameLoginEditText.setSelection(0);
        }

    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                getIntent().putExtra(LOGIN_ACTIVITY_USER_ID, loggedInUserId);
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                return true;
            }
        });

    }



    private void loginPage() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(LOGIN_ACTIVITY_USER_ID, loggedInUserId);

    }


    private void updateSharedPreference(){
        //TODO: Need help with preference keys...are they necessary?
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor= sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent forgotPasswordIntentFactory(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }
}
