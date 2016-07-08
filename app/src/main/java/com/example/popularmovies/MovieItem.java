package com.example.popularmovies;

/**
 * Created by vims1 on 7/6/2016.
 */
public class MovieItem {
    private String moviePoster;
    private String movieTitle;
    private String moviePopularity;
    private String movieOverview;
    private String movieRelease_Date;
    private String movieVote_average;


    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieTitle()
    {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle)
    {
        this.movieTitle=movieTitle;
    }

    public String getMoviePopularity()
    {
        return moviePopularity;
    }

    public void setMoviePopularity(String rating)
    {
        this.moviePopularity=rating;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieRelease_Date() {
        return movieRelease_Date;
    }

    public void setMovieRelease_Date(String movieRelease_Date) {
        this.movieRelease_Date = movieRelease_Date;
    }

    public String getMovieVote_average() {
        return movieVote_average;
    }

    public void setMovieVote_average(String movieVote_average) {
        this.movieVote_average = movieVote_average;
    }
}
