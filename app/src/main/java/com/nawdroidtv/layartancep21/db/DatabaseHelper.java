package com.nawdroidtv.layartancep21.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.TABLE_FAVORITE;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_DATE;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_HEAD;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_LANG;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_NAME;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_PLOT;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_POST;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_RATI;
import static com.nawdroidtv.layartancep21.db.DatabaseContract.favoriteColumns.MOVIE_VOTE;

/**
 * Created by Irfan Nawawi on 30/01/2019.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE,
            _ID, MOVIE_NAME, MOVIE_VOTE, MOVIE_LANG, MOVIE_PLOT,
            MOVIE_RATI, MOVIE_DATE, MOVIE_HEAD, MOVIE_POST
    );
    public static String DATABASE_NAME = "moviedb";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(sqLiteDatabase);
    }


}
