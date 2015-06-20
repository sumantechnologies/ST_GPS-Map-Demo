package st.gpsmap;



import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import st.gpsmap.R;

public class SupportMapDemo extends FragmentActivity {
	 GoogleMap googleMap;
	Context aContext=SupportMapDemo.this;
	Handler mHandler = new Handler();
	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.supportmap);
 		try{
 	 		initilizeMap();

 			}
 		 catch (Exception e) {
 			e.printStackTrace();
 		}
 	 		 mHandler.postDelayed(mUpdateTimeTask, 1000);

 		
 	}
	@Override
	protected void onRestart() {
	    super.onRestart();  // Always call the superclass method first
	    mHandler.postDelayed(mUpdateTimeTask, 1000);
	    // Activity being restarted from stopped state    
	}
	private Runnable mUpdateTimeTask = new Runnable() {
	    public void run() {
	    	
	mapfilllocation();
	    	mHandler.postDelayed(this,2* 1000);
	    }
	   };
	public void onStop() {
		super.onStop();
		 mHandler.removeCallbacks(mUpdateTimeTask);
	}

@Override
protected void onResume() {
	super.onResume();
	initilizeMap();
}
public void mapfilllocation(){
	// Changing map type
	try
	{
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

			// Showing / hiding your current location
			

				// random latitude and logitude
				double[] randomLocation = createRandLocation();
				MarkerOptions marker = null;
				// Adding a marker
				

				Log.e("Random", "> " + randomLocation[0] + ", "
						+ randomLocation[1]);

				 marker = new MarkerOptions().position(
							new LatLng(randomLocation[0], randomLocation[1]))
							.title("You are Here !!!");
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				googleMap.addMarker(marker);
				
				// Move the camera to last position with a zoom level
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(randomLocation[0],
									randomLocation[1])).zoom(15).build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				

		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
/**
 * function to load map If map is not created it will create it for you
 * */
private double[] createRandLocation() {
	GPSTracker gps;
	 gps = new GPSTracker(aContext);
	 return new double[] { gps.latitude,gps.longitude };
	  
	
}

private void initilizeMap() {
	try
	{
	if (googleMap == null) {
		//googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
		SupportMapFragment supportMapFragment = (SupportMapFragment) 
				getSupportFragmentManager().findFragmentById(R.id.mapz);
		googleMap= supportMapFragment.getMap();
		  googleMap.setMyLocationEnabled(true);
		
		  // check if map is created successfully or not
		if (googleMap == null) {
			Toast.makeText(getApplicationContext(),
					"Sorry! unable to create maps", Toast.LENGTH_SHORT)
					.show();
		}
	}
	//googleMap.setMyLocationEnabled(true);

	// Enable / Disable zooming controls
	googleMap.getUiSettings().setZoomControlsEnabled(false);

	// Enable / Disable my location button
	googleMap.getUiSettings().setMyLocationButtonEnabled(true);

	// Enable / Disable Compass icon
	googleMap.getUiSettings().setCompassEnabled(true);

	// Enable / Disable Rotate gesture
	googleMap.getUiSettings().setRotateGesturesEnabled(true);

	// Enable / Disable zooming functionality
	googleMap.getUiSettings().setZoomGesturesEnabled(true);

	
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}
