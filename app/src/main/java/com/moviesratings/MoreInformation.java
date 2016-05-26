package com.moviesratings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moviesratings.Adapter.MoviesAdapter;
import com.moviesratings.helper.WebViewController;

public class MoreInformation extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviews);
        webView = (WebView)findViewById(R.id.help_webview);

        // Intent to pass the url of each textview to open a webview
        Intent intent = getIntent();
        final String moreInfo = intent.getStringExtra(MoviesAdapter.MESSAGE_MOVIES);

        //if condition to check the current network state of the device.
        //if there is an internet connection the a website will be loaded in the MoreInformation
        //Activity. The link which is loaded is passed from the listview Activities(AllMovies-SavedMovies-Search).
        if (isNetworkStatusAvialable(getApplicationContext())){
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(moreInfo);
            webView.setWebViewClient(new WebViewController());
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setLongClickable(false);
            //I set up a webview client because a webview should react and respond to user preferences
            // For example if there is an email link on the page users have the option to click on the link
            // and the mail intent will fire up with the to: field completed.
            //Moreover users can download documents in extensions such as: pdf-docx.
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.endsWith(".pdf")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    } else if (url.endsWith(".docx")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                    } else if (url.startsWith("mailto:")) {
                        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                        startActivity(i);
                    } else if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    } else if (url.startsWith("tel:")) {
                        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                        startActivity(i);
                    } else if (url.startsWith(".doc")) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(i);
                    }
                    return true;

                }
            });
        } else{
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("Please connect to the internet")
                    .setPositiveButton(android.R.string.yes,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = new Intent(
                                            Settings.ACTION_SETTINGS);
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton(android.R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    MoreInformation.this.finish();
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                    .show();




        }

    }

    //Method to check the network status of the device by calling the
    // Connectivity Manager.
    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    //Animation
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Animation
                this.finish();
                overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
