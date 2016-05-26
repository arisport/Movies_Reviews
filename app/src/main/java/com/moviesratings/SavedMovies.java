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
        //Create a new movieList to store the movies
        moviesList = new ArrayList<Movie>();

        // Referencing the coordinator layout of my xml in order to use snackbar.
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        // Referencing the list
        ListView listview = (ListView) findViewById(R.id.list);
        // Creating new adapter
        adapter = new MoviesAdapter(getApplicationContext(), R.layout.row, moviesList);
        // attach the adapter to my list
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                //Users are able to delete a movie by just tapping on each separate item of the list.
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.deleteMovie(new Movie(moviesList.get(position).getDisplay_title(), moviesList.get(position).getRating(), moviesList.get(position).getCritics_pick(),
                        moviesList.get(position).getHeadline(), moviesList.get(position).getSummary(), moviesList.get(position).getUrl(), moviesList.get(position).getDate_updated()));
                Snackbar.make(coordinatorLayout, moviesList.get(position).getDisplay_title() + " Removed", Snackbar.LENGTH_SHORT)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(getApplicationContext(), SavedMovies.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
                            }
                        })
                        .show();
            }
        });

        final Snackbar snackBar =  Snackbar.make(coordinatorLayout, "To Delete Movie Just Click On Each List Item", Snackbar.LENGTH_INDEFINITE);
        snackBar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        })
                .show();

        //Call the Function
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

    //Method to get all the movies from the Database.
    //A for loop is used to go through all the movies from the List and
    // at the end all the movies are saved in a new list which basicly is the list
    // which is displayed in the UI.
    public void addToListView(){
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<Movie> contacts = db.getAllMovies();

        for (Movie m : contacts) {
            Movie movie = new Movie();
            movie.setDisplay_title(m.getDisplay_title());
            movie.setSummary(m.getRating());
            movie.setRating(m.getSummary());
            movie.setHeadline(m.getUrl());
            movie.setCritics_pick(m.getHeadline());
            movie.setDate_updated(m.getCritics_pick());
            movie.setUrl(m.getDate_updated());
            moviesList.add(movie);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_movies, menu);
        return true;
    }

    //Animation
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

        //Animation
        if (id == android.R.id.home) {
            this.finish();
            overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
