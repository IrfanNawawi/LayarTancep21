package com.nawdroidtv.favoritetancep21.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_DATE;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_HEAD;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_LANG;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_NAME;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_PLOT;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_POST;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_RATI;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.favoriteColumns.MOVIE_VOTE;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.getColumnDouble;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.getColumnInt;
import static com.nawdroidtv.favoritetancep21.db.DatabaseContract.getColumnString;

public class FavoriteItem implements Parcelable {

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("id")
    private int id;

    @SerializedName("vote_count")
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return
                "Movie{" +
                        "overview = '" + overview + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",original_title = '" + originalTitle + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",backdrop_path = '" + backdropPath + '\'' +
                        ",release_date = '" + releaseDate + '\'' +
                        ",vote_average = '" + voteAverage + '\'' +
                        ",id = '" + id + '\'' +
                        ",vote_count = '" + voteCount + '\'' +
                        "}";
    }

    public FavoriteItem() {

    }

    public FavoriteItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.originalLanguage = getColumnString(cursor, MOVIE_LANG);
        this.originalTitle = getColumnString(cursor, MOVIE_NAME);
        this.posterPath = getColumnString(cursor, MOVIE_POST);
        this.backdropPath = getColumnString(cursor, MOVIE_HEAD);
        this.releaseDate = getColumnString(cursor, MOVIE_DATE);
        this.voteAverage = getColumnDouble(cursor, MOVIE_RATI);
        this.overview = getColumnString(cursor, MOVIE_PLOT);
        this.voteCount = getColumnInt(cursor, MOVIE_VOTE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.voteAverage);
        dest.writeInt(this.id);
        dest.writeInt(this.voteCount);
    }

    protected FavoriteItem(Parcel in) {
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.id = in.readInt();
        this.voteCount = in.readInt();
    }

    public static final Creator<FavoriteItem> CREATOR = new Creator<FavoriteItem>() {
        @Override
        public FavoriteItem createFromParcel(Parcel source) {
            return new FavoriteItem(source);
        }

        @Override
        public FavoriteItem[] newArray(int size) {
            return new FavoriteItem[size];
        }
    };
}