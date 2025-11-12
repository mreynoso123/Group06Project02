package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ParentChoreDisplayActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_parent_chore_display);
  }

  static Intent parentChoreDisplayActivityIntentFactory(Context context) {
    return new Intent(context, ParentChoreDisplayActivity.class);
  }
}
