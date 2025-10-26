package com.mareyn.group06project02;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.openapitools.client.infrastructure.ApiClient;

import example.client.api.MyServerApi;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    var apiClient = new ApiClient();
    var service = apiClient.createService(MyServerApi.class);
    var response = service.familyNew(10, "hunter123", "Edwin's Family");
    System.out.println(response);
    
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(
      findViewById(R.id.main),
      (v, insets) -> {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
      });
  }
}
