package com.moviesratings;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String MESSAGE = "com.moviesratings.MESSAGE";
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencing the coordinator layout of my xml in order to use snackbar.
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        // Referencing the buttons  and add action with the setOnClickListener
        Button all_movies = (Button) findViewById(R.id.AllMovies);
        all_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ij = new Intent(getApplicationContext(), AllMovies.class);
                startActivity(ij);
                overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
            }
        });

        Button saved_movies = (Button) findViewById(R.id.SavedMovies);
        saved_movies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ij = new Intent(getApplicationContext(), SavedMovies.class);
                startActivity(ij);
                overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
            }
        });
    }

    /**
     * Called when the user clicks the Search button
     */
    public void sendMovieRequest(View view) {
        Intent intent = new Intent(this, Search.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        //If condition to check if the query string is empty
        if (message.matches("")) {
            final Snackbar snackBar =  Snackbar.make(coordinatorLayout, "Please Enter A Value", Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackBar.dismiss();
                        }
                    })
                    .show();
            return;
        } else {
            intent.putExtra(MESSAGE, message);
            startActivity(intent);
            overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
        }


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
