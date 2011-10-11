package locationaware.apps.android;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import localization.data.entity.contentobject.LocationDataObject;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class BrowseLocation extends Activity implements OnClickListener {

	TextView userNameTView;
	Spinner listLocationsSpinner;

	Button buildMapDataButton;
	Button browseMapDataButton;

	public static LocationDataObject rootLocation = null;
	public static LocationDataObject selectedLocation;
	public static TreeMap<Integer, LocationDataObject> locationTreeMap;

	@Override
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse);

		userNameTView = (TextView) findViewById(R.id.browse_username);
		listLocationsSpinner = (Spinner) findViewById(R.id.browse_list_locations);

		buildMapDataButton = (Button) findViewById(R.id.browse_build_map_data_button);
		buildMapDataButton.setOnClickListener(this);

		browseMapDataButton = (Button) findViewById(R.id.browse_browse_map_data_button);
		browseMapDataButton.setOnClickListener(this);

		// create local list root locations
		List<LocationDataObject> locationList = LocalizationApplication.loginUser
				.getLocations();

		rootLocation = new LocationDataObject();
		rootLocation.setLocationId("" + 0);
		rootLocation.setLocationName("Root");
		rootLocation.setParentLocationId(null);
		rootLocation.setLocations(new ArrayList<LocationDataObject>());

		locationTreeMap = new TreeMap<Integer, LocationDataObject>();
		locationTreeMap.put(0, rootLocation);

		for (LocationDataObject locationDataObject : locationList) {
			locationDataObject
					.setLocations(new ArrayList<LocationDataObject>());
			locationTreeMap.put(
					Integer.parseInt(locationDataObject.getLocationId()),
					locationDataObject);
		}

		for (LocationDataObject locationDataObject : locationList) {
			if (locationDataObject.getParentLocationId() == null) {
				locationDataObject.setParentLocationId("" + 0);
				rootLocation.getLocations().add(locationDataObject);
			} else {
				LocationDataObject rootLocationDataObject = locationTreeMap
						.get(Integer.parseInt(locationDataObject
								.getParentLocationId()));
				if (rootLocationDataObject != null) {
					rootLocationDataObject.getLocations().add(
							locationDataObject);
				}
			}
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		userNameTView.setText(LocalizationApplication.loginUser.getUserName());

		ArrayList<LocationDataObject> listLocationDO = new ArrayList<LocationDataObject>();

		listLocationDO.add(rootLocation);

		for (LocationDataObject locationDataObject : rootLocation
				.getLocations()) {
			listLocationDO.add(locationDataObject);
		}

		ArrayAdapter<LocationDataObject> spinnerAdapter = new ArrayAdapter<LocationDataObject>(
				this, android.R.layout.simple_spinner_item, listLocationDO);
		listLocationsSpinner.setAdapter(spinnerAdapter);

		listLocationsSpinner
				.setOnItemSelectedListener(new MyOnItemSelectedListener(this));
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;

		if (!selectedLocation.getLocationId().equals("0")) {
			switch (v.getId()) {
			case R.id.browse_build_map_data_button:
				intent = new Intent(this, BuildMapData.class);
				startActivity(intent);
				break;

			case R.id.browse_browse_map_data_button:
				intent = new Intent(this, BrowseMapData.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}		
	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		Activity activity;

		public MyOnItemSelectedListener(Activity activity) {
			this.activity = activity;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			selectedLocation = (LocationDataObject) listLocationsSpinner
					.getSelectedItem();

			if (!selectedLocation.getLocations().isEmpty()) {

				ArrayList<LocationDataObject> listLocationDO = new ArrayList<LocationDataObject>();

				listLocationDO.add(selectedLocation);
				listLocationDO.addAll(selectedLocation.getLocations());

				if (selectedLocation.getParentLocationId() != null) {
					LocationDataObject rootLocation = locationTreeMap
							.get(Integer.parseInt(selectedLocation
									.getParentLocationId()));
					if (rootLocation != null) {
						listLocationDO.add(rootLocation);
					}
				}

				ArrayAdapter<LocationDataObject> spinnerAdapter = new ArrayAdapter<LocationDataObject>(
						this.activity, android.R.layout.simple_spinner_item,
						listLocationDO);
				listLocationsSpinner.setAdapter(spinnerAdapter);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

}
