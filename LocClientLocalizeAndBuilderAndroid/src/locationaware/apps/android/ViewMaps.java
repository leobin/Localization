//package locationaware.apps.android;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import locationaware.CommonConfig;
//import locationaware.MapData;
//import android.app.Activity;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//
//public class ViewMaps extends Activity implements OnClickListener {
//
//	Spinner listMaps;
//	EditText mapContentEText;
//
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.view);
//
//		String dirMapData = Environment.getExternalStorageDirectory()
//				.getAbsolutePath();
//		dirMapData += "/" + Prefs.getDirMapData(this);
//		String[] allFileNames = (new File(dirMapData)).list();
//		ArrayList<String> allMapNames = new ArrayList<String>();
//		for (int i = 0; i < allFileNames.length; ++i) {
//			if (allFileNames[i].endsWith(CommonConfig.extensionMapDataFile)) {
//				allMapNames.add(allFileNames[i]);
//			}
//		}
//
//		listMaps = (Spinner) findViewById(R.id.list_maps);
//		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, allMapNames);
//		listMaps.setAdapter(spinnerAdapter);
//
//		mapContentEText = (EditText) findViewById(R.id.map_content);
//
//		Button viewButton = (Button) findViewById(R.id.view_button);
//		viewButton.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.view_button:
//			if (listMaps.getSelectedItem() != null) {
//				String mapName = listMaps.getSelectedItem().toString();
//				String strMapContent = "";
//				if (mapName.endsWith(CommonConfig.extensionMapDataFile)) {
//					String dirMapData = Environment
//							.getExternalStorageDirectory().getAbsolutePath();
//					dirMapData += "/" + Prefs.getDirMapData(this);
//					MapData mapData = MapData.readMapData(dirMapData, mapName);
//					strMapContent = mapData.toString();
//				}
//				System.out.println(strMapContent);
//				mapContentEText.setText(strMapContent);
//			}
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
