package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private ImageView imageView;
    private RatingBar mRatingBar;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_detail, container, false);


        Intent intent = getActivity().getIntent();
        getActivity().setTitle(intent.getStringExtra("title"));

        fillValueText(rootView,intent,"title",R.id.title_Text);
        fillValueText(rootView,intent,"releaseDate",R.id.release_text);
        fillValueText(rootView,intent,"overview",R.id.overview_Text);

        String imgPath=intent.getStringExtra("image");
        imageView=(ImageView)rootView.findViewById(R.id.poster_Image);
        Picasso.with(getActivity()).load(imgPath).into(imageView);

       String rating=intent.getStringExtra("rating");
        mRatingBar=(RatingBar)rootView.findViewById(R.id.rating);
        mRatingBar.setRating(Float.valueOf(rating));


        return rootView;
    }

    private void fillValueText(View rootView,Intent intent,String extra,int rId)
    {
        if (intent != null && intent.hasExtra(extra)) {
            String extraVariable = intent.getStringExtra(extra);
            ((TextView) rootView.findViewById(rId))
                    .setText(extraVariable);
        }
    }
}
