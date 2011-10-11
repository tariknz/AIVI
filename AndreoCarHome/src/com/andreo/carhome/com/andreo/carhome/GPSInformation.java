package com.andreo.carhome.com.andreo.carhome;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.util.Log;

public class GPSInformation {

	private LocationManager lm;
	private LocationListener ll;

	private float speed;

	public GPSInformation(LocationManager locationManager){
		lm = locationManager;
		ll = new LocationListener() {

			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub

			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onLocationChanged(Location location) {
				try{
					setSpeed(location.getSpeed());
				}catch(Exception e){
					Log.e("AndreoCarHome","Error getting speed: " + e);
				}
			}
		};
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);

	}

	private void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getSpeed() {
		return speed;
	}


}
