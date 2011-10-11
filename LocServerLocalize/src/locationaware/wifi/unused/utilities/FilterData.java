package locationaware.wifi.unused.utilities;

import java.util.ArrayList;

import locationaware.clientserver.MapData;
import locationaware.wifi.mapdata.WifiMapData;
import locationaware.wifi.unused.filterAlg.OfflineFilterAlg;

public class FilterData {
	OfflineFilterAlg filter = null;
	private int filterAlgID = 0;
	
	public static final ArrayList<String> ALGORITHMS = new ArrayList<String>();
	static {
		ALGORITHMS.add("locationaware.wifi.filterAlg.filterAlgVD.OfflineFilterAlgVd");
	}
	
	public int getFilterAlgID() {
		return filterAlgID;
	}

	public void setFilterAlg(int filterAlgID) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.filterAlgID = filterAlgID;
		
		filter = (OfflineFilterAlg) Class.forName(ALGORITHMS.get(filterAlgID)).newInstance();
	}
	
	public String getFilteredDataFileName(String wifiMapDataFileName) {
		return wifiMapDataFileName.substring(0, wifiMapDataFileName.length() - MapData.EXTENSIONMAPDATAFILE.length())
		+ "." + filter.getAlgorithmName() + ".filtered" + MapData.EXTENSIONMAPDATAFILE;
	}

	public WifiMapData filterData(WifiMapData rawData) {
		WifiMapData filteredData = filter.filterData(rawData);
		filteredData.setFiltered(true);
		if (!filteredData.getFilterAlgNameList().contains(filter.getAlgorithmName()))
			filteredData.addFilterAlgName(filter.getAlgorithmName());
		return filteredData;
	}
	
	public WifiMapData filterData(String rawDataFilePath) {
		return filter.filterData((WifiMapData)WifiMapData.readMapData(rawDataFilePath));
	}
	
	public WifiMapData filterData(String rawDataDirPath, String rawDataFileName) {
		return filterData(rawDataDirPath + "/" + rawDataFileName);
	}

}
