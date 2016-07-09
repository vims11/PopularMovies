package com.example.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    private GridView gridView;
    private GridViewMovieAdapter gridImageAdapter;
    private TextView loadTextView;
    private String STRING_QUERY="popular";

    private ArrayList<MovieItem> movieGridData;

    public MoviesFragment() {
    }

    public void setStringQuery(String stringQuery) {
        STRING_QUERY=stringQuery;
    }

    public String getStringQuery()
    {
        return STRING_QUERY;
    }

    private void updateMovie() {
        FetchMovieTask fetchMovieTask = new FetchMovieTask();
        fetchMovieTask.execute(STRING_QUERY);   //"top_rated" ir "popular"
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridview_movies);

        loadTextView=(TextView) rootView.findViewById(R.id.load_textView);

        movieGridData = new ArrayList<>();
        gridImageAdapter = new GridViewMovieAdapter(getActivity(), R.layout.grid_list_item_movies, movieGridData);
        gridView.setAdapter(gridImageAdapter);

        updateMovie();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MovieItem mItem = (MovieItem) parent.getItemAtPosition(position);

                //Create Intent and pass the value (detail of the movie) to DetailActivity class

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra( "title", mItem.getMovieTitle());
                intent.putExtra("releaseDate",mItem.getMovieRelease_Date());
                intent.putExtra("rating",mItem.getMovieVote_average());
                intent.putExtra("overview",mItem.getMovieOverview());
                intent.putExtra("image", mItem.getMoviePoster());

                startActivity(intent);

            }
        });

        return rootView;
    }

//    boolean moviesUpdated = false;
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if(moviesUpdated == false) {
//            updateMovie();
//            moviesUpdated = true;
//        }
//    }

    public class FetchMovieTask extends AsyncTask<String, Void, Boolean> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            loadTextView.setVisibility(View.VISIBLE);

        }

        //Code given in the Udacity Sunshine project
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean isdataSuccessful = false;

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonStr = null;


            //https://api.themoviedb.org/3/movie/top_rated?api_key=9575a8cde5454829a6811daeaced302a&page=2

            final String FORECAST_BASE_URL="https://api.themoviedb.org/3/movie/"+params[0]+"?";
            final String APPID_PARAM="api_key";
            final String PAGE_NUM="page";
            try {
            Uri buildUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM,BuildConfig.OPEN_MOVIE_MAP_API_KEY).build();

            String myUrl = buildUri.toString();

                URL url = new URL(myUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) return isdataSuccessful;

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                movieJsonStr = buffer.toString();

            } catch (Exception e) {
                Log.e(LOG_TAG,"Error in connection",e);
            } finally {
                if (urlConnection != null) urlConnection.disconnect();

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error while closing the stream", e);
                    }
                }
            }

            try {
                isdataSuccessful=parseResult(movieJsonStr);
            } catch (JSONException e) {
                isdataSuccessful=false;
                e.printStackTrace();
            }

            return isdataSuccessful;
        }



        @Override
        protected void onPostExecute(Boolean dataSuccessful) {

            if(dataSuccessful) {
                //gridImageAdapter.clear();
                gridImageAdapter.setGridMovieData(movieGridData);
            }
            else
                Toast.makeText(getActivity(), "Data was not able to fetch", Toast.LENGTH_LONG).show();

            loadTextView.setVisibility(View.GONE);
        }



    }

    private boolean parseResult(String result) throws JSONException {

        final String RESULTS="results";
       // final String ID="id"
        final String POSTER_PATH="poster_path";
        final String TITLE="title";
        final String OVERVIEW="overview";
        final String USER_RATING="vote_average";
        final String RELEASE_DATE="release_date";
        final String POPULARITY="popularity";

        JSONObject movieJson = new JSONObject(result);
        JSONArray movieArray = movieJson.getJSONArray(RESULTS);
        MovieItem movieItem;

        for(int i = 0; i < movieArray.length(); i++) {
          /*  Log.i("Overview",movieArray.getJSONObject(i).getString(OVERVIEW));
            Log.i("Release Date",movieArray.getJSONObject(i).getString(RELEASE_DATE));
            Log.i("Poster Path","http://image.tmdb.org/t/p/w185"+movieArray.getJSONObject(i).getString(POSTER_PATH));
            Log.i("Title",movieArray.getJSONObject(i).getString(TITLE));
            Log.i("Popularity",movieArray.getJSONObject(i).getString(POPULARITY));
            Log.i("Vote average",movieArray.getJSONObject(i).getString(USER_RATING));*/

            movieItem = new MovieItem();

           // Log.i("Poster Path","http://image.tmdb.org/t/p/w185"+movieArray.getJSONObject(i).getString(POSTER_PATH));
            String mOverview = movieArray.getJSONObject(i).getString(OVERVIEW);
            movieItem.setMovieOverview(mOverview);

            String mRelease_Date = movieArray.getJSONObject(i).getString(RELEASE_DATE).split("-")[0];
            movieItem.setMovieRelease_Date(mRelease_Date);

            String mPoster_Path = "http://image.tmdb.org/t/p/w342" + movieArray.getJSONObject(i).getString(POSTER_PATH);
            movieItem.setMoviePoster(mPoster_Path);

            String mTitle = movieArray.getJSONObject(i).getString(TITLE);
            movieItem.setMovieTitle(mTitle);

            String mPopularity = movieArray.getJSONObject(i).getString(POPULARITY);
            movieItem.setMoviePopularity(mPopularity);

            String mVote_average = movieArray.getJSONObject(i).getString(USER_RATING);
            movieItem.setMovieVote_average(mVote_average);

            movieGridData.add(movieItem);

        }


            //Log.i("json",result);

        return true;
    }
}
