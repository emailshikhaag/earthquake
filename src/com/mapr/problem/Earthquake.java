package com.mapr.problem;

/**
 *   This class represents earthquake properties. We can add more properties to earthquake
 *   if needed.
 */

public class Earthquake implements Comparable<Earthquake> {

    private final String placeOfEarthquake;
    private final Double magnitude;
    private final Long time;

    public Earthquake(String place, Double magnitude, long time) {

        this.placeOfEarthquake = place;
        this.magnitude = magnitude;
        this.time = time;
    }

    public String getPlaceOfEarthquake() {
        return placeOfEarthquake;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public int compareTo(Earthquake o) {
        return time.compareTo(o.getTime());
    }

}
