package locationaware.apps.android;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import locationaware.client.ClientLocationGPSDetector;
import locationaware.client.ClientLocationWifiDetector;
import locationaware.clientserver.Location;
import locationaware.eventlistener.NewResultComingEventListener;
import locationaware.myevent.NewResultComingEvent;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetectLocation extends Activity implements OnClickListener{

	Spinner listTypeSpinner;
	CheckBox convertCheckbox;
	Button startLocalizeButton;
	Button stopLocalizeButton;
	
	TextView locationNameText;
	TextView rootLocationText;
	TextView userNameText;
	TextView calibrateText;
	TextView resultTimeText;
	int countInt = 0;
	long lastGetResultTime = 0;
	
	ClientLocationWifiDetector wifiDetector = new ClientLocationWifiDetector();
	ClientLocationGPSDetector gpsDetector = new ClientLocationGPSDetector(this);
	
	Handler myHandler = new Handler();
	
	Runnable updateUI = new Runnable() {

		@Override
		public void run() {
			String rootLocationName = "", locationName = "", userName = "", calibrate = "", resultTime = "";
			
			if (((String)listTypeSpinner.getSelectedItem()).equals("WIFI")) {
				if (!wifiDetector.getListDetectedLocation().isEmpty()) {
					for (Location rootLocation : wifiDetector.getListDetectedLocation().keySet()) {
						if (!wifiDetector.getListDetectedLocation().get(rootLocation).isEmpty()) {
							Date date = (new Date(lastGetResultTime));
							resultTime = "No_" + countInt + "-Time_" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds(); 
							rootLocationName = rootLocation.getLocationName();
							locationName = wifiDetector.getListDetectedLocation().get(rootLocation).get(0).getLocationName();
							userName = rootLocation.getUserName();
							int size = wifiDetector.getListDetectedLocation().get(rootLocation).size();
							if (wifiDetector.getListDetectedLocation().get(rootLocation).get(size - 1) == null) {
								calibrate = "not yet";
							} else if (convertCheckbox.isChecked()) {
								calibrate = "already";
							}
							//only return the first location
							break;
						} else {
							Date date = (new Date(lastGetResultTime));
							resultTime = "No_" + countInt + "-Time_" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
							rootLocationName = "Unknown";
							locationName = "Unknown";														
						}
					}
				}
			} else {
				if (!gpsDetector.getListDetectedLocation().isEmpty()) {
					for (String rootLocation : gpsDetector.getListDetectedLocation().keySet()) {
						if (!gpsDetector.getListDetectedLocation().get(rootLocation).isEmpty()) {
							Date date = (new Date(lastGetResultTime));
							resultTime = "No_" + countInt + "-Time_" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
							rootLocationName = rootLocation;
							locationName = gpsDetector.getListDetectedLocation().get(rootLocation).get(0).getLocationName();
							//only return the first location
							break;
						} else {
							Date date = (new Date(lastGetResultTime));
							resultTime = "No_" + countInt + "-Time_" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
							rootLocationName = "Unknown";
							locationName = "Unknown";							
						}
					}
				}
			}

			
			locationNameText.setText(locationName);
			rootLocationText.setText(rootLocationName);
			userNameText.setText(userName);
			calibrateText.setText(calibrate);
			resultTimeText.setText(resultTime);
		}
	};

	
	@Override
	public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect);
        
        listTypeSpinner = (Spinner) findViewById(R.id.detect_list_types);

        ArrayList<String> listType = new ArrayList<String>();
		listType.add("GPS");
		listType.add("WIFI");

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listType);
		listTypeSpinner.setAdapter(spinnerAdapter);
		
		convertCheckbox = (CheckBox) findViewById(R.id.detect_convert);
        
        startLocalizeButton = (Button) findViewById(R.id.detect_start_localize_button);
        startLocalizeButton.setOnClickListener(this);
        
        stopLocalizeButton = (Button) findViewById(R.id.detect_stop_localize_button);
        stopLocalizeButton.setOnClickListener(this);
        
        locationNameText = (TextView) findViewById(R.id.detect_locationname);
        rootLocationText = (TextView) findViewById(R.id.detect_rootlocation);
        userNameText = (TextView) findViewById(R.id.detect_userName);
        calibrateText = (TextView) findViewById(R.id.detect_calibrateStatus);
        resultTimeText = (TextView) findViewById(R.id.detect_result_time);
        
        wifiDetector.setFrequency(1.0);
        wifiDetector.setServerHost(Prefs.getServerHost(getApplicationContext()));
        wifiDetector.setPort(Prefs.getDetectWifiPort(getApplicationContext()));
        wifiDetector.setDetectAtClient(false);
        wifiDetector.setConvert(false);
        wifiDetector.setConfidence(90);
        wifiDetector.addNewResultComingEventListener(new NewResultComingEventListener() {

            public void handleEvent(NewResultComingEvent nrce) {
            	myHandler.post(updateUI);
            	countInt += 1;
            	lastGetResultTime = Calendar.getInstance().getTimeInMillis();
            }
        });
        
        gpsDetector.setServerHost(Prefs.getServerHost(getApplicationContext()));
        gpsDetector.setPort(Prefs.getDetectWifiPort(getApplicationContext()));
        gpsDetector.setConfidence(90);
        gpsDetector.addNewResultComingEventListener(new NewResultComingEventListener() {

            public void handleEvent(NewResultComingEvent nrce) {
            	myHandler.post(updateUI);
            	countInt += 1;
            	lastGetResultTime = Calendar.getInstance().getTimeInMillis();
            	
            }
        });        

	};
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.detect_start_localize_button:
			
			ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo == null || !networkInfo.isConnected()) {
				myHandler.post(new FailConnectInternet());
				return;
			}
			
			if (((String)listTypeSpinner.getSelectedItem()).equals("GPS")) {
		        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					Toast.makeText( getApplicationContext(),

							"Gps Disabled",

							Toast.LENGTH_SHORT ).show();
					return;
				}
			}
			
			startLocalizeButton.setEnabled(false);
			startLocalizeButton.setFocusable(false);
			stopLocalizeButton.setEnabled(true);
			stopLocalizeButton.setFocusable(true);
			listTypeSpinner.setEnabled(false);
			listTypeSpinner.setFocusable(false);
			
			countInt = 0;
			
			if (((String)listTypeSpinner.getSelectedItem()).equals("WIFI")) {
				wifiDetector.setConvert(convertCheckbox.isChecked());
				wifiDetector.startDetectLocation();
			} else {
				gpsDetector.startDetectLocation();
			}
			break;
		
		case R.id.detect_stop_localize_button:
			startLocalizeButton.setEnabled(true);
			startLocalizeButton.setFocusable(true);
			stopLocalizeButton.setEnabled(false);
			stopLocalizeButton.setFocusable(false);
			listTypeSpinner.setEnabled(true);
			listTypeSpinner.setFocusable(true);

			if (((String)listTypeSpinner.getSelectedItem()).equals("WIFI")) {
				wifiDetector.stopDectectLocation();
			} else {
				gpsDetector.stopDectectLocation();
			}
			break;
						
		default:
			break;
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
