package locationaware.apps;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import localization.data.entity.contentobject.LocationDataObject;
import locationaware.client.ClientCalibration;
import locationaware.client.ClientLocationFileDetector;
import locationaware.clientserver.Location;
import locationaware.clientserver.MapData;
import locationaware.eventlistener.NewResultComingEventListener;
import locationaware.myevent.NewResultComingEvent;
import locationaware.wifi.AccessPoint;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.debug.TestingFile;
import locationaware.wifi.mapdata.Vd2_3MapData;

import com.localization.other.ApplicationConfiguration;
import com.localization.server.ServerAPI;

public class ClientDetectionConsole {

	public static String group = "36";
	public static String oldPath = "C:Data\\3_old\\" + group;
	public static String newPath = "C:Data\\3_new\\" + group;
	public static int[] calibrationCells = { 50 };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String calibrateWifiMapPath = "";
		File folder = new File(oldPath);
		File[] listTestingFile = folder.listFiles();
		
		LocationDataObject locationHung = (new ServerAPI()).searchLocationByLocationId(850 + "");
		LocationDataObject locationThay = (new ServerAPI()).searchLocationByLocationId(673 + "");
		
		for (int i = 0; i < listTestingFile.length; ++i) {
			if (listTestingFile[i].getName().endsWith(".mapdata")) {
				calibrateWifiMapPath = listTestingFile[i].getAbsolutePath();
				break;
			}
		}
		long scannerMAC = ((Vd2_3MapData) MapData
				.readMapData(calibrateWifiMapPath)).getScannerMAC();

		ArrayList<String> mapFilePath = new ArrayList<String>();
		for (int i = 0; i < listTestingFile.length; ++i) {
			if (listTestingFile[i].getName().endsWith(".mapdata")) {
				LocationDataObject location = (new ServerAPI())
						.searchLocationByLocationId(MapData.readMapData(
								listTestingFile[i].getAbsolutePath())
								.getLocationId());
				for (int j = 0; j < calibrationCells.length; ++j) {
					if (calibrationCells[j] == Integer.parseInt(location
							.getLocationName())) {
						mapFilePath.add(listTestingFile[i].getAbsolutePath());
						break;
					}
				}
			}
		}
		
		ArrayList<Vd2_3MapData> listMapData = new ArrayList<Vd2_3MapData>();
		for (String mapDataFilePath : mapFilePath) {
			File mapDataFile = new File(mapDataFilePath);

			if (mapDataFile.exists()) {
				MapData mapData = MapData.readMapData(mapDataFilePath);
				if (mapData instanceof Vd2_3MapData) {
					Vd2_3MapData vd2_3MapData = (Vd2_3MapData) mapData;
					listMapData.add(vd2_3MapData);
					
					LocationDataObject locationTemp = (new ServerAPI()).searchLocationByLocationId( vd2_3MapData.getLocationId() );
					for (LocationDataObject location : locationHung.getLocations()) {
						if (location.getLocationName().equals(locationTemp.getLocationName())) {
							Vd2_3MapData vd2_3MapDataHung = new Vd2_3MapData(vd2_3MapData);
							vd2_3MapDataHung.setLocationId(location.getLocationId());
							vd2_3MapDataHung.setUserId(20 + "");
							listMapData.add(vd2_3MapDataHung);
							break;
						}
					}
					
					for (LocationDataObject location : locationThay.getLocations()) {
						if (location.getLocationName().equals(locationTemp.getLocationName())) {
							Vd2_3MapData vd2_3MapDataThay = new Vd2_3MapData(vd2_3MapData);
							vd2_3MapDataThay.setLocationId(location.getLocationId());
							vd2_3MapDataThay.setUserId(21 + "");
							listMapData.add(vd2_3MapDataThay);
							break;
						}
					}

				}
			}
		}

