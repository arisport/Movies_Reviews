package com.moviesratings;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.moviesratings.Adapter.MoviesAdapter;
import com.moviesratings.Database.DatabaseHandler;
import com.moviesratings.Model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SavedMovies extends AppCompatActivity {

    ArrayList<Movie> moviesList;
    MoviesAdapter adapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_movies);
        moviesList = new ArrayList<Movie>();

        ListView listview = (ListView) findViewById(R.id.list);
        adapter = new MoviesAdapter(getApplicationContext(), R.layout.row, moviesList);
        listview.setAdapter(adapter);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        addToListView();

        if (moviesList.isEmpty()) {
            Snackbar.make(coordinatorLayout, "You Don't Have Any Saved Movies", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Add", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
                        }
                    })
                    .show();
        }

    }

    public void addToListView(){
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Movie> contacts = db.getAllMovies();

        for (Movie cn : contacts) {
            Movie movie = new Movie();
            movie.setDisplay_title(cn.getDisplay_title());
            movie.setSummary(cn.getRating());
            movie.setRating(cn.getSummary());
            movie.setHeadline(cn.getUrl());
            movie.setCritics_pick(cn.getHeadline());
            movie.setDate_updated(cn.getCritics_pick());
            movie.setUrl(cn.getDate_updated());
            moviesList.add(movie);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_movies, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            this.finish();
            overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
