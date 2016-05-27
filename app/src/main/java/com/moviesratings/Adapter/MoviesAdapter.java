package com.moviesratings.Adapter;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviesratings.Model.Movie;
import com.moviesratings.MoreInformation;
import com.moviesratings.R;

public class MoviesAdapter extends ArrayAdapter<Movie> {
	ArrayList<Movie> moviesList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;
	public final static String MESSAGE_MOVIES = "com.moviesratings.MESSAGE";

	public MoviesAdapter(Context context, int resource, ArrayList<Movie> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		moviesList = objects;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			holder.tvDescription = (TextView) v.findViewById(R.id.tvDescriptionn);
			holder.MpaRating = (TextView) v.findViewById(R.id.MpaRating);
			holder.Headline = (TextView) v.findViewById(R.id.Headline);
			holder.CriticsPick = (TextView) v.findViewById(R.id.Critics);
			holder.DateUpdated = (TextView) v.findViewById(R.id.DateUpdated);
			holder.Url = (TextView) v.findViewById(R.id.Url);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		holder.imageview.setImageResource(R.drawable.small_movie_poster);
		if (moviesList.get(position).getThumbnailUrl() == null){
			Log.d("Error","Null"+moviesList.get(position).getThumbnailUrl());
		} else {
			new DownloadImageTask(holder.imageview).execute(moviesList.get(position).getThumbnailUrl());
		}
		holder.tvName.setText(moviesList.get(position).getDisplay_title());
		holder.tvDescription.setText(moviesList.get(position).getSummary());
		holder.MpaRating.setText("Mpa Rating: " + moviesList.get(position).getRating());
		holder.Headline.setText(moviesList.get(position).getHeadline());
		holder.CriticsPick.setText("Critics Pick:" + moviesList.get(position).getCritics_pick());
		holder.DateUpdated.setText("Date Updated: " + moviesList.get(position).getDate_updated());
		holder.Url.setText("" + moviesList.get(position).getUrl());
		holder.Url.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = moviesList.get(position).getUrl();
				Intent intent = new Intent(getContext(), MoreInformation.class);
				intent.putExtra(MESSAGE_MOVIES, content);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getContext().startActivity(intent);
			}
		});
		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvName;
		public TextView tvDescription;
		public TextView MpaRating;
		public TextView Headline;
		public TextView CriticsPick;
		public TextView DateUpdated;
		public TextView Url;

	}


	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
}