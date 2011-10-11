package locationaware.apps.android;

import localization.data.entity.contentobject.PointDataObject;
import locationaware.clientserver.MapData;
import locationaware.gps.GPSCoordinate;
import locationaware.gps.mapdata.GPSMapData;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localization.server.CommonConfig;

public class CollectGPSMapData extends Activity implements OnClickListener{

    private LocationManager locationManager;
	private MyLocationListener locationListener;
    private String provider;
	
	TextView userNameTextView;
	TextView locationNameTextView;	
	TextView pointIDTextView;
	TextView pointOrderTextView;
	TextView distanceTextView;
	TextView gpsCoordinateEditText;
	
	Spinner listPoint;
	
	Button getGPSCoordinateButton;
	Button finishButton;
	
	GPSMapData gpsMapData;
	GPSCoordinate gpsCoordinate;
	private String mapDataFilePath;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectgps);
		
		userNameTextView = (TextView) findViewById(R.id.collectgps_username);
		locationNameTextView = (TextView) findViewById(R.id.collectgps_locationname);
		pointIDTextView = (TextView) findViewById(R.id.collectgps_pointID);
		pointOrderTextView = (TextView) findViewById(R.id.collectgps_pointorder);
		distanceTextView = (TextView) findViewById(R.id.collectgps_distance);
		gpsCoordinateEditText = (TextView) findViewById(R.id.collectgps_gps_coordinate);

		getGPSCoordinateButton = (Button) findViewById(R.id.collectgps_get_gps_coordinate_button);
		getGPSCoordinateButton.setOnClickListener(this);
		
		finishButton = (Button) findViewById(R.id.collectgps_finish_button);
		finishButton.setOnClickListener(this);
		
        //---use the LocationManager class to obtain GPS locations---
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
        
        locationListener = new MyLocationListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);

        locationManager.requestLocationUpdates(
                provider, 
                0, 
                0, 
                locationListener);
                
		listPoint = (Spinner) findViewById(R.id.collectgps_list_points);
		ArrayAdapter<PointDataObject> spinnerAdapter = new ArrayAdapter<PointDataObject>(this,
				android.R.layout.simple_spinner_item, BrowseLocation.selectedLocation.getPoints());
		listPoint.setAdapter(spinnerAdapter);
		
		listPoint.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (listPoint == null || pointIDTextView == null || pointOrderTextView == null || gpsCoordinateEditText == null) {
					return;
				}
				PointDataObject point = (PointDataObject) listPoint.getSelectedItem();
				if (point == null) {
					return;
				}
				pointIDTextView.setText(point.getPointId());
				pointOrderTextView.setText(point.getPointOrder()+"");
				gpsCoordinateEditText.setText(gpsMapData.getMappingGPStoPoint().get(point.getPointId()) + "");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
        String dirMapData = Environment.getExternalStorageDirectory().getAbsolutePath();
    	dirMapData += "/" + Prefs.getDirMapData(this);
		mapDataFilePath = dirMapData + "/"
		+ BrowseLocation.selectedLocation.getLocationId() + "_" + LocalizationApplication.loginUser.getUserId() + "_" + BrowseMapData.GPSMAPDATA
		+ CommonConfig.extensionMapDataFile;
		userNameTextView.setText(LocalizationApplication.loginUser.getUserName());
		locationNameTextView.setText(BrowseLocation.selectedLocation.getLocationName());
        
		gpsCoordinate = new GPSCoordinate(0, 0, 0);
        gpsMapData = new GPSMapData();
        gpsMapData.setLocationId(BrowseLocation.selectedLocation.getLocationId());
        gpsMapData.setUserId(LocalizationApplication.loginUser.getUserId());
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.collectgps_get_gps_coordinate_button:
	        gpsCoordinateEditText.setText(gpsCoordinate + "");
	        int previousIndex = listPoint.getSelectedItemPosition() - 1;
	        if (previousIndex == -1) 
	        	previousIndex = BrowseLocation.selectedLocation.getPoints().size() - 1;
	        GPSCoordinate previousGPS = gpsMapData.getMappingGPStoPoint().get(((PointDataObject) listPoint.getItemAtPosition(previousIndex)).getPointId());
	        distanceTextView.setText(distanceGPS(gpsCoordinate, previousGPS) + " m");
	        GPSCoordinate tempGpsCoordinate = new GPSCoordinate(gpsCoordinate.getAltitude(), gpsCoordinate.getLatitude(), gpsCoordinate.getLongitude());
	        gpsMapData.getMappingGPStoPoint().put(((PointDataObject)listPoint.getSelectedItem()).getPointId(), tempGpsCoordinate);
			break;

		case R.id.collectgps_finish_button:
			MapData.writeMapData(gpsMapData, mapDataFilePath);
			break;
		default:
			break;
		}
	}

	private double distanceGPS(GPSCoordinate gpsCoordinate2,
			GPSCoordinate previousGPS) {
		if (previousGPS == null || gpsCoordinate2 == null) {
			return 0;
		}
		
		double R = 6371; //km
		double dLat = (previousGPS.getLatitude()-gpsCoordinate2.getLatitude());
		double dLon = (previousGPS.getLongitude()-gpsCoordinate2.getLongitude()); 
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.cos(gpsCoordinate2.getLatitude()) * Math.cos(previousGPS.getLatitude()) * 
		        Math.sin(dLon/2) * Math.sin(dLon/2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R * c;
		return d*1000;
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			gpsCoordinate.setAltitude(location.getAltitude());
			gpsCoordinate.setLatitude(location.getLatitude());
			gpsCoordinate.setLongitude(location.getLongitude());
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText( getApplicationContext(),

					"Gps Disabled",

					Toast.LENGTH_SHORT ).show();
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText( getApplicationContext(),

					"Gps Enabled",

					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
