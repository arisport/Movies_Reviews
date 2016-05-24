package com.moviesratings.Adapter;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.moviesratings.Model.Movie;
import com.moviesratings.R;

public class MoviesAdapter extends ArrayAdapter<Movie> {
	ArrayList<Movie> moviesList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public MoviesAdapter(Context context, int resource, ArrayList<Movie> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		moviesList = objects;
	}
 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvName = (TextView) v.findViewById(R.id.tvName);
			holder.tvDescription = (TextView) v.findViewById(R.id.tvDescriptionn);
			holder.tvDOB = (TextView) v.findViewById(R.id.tvDateOfBirth);
			holder.tvCountry = (TextView) v.findViewById(R.id.tvCountry);
			holder.tvHeight = (TextView) v.findViewById(R.id.tvHeight);
			holder.tvSpouse = (TextView) v.findViewById(R.id.tvSpouse);
			holder.tvChildren = (TextView) v.findViewById(R.id.tvChildren);
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
		holder.tvDOB.setText("Mpa Rating: " + moviesList.get(position).getRating());
		holder.tvCountry.setText(moviesList.get(position).getHeadline());
		holder.tvHeight.setText("Critics Pick:"+moviesList.get(position).getCritics_pick());
		holder.tvSpouse.setText("Date Updated: " + moviesList.get(position).getDate_updated());
		holder.tvChildren.setText("Follow: " + moviesList.get(position).getUrl());
		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvName;
		public TextView tvDescription;
		public TextView tvDOB;
		public TextView tvCountry;
		public TextView tvHeight;
		public TextView tvSpouse;
		public TextView tvChildren;

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