package com.example.musixmatch;
//
//Group -15
//Kishan Singh
//Amit SK

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name;
    SeekBar seekBar;
    TextView limit;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    RadioGroup radioGroup;
    RadioButton rb_artist , rb_track ;
    ProgressBar progressBar;
    int size ;

    public static String API_KEY = "ce4edf54447ce444b9d6c064ea5e6bcc";
    public static String URL_TAG = "URL";
    public static String URL = "http://api.musixmatch.com/ws/1.1/track.search?apikey="+API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView =  findViewById(R.id.recyclerView);

        name=findViewById(R.id.editText);
        seekBar=findViewById(R.id.seekBar);
        limit=findViewById(R.id.textViewLimit);
        seekBar.setMax(20);
        size = 5;
        limit.setText("limit : " + size+"");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 5;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressChanged = 5+ i;
                limit.setText("limit : "+progressChanged+"");
                size = 5+i ;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button search_btn = findViewById(R.id.button);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
//                    if(name==null  || !name.getText().toString().isEmpty()){
//                        Toast.makeText(getApplicationContext() ,  , Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    Toast.makeText(getApplicationContext() , "Connected" , Toast.LENGTH_SHORT).show();
                    Log.d("Demo" , name.getText().toString());
                    new JSONParseAsync().execute(URL+"&s_track_rating=desc"+"&page_size="+size+"&q="+name.getText().toString());
                }
                else Toast.makeText(getApplicationContext() , "Not Connected" , Toast.LENGTH_SHORT).show();
                return;


            }
        });

        //radiogroup

        rb_artist =findViewById(R.id.radioButton);
        rb_track=findViewById(R.id.radioButton2); ;


        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if(isConnected()){
                    Toast.makeText(getApplicationContext() , "Connected" , Toast.LENGTH_SHORT).show();
                    if(rb_artist.isChecked())

                    { Log.d("Demo" , name.getText().toString());
                        new JSONParseAsync().execute(URL+"&s_track_rating=desc"+"&page_size="+size+"&q="+name.getText().toString());

                    }
                    else if(rb_track.isChecked())
                    {
                        Log.d("Demo" , name.getText().toString());
                        new JSONParseAsync().execute(URL+"&s_artist_rating=desc"+"&page_size="+size+"&q="+name.getText().toString());
                    }

                }
                else Toast.makeText(getApplicationContext() , "Not Connected" , Toast.LENGTH_SHORT).show();
                return;


            }



        });


    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }




    private class JSONParseAsync extends AsyncTask<String, Void, ArrayList<Track>> {
        @Override
        protected void onPostExecute(ArrayList<Track> trackArrayList)
        {
            if(trackArrayList.size()!=0){
                // Log.d("Demo", trackArrayList+"");
            }
            else Log.d("Demo", "null");

            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

            mAdapter = new TrackAdapter(trackArrayList , MainActivity.this);
            recyclerView.setAdapter(mAdapter);

            progressBar.setVisibility(View.INVISIBLE);


        }

        @Override
        protected ArrayList<Track> doInBackground(String... strings) {

            HttpURLConnection connection = null;
            ArrayList<Track> trackArrayList = new ArrayList<>();

            String result = null;
            try {
                java.net.URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result = IOUtils.toString(connection.getInputStream(), "UTF-8");

                    JSONObject jsonObj = new JSONObject(result);

                    JSONObject message = jsonObj.getJSONObject("message");
                    JSONObject body = message.getJSONObject("body");

                    JSONArray trackArray =  body.getJSONArray("track_list");


                    for( int i = 0 ; i < trackArray.length();i++)
                    {
                        JSONObject trackJSON = trackArray.getJSONObject(i);

                        JSONObject trackArrayJSONObject = trackJSON.getJSONObject("track");
                        Track track = new Track();
                        track.track_name = trackArrayJSONObject.getString("track_name");
                        track.album_name = trackArrayJSONObject.getString("album_name");
                        track.artist_name = trackArrayJSONObject.getString("artist_name");
                        track.updated_time = trackArrayJSONObject.getString("updated_time");
                        track.track_share_url = trackArrayJSONObject.getString("track_share_url");




                        trackArrayList.add(track);

                    }


                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            finally {
                //Close open connections and reader
                if (connection != null) {
                    connection.disconnect();
                }

            }
            return trackArrayList;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

    }

}
