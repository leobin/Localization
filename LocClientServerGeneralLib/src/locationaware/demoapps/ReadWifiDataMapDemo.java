package locationaware.demoapps;

import java.io.IOException;

import locationaware.wifi.mapdata.WifiMapData;


/**
 * @author 
 * Read big mapdata (wifi)
 *
 */
public class ReadWifiDataMapDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String wifiMapDataDirPath = "C:/RawData";
		String wifiMapDataFileName = "13_2_WifiMapData.mapdata";
		WifiMapData wifiMapData = (WifiMapData) WifiMapData.readMapData(wifiMapDataDirPath, wifiMapDataFileName);
		System.out.println("Information in " + wifiMapDataFileName + " is: ");
		System.out.println(wifiMapData.toString());
	}

}
