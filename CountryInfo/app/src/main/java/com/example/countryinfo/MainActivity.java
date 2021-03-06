package com.example.countryinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    CountryAdapter Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listview);
        new HttpGetTask().execute();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country c = Adapter.getItem(position);
                sendcovidInfo(c.getTennuoc(),c.getHinhquockiURL());
            }
        });


    }
    private void sendcovidInfo(String name,String URL){
        Intent intent = new Intent(this, secondactivity.class);
        Bundle thongtin = new Bundle();
        thongtin.putString("Key_1",name);
        thongtin.putString("Key_2",URL);
        intent.putExtras(thongtin);
        startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStop() {
        //Adapter.imageLoader.clearCache();
        super.onStop();
    }

    class HttpGetTask extends AsyncTask<Void, Void, List<Country>> {

        private static final String TAG = "HttpGetTask";

        // Get your own user name at http://www.geonames.org/login
        private static final String USER_NAME = "aporter";

        private static final String HOST = "api.geonames.org";
        //        private static final String URL = "http://" + HOST + "/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
//                + USER_NAME;
        private static final String URL = "http://api.geonames.org/countryInfoJSON?username=caoth";

        @Override
        protected List<Country> doInBackground(Void... params) {
            //String data = null;
            List<Country> data = null;
            HttpURLConnection httpUrlConnection = null;

            try {
                // 1. Get connection. 2. Prepare request (URI)
                httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();

                // 3. This app does not use a request body
                // 4. Read the response
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());

                data = readJSONStream(readStream(in));

            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException | JSONException exception) {
                Log.e(TAG, "IOException");
            } finally {
                if (null != httpUrlConnection) {
                    // 5. Disconnect
                    httpUrlConnection.disconnect();
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<Country> result) {
            //mTextView.setText(result);
            if(ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Adapter = new CountryAdapter(MainActivity.this, (ArrayList<Country>) result);
                lv.setAdapter(Adapter);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
                Adapter = new CountryAdapter(MainActivity.this, (ArrayList<Country>) result);
                lv.setAdapter(Adapter);

            }
        }

        private String readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder data = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    data.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException");
                    }
                }
            }
            return data.toString();
        }

        private List<Country> readJSONStream(String in) throws JSONException {
            List<Country> data = new ArrayList<Country>();

            JSONObject jsonObj = new JSONObject(in);
            JSONArray countries = jsonObj.getJSONArray("geonames");
            for(int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);

                Country c = new Country(country.getString("countryName"), Integer.parseInt(country.getString("population"))
                        ,Double.parseDouble(country.getString("areaInSqKm")),country.getString("countryCode"));
                data.add(c);
            }

            return data;
        }
    }
}