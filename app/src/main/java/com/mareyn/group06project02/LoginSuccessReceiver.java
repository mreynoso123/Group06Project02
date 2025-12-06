package com.mareyn.group06project02;

import static com.mareyn.group06project02.LoginActivity.ACTION_LOGIN_SUCCESS;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class LoginSuccessReceiver extends BroadcastReceiver {

  private static final String CHANNEL_ID = "login_success_channel";

  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    if (ACTION_LOGIN_SUCCESS.equals(action)) {
      String message = intent.getStringExtra("message");
      android.util.Log.d("LoginSuccessReceiver", "onReceive: message = " + message);

      createNotificationChannel(context);
      NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("ChoreScore")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

      Notification notification = builder.build();
      NotificationManagerCompat manager = NotificationManagerCompat.from(context);
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
        != PackageManager.PERMISSION_GRANTED) {
        // Permission not granted – don't try to show the notification
        return;
      }
      manager.notify(1, notification);
    }
  }

  private void createNotificationChannel(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "Login Notifications";
      String description = context.getString(R.string.channel_description);
      int importance = NotificationManager.IMPORTANCE_DEFAULT;

      NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
      channel.setDescription(description);

      NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannel(channel);
    }
  }
}
