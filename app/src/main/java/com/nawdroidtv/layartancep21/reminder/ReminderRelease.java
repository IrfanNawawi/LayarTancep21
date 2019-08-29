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

import com.nawdroidtv.layartancep21.BuildConfig;
import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.api.ApiInterface;
import com.nawdroidtv.layartancep21.api.ApiService;
import com.nawdroidtv.layartancep21.model.Movie;
import com.nawdroidtv.layartancep21.model.MovieResponse;
import com.nawdroidtv.layartancep21.view.DetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nawdroidtv.layartancep21.Helper.Konstanta.CHANNEL_RELEASE_ID;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.CHANNEL_RELEASE_NAME;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.EXTRA_MESSAGE_PREF;
import static com.nawdroidtv.layartancep21.Helper.Konstanta.EXTRA_TYPE_PREF;

/**
 * Created by Irfan Nawawi on 10/02/2019.
 */

public class ReminderRelease extends BroadcastReceiver {
    private final int ID_RELEASE = 101;
    ArrayList<Movie> movies = new ArrayList<>();

    public ReminderRelease() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getNowPlayingMovie(context);
    }

    private void showNotification(Context context, String title, String message, int notifId, Movie item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("movie_header", item.getBackdropPath());
        intent.putExtra("movie_thumbnail", item.getPosterPath());
        intent.putExtra("movie_title", item.getOriginalTitle());
        intent.putExtra("movie_overview", item.getOverview());
        intent.putExtra("movie_rating", Double.toString(item.getVoteAverage()));
        intent.putExtra("movie_date", item.getReleaseDate());
        intent.putExtra("movie_vote", Integer.toString(item.getVoteCount()));
        intent.putExtra("movie_language", item.getOriginalLanguage());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_RELEASE_ID)
                .setSmallIcon(R.mipmap.ic_movie)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_RELEASE_ID, CHANNEL_RELEASE_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_RELEASE_ID);
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
        Intent intent = new Intent(context, ReminderRelease.class);
        intent.putExtra(EXTRA_MESSAGE_PREF, message);
        intent.putExtra(EXTRA_TYPE_PREF, type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.SECOND, 0);

        int requestCode = ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "Release set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderRelease.class);
        int requestCode = ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Release cancel up", Toast.LENGTH_SHORT).show();
    }

    private void getNowPlayingMovie(final Context context) {
        Date calendar = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToday = dateFormat.format(calendar);
        ApiService Service = ApiInterface.getRetrofit().create(ApiService.class);
        Call<MovieResponse> call = Service.getUpcomingMovie(BuildConfig.TMDB_API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    movies = response.body().getResults();
                    for (Movie movie : movies) {
                        int notiId = 110;
                        String date = movie.getReleaseDate();
                        String title = movie.getOriginalTitle();
                        String message = movie.getOverview();
                        if (date.equalsIgnoreCase(dateToday)) {
                            movies.add(movie);
                        }
                        showNotification(context,title,message,notiId,movie);
                    }
                }
//                int index = new Random().nextInt(movies.size());
//                Movie movie = movies.get(index);
//                int notiId = 110;
//
//                String title = movies.get(index).getOriginalTitle();
//                String message = movies.get(index).getOverview();
//                showNotification(context, title, message, notiId, movie);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(context, "Error Fetching Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
