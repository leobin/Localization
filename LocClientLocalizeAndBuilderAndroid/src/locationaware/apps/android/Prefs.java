package locationaware.apps.android;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Prefs extends PreferenceActivity {
    
    private static final String DEFAULT_DIRMAPDATA = "LocationMapData/";
    private static final String DEFAULT_SERVERHOST = "ubigroup.dyndns.org";
    private static final String DEFAULT_DETECT_WIFI_PORT = "10003";
    private static final String DEFAULT_SEND_MAP_PORT = "10002";
    private static final String DEFAULT_CALL_OBJECT_PORT = "10002";
    private static final String DEFAULT_CALL_FUNCTION_PORT = "10002";
    private static final String DEFAULT_DETECT_GPS_PORT = "10003";
    private static final String DEFAULT_NUMBER_ENTRIES = "10";
    private static final String DEFAULT_SCANNING_TIME = "15";
    private static final String DEFAULT_FREQUENCY = "1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
	}
	
	public static String getDirMapData(Context context) {
		String dirMapData = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.directory_map_data_key), DEFAULT_DIRMAPDATA);
		return dirMapData;
	}
	
	public static String getServerHost(Context context) {
		String serverHost = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.serverhost_key), DEFAULT_SERVERHOST);
		return serverHost;
	}

	public static int getDetectWifiPort(Context context) {
		String detectWifiPort = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.detectbywifiport_key), DEFAULT_DETECT_WIFI_PORT);
		return Integer.parseInt(detectWifiPort);
	}
	
	public static int getSendMapPort(Context context) {
		String sendMapPort = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.sendmapport_key), DEFAULT_SEND_MAP_PORT);
		return Integer.parseInt(sendMapPort);
	}
	
	public static int getCallFunctionPort(Context context) {
		String callFunctionPort = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.callfunctionport_key), DEFAULT_CALL_FUNCTION_PORT);
		return Integer.parseInt(callFunctionPort);
	}
	
	public static int getCallObjectPort(Context context) {
		String callObjectPort = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.callobjectport_key), DEFAULT_CALL_OBJECT_PORT);
		return Integer.parseInt(callObjectPort);
	}

	public static int getDetectGPSPort(Context context) {
		String detectGPSPort = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.detectbygpsport_key), DEFAULT_DETECT_GPS_PORT);
		return Integer.parseInt(detectGPSPort);
	}

	public static int getNumberEntries(Context context) {
		String numberEntries = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.number_entries_key), DEFAULT_NUMBER_ENTRIES);
		return Integer.parseInt(numberEntries);
	}
	
	public static int getScanningTime(Context context) {
		String scanningTime = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.scanning_time_key), DEFAULT_SCANNING_TIME);
		return Integer.parseInt(scanningTime);
	}
	
	public static int getFrequency(Context context) {
		String frequency = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.frequency_key), DEFAULT_FREQUENCY);
		return Integer.parseInt(frequency);
	}
	
	
}
