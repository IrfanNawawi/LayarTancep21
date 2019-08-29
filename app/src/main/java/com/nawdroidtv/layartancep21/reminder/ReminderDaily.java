package com.nawdroidtv.layartancep21.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.view.MainActivity;

import java.util.Calendar;

import static com.nawdroidtv.layartancep21.Helper.Konstanta.CHANNEL_DAILY_ID;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.CHANNEL_DAILY_NAME;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.EXTRA_MESSAGE_PREF;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.EXTRA_TYPE_PREF;

/**
 * Created by Irfan Nawawi on 09/02/2019.
 */

public class ReminderDaily extends BroadcastReceiver {

    private final int ID_DAILY = 100;

    public ReminderDaily() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE_PREF);
        String title = "Daily Reminder";
        int notifId = ID_DAILY;

        showReminderNotification(context, title, message, notifId);
    }

    private void showReminderNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_DAILY_ID)
                .setSmallIcon(R.mipmap.ic_movie)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_DAILY_ID, CHANNEL_DAILY_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_DAILY_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public void setReminder(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderDaily.class);
        intent.putExtra(EXTRA_MESSAGE_PREF, message);
        intent.putExtra(EXTRA_TYPE_PREF, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = ID_DAILY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Daily set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderDaily.class);
        int requestCode = ID_DAILY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Daily cancel up", Toast.LENGTH_SHORT).show();
    }
}