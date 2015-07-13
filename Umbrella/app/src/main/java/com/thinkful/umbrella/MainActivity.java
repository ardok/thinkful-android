package com.thinkful.umbrella;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "UmbrellaMainActivity";

    private TextView mTextView;

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;
    private int mLocationUpdateCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bind views
        mTextView = (TextView) findViewById(R.id.textView);

        // Set Google API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(500);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        mLocationUpdateCount = mLocationUpdateCount + 1;
        if (mLastLocation != null && mLocationUpdateCount >= 3) {
            // Only update the location 3x, then stop it
            stopLocationUpdates();

            // Reset count too
            mLocationUpdateCount = 0;

            // Fetch weather data
            // http://openweathermap.org/forecast
            String url = String.format("http://api.openweathermap.org/data/2.5/forecast/daily?lat=%s&lon=%s&mode=json&units=metric&cnt=1",
                    mLastLocation.getLatitude(), mLastLocation.getLongitude());

            Log.i(TAG, String.format(
                "Location locked (lat, lng): (%s, %s)", mLastLocation.getLatitude(), mLastLocation.getLongitude()
            ));

            WebServiceTask webserviceTask = new WebServiceTask();
            webserviceTask.execute(url);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private class WebServiceTask extends AsyncTask<String, String, String> {

        private String url;

        @Override
        protected String doInBackground(String... params) {
            String useUmbrellaStr = "Don't know, sorry about that.";
            HttpURLConnection urlConnection = null;
            this.url = params[0];
            Log.i(TAG, "WebServiceTask URL: " + this.url);

            try {
                URL url = new URL(this.url);
                urlConnection = (HttpURLConnection) url.openConnection();
                useUmbrellaStr = useUmbrella(urlConnection.getInputStream());
            } catch (IOException e) {
                Log.e(TAG, "WebServiceTask error in connecting", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return useUmbrellaStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTextView.setText("Should I take an umbrella today? " + s);
        }

        protected String useUmbrella(InputStream in) {
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
                Log.i(TAG, String.format("WebServiceTask response received for url: %s. %s", this.url, todayForecast));
                if (todayForecast.has("rain") || todayForecast.has("snow")) {
                    return "Yes";
                }
                return "No";
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
            return "Don't know, sorry about that.";
        }
    }
}
