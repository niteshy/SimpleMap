package com.example.mapapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class MapActivity extends Activity {

	private GoogleMap googleMap;
	private LocationUpdater myLocationUpdater; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		try {
			initializeMap();  
			myLocationUpdater.start(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            }
           
            googleMap.setMyLocationEnabled(true);

        }

	}
	
	@Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
