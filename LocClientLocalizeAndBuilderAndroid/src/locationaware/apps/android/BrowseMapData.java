package locationaware.apps.android;

import java.io.File;
import java.util.ArrayList;

import locationaware.client.ClientCalibration;
import locationaware.client.ClientMapDataSender;
import locationaware.clientserver.MapData;
import locationaware.wifi.mapdata.Vd2_3MapData;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localization.server.CommonConfig;

public class BrowseMapData extends Activity implements OnClickListener {

	public static final String VD2_3MAPDATA = "Vd2_3MapData";
	public static final String GPSMAPDATA = "GPSMapData";

	TextView userNameText;
	TextView locationText;
//	Spinner listMapPlace;
	Spinner listMapTypes;
	EditText mapContentEditText;
	Button uploadButton;
	Button calibrateButton;
	
	Handler myHandler = new Handler();
	
    ClientMapDataSender sender = new ClientMapDataSender();
    ClientCalibration calibrate = new ClientCalibration();
	private String mapDataFilePath;

	@Override
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browsemap);

		userNameText = (TextView) findViewById(R.id.browsemap_username);
		locationText = (TextView) findViewById(R.id.browsemap_locationname);

//		listMapPlace = (Spinner) findViewById(R.id.browsemap_list_mapplace);
		listMapTypes = (Spinner) findViewById(R.id.browsemap_list_mapdatatypes);

		mapContentEditText = (EditText) findViewById(R.id.browsemap_map_content);

		uploadButton = (Button) findViewById(R.id.browsemap_upload_button);
		uploadButton.setOnClickListener(this);
		
		calibrateButton = (Button) findViewById(R.id.browsemap_calibrate_button);
		calibrateButton.setOnClickListener(this);

		// set value for spinner listMapPlace
//		ArrayList<String> listPlaces = new ArrayList<String>();
//		listPlaces.add("Client");
//		listPlaces.add("Server");
//
//		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, listPlaces);
//		listMapPlace.setAdapter(spinnerAdapter);

		ArrayList<String> listMapClients = new ArrayList<String>();
		listMapClients.add(VD2_3MAPDATA);
		listMapClients.add(GPSMAPDATA);
		ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listMapClients);
		listMapTypes.setAdapter(spinnerAdapter2);

		listMapTypes.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
		        String dirMapData = Environment.getExternalStorageDirectory().getAbsolutePath();
		    	dirMapData += "/" + Prefs.getDirMapData(getApplicationContext());
				mapDataFilePath = dirMapData + "/"
				+ BrowseLocation.selectedLocation.getLocationId() + "_" + LocalizationApplication.loginUser.getUserId() + "_" + listMapTypes.getSelectedItem()
				+ CommonConfig.extensionMapDataFile;    
				
				mapContentEditText.setText(MapData.readMapData(mapDataFilePath) + "");
				
				if (listMapTypes.getSelectedItem().equals(VD2_3MAPDATA)) {
					calibrateButton.setClickable(true);
				} else {
					calibrateButton.setClickable(false);
				}
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	};

	@Override
	public void onResume() {
		super.onResume();

        sender.setPort(Prefs.getSendMapPort(getApplicationContext()));
        sender.setServerHost(Prefs.getServerHost(getApplicationContext()));
        
        calibrate.setPort(Prefs.getDetectWifiPort(getApplicationContext()));
        calibrate.setServerHost(Prefs.getServerHost(getApplicationContext()));
		
        String dirMapData = Environment.getExternalStorageDirectory().getAbsolutePath();
    	dirMapData += "/" + Prefs.getDirMapData(getApplicationContext());
		mapDataFilePath = dirMapData + "/"
		+ BrowseLocation.selectedLocation.getLocationId() + "_" + LocalizationApplication.loginUser.getUserId() + "_" + listMapTypes.getSelectedItem()
		+ CommonConfig.extensionMapDataFile;  
		
		userNameText.setText(LocalizationApplication.loginUser.getUserName());
		locationText.setText(BrowseLocation.selectedLocation.getLocationName());
		
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {
			myHandler.post(new FailConnectInternet());
			return;
		}
		
		switch (v.getId()) {
		case R.id.browsemap_upload_button:
			new Thread(new Upload(this)).start();
			break;
			
		case R.id.browsemap_calibrate_button:
			new Thread(new Calibrate(this)).start();
			break;

		default:
			break;
		}
	}
	
	public class Upload implements Runnable {
		private Activity currentActivity;
		
		public Upload(Activity current) {
			this.currentActivity = current;
		}
		
		@Override
		public void run() {
	        File fileMapData = new File(mapDataFilePath);

	        if (fileMapData.exists()) {
	            MapData mapData = MapData.readMapData(mapDataFilePath);
	            myHandler.post(new MyToast(currentActivity, sender.sendMapData(mapData)));
	        }								
		}
		
	}

	public class MyToast implements Runnable {

		private Activity currentActivity;
		private boolean flag;
		
		public MyToast(Activity current, boolean flag) {
			this.currentActivity = current;
			this.flag = flag;
		}
		
		@Override
		public void run() {
			String message = null;
			if (flag) {
				message = "Upload Success";
			} else {
				message = "Upload Fail";
			}
			
			(Toast.makeText(currentActivity.getApplicationContext(),
					message,
					Toast.LENGTH_LONG)).show();			
		}
		
	}
	
	public class Calibrate implements Runnable {
		private Activity currentActivity;
		
		public Calibrate(Activity current) {
			this.currentActivity = current;
		}
		
		@Override
		public void run() {
	        File fileMapData = new File(mapDataFilePath);

	        if (fileMapData.exists()) {
	            MapData mapData = MapData.readMapData(mapDataFilePath);
	            ArrayList<Vd2_3MapData> vd2_3MapDatasArrayList = new ArrayList<Vd2_3MapData>();
	            vd2_3MapDatasArrayList.add((Vd2_3MapData) mapData);
	            myHandler.post(new MyToastCalibrate(currentActivity, calibrate.calirate(vd2_3MapDatasArrayList)));
	        }								
		}
		
	}

	public class MyToastCalibrate implements Runnable {

		private Activity currentActivity;
		private boolean flag;
		
		public MyToastCalibrate(Activity current, boolean flag) {
			this.currentActivity = current;
			this.flag = flag;
		}
		
		@Override
		public void run() {
			String message = null;
			if (flag) {
				message = "Calibration Success";
			} else {
				message = "Calibration Fail";
			}
			
			(Toast.makeText(currentActivity.getApplicationContext(),
					message,
					Toast.LENGTH_LONG)).show();			
		}
		
	}
	
	private class FailConnectInternet implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			(Toast.makeText(getApplicationContext(),
					"Fail to connect internet",
					Toast.LENGTH_LONG)).show();		}
	}


}
