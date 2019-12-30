package com.example.onlineshopping_ecommerce.model;

public class LatitudeLongitude {
    private double lat;
    private double lon;
    private String marker;

    public LatitudeLongitude(double lan, double lon, String marker) {
        this.lat = lan;
        this.lon = lon;
        this.marker = marker;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
