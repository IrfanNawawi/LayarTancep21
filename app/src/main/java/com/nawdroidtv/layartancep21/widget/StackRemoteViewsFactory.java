package com.nawdroidtv.layartancep21.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nawdroidtv.layartancep21.BuildConfig;
import com.nawdroidtv.layartancep21.R;
import com.nawdroidtv.layartancep21.model.Favorite;

import java.util.concurrent.ExecutionException;

import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.CONTENT_URI;

/**
 * Created by Irfan Nawawi on 05/02/2019.
 */

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private Cursor movieList;

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        movieList = context.getContentResolver().query(CONTENT_URI
                , null
                , null
                , null
                , null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Favorite item = getItem(i);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        final String poster = BuildConfig.BASE_URL_IMG + "w500" + item.getPosterPath();
        Bitmap preview = null;
        try {
            preview = Glide.with(context)
                    .asBitmap()
                    .load(poster)
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, preview);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(ImageFavoriteWidget.EXTRA_ITEM, i);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Favorite getItem(int i) {
        if (!movieList.moveToPosition(i)) {
            throw new IllegalStateException("Movie Invalid");
        }
        return new Favorite(movieList);
    }
}
