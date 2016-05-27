package com.moviesratings.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.moviesratings.Model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aris on 25/05/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "FavouriteMovies";

    // Movie table name
    private static final String TABLE_MOVIES = "Movies";

    // Movie Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_MPA_RATING = "mpa_rating";
    private static final String KEY_HEADLINE = "headline";
    private static final String KEY_CRITICS_PICK = "critics_pick";
    private static final String KEY_DATE_UPDATED = "date_updated";
    private static final String KEY_FOLLOW = "follow";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT, " + KEY_MPA_RATING + " TEXT, " + KEY_HEADLINE +" TEXT, " + KEY_CRITICS_PICK + " TEXT, " + KEY_DATE_UPDATED +" TEXT, " + KEY_FOLLOW + " TEXT, " +
                KEY_DESCRIPTION + " TEXT " + ")";
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);

        // Create tables again
        onCreate(db);
    }

    /**
            * All CRUD(Create, Read, Update, Delete) Operations
    */

    // Adding new Movie
    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getDisplay_title()); // Movie title
        values.put(KEY_DESCRIPTION, movie.getSummary()); // Movie description
        values.put(KEY_MPA_RATING, movie.getRating()); // MPA Rating
        values.put(KEY_HEADLINE, movie.getHeadline()); // Movie Headline
        values.put(KEY_CRITICS_PICK, movie.getCritics_pick()); // Criticts pick
        values.put(KEY_DATE_UPDATED, movie.getDate_updated()); // Movie description
        values.put(KEY_FOLLOW, movie.getUrl()); // Link description

        // Inserting Row
        db.insert(TABLE_MOVIES, null, values);
        db.close(); // Closing database connection
        Log.d("Added","Added");
    }

    // Getting All Movies
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<Movie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setDisplay_title(cursor.getString(1));
                movie.setSummary(cursor.getString(2));
                movie.setRating(cursor.getString(3));
                movie.setHeadline(cursor.getString(4));
                movie.setCritics_pick(cursor.getString(5));
                movie.setDate_updated(cursor.getString(6));
                movie.setUrl(cursor.getString(7));
                // Adding contact to list
                movieList.add(movie);
            } while (cursor.moveToNext());
        }

        // return contact list
        return movieList;
    }



    // Deleting single movie
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_TITLE + " = ?",
                new String[] { String.valueOf(movie.getDisplay_title()) });
        db.close();
    }


    // Getting Movies Count
    public int getMoviesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

}