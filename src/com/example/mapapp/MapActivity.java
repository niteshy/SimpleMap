package com.example.mapapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {

	private final String tag = "MapAcitivity";
	private GoogleMap googleMap;
	private LocationUpdater myLocationUpdater = new LocationUpdater();
	private Handler handler = new Handler();

	private Runnable updateMap = new Runnable() {

		@Override
		public void run() {
			if (Globals.myLocation != null) {
				Log.d(tag, "Run Lat = " + Globals.myLocation.getLatitude()
						+ " Long = " + Globals.myLocation.getLongitude());

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(Globals.myLocation.getLatitude(),
								Globals.myLocation.getLongitude())).zoom(12)
						.build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
				
				final Marker m = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Globals.myLocation.getLatitude(),
								Globals.myLocation.getLongitude()))
                .title("Latitude = " + Globals.myLocation.getLatitude() + " | Longitude = " + Globals.myLocation.getLongitude())
                .snippet(Utils.getHumanReadableTime(Globals.myLocation.getTime())));

			}

			handler.postDelayed(this, 1000 * 1);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		try {
			initializeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.post(updateMap);

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
			} else
				googleMap.setMyLocationEnabled(true);

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocationUpdater.start(MapActivity.this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationUpdater.stop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
