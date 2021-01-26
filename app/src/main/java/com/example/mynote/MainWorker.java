package com.example.mynote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mynote.model.EditNoteViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class MainWorker extends Worker {

    public static final String TAG = "MainWorker";
    public static final String DIVIDER = String.join("", Collections.nCopies(100, "-"));
    public static final String CHANNEL_ID = "com.example.mynote";
    public static final String name = "note_reminder";
    public static final String desc = "reminder note";
    public static final int REQUEST_NOTIFY = 54;
    public static final int NOTIFICATION_ID = 332;

    private NotificationManagerCompat notificationManagerCompat;

    public MainWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();

        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        channel.setDescription(desc);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.enableVibration(true);

        notificationManagerCompat =  NotificationManagerCompat.from(context);

        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_outline_sticky_note_2_24)
                .setContentTitle("Your note has timeout")
                .setContentText("Something")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManagerCompat.createNotificationChannel(channel);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        return Result.success();
    }
}
