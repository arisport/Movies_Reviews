package com.moviesratings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.moviesratings.Adapter.MoviesAdapter;
import com.moviesratings.Database.DatabaseHandler;
import com.moviesratings.Model.Movie;
import com.moviesratings.helper.ServiceHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;


public class AllMovies extends AppCompatActivity {

    ArrayList<Movie> moviesList;
    private String url = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=75ae7de71d0f42adbc2bb18832981a35";
    MoviesAdapter adapter;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allmovies);
        //Create a new movieList to store the movies
        moviesList = new ArrayList<Movie>();

        //Call the JSONAsyncTask class with the url we defined before as variable
        new JSONAsyncTask().execute(url);

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
                //Users are able to save a movie by just tapping on each separate item of the list.
                // In order to save the movie, we creating a new database handler db.
                //We inherit the data from the list (we defined before movielist) by using the
                // method get(position) of the list.
                //At the end a snackbar with a button view is displayed on the screen informing the user which movie is saved
                // Moreover users can click on the view button of the snackbar and visit the saved movies activity.
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                db.addMovie(new Movie(moviesList.get(position).getDisplay_title(), moviesList.get(position).getRating(), moviesList.get(position).getCritics_pick(),
                        moviesList.get(position).getHeadline(), moviesList.get(position).getSummary(), moviesList.get(position).getUrl(), moviesList.get(position).getDate_updated()));
                Snackbar.make(coordinatorLayout, moviesList.get(position).getDisplay_title() + " Saved Locally", Snackbar.LENGTH_INDEFINITE)
                        .setAction("View", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), SavedMovies.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
                            }
                        })
                        .show();
            }
        });

    }

    // The class JSONAsynch is executing the url in order to send the request to server
    // and to get a response. If the request has respond code 200 means that the request
    // has been carried out successfully. Moreover by extending my class to AsyncTask class,
    // AsyncTask enables proper and easy use of the UI thread. This class allows to perform
    // background operations and publish results on the UI thread without having to manipulate
    // threads and/or handlers

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(AllMovies.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
                ServiceHandler sh = new ServiceHandler();
                // Making a request to url and getting response
                String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
                //Testing Purposes
                Log.d("Response: ", "> " + jsonStr);
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                // Status code 200 means that the request has been carried out successfully
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    // Creating a new JSON object and a new JSON array
                    JSONObject JsonObject = new JSONObject(data);
                    JSONArray JsonArray = JsonObject.getJSONArray("results");

                    // for loop in order to visit all the elements of the JSONArray
                    // also in each iteration we creating a new object Movie
                    // and we are setting the values from the response
                    for (int i = 0; i < JsonArray.length(); i++) {
                        JSONObject obj = JsonArray.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setDisplay_title(obj.getString("display_title"));
                        movie.setRating(obj.getString("mpaa_rating"));
                        movie.setCritics_pick(obj.getString("critics_pick"));
                        movie.setByline(obj.getString("byline"));
                        movie.setHeadline(obj.getString("headline"));
                        movie.setSummary(obj.getString("summary_short"));
                        movie.setPublication_date(obj.getString("publication_date"));
                        movie.setOpening_date(obj.getString("opening_date"));
                        movie.setDate_updated(obj.getString("date_updated"));
                        // Link node is JSON Object
                        JSONObject link = obj.getJSONObject("link");
                        movie.setType(link.getString("type"));
                        movie.setUrl(link.getString("url"));
                        movie.setSuggested(link.getString("suggested_link_text"));
                        // Multimedia node is JSON Object
                           JSONObject multimedia = obj.getJSONObject("multimedia");
                            movie.setType_multimedia(multimedia.getString("type"));
                            movie.setThumbnailUrl(multimedia.getString("src"));
                            movie.setHeight(multimedia.getString("height"));
                            movie.setWidth(multimedia.getString("width"));

                        // Snackbar to display information to the user about how to use the Activity.
                        final Snackbar snackBar =  Snackbar.make(coordinatorLayout, "To Save Movie Just Tap On Each List Item", Snackbar.LENGTH_INDEFINITE);
                        snackBar.setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                snackBar.dismiss();
                            }
                        })
                                .show();

                        // adding movie to movies array
                        moviesList.add(movie);
                    }

                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

    // Animation
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Animation
        overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            this.finish();
            // Animation
            overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
