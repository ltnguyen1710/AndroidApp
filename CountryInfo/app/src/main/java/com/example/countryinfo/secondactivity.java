package com.example.countryinfo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

public class secondactivity extends AppCompatActivity {
    class countryinfoCovid{
        String name;
        int cases;
        int todayCases;
        int deaths;
        int todayDeaths;
        int recovered;
        int todayRecovered;
        int active;
        int critical;
        int casesPerOneMillion;
        int deathsPerOneMillion;
        int tests;
        int testsPerOneMillion;
        int oneCasePerPeople;
        int oneDeathPerPeople;
        int oneTestPerPeople;
        double activePerOneMillion;
        double recoveredPerOneMillion;
        double criticalPerOneMillion;

        public countryinfoCovid(String name, int cases, int todayCases, int deaths, int todayDeaths, int recovered,
                                int todayRecovered, int active, int critical, int casesPerOneMillion,
                                int deathsPerOneMillion, int tests, int testsPerOneMillion, int oneCasePerPeople,
                                int oneDeathPerPeople, int oneTestPerPeople, double activePerOneMillion, double recoveredPerOneMillion,
                                double criticalPerOneMillion) {
            this.name = name;
            this.cases = cases;
            this.todayCases = todayCases;
            this.deaths = deaths;
            this.todayDeaths = todayDeaths;
            this.recovered = recovered;
            this.todayRecovered = todayRecovered;
            this.active = active;
            this.critical = critical;
            this.casesPerOneMillion = casesPerOneMillion;
            this.deathsPerOneMillion = deathsPerOneMillion;
            this.tests = tests;
            this.testsPerOneMillion = testsPerOneMillion;
            this.oneCasePerPeople = oneCasePerPeople;
            this.oneDeathPerPeople = oneDeathPerPeople;
            this.oneTestPerPeople = oneTestPerPeople;
            this.activePerOneMillion = activePerOneMillion;
            this.recoveredPerOneMillion = recoveredPerOneMillion;
            this.criticalPerOneMillion = criticalPerOneMillion;
        }
    }
    private String countryname, URLhinh;
    private ImageLoader imageLoader;
    private ImageView imageView;
    private TextView name, cases, todayCases, deaths, todayDeaths, recovered,
     todayRecovered, active,  critical,  casesPerOneMillion,
     deathsPerOneMillion, tests,  testsPerOneMillion,  oneCasePerPeople,
     oneDeathPerPeople,  oneTestPerPeople,  activePerOneMillion,  recoveredPerOneMillion,
   criticalPerOneMillion;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
        Bundle thongtin = getIntent().getExtras();
        countryname = thongtin.getString("Key_1");
        URLhinh = thongtin.getString("Key_2");
        name = findViewById(R.id.textView);
        cases = findViewById(R.id.textView1);
        todayCases = findViewById(R.id.textView2);
        deaths = findViewById(R.id.textView3);
        todayDeaths = findViewById(R.id.textView4);
        recovered = findViewById(R.id.textView5);
        todayRecovered = findViewById(R.id.textView6);
        active = findViewById(R.id.textView7);
        critical = findViewById(R.id.textView8);
        casesPerOneMillion = findViewById(R.id.textView9);
        deathsPerOneMillion = findViewById(R.id.textView10);
        tests = findViewById(R.id.textView11);
        testsPerOneMillion = findViewById(R.id.textView12);
        oneCasePerPeople = findViewById(R.id.textView13);
        oneDeathPerPeople = findViewById(R.id.textView14);
        oneTestPerPeople = findViewById(R.id.textView15);
        activePerOneMillion = findViewById(R.id.textView16);
        recoveredPerOneMillion = findViewById(R.id.textView17);
        criticalPerOneMillion = findViewById(R.id.textView18);
        imageView = findViewById(R.id.imageView);
        imageLoader= new ImageLoader(this);

