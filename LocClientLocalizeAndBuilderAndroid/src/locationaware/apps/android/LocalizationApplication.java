package locationaware.apps.android;

import java.io.File;

import localization.data.entity.contentobject.UserDataObject;
import locationaware.wifi.androidapi.AndroidSupplier;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LocalizationApplication extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	
	Button loginButton; 
	Button browseLocationButton;
	Button detectLocationButton;
	Button exitButton;
	
	Handler myHandler = new Handler();
	public static LocalizationApplication localizationApplication; 
	
	public static UserDataObject loginUser = null;

	public String getDirMapData() {
		return Prefs.getDirMapData(localizationApplication.getApplicationContext());
	}
	
	public String getServerHost() {
		return Prefs.getServerHost(localizationApplication.getApplicationContext());
	}

	public int getDetectWifiPort() {
		return Prefs.getDetectWifiPort(localizationApplication.getApplicationContext());
	}
	
	public int getSendMapPort() {
		return Prefs.getSendMapPort(localizationApplication.getApplicationContext());
	}
	
	public int getCallFunctionPort() {
		return Prefs.getCallFunctionPort(localizationApplication.getApplicationContext());
	}
	
	public int getCallObjectPort() {
		return Prefs.getCallObjectPort(localizationApplication.getApplicationContext());
	}

	public int getDetectGPSPort() {
		return Prefs.getDetectGPSPort(localizationApplication.getApplicationContext());
	}
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Set up click listeners for all the buttons
        loginButton = (Button) findViewById(R.id.main_login_button);
        loginButton.setOnClickListener(this);
        
        browseLocationButton = (Button) findViewById(R.id.main_browse_location_button);
        browseLocationButton.setOnClickListener(this);
        
        detectLocationButton = (Button) findViewById(R.id.main_detect_location_button);
        detectLocationButton.setOnClickListener(this);        
        
        exitButton = (Button) findViewById(R.id.main_exit_button);
        exitButton.setOnClickListener(this);

        String dirMapData = Environment.getExternalStorageDirectory().getAbsolutePath();
    	dirMapData += "/" + Prefs.getDirMapData(this);
    	(new File(dirMapData)).mkdir();
    	
    	AndroidSupplier.context = getApplicationContext();
    	
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		localizationApplication = this;
		if (loginUser == null) {
			browseLocationButton.setEnabled(false);
			browseLocationButton.setFocusable(false);
		} else {
			browseLocationButton.setEnabled(true);
			browseLocationButton.setFocusable(true);
		}
		
	};
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.settings:
		    	startActivity(new Intent(this, Prefs.class));
		    	return true;
		    	// More items go here (if any) ...
		}
	    
	    return false;
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	(Toast.makeText(this.getApplicationContext(),
				"On Destroy",
				Toast.LENGTH_LONG)).show();	
    	super.onDestroy();
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		
		switch (v.getId()) {
		case R.id.main_login_button:
			intent = new Intent(this, Login.class);
			startActivity(intent);
			break;
		
		case R.id.main_browse_location_button:
			ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo == null || !networkInfo.isConnected()) {
				myHandler.post(new FailConnectInternet());
				return;
			}

			intent = new Intent(this, BrowseLocation.class);
			startActivity(intent);
			break;
						
		case R.id.main_detect_location_button:
			intent = new Intent(this, DetectLocation.class);
			startActivity(intent);
			break;

		case R.id.main_exit_button:
			finish();
			System.exit(0);
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