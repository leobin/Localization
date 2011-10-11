package locationaware.apps;

import java.util.Vector;

import locationaware.client.ClientMapDataSender;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.debug.TestingFile;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

public class ConvertTestingFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestingFile testingFile = TestingFile.readTestingFile("E:/Users/Dinh/Desktop/Additional_Training/35/607_true_0.5.testfile");
		WifiMapData wifiMapData = convert(testingFile);
		Vd2_3MapData vd2_3MapData = new Vd2_3MapData(wifiMapData);
		
		ClientMapDataSender clientMapDataSender = new ClientMapDataSender();
		clientMapDataSender.setServerHost("ubigroup.dyndns.org");
		clientMapDataSender.setPort(10002);
		clientMapDataSender.sendMapData(vd2_3MapData);
		
	}

	private static WifiMapData convert(TestingFile testingFile) {
		
		WifiMapData wifiMapData = new WifiMapData();
		
		wifiMapData.setLocationId(testingFile.selectTestLocation.getLocationId());
		
		wifiMapData.setUserId(testingFile.selectTestLocation.getUser().getUserId());
		
		Vector<ScanningPoint> vectorScanningPoints = new Vector<ScanningPoint>();
		for (ScanningPoint scanningPoint : testingFile.listScanningPoint) {
			vectorScanningPoints.add(scanningPoint);
		}
		
		wifiMapData.setSpsList(vectorScanningPoints);
		
		//wifiMapData.setScannerMAC(testingFile.); khong biet MAC cua device tao TestingFile
		
		return wifiMapData;
	}
}
