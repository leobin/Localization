package locationaware.apps;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;
import localization.data.entity.contentobject.LocationDataObject;
import locationaware.client.ClientCalibration;
import locationaware.client.ClientLocationFileDetector;
import locationaware.clientserver.Location;
import locationaware.clientserver.MapData;
import locationaware.eventlistener.NewResultComingEventListener;
import locationaware.myevent.NewResultComingEvent;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.dataminingtools.StatisticTools;
import locationaware.wifi.debug.TestingFile;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;
import com.localization.other.ApplicationConfiguration;
import com.localization.server.ServerAPI;

//"05_Android", "07_Android", "09_Android", "11_Android", "12", "13_Android", "15_Android", "17_Android", "18", "20", "21", "22", "23", "24_1", "24_2", "25", "26", "27", "28", "29"
//12, 29, 32

public class CalibrateDurationOne {

	public static String[] groups = {"05_Android", "07_Android", "09_Android", "11_Android", "13_Android", "15_Android", "17_Android", "18", "20", "21", "22", "23", "24_1", "24_2", "25", "26", "27", "28", "30", "31"};
	public static String oldDataPath = "E:\\Users\\Dinh\\Desktop\\nhngchyph\\Dedicated";
	public static String newDataPath = "E:\\Users\\Dinh\\Desktop\\nhngchyph\\Duration"; 
	public static String sampleDataPath = "E:\\Users\\Dinh\\Desktop\\nhngchyph\\Sample";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int t = 2; t <= 5; ++t) {
			int calibratedCell = 9;
			for (int run = 0; run < groups.length; ++run) {
				String oldPath = oldDataPath + "\\" + groups[run];
				String newPath = newDataPath + "\\" + t + "\\" + groups[run];
				String samplePath = sampleDataPath + "\\" + groups[run];
				String calibrateWifiMapPath = "";
				File folder = new File(oldPath);
				File[] listTestingFile = folder.listFiles();
				File sampleFolder = new File(samplePath);
				File[] sampleFile = sampleFolder.listFiles();
				LocationDataObject locationAnh = (new ServerAPI()).searchLocationByLocationId(547 + "");
				LocationDataObject locationHung = (new ServerAPI()).searchLocationByLocationId(850 + "");
				LocationDataObject locationThay = (new ServerAPI()).searchLocationByLocationId(673 + "");
				Vd2_3MapData mapDataAnh = null;
				for (int i = 0; i < sampleFile.length; ++i) {
					if (sampleFile[i].getName().endsWith(".mapdata")) {
						calibrateWifiMapPath = sampleFile[i].getAbsolutePath();
						if (MapData.readMapData(calibrateWifiMapPath) instanceof Vd2_3MapData) {
							mapDataAnh = (Vd2_3MapData) MapData.readMapData(calibrateWifiMapPath);
							mapDataAnh.setUserId(locationAnh.getUser().getUserId());
							break;
						}
					}
				}
				long scannerMAC = mapDataAnh.getScannerMAC();
				WifiMapData map = new WifiMapData();
				ArrayList<Vd2_3MapData> listMapData = new ArrayList<Vd2_3MapData>();
				for (int i = 0; i < listTestingFile.length; ++i) {
					if (listTestingFile[i].getName().endsWith(".testfile")) {
						try {
							TestingFile tmp = TestingFile.readTestingFile(listTestingFile[i].getAbsolutePath());
							if (calibratedCell == Integer.parseInt(tmp.selectTestLocation.getLocationName())) {
								map.setLocationId(tmp.selectTestLocation.getLocationId());
								Vector<ScanningPoint> spLst = new Vector<ScanningPoint>();
								for (int _ = 0; _ < tmp.listScanningPoint.size() * t / 5; ++_)
									spLst.add(tmp.listScanningPoint.get(_));
								map.setSpsList(spLst);
								mapDataAnh.setStatistics(StatisticTools.statistic(StatisticTools.surveyAccessPoint(map)));
								for (LocationDataObject location : locationAnh.getLocations()) {
									if (calibratedCell == Integer.parseInt(location.getLocationName())) {
										mapDataAnh.setLocationId(location.getLocationId());
										listMapData.add(mapDataAnh);
										break;
									}
								}
								for (LocationDataObject location : locationHung.getLocations()) {
									if (calibratedCell == Integer.parseInt(location.getLocationName())) {
										Vd2_3MapData vd2_3MapDataHung = new Vd2_3MapData(mapDataAnh);
										vd2_3MapDataHung.setLocationId(location.getLocationId());
										vd2_3MapDataHung.setUserId(20 + "");
										listMapData.add(vd2_3MapDataHung);
										break;
									}
								}
								for (LocationDataObject location : locationThay.getLocations()) {
									if (calibratedCell == Integer.parseInt(location.getLocationName())) {
										Vd2_3MapData vd2_3MapDataThay = new Vd2_3MapData(mapDataAnh);
										vd2_3MapDataThay.setLocationId(location.getLocationId());
										vd2_3MapDataThay.setUserId(21 + "");
										listMapData.add(vd2_3MapDataThay);
										break;
									}
								}
							}
						} catch (NumberFormatException e) {
						}
					}
				}
				if (!listMapData.isEmpty()) {
					ClientCalibration calibration = new ClientCalibration();
					calibration.setServerHost(ApplicationConfiguration.load().serverHost);
					calibration.setPort(ApplicationConfiguration.load().localizePort);

					if (calibration.calirate(listMapData)) {
						System.out.println("Calibration success");
					} else {
						System.out.println("Failed to calibrate");
						return;
					}
				} else {
					System.out.println("Empty! Failed to calibrate");
					return;
				}

				for (int i = 0; i < listTestingFile.length; ++i) {
					if (listTestingFile[i].getName().endsWith(".testfile")) {
						String oldTestingFilePath = listTestingFile[i].getAbsolutePath();
						String newTestingFilePath = newPath + "\\" + listTestingFile[i].getName();

						TestingFile oldTestingFile = TestingFile.readTestingFile(oldTestingFilePath);

						final TestingFile newTestingFile = new TestingFile();
						newTestingFile.frequency = oldTestingFile.frequency;
						newTestingFile.isConvert = oldTestingFile.isConvert;
						newTestingFile.listScanningPoint = oldTestingFile.listScanningPoint;
						newTestingFile.selectTestLocation = oldTestingFile.selectTestLocation;
						newTestingFile.listResult = new ArrayList<TreeMap<Location, ArrayList<Location>>>();

						final ClientLocationFileDetector detector = new ClientLocationFileDetector();
						detector.setFrequency(20.0);
						detector.setServerHost("ubigroup.dyndns.org");
						detector.setPort(10003);
						detector.setConvert(true);

						detector.addNewResultComingEventListener(new NewResultComingEventListener() {
							@Override
							public void handleEvent(NewResultComingEvent evt) {
								newTestingFile.listResult.add(detector.getListDetectedLocation());
							}
						});

						detector.startDetectLocation(oldTestingFile.listScanningPoint, scannerMAC);
						while (detector.isActive());

						TestingFile.writeTestfile(newTestingFile, newTestingFilePath);
						System.out.println(oldTestingFilePath + " -> " + newTestingFilePath);
					}
				}
			}
		}
	}
}