        new HttpGetTask().execute();

    }
    class HttpGetTask extends AsyncTask<Void, Void, List<countryinfoCovid>> {

        private static final String TAG = "HttpGetTask";

        // Get your own user name at http://www.geonames.org/login
        private static final String USER_NAME = "aporter";

        private static final String HOST = "api.geonames.org";
        //        private static final String URL = "http://" + HOST + "/earthquakesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&username="
//                + USER_NAME;
        private static final String URL = "https://disease.sh/v3/covid-19/countries";

        @Override
        protected List<countryinfoCovid> doInBackground(Void... params) {
            //String data = null;
            List<countryinfoCovid> data = null;
            HttpURLConnection httpUrlConnection = null;

            try {
                // 1. Get connection. 2. Prepare request (URI)
                httpUrlConnection = (HttpURLConnection) new URL(URL).openConnection();
                System.out.println("after httpUrlConnection");
                // 3. This app does not use a request body
                // 4. Read the response
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
                System.out.println("after in");
                data = readJSONStream(readStream(in));
                System.out.println("after data");
            } catch (MalformedURLException exception) {
                Log.e(TAG, "MalformedURLException");
            } catch (IOException | JSONException exception) {
                Log.e(TAG, "IOException, doInBackground");
            } finally {
                if (null != httpUrlConnection) {
                    // 5. Disconnect
                    httpUrlConnection.disconnect();
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<countryinfoCovid> result) {
            //mTextView.setText(result);
            try{
                for(countryinfoCovid info: result) {
                    if (info.name.equalsIgnoreCase(countryname)) {
                        name.setText(info.name);
                        cases.setText("Cases: "+String.valueOf(info.cases));
                        todayCases.setText("Today Cases: "+String.valueOf(info.todayCases));
                        deaths.setText("Deaths: "+String.valueOf(info.deaths));
                        todayDeaths.setText("Today Deaths: "+String.valueOf(info.todayDeaths));
                        recovered.setText("Recovered: "+String.valueOf(info.recovered));
                        todayRecovered.setText("Today Recovered: "+String.valueOf(info.todayRecovered));
                        active.setText("Active: "+String.valueOf(info.active));
                        critical.setText("Critical: "+String.valueOf(info.critical));
                        casesPerOneMillion.setText("Cases Per 1 Million: "+String.valueOf(info.casesPerOneMillion));
                        deathsPerOneMillion.setText("Deaths Per 1 Million: "+String.valueOf(info.deathsPerOneMillion));
                        tests.setText("Tests: "+String.valueOf(info.tests));
                        testsPerOneMillion.setText("Tests Per 1 Million: "+String.valueOf(info.testsPerOneMillion));
                        oneCasePerPeople.setText("1 Case Per People: "+String.valueOf(info.oneCasePerPeople));
                        oneTestPerPeople.setText("1 Case Per People: "+String.valueOf(info.oneTestPerPeople));
                        oneDeathPerPeople.setText("1 Case Per People: "+String.valueOf(info.oneDeathPerPeople));
                        activePerOneMillion.setText("Active Per 1 Million: "+String.valueOf(info.activePerOneMillion));
                        recoveredPerOneMillion.setText("Recovered Per 1 Million: "+String.valueOf(info.recoveredPerOneMillion));
                        criticalPerOneMillion.setText("Critical Per 1 Million: "+String.valueOf(info.criticalPerOneMillion));
                        imageLoader.DisplayImage(URLhinh,imageView);
                    }
                }
            }catch (Exception e){
                Log.e(TAG, e.toString());
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
                Log.e(TAG, "IOException readStream");
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException readStream2");
                    }
                }
            }
            return data.toString();
        }

        private List<countryinfoCovid> readJSONStream(String in) throws JSONException {
            List<countryinfoCovid> data = new ArrayList<countryinfoCovid>();

            //JSONObject jsonObj = new JSONObject(in);
            //System.out.println(jsonObj.getJSONArray(""));
            JSONArray countries = new JSONArray(in);
            for(int i = 0; i < countries.length(); i++) {
                JSONObject country = countries.getJSONObject(i);

                countryinfoCovid c = new countryinfoCovid(country.getString("country"), Integer.parseInt(country.getString("cases"))
                        , Integer.parseInt(country.getString("todayCases")), Integer.parseInt(country.getString("deaths")),
                        Integer.parseInt(country.getString("todayDeaths")),Integer.parseInt(country.getString("recovered")),
                        Integer.parseInt(country.getString("todayRecovered")), Integer.parseInt(country.getString("active")),
                        Integer.parseInt(country.getString("critical")), Integer.parseInt(country.getString("casesPerOneMillion")),
                        Integer.parseInt(country.getString("deathsPerOneMillion")), Integer.parseInt(country.getString("tests")),
                        Integer.parseInt(country.getString("testsPerOneMillion")), Integer.parseInt(country.getString("oneCasePerPeople")),
                        Integer.parseInt(country.getString("oneDeathPerPeople")), Integer.parseInt(country.getString("oneTestPerPeople")),
                        Double.parseDouble(country.getString("activePerOneMillion")), Double.parseDouble(country.getString("recoveredPerOneMillion")),
                        Double.parseDouble(country.getString("criticalPerOneMillion")));
                data.add(c);
            }

            return data;
        }
    }
}
