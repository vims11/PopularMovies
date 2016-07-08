package com.example.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vims1 on 7/6/2016.
 */
public class GridViewMovieAdapter extends ArrayAdapter<MovieItem> {

    private Context context;
    private int resourceId;
    private ArrayList<MovieItem> movies_data = new ArrayList<MovieItem>();

    public GridViewMovieAdapter(Context context, int resourceId, ArrayList<MovieItem> movies_data) {
        super(context, resourceId, movies_data);

        this.context=context;
        this.resourceId=resourceId;
        this.movies_data=movies_data;
    }

    //Grid has an ImageView for movie poster and two Text View for title nd rating
    static class MovieViewHolder {
        ImageView movieImage;
      //  TextView movieTitle;
       // TextView movieRate;
    }

    public void setGridMovieData(ArrayList<MovieItem> movies_data)
    {
        this.movies_data=movies_data;
        notifyDataSetChanged();
    }

    //getView() is implemented for creating view for each Grid Item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View eachGridView=convertView;
        MovieViewHolder movieViewHolder;

        if(eachGridView==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            eachGridView=inflater.inflate(resourceId, parent, false);

            movieViewHolder=new MovieViewHolder();

            movieViewHolder.movieImage=(ImageView)eachGridView.findViewById(R.id.grid_movie_image);
        //    movieViewHolder.movieTitle=(TextView)eachGridView.findViewById(R.id.grid_movie_title);
          //  movieViewHolder.movieRate=(TextView)eachGridView.findViewById(R.id.grid_movie_rating);
            eachGridView.setTag(movieViewHolder);     //set the tag of the gridView as the movieViewHolder
        }
        else
         movieViewHolder=(MovieViewHolder)eachGridView.getTag();  // get the tag of the gridview

        MovieItem movieItem= (MovieItem) movies_data.get(position);  // From the Array List using position  get the values of the movieItem to set the View

        Picasso.with(context).load(movieItem.getMoviePoster()).into(movieViewHolder.movieImage);
      //  movieViewHolder.movieTitle.setText(movieItem.getMovieTitle());
       // movieViewHolder.movieRate.setText(movieItem.getrating());

        return eachGridView;
    }
}
