package com.example.balav.moviegridstage2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balav.moviegridstage2.model.Movie;
import com.example.balav.moviegridstage2.utils.JsonUtils;
import com.example.balav.moviegridstage2.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = JsonUtils.class.getSimpleName();
    public static final String MOVIE_ID = "movie_id";
    private static final int DEFAULT_ID = -1;
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_detail);


        ImageView posterIv = findViewById(R.id.poster_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int movie_id = intent.getIntExtra(MOVIE_ID, DEFAULT_ID);
        if (movie_id == DEFAULT_ID) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        if(NetworkUtils.isConnected (DetailActivity.this)){
            loadMovie (movie_id);

        }else{
            posterIv.setVisibility (View.INVISIBLE);populateUI_NoConnection();

        }
    }

    private void populateUI_NoConnection(){
//        TextView mTitle = (TextView) findViewById(R.id.title_tv);
//        TextView mPlotSynopsis = (TextView) findViewById(R.id.plot_synopsis_tv);
//        TextView mUserRating = (TextView)findViewById (R.id.user_rating_tv);
//        TextView mReleaseDate = (TextView)findViewById (R.id.relase_date_tv);
//        mTitle.setText ("Please Check your Internet Connection");
//        mTitle.setTextSize (16);
//        mTitle.setTextColor (Color.RED);
//        mPlotSynopsis.setVisibility (View.INVISIBLE);
//        mUserRating.setVisibility (View.INVISIBLE);
//        mReleaseDate.setVisibility (View.INVISIBLE);
//
//        ((TextView) findViewById(R.id.title_label_tv)).setVisibility (View.INVISIBLE);
//        ((TextView) findViewById(R.id.plot_synopsis_label_tv)).setVisibility (View.INVISIBLE);
//        ((TextView)findViewById (R.id.user_rating_label_tv)).setVisibility (View.INVISIBLE);
//        ((TextView)findViewById (R.id.relase_date_label_tv)).setVisibility (View.INVISIBLE);
    }


    private void populateUI(Movie movie) {
        TextView mTitle = (TextView) findViewById(R.id.title_tv);
        TextView mPlotSynopsis = (TextView) findViewById(R.id.plot_synopsis_tv);
        TextView mUserRating = (TextView)findViewById (R.id.user_rating_tv);
        TextView mReleaseDate = (TextView)findViewById (R.id.relase_date_tv);
        mTitle.setText (movie.getTitle ());
        mPlotSynopsis.setText (movie.getPlotSynopsis ());
        mUserRating.setText (Double.toString(movie.getUserRating ()));
        mReleaseDate.setText (movie.getReleaseDate ());
        loadTrailerView();
    }

    private void loadTrailerView(){
        RecyclerView  rvTrailer = (RecyclerView) findViewById(R.id.rv_trailers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTrailer.setLayoutManager (layoutManager);
        List<String> listTrailers = Arrays.asList (movie.getTrailors ().split (","));
        Context context = DetailActivity.this;
        TrailerAdapter adapterTrailer =  new TrailerAdapter (listTrailers);
        rvTrailer.setAdapter (adapterTrailer);
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void loadMovie(int movie_id){
        new DetailActivity .FetchMovie ().execute(Integer.toString (movie_id));
    }

    public class FetchMovie extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            String movie_id = params[0];
            URL movieUrl = NetworkUtils.buildMovieUrl (Integer.parseInt (movie_id));
            URL trailerUrl = NetworkUtils.buildTrailerUrl (Integer.parseInt (movie_id));
            URL reviewUrl= NetworkUtils.buildReviewUrl (Integer.parseInt (movie_id));
            String movieResults=null;
            String trailers=null;
            String reviews = null;
            JSONObject jsonObject =null;
            try{
                movieResults= NetworkUtils.getResponseFromHttpUrl(movieUrl);
                jsonObject = new JSONObject (movieResults);
                List<String> listVideoKeys=JsonUtils.getVideoKeys (NetworkUtils.getResponseFromHttpUrl(trailerUrl));
                jsonObject.put(Constants.TRAILER_VIDEO, TextUtils.join(",",listVideoKeys));
                jsonObject.put(Constants.REVIEWS,JsonUtils.getReviews (NetworkUtils.getResponseFromHttpUrl(reviewUrl)));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace ();
            } catch (Exception e){
                e.printStackTrace ();
            }
            return jsonObject.toString ();
        }
        @Override
        protected void onPostExecute(String movieResults){
            Log.v("onPostExecute-->",movieResults);
            Context context = DetailActivity.this;
            movie= JsonUtils.parseMovieJson (movieResults);
            Log.v(TAG,"--->"+movie.getTitle ());
            Log.v(TAG,"-->"+movie.getPlotSynopsis ());



            populateUI(movie);

            Picasso.with(DetailActivity.this)
                    .load(NetworkUtils.buildImageUrl (movie.getPosterPath ().replaceFirst ("/",""),NetworkUtils.IMAGE_SIZE_500))
                    //.placeholder(R.drawable.user_placeholder)
                    //.error(R.drawable.user_placeholder_error)
                    .into((ImageView)findViewById(R.id.poster_iv));
        }
    }
}
