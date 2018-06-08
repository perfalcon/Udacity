package com.example.balav.moviegridstage2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.balav.moviegridstage2.utils.JsonUtils;
import com.example.balav.moviegridstage2.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> listPosterUrls;
    private List<Integer> listIDs;
    private static final String MOVIE_SORT_POPULAR="popular";
    private static final String MOVIE_SORT_TOP_RATED="top_rated";

    private MovieAdapter mAdapter;
    private RecyclerView mImageGrid;

    private static final int NUM_LIST_ITEMS = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        loadMovies(MOVIE_SORT_POPULAR);
    }


    private void loadGridView(){
        mImageGrid = (RecyclerView) findViewById(R.id.rv_numbers);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mImageGrid.setLayoutManager(gridLayoutManager);
        mImageGrid.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this,listPosterUrls,listIDs);
        mImageGrid.setAdapter(mAdapter);
      /*  mImageGrid.setO.setOnItemClickListener(new AdapterView.OnItemClickListener () {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                launchDetailActivity(listIDs.get (position));
            }
        });*/
    }


    private void loadMovies(String movie_type){
        if(NetworkUtils.isConnected (MainActivity.this)){
            displayErrorMessage(View.INVISIBLE);
            new FetchMovieTask ().execute(movie_type);
        }else {
            displayErrorMessage(View.VISIBLE);
        }
    }

     private void displayErrorMessage(int flag){
        Log.v ("MainActivity","No Internet Connection");
        TextView mMsg = (TextView) findViewById(R.id.messae_tv);
        mMsg.setVisibility (flag);
        mMsg.setTextColor (Color.RED);
      /*  if(flag == View.INVISIBLE){
            mImageGrid.setVisibility (View.VISIBLE);  //.setAlpha (0);
        }else{
            mImageGrid.setVisibility (View.INVISIBLE);//.setAlpha (1);
        }*/
    }


    public class FetchMovieTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            String movie_type = params[0];
            URL movieUrl = NetworkUtils.buildUrl(movie_type);
            String movieResults=null;
            try{
                movieResults= NetworkUtils.getResponseFromHttpUrl(movieUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieResults;
        }
        @Override
        protected void onPostExecute(String movieResults){
            Log.v("onPostExecute-->",movieResults.toString ());
            Context context = MainActivity.this;
            listPosterUrls= JsonUtils.getPosterImages (movieResults);
            listIDs = JsonUtils.getMoviesIDs (movieResults);
            Log.v ("PosterUrlsSize--->", ""+listPosterUrls.size ());
            loadGridView();
        }
    }
}