		if (!listMapData.isEmpty()) {
			ClientCalibration calibration = new ClientCalibration();
			calibration
					.setServerHost(ApplicationConfiguration.load().serverHost);
			calibration.setPort(ApplicationConfiguration.load().localizePort);

			if (calibration.calirate(listMapData)) {
				System.out.println("Calibration success");
			} else {
				System.out.println("Failed to calibrate");
				return;
			}
		} else {
			System.out.println("Failed to calibrate");
			return;
		}

		for (int i = 0; i < listTestingFile.length; ++i) {
			if (listTestingFile[i].getName().endsWith(".testfile")) {
				String oldTestingFilePath = listTestingFile[i]
						.getAbsolutePath();
				String newTestingFilePath = newPath + "\\"
						+ listTestingFile[i].getName();

				TestingFile oldTestingFile = TestingFile
						.readTestingFile(oldTestingFilePath);

				final TestingFile newTestingFile = new TestingFile();
				newTestingFile.frequency = oldTestingFile.frequency;
				newTestingFile.isConvert = oldTestingFile.isConvert;
				newTestingFile.listScanningPoint = oldTestingFile.listScanningPoint;
				newTestingFile.selectTestLocation = oldTestingFile.selectTestLocation;
				newTestingFile.listResult = new ArrayList<TreeMap<Location, ArrayList<Location>>>();

				final ClientLocationFileDetector detector = new ClientLocationFileDetector();
				detector.setFrequency(10.0);
				detector.setServerHost("ubigroup.dyndns.org");
				detector.setPort(10003);
				detector.setConvert(true);

				detector.addNewResultComingEventListener(new NewResultComingEventListener() {
					@Override
					public void handleEvent(NewResultComingEvent evt) {
						newTestingFile.listResult.add(detector
								.getListDetectedLocation());
					}
				});

				detector.startDetectLocation(oldTestingFile.listScanningPoint,
						scannerMAC);
				while (detector.isActive())
					;

				TestingFile.writeTestfile(newTestingFile, newTestingFilePath);
				System.out.println(oldTestingFilePath + " -> "
						+ newTestingFilePath);
			}
		}
		
	}
	
	public static ArrayList<ScanningPoint> getScanningPoints(Vd2_3MapData vd2_3MapData) {
		ArrayList<ScanningPoint> scanningPointArrayList = new ArrayList<ScanningPoint>();
		
		int noScanningPoint = 0;
		
		for (Long mac : vd2_3MapData.getStatistics().keySet()) {
			if (vd2_3MapData.getStatistics().get(mac).signalList.size() > noScanningPoint) {
				noScanningPoint = vd2_3MapData.getStatistics().get(mac).signalList.size();
			}
		}
		
		System.out.println(noScanningPoint);
		System.out.println(vd2_3MapData.getStatistics().size());
		
		ArrayList<Long> removeMACs = new ArrayList<Long>();
		
		for (Long mac : vd2_3MapData.getStatistics().keySet()) {
			if (Double.compare(vd2_3MapData.getStatistics().get(mac).signalList.size(), noScanningPoint*0.40) <= 0) {
				removeMACs.add(mac);
			}
		}
		
		for (Long removeMAC : removeMACs){
			vd2_3MapData.getStatistics().remove(removeMAC);
		}
		
		System.out.println(vd2_3MapData.getStatistics().size());
		
		for (int i = 0; i < noScanningPoint; ++i) {
			ScanningPoint sp = new ScanningPoint();
			
			for (Long mac : vd2_3MapData.getStatistics().keySet()) {
				try {
					if (i < vd2_3MapData.getStatistics().get(mac).signalList.size()) {
						AccessPoint ap = vd2_3MapData.getStatistics().get(mac).accessPoint.clone();
						ap.setSignalStrength(vd2_3MapData.getStatistics().get(mac).signalList.get(i));
						sp.getApsList().add(ap);
					}
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			if (Double.compare(sp.getApsList().size(), 0.85*vd2_3MapData.getStatistics().size()) >= 0) {
				scanningPointArrayList.add(sp);
			}
		}
		
		System.out.println(scanningPointArrayList.size());
		
		return scanningPointArrayList;
	}
}
