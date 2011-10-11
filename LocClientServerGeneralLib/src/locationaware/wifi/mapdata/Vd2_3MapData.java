package locationaware.wifi.mapdata;

import static locationaware.wifi.dataminingtools.DebugTools.prettyString;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TreeMap;

import locationaware.clientserver.MapData;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.dataminingtools.StatisticTools;

/**
 * @author 
 * The small map of the big WifiMapData (stored on the server)
 * 
 */
public class Vd2_3MapData extends MapData implements Comparable<Vd2_3MapData>, Serializable {
	private static final long serialVersionUID = 2524128354623171158L;
	public static final String DATATYPE_CLASSNAME = Vd2_3MapData.class.getName();
	public static final String DATATYPE_NAME = "Vd2_3MapData";
	public static final String VERSION_1 = "1.0";
	private TreeMap<Long, APStatistic> statistics; // mapping between MAC address to APStatistic 
	private int numberOfEntries;
	private long scannerMAC = 0;
	private String OS;

	/**
	 * @return mapping between MAC address to APStatistic
	 */
	public TreeMap<Long, APStatistic> getStatistics() {
		return statistics;
	}

	public void setStatistics(TreeMap<Long, APStatistic> statistics) {
		this.statistics = statistics;
	}


	public Vd2_3MapData(WifiMapData wifiMapData) {
		if (wifiMapData != null) {
			creationDate = Calendar.getInstance().getTime();
			locationId = wifiMapData.getLocationId();
			version = VERSION_1;
			userId = wifiMapData.getUserId();
			dataTypeClassName = Vd2_3MapData.class.getName();
			statistics = StatisticTools.statistic(StatisticTools.surveyAccessPoint(wifiMapData));
			numberOfEntries = wifiMapData.getSpsList().size();
			scannerMAC = wifiMapData.getScannerMAC();	
			OS = wifiMapData.getOS();
		}
	}
	
	public Vd2_3MapData(Vd2_3MapData vd2_3MapData) {
		if (vd2_3MapData != null) {
			creationDate = Calendar.getInstance().getTime();
			locationId = vd2_3MapData.getLocationId();
			version = VERSION_1;
			userId = vd2_3MapData.getUserId();
			dataTypeClassName = Vd2_3MapData.class.getName();
			statistics = vd2_3MapData.statistics;
			numberOfEntries = vd2_3MapData.numberOfEntries;
			scannerMAC = vd2_3MapData.getScannerMAC();	
			OS = vd2_3MapData.getOS();
		}
	}
	
	
	/**
	 * Create a small Wifi map from a big WiFi map
	 * @param wifiMapData
	 * @return
	 */
	public static Vd2_3MapData createVd2_3MapData(WifiMapData wifiMapData) {
		Vd2_3MapData vd2_3MapData = new Vd2_3MapData(wifiMapData);
		return vd2_3MapData;
	}
	
	/**
	 * Create a small Wifi map from a big WiFi map (in a file)
	 * @param wifiMapDataFilePath
	 * @return
	 */
	public static Vd2_3MapData createVd2_3MapData(String wifiMapDataFilePath) {
		// TODO Auto-generated method stub
		WifiMapData wifiMapData = (WifiMapData) WifiMapData.readMapData(wifiMapDataFilePath);
		
		return createVd2_3MapData(wifiMapData);
	}	

	/**
	 * Create a small Wifi map from a big WiFi map (in a file)
	 * @param wifiMapDataDirPath
	 * @param vd2_3MapDataDirPath
	 */
	public static void createVd2_3MapData(String wifiMapDataDirPath, String vd2_3MapDataDirPath) {
		// TODO Auto-generated method stub
		File wifiMapDataDir = new File(wifiMapDataDirPath);
		File vd2_3MapDataDir = new File(vd2_3MapDataDirPath);

		createVd2_3MapData(wifiMapDataDir, vd2_3MapDataDir);
	}
	
	/**
	 * 
	 * Create multiple small Wifi map from multiple big WiFi map (in a directory)
	 * 
	 * @param wifiMapDataDir
	 * @param vd2_3MapDataDir
	 */
	public static void createVd2_3MapData(File wifiMapDataDir, File vd2_3MapDataDir) {
		if (wifiMapDataDir != null && wifiMapDataDir.isDirectory()) {
			vd2_3MapDataDir.mkdirs();
			File[] allFiles = wifiMapDataDir.listFiles();
			Vd2_3MapData vd2_3MapData;
			for (int i = 0; i < allFiles.length; ++i) {
				if (allFiles[i].isDirectory()) {
					File subSmallMapDir = new File(
							vd2_3MapDataDir.getAbsolutePath() + "/"
									+ allFiles[i].getName());
					createVd2_3MapData(allFiles[i], subSmallMapDir);
					continue;
				} else if (allFiles[i].getName().endsWith(MapData.EXTENSIONMAPDATAFILE)) {
					vd2_3MapData = createVd2_3MapData((WifiMapData) MapData.readMapData(allFiles[i].getAbsolutePath())); 
					String mapDataFileName = vd2_3MapData.getLocationId() + "." + vd2_3MapData.getDataTypeClassName() + MapData.EXTENSIONMAPDATAFILE;
					MapData.writeMapData(vd2_3MapData, vd2_3MapDataDir.getAbsolutePath(), mapDataFileName);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * compare the locationID
	 */
	@Override
	public int compareTo(Vd2_3MapData o) {
		return locationId.compareTo(o.locationId); 
	}
	
	public String toString() {
		return prettyString(locationId, userId, creationDate, statistics);
	}

	public void setNumberOfEntries(int numberOfEntries) {
		this.numberOfEntries = numberOfEntries;
	}

	/**
	 * @return the number of entries of the big map that the small map
	 * derived from
	 */
	public int getNumberOfEntries() {
		return numberOfEntries;
	}

	public long getScannerMAC() {
		return scannerMAC;
	}

	public void setOS(String oS) {
		OS = oS;
	}

	public String getOS() {
		return OS;
	}
}
