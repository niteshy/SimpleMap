package com.example.mapapp;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class LocationUpdater implements LocationListener {

	private LocationManager locationManager;

	public void start(Context context) {

		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);

		List<String> providers = locationManager.getProviders(false);
		if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, this);
		}
		if (providers.contains(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Globals.myLocation = location;
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void stop() {
		if (locationManager != null)
			locationManager.removeUpdates(this);
	}

	public boolean isLocationProviderEnabled(final Context context) {
		boolean locationEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!locationEnabled) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("No location access");
			builder.setMessage("Please allow to access your location. Turn it ON from Location Services");
			builder.setCancelable(false);
			builder.setPositiveButton("Settings",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int id) {
							context.startActivity(new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							dialog.cancel();
						}
					});
			builder.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int id) {
							dialog.cancel();
						}
					});
			final AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
		return true;
	}

	public boolean isLocationNonNull(final Context context) {
		if (Globals.myLocation == null) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Sorry! We could not locate you. Please try again!");
			builder.setCancelable(false);
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								final int id) {
							dialog.cancel();
						}
					});
			final AlertDialog alert = builder.create();
			alert.show();
			return false;
		}
		return true;
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

