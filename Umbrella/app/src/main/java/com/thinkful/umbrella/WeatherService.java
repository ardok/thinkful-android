package com.thinkful.umbrella;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ardokusuma on 7/25/15.
 */
public class WeatherService {
    private static final String TAG = "WeatherService";

    public static final int CLEAR = 0;
    public static final int RAIN = 1;
    public static final int SNOW = 2;
    public static final int DONTKNOW = 3;

    public WeatherService() {
    }

    public int getWeather(String... params) {
        HttpURLConnection urlConnection = null;
        int useUmbrellaResponse = DONTKNOW;
        try {
            String urlStr = String.format(
                "http://api.openweathermap.org/data/2.5/forecast/daily?lat=%s&lon=%s&mode=json&units=metric&cnt=1",
                params[0],
                params[1]
            );
            Log.i(TAG, String.format("WebServiceTask request url: %s", urlStr));
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            useUmbrellaResponse = useUmbrella(urlConnection.getInputStream());
        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return useUmbrellaResponse;
    }

    protected int useUmbrella(InputStream in) {
        //read and parse InputStream
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            JSONObject forecastJson = new JSONObject(stringBuilder.toString());
            JSONArray weatherArray = forecastJson.getJSONArray("list");
            JSONObject todayForecast = weatherArray.getJSONObject(0);
            Log.i(TAG, String.format("WebServiceTask response received: %s", todayForecast));
            if (todayForecast.has("snow")) {
                return SNOW;
            } else if (todayForecast.has("rain")) {
                return RAIN;
            } else {
                return CLEAR;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error", e);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // Finally block will always get called even if you return earlier in your
            //   try catch block
            try {
                bufferedReader.close();
            } catch (final IOException e) {
                Log.e(TAG, "Error closing stream", e);
            }
        }

        return DONTKNOW;
    }
}
