package locationaware.apps.android;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import locationaware.clientserver.MapData;
import locationaware.wifi.androidapi.AndroidSupplier;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.utilities.LocationWifiDataCollector;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.localization.server.CommonConfig;

public class CollectWifiMapData extends Activity implements OnClickListener {
	LocationWifiDataCollector collector;

	private static final String ELAPSE = "elapse";

	TextView userNameTextView;
	TextView locationNameTextView;	
	TextView numberOfEntries;
	TextView elapseTime;
	
	EditText minTimeEditText;
	EditText minEntriesEditText;
	EditText frequencyEditText;
	
	Button startScanButton;
	Button endScanButton;
	
	Handler myHandler = new Handler();
	
	double frequency;
	int elapse;
	boolean overwrite = false;
	String mapDataFilePath = "";
	
	AlertDialog overwriteAlertDialog;
	AlertDialog finishAlertDialog;

	int timerPeriod = 3; // seconds
	Timer timer;
	TimerTask timerTask;
	
	Runnable updateUI1 = new Runnable() {

		@Override
		public void run() {
			numberOfEntries.setText(collector.getNumberOfSPs() + "");
			elapseTime.setText(elapse + " seconds");
		}
	};

	Runnable updateUI2 = new Runnable() {

		@Override
		public void run() {
			numberOfEntries.setText("0");
			elapseTime.setText("0 seconds");
			finishAlertDialog.show();
			startScanButton.setEnabled(true);
			startScanButton.setFocusable(true);
			timerTask.cancel();
			timerTask = new TimerTask() {

				@Override
				public void run() {
					if (collector.isActive()) {
						elapse += 3;
						myHandler.post(updateUI1);
					} else {
						myHandler.post(updateUI2);
					}
				}
			};
			
            Vd2_3MapData vd2_3MapData = Vd2_3MapData.createVd2_3MapData(collector.getWifiMapData());
            MapData.writeMapData(vd2_3MapData, mapDataFilePath);
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectwifi);
		
		collector = (LocationWifiDataCollector) this.getLastNonConfigurationInstance();
		
		userNameTextView = (TextView) findViewById(R.id.collectwifi_username);
		locationNameTextView = (TextView) findViewById(R.id.collectwifi_locationname);
		numberOfEntries = (TextView) findViewById(R.id.collectwifi_number_of_entries);
		elapseTime = (TextView) findViewById(R.id.collectwifi_elapse_time);

		minEntriesEditText = (EditText) findViewById(R.id.collectwifi_minentries_edittext);
		minTimeEditText = (EditText) findViewById(R.id.collectwifi_mintime_edittext);
		frequencyEditText = (EditText) findViewById(R.id.collectwifi_frequency_edittext);
		
		startScanButton = (Button) findViewById(R.id.collectwifi_start_scan_button);
		startScanButton.setOnClickListener(this);
		endScanButton = (Button) findViewById(R.id.collectwifi_end_scan_button);
		endScanButton.setOnClickListener(this);

		timer = new Timer(true);
		timerTask = new TimerTask() {

			@Override
			public void run() {
				if (collector.isActive()) {
					elapse += 3;
					myHandler.post(updateUI1);
				} else {
					myHandler.post(updateUI2);
				}
			}
		};
		
		if (collector == null ) {
			collector = new LocationWifiDataCollector();
		} else if (collector.isActive()) {
			startScanButton.setEnabled(false);
			startScanButton.setFocusable(false);
						
			elapse = getPreferences(MODE_PRIVATE).getInt(ELAPSE, 0);
			timer.schedule(timerTask, timerPeriod * 1000,
					timerPeriod * 1000);
		}
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage("This location has already been collected data.\nDo you want overwrite old data?");
		alertDialogBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						startCollecting();
						dialog.cancel();
					}
				});
		alertDialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
					}
				});
		overwriteAlertDialog = alertDialogBuilder.create();
		
		alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("Finish collecting!");
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		finishAlertDialog = alertDialogBuilder.create();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        String dirMapData = Environment.getExternalStorageDirectory().getAbsolutePath();
    	dirMapData += "/" + Prefs.getDirMapData(this);
		mapDataFilePath = dirMapData + "/"
		+ BrowseLocation.selectedLocation.getLocationId() + "_" + LocalizationApplication.loginUser.getUserId() + "_" + BrowseMapData.VD2_3MAPDATA
		+ CommonConfig.extensionMapDataFile;
		userNameTextView.setText(LocalizationApplication.loginUser.getUserName());
		locationNameTextView.setText(BrowseLocation.selectedLocation.getLocationName());

		minEntriesEditText.setText("" + Prefs.getNumberEntries(this.getApplicationContext()));
		minTimeEditText.setText("" + Prefs.getScanningTime(this.getApplicationContext()));
		frequencyEditText.setText("" + Prefs.getFrequency(this.getApplicationContext()));
	};	

	@Override
	protected void onPause() {
		super.onPause();
		getPreferences(MODE_PRIVATE).edit().putInt(ELAPSE, elapse).commit();
		timerTask.cancel();
		timer.cancel();
	};
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
	}

	@Override
	protected void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
	}
	
	@Override 
	public Object onRetainNonConfigurationInstance() {
		return collector;
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.collectwifi_start_scan_button:

			if (!collector.isActive()) {

				WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				if (!wifiManager.isWifiEnabled()) {
					myHandler.post(new FailWifiNotEnable());
					return;
				}

				
				int min;

				min = Integer.parseInt(minTimeEditText.getText().toString());
				if (min < 0) {
					(Toast.makeText(getApplicationContext(),
							"Error: Minimum Collection Period",
							Toast.LENGTH_LONG)).show();
					return;
				}
				collector.setMinTimeInSecond(min);

				min = Integer.parseInt(minEntriesEditText.getText().toString());
				if (min < 0) {
					(Toast.makeText(getApplicationContext(),
							"Error: Minimum Number of Entries",
							Toast.LENGTH_LONG)).show();
					return;
				}
				collector.setMinNumberOfSPs(min);

				frequency = Integer.parseInt(frequencyEditText.getText().toString());

				if (frequency < 0) {
					(Toast.makeText(getApplicationContext(),
							"Error: Frequency", Toast.LENGTH_LONG)).show();
					return;
				}

				File file = new File(mapDataFilePath);

				if (file.exists()) {
					overwriteAlertDialog.show();
				} else {
					startCollecting();
				}
			}

			break;
			
		case R.id.collectwifi_end_scan_button:
			numberOfEntries.setText("0");
			elapseTime.setText("0 seconds");
			finishAlertDialog.show();
			startScanButton.setEnabled(true);
			startScanButton.setFocusable(true);
			timerTask.cancel();
			timerTask = new TimerTask() {

				@Override
				public void run() {
					if (collector.isActive()) {
						elapse += 3;
						myHandler.post(updateUI1);
					} else {
						myHandler.post(updateUI2);
					}
				}
			};
			
            Vd2_3MapData vd2_3MapData = Vd2_3MapData.createVd2_3MapData(collector.getWifiMapData());
            MapData.writeMapData(vd2_3MapData, mapDataFilePath);            
			break;

		default:
			break;
		}
	}
	
	private void startCollecting() {
		startScanButton.setEnabled(false);
		startScanButton.setFocusable(false);

		AndroidSupplier.context = getApplicationContext();
	
		collector.startCollecting(null,
				BrowseLocation.selectedLocation.getLocationId(), LocalizationApplication.loginUser.getUserId(), false, frequency);

		timer.purge();
		timer.schedule(timerTask, timerPeriod * 1000,
				timerPeriod * 1000);
		elapse = 0;		
	}
	
	private class FailWifiNotEnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			(Toast.makeText(getApplicationContext(),
					"You have not enable wifi yet!",
					Toast.LENGTH_LONG)).show();		}
	}


}
