package locationaware.apps.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BuildMapData extends Activity implements OnClickListener {

	public static final int WIFI = 0;
	public static final int GPS = 1;

	TextView userNameText;
	TextView locationText;

	Spinner listMapTypesSpinner;
	Button buildButton;

	@Override
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buildmap);

		userNameText = (TextView) findViewById(R.id.buildmap_username);
		locationText = (TextView) findViewById(R.id.buildmap_locationname);

		listMapTypesSpinner = (Spinner) findViewById(R.id.buildmap_list_maptypes);

		buildButton = (Button) findViewById(R.id.buildmap_build_button);
		buildButton.setOnClickListener(this);

		// set value for spinner listMapTypes
		ArrayList<String> listMapTypes = new ArrayList<String>();
		listMapTypes.add("Wifi Map");
		listMapTypes.add("GPS MAP");

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listMapTypes);
		listMapTypesSpinner.setAdapter(spinnerAdapter);

	}

	@Override
	public void onResume() {
		super.onResume();
//		UserDataObject user = LocalizationApplication.loginUser;
//		LocationDataObject location = BrowseLocation.selectedLocation;
		userNameText.setText(LocalizationApplication.loginUser.getUserName());
		locationText.setText(BrowseLocation.selectedLocation.getLocationName());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buildmap_build_button:
			Intent intent;

			switch (listMapTypesSpinner.getSelectedItemPosition()) {
			case WIFI:
				intent = new Intent(this, CollectWifiMapData.class);
				startActivity(intent);
				break;

			case GPS:
		        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					intent = new Intent(this, CollectGPSMapData.class);
					startActivity(intent);
				} else {
					Toast.makeText( getApplicationContext(),

							"Gps Disabled",

							Toast.LENGTH_SHORT ).show();
				}
				break;

			default:
				break;
			}

			break;

		default:
			break;
		}

	}

}
