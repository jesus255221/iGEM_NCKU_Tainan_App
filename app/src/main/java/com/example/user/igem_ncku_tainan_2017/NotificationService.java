package com.example.user.igem_ncku_tainan_2017;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(5000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        showText();
    }

    private void showText() {
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Oops!")
                .setContentText("WTF...")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1234, notification);
    }
}
