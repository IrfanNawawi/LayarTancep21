package com.nawdroidtv.layartancep21.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.nawdroidtv.layartancep21.model.Favorite;

import java.util.ArrayList;

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

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<Favorite> query() {
        ArrayList<Favorite> list = new ArrayList<Favorite>();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC", null);
        cursor.moveToFirst();
        Favorite favorite;
        if (cursor.getCount() > 0) {
            do {
                favorite = new Favorite();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_NAME)));
                favorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_PLOT)));
                favorite.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_DATE)));
                favorite.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_HEAD)));
                favorite.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_LANG)));
                favorite.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_POST)));
                favorite.setVoteAverage(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_RATI)));
                favorite.setVoteCount(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_VOTE)));

                list.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
