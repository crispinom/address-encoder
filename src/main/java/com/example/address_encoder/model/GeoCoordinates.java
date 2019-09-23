package com.example.address_encoder.model;


/**
 * 
 * Object to hold geo coordinates latitude and longtitude.
 * 
 * @author cris
 *
 */
public class GeoCoordinates {

	private double latitude;
	private double longtitude;

	public GeoCoordinates(double latitude, double longtitude) {
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	@Override
	public String toString() {
		return "GeoCoordinates [latitude=" + latitude + ", longtitude=" + longtitude + "]";
	}

}
