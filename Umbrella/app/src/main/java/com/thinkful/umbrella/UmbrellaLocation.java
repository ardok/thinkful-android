package com.thinkful.umbrella;

/**
 * Created by ardokusuma on 7/25/15.
 */
public class UmbrellaLocation {
    private double lat;
    private double lng;

    public UmbrellaLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lng;
    }
}
