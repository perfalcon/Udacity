package com.example.balav.bakingapp_utils;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.balav.bakingapp_utils.model.Baking;
import com.example.balav.bakingapp_utils.utils.GsonUtils;
import com.example.balav.bakingapp_utils.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private TextView btnRequest;
    private List<Baking> mBaking;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    private String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private  NetworkUtils mNetworkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        btnRequest = (TextView) findViewById(R.id.tv_text);
        mNetworkUtils = new NetworkUtils ();

        btnRequest.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v){
                                              new FetchBakingDetails ().execute(url);
                                          }
                                      }

        );
    }

    protected  void displayBakingDetails(){
        for (Baking baking : mBaking) {
            Log.v(TAG,"Baking -->"+baking.toString ());
        }
    }


    public class FetchBakingDetails extends AsyncTask<String,Void, String>{

        String baking_response=null;

        @Override
        protected String doInBackground(String... params) {
            baking_response = mNetworkUtils.sendAndRequestResponse(MainActivity.this);
            return baking_response!=null?baking_response:"";
        }
        @Override
        protected void onPostExecute(String bakingResults){
            Log.v(TAG,"[onPostExecute]-->"+bakingResults);
           mBaking= new GsonUtils ().populateBaking (bakingResults);
            displayBakingDetails();

        }
    }





}
