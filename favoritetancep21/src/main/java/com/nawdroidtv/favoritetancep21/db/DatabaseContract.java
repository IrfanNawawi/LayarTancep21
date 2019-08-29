package com.nawdroidtv.favoritetancep21.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Irfan Nawawi on 30/01/2019.
 */

public class DatabaseContract {
    public static final String AUTHORITY = "com.nawdroidtv.layartancep21";
    public static String TABLE_FAVORITE = "favorite";
    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static Double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static final class favoriteColumns implements BaseColumns {
        public static final String MOVIE_NAME = "movieName";
        public static final String MOVIE_VOTE = "movieVote";
        public static final String MOVIE_LANG = "movieLang";
        public static final String MOVIE_PLOT = "moviePlot";
        public static final String MOVIE_RATI = "movieRati";
        public static final String MOVIE_DATE = "movieDate";
        public static final String MOVIE_HEAD = "movieHead";
        public static final String MOVIE_POST = "moviePost";
    }
}
