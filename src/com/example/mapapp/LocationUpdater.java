package com.example.mapapp;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class LocationUpdater implements ConnectionCallbacks,
                OnConnectionFailedListener, LocationListener {

        private static LocationClient mLocationClient;
        private static final String TAG = "LocationUpdater";
        private static final LocationRequest REQUEST = LocationRequest.create()
                        .setInterval(5000) // 5 seconds
                        .setFastestInterval(16) // 16ms = 60fps
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        public void start(Context context) {
                setUpLocationClientIfNeeded(context);
                if (!mLocationClient.isConnected()) {
                        mLocationClient.connect();
                        Log.d(TAG, "mLocation Client CONNECTION INITIATE, activity->"
                                        + context.getApplicationContext().getClass());
                }
        }

        public void stop() {
                if (mLocationClient != null && mLocationClient.isConnected()) {
                        mLocationClient.disconnect();
                        Log.d(TAG, "mLocation Client DISCONNECTED");
                }
        }

        private void setUpLocationClientIfNeeded(Context context) {
                if (mLocationClient == null) {
                        mLocationClient = new LocationClient(context, this, // ConnectionCallbacks
                                        this); // OnConnectionFailedListener
                }
        }

        @Override
        public void onLocationChanged(Location location) {
                Globals.myLocation = location;
        }

        @Override
        public void onConnectionFailed(ConnectionResult arg0) {
                Log.d(TAG, "mLocation Client CONNECTION FAILED");
        }

        @Override
        public void onConnected(Bundle arg0) {
                Log.d(TAG, "mLocation Client CONNECTED");
                mLocationClient.requestLocationUpdates(REQUEST, this); // LocationListener
        }

        @Override
        public void onDisconnected() {
        }


	public static double[] getLastKnownLocation(Context context) {
		LocationManager lm = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}

		double[] gps = new double[2];
		if (l != null) {
			gps[0] = l.getLatitude();
			gps[1] = l.getLongitude();
		}
		return gps;
	}
}

