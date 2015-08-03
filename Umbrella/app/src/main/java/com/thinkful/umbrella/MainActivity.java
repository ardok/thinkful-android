package com.thinkful.umbrella;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "UmbrellaMainActivity";

    private GoogleApiClient mGoogleApiClient;

    private Location mLastLocation;
    private int mLocationUpdateCount = 0;

    private Button btnSetAlarm;
    private Button btnCancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSetAlarm = (Button) findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = (Button) findViewById(R.id.btnCancelAlarm);

        // Set Google API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation != null) {
                    Alarm alarm = new Alarm();
                    alarm.setAlarm(getApplicationContext(), new AlarmSetParams(7, 30),
                            new UmbrellaLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                }
            }
        });
        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation != null) {
                    Alarm alarm = new Alarm();
                    alarm.cancelAlarm(getApplicationContext(),
                            new UmbrellaLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                }
            }
        });
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
        if (!mGoogleApiClient.isConnected()) {
            return;
        }
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
            String url = String.format(
                "http://api.openweathermap.org/data/2.5/forecast/daily?lat=%s&lon=%s&mode=json&units=metric&cnt=1",
                mLastLocation.getLatitude(),
                mLastLocation.getLongitude()
            );

            Log.i(TAG, String.format(
                "UmbrellaLocation locked (lat, lng): (%s, %s)", mLastLocation.getLatitude(), mLastLocation.getLongitude()
            ));

            WebServiceTask webserviceTask = new WebServiceTask();
            webserviceTask.execute(url);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
