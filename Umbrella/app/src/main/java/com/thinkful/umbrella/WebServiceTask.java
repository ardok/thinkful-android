package com.thinkful.umbrella;

import android.os.AsyncTask;
import android.widget.TextView;

/**
 * Created by ardokusuma on 7/25/15.
 */
public class WebServiceTask extends AsyncTask<String, String, String> {

    private TextView textViewToUpdate;

    public WebServiceTask() {}

    public WebServiceTask(TextView textViewToUpdate) {
        this.textViewToUpdate = textViewToUpdate;
    }

    @Override
    protected String doInBackground(String... params) {
        WeatherService weatherService = new WeatherService();
        int weatherResponse = weatherService.getWeather(params[0], params[1]);
        switch (weatherResponse) {
            case WeatherService.CLEAR:
                return "No";
            case WeatherService.RAIN:
            case WeatherService.SNOW:
                return "Yes";
            default:
                return "Sorry, I don't know!";
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textViewToUpdate.setText("Take an umbrella? " + s);
    }
}