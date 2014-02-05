package com.example.mapapp.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.DetectionRemover;
import com.example.mapapp.DetectionRequester;
import com.example.mapapp.R;
import com.example.mapapp.services.LocationUpdater;
import com.example.mapapp.utils.Constants;
import com.example.mapapp.utils.Globals;
import com.example.mapapp.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	private final String TAG = "MapActivity";
	private GoogleMap googleMap;
	private LocationUpdater myLocationUpdater = new LocationUpdater();
	private Handler handler = new Handler();
	public static TextView textView1;
	public static String currentActivity = null;

	/*
	 * Intent filter for incoming broadcasts from the IntentService.
	 */
	IntentFilter mBroadcastFilter;

	// Instance of a local broadcast manager
	private LocalBroadcastManager mBroadcastManager;

	// The activity recognition update request object
	private DetectionRequester mDetectionRequester;

	// The activity recognition update removal object
	private DetectionRemover mDetectionRemover;

	private Runnable updateMap = new Runnable() {

		@Override
		public void run() {
			if (Globals.myLocation != null) {
				googleMap.clear();
				Log.d(TAG, "Run Lat = " + Globals.myLocation.getLatitude()
						+ " Long = " + Globals.myLocation.getLongitude()
						+ " Current Time " + Globals.myLocation.getTime());

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(Globals.myLocation.getLatitude(),
								Globals.myLocation.getLongitude())).zoom(14)
						.build();

				googleMap.moveCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));

				googleMap.addMarker(new MarkerOptions()
						.position(
								new LatLng(Globals.myLocation.getLatitude(),
										Globals.myLocation.getLongitude()))
						.title("Location (" + Globals.myLocation.getLatitude()
								+ ", " + Globals.myLocation.getLongitude()
								+ ")")
						.snippet(
								Utils.getHumanReadableTime(Globals.myLocation
										.getTime())));
				addAccuracyCircle(Globals.myLocation);
				updateActivityHistory();

			}

			handler.postDelayed(this, 1000 * 60);
		}

	};

	private void addAccuracyCircle(Location l) {
		CircleOptions co = new CircleOptions();
		co.center(new LatLng(l.getLatitude(), l.getLongitude()));
		co.radius(l.getAccuracy());
		co.fillColor(TRIM_MEMORY_BACKGROUND);
		co.strokeColor(TRIM_MEMORY_BACKGROUND);
		co.strokeWidth(TRIM_MEMORY_BACKGROUND);
		googleMap.addCircle(co);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		textView1 = (TextView) findViewById(R.id.textView1);

		try {
			initializeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set the broadcast receiver intent filer
		mBroadcastManager = LocalBroadcastManager.getInstance(this);

		// Create a new Intent filter for the broadcast receiver
		mBroadcastFilter = new IntentFilter(
				Constants.ACTION_REFRESH_STATUS_LIST);
		//mBroadcastFilter.addCategory(Constants.CATEGORY_LOCATION_SERVICES);

		// Get detection requester and remover objects
		mDetectionRequester = new DetectionRequester(this);
		mDetectionRemover = new DetectionRemover(this);

		setInitialPosition();
	}

	private void setInitialPosition() {
		Location lastLocation = LocationUpdater
				.getLastKnownLocation(MapActivity.this);
		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(lastLocation.getLatitude(), lastLocation
						.getLongitude())).zoom(14).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		googleMap.addMarker(new MarkerOptions()
				.position(
						new LatLng(lastLocation.getLatitude(), lastLocation
								.getLongitude()))
				.title("Last Location (" + lastLocation.getLatitude() + ", "
						+ lastLocation.getLongitude() + ")")
				.snippet(Utils.getHumanReadableTime(lastLocation.getTime())));
		addAccuracyCircle(lastLocation);
	}

	private void initializeMap() {
		// Get a handle to the Map Fragment
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			} else {
				googleMap.setMyLocationEnabled(true);
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocationUpdater.start(MapActivity.this);

		// Pass the update request to the requester object
		mDetectionRequester.requestUpdates();
		handler.post(updateMap);

		// Register the broadcast receiver
		mBroadcastManager
				.registerReceiver(updateListReceiver, mBroadcastFilter);

		// Load updated activity history
		updateActivityHistory();
	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationUpdater.stop();
		handler.removeCallbacks(updateMap);

		// Pass the remove request to the remover object
		mDetectionRemover.removeUpdates(mDetectionRequester
				.getRequestPendingIntent());

		/*
		 * Cancel the PendingIntent. Even if the removal request fails,
		 * canceling the PendingIntent will stop the updates.
		 */
		mDetectionRequester.getRequestPendingIntent().cancel();

		// Stop listening to broadcasts when the Activity isn't visible.
		mBroadcastManager.unregisterReceiver(updateListReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	/**
	 * Set current activity
	 */
	public void updateActivityHistory() {
		Log.d(TAG, "Setting Update Text = " + currentActivity);
		textView1.setText(currentActivity);

	}

	/**
	 * Broadcast receiver that receives activity update intents It checks to see
	 * if the ListView contains items. If it doesn't, it pulls in history. This
	 * receiver is local only. It can't read broadcast Intents from other apps.
	 */
	BroadcastReceiver updateListReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			/*
			 * When an Intent is received from the update listener
			 * IntentService, update the displayed log.
			 */
			updateActivityHistory();
		}
	};
}
