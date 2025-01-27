package com.nawdroidtv.layartancep21.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.view.DetailActivity;

import static android.provider.BaseColumns._ID;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.CONTENT_URI;

/**
 * Implementation of App Widget functionality.
 */
public class ImageFavoriteWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "com.nawdroidtv.layartancep21.widget.EXTRA_ITEM";
    private static final String TOAST_ACTION = "com.nawdroidtv.layartancep21.widget.TOAST_ACTION";
//    public static final String UPDATE_ACTION = "com.nawdroidtv.layartancep21.widget.UPDATE_ACTION";

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_favorite_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, ImageFavoriteWidget.class);
        toastIntent.setAction(ImageFavoriteWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(TOAST_ACTION)) {
                Intent toast = new Intent(context, DetailActivity.class);
                Uri uri = Uri.parse(CONTENT_URI + "/" + intent.getStringExtra(_ID));
                toast.setData(uri);
                context.startActivity(toast);
            }
        }
    }
}