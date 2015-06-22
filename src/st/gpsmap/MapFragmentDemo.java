package st.gpsmap;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import st.gpsmap.R;

public class MapFragmentDemo extends Activity{
	 GoogleMap googleMap;
	Context aContext=MapFragmentDemo.this;
	Handler mHandler = new Handler();

	@Override
 	protected void onCreate(Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setContentView(R.layout.map);
 		try{
 		ImageView download = (ImageView) findViewById(R.id.download);
 		download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Uri uri = Uri.parse("https://github.com/sumantechnologies/ST_GPS-Map-Demo");
            	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            	startActivity(intent);
            }
        });
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
	    	//createRandLocation();
	    	mHandler.postDelayed(this,2* 1000);
	    }
	   };
	public void onStop() {
		super.onStop();
		//Toast.makeText(LiveSingles.this,"Stop..." ,Toast.LENGTH_LONG).show();
		 mHandler.removeCallbacks(mUpdateTimeTask);
	}

@Override
protected void onResume() {
	super.onResume();
	initilizeMap();
}
public void mapfilllocation(){
	try
	{
	// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			
			// Showing / hiding your current location
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

			double latitude = 17.385044;
			double longitude = 78.486671;

				// random latitude and logitude
				double[] randomLocation = createRandLocation(latitude,
						longitude);
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
 **/
 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
 private void initilizeMap() {
	 try
	 {
	if (googleMap == null) {
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		// check if map is created successfully or not
		if (googleMap == null) {
			Toast.makeText(getApplicationContext(),
					"Sorry! unable to create maps", Toast.LENGTH_SHORT)
					.show();
		}
	}

	
}
catch(Exception e)
{
e.printStackTrace();
}
 		
 	}
 	
private double[] createRandLocation(double latitude, double longitude) {
	GPSTracker gps;
	 gps = new GPSTracker(aContext);
	 return new double[] { gps.latitude,gps.longitude };
	  
	/*return new double[] { gps.latitude + ((Math.random() - 0.5) / 500),
			gps.longitude + ((Math.random() - 0.5) / 500),
			150 + ((Math.random() - 0.5) * 10) };*/
}
}
