/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import locationaware.wifi.osindependentapi.OSIndependentAPI;
import locationaware.wifi.osindependentapi.ScanningPointStub;

/**
 * 
 * @author nhthien8x
 */
public class OSDetector {
	public static String OS_XP = "Windows XP";
	public static String OS_Vista = "Windows Vista";
	public static String OS_Seven = "Windows 7";
	public static String OS_Linux = "Linux";
	public static String OS_Android = "Android";
	public static String OS_Mac = "Mac";

	OSIndependentAPI osIndependentAPI;
	String AndroidAPI = "locationaware.wifi.androidapi.AndroidAPI";
	String WinVistaOrSevenAPI = "locationaware.wifi.winvistaorsevenapi.WinVistaOrSevenAPI";
	String LinuxAPI = "locationaware.wifi.linuxapi.LinuxAPI";
	String PlacelabAPI = "locationaware.wifi.placelabapi.PlacelabAPI";
	private static String DEFAULT_WIFI_DEVICE = "wlan0";

	/**
	 * Get the OS names
	 * 
	 * @return
	 */
	public static String getOS() {

		String strOSName = System.getProperty("os.name");
		if (strOSName.indexOf(OS_XP) > -1) {
			return OS_XP;
		}

		if (strOSName.indexOf(OS_Vista) > -1) {
			return OS_Vista;
		}

		if (strOSName.indexOf(OS_Seven) > -1) {
			return OS_Seven;
		}
		
		if (strOSName.indexOf(OS_Mac) >-1){
			return OS_Mac;
		}
		// /////////////////////////////////////////////
		if (strOSName.indexOf(OS_Linux) > -1) {
			String strUsername = System.getProperty("user.name");
			if (strUsername.length() == 0) {
				return OS_Android;
			}
			return OS_Linux;
		}
		// ////////////////////////////////////////////////
		return "";
	}

	/**
	 * Detect xem có phải là Windows Vista hay Windows 7 hay không
	 * 
	 * @return
	 */
	public static boolean isVistaOrSeven() {
		String strOSName = getOS();

		if (strOSName.equals(OS_Vista)) {
			return true;
		}

		if (strOSName.equals(OS_Seven)) {
			return true;
		}

		return false;
	}

	public static boolean isAndroid() {
		String strOSName = getOS();
		if (strOSName.equals(OS_Android)) {
			return true;
		}
		return false;
	}
	
	/**
	 * For XP, find the wireless
	 * For Linux, assume it's is wlan0 (should fix)
	 * @return
	 */
	private String getWifiDeviceName() {
		if (getOS().equals(OS_XP)) {
			String wifiDeviceName = null;
			String str;
			String strCommand = "ipconfig /all";

			Process pro = null;
			try {
				pro = Runtime.getRuntime().exec(strCommand);
			} catch (IOException ex) {
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					pro.getInputStream()));

			try {

				while ((str = reader.readLine()) != null) {
					if (str.startsWith("Ethernet adapter Wireless")) {
						while ((str = reader.readLine()) != null) {
							if (str.indexOf("Description") != -1) {
								wifiDeviceName = str
										.substring(str.indexOf(":") + 2);
								break;
							}
						}
						break;
					}
				}

				reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			return wifiDeviceName;
		}
		return DEFAULT_WIFI_DEVICE;
	}

	public OSDetector() {
		if (isAndroid()) {
			osIndependentAPI = OSIndependentAPI.getInstance(AndroidAPI);
		} else if (isVistaOrSeven()) {
			osIndependentAPI = OSIndependentAPI.getInstance(WinVistaOrSevenAPI);
		} else if (getOS().equals(OS_Linux)) {
			osIndependentAPI = OSIndependentAPI.getInstance(LinuxAPI);
		} else if (getOS().equals(OS_Mac)) {
			osIndependentAPI = OSIndependentAPI.getInstance(LinuxAPI);
		} else {
			osIndependentAPI = OSIndependentAPI.getInstance(PlacelabAPI);
			OSIndependentAPI.setWifiDevice(getWifiDeviceName());
		}

	}

	/**
	 * @return a Wifi ScanningPoint
	 */
	public ScanningPoint scan() {
		ScanningPointStub sps = osIndependentAPI.scan();
		ScanningPoint sp = ScanningPoint.convertFromScanningPointStub(sps);
		return sp;
	}
	
	/**
	 * @return the MAC address of the wifi
	 */
	public long getScannerMAC() {
		long scannerMAC = osIndependentAPI.getScannerMAC();
		return scannerMAC;
	}
}
