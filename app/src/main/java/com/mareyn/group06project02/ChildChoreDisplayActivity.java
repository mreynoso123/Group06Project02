package com.mareyn.group06project02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChildChoreDisplayActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_child_task_display);
  }

  static Intent ChildTaskDisplayActivityIntentFactory(Context context) {
    return new Intent(context, ChildChoreDisplayActivity.class);
  }
}
