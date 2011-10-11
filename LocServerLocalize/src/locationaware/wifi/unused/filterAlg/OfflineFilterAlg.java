package locationaware.wifi.unused.filterAlg;

import locationaware.wifi.mapdata.WifiMapData;

public interface OfflineFilterAlg {
	public String getAlgorithmName();
	public WifiMapData filterData(WifiMapData rawData);
}
