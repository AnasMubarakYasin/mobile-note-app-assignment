package com.example.mynote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mynote.model.EditNoteViewModel;

import java.util.Collections;
import java.util.Date;

import static com.example.mynote.model.EditNoteViewModel.CONTENT;
import static com.example.mynote.model.EditNoteViewModel.ID;
import static com.example.mynote.model.EditNoteViewModel.REMINDER;
import static com.example.mynote.model.EditNoteViewModel.TITLE;

public class NoteReminderReceiver extends BroadcastReceiver {

    public static final String TAG = "CommonMainReceiver";
    public static final String DIVIDER = String.join("", Collections.nCopies(100, "-"));
    public static final String CHANNEL_ID = "com.example.mynote";
    public static final String name = "note_reminder";
    public static final String desc = "reminder note";
    public static final int REQUEST_NOTIFY = 54;
    public static final int NOTIFICATION_ID = 332;

    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: "+DIVIDER);

        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        channel.setDescription(desc);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.enableVibration(true);

        notificationManagerCompat =  NotificationManagerCompat.from(context);

        Intent contentIntent = new Intent(context, MainActivity.class);

        final int id = intent.getIntExtra(ID, (int) new Date().getTime());
        final String title = intent.getStringExtra(TITLE);
        final String content = intent.getStringExtra(CONTENT);
        final String reminder = intent.getStringExtra(REMINDER);

        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, REQUEST_NOTIFY, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_outline_sticky_note_2_24)
                .setContentTitle("Your note has timeout")
                .setContentText(content)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        Log.d(TAG, "onReceive: notify note id "+ id);
        Log.d(TAG, "onReceive: notify note title "+ title);
        Log.d(TAG, "onReceive: notify note reminder "+ reminder);

        notificationManagerCompat.createNotificationChannel(channel);
        notificationManagerCompat.notify(id, builder.build());
    }
}