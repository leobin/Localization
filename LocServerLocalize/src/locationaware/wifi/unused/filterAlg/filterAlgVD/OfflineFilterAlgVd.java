package locationaware.wifi.unused.filterAlg.filterAlgVD;

import java.util.ArrayList;
import java.util.Collections;

import locationaware.wifi.AccessPoint;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.dataminingtools.StatisticTools;
import locationaware.wifi.mapdata.WifiMapData;
import locationaware.wifi.unused.filterAlg.OfflineFilterAlg;

public class OfflineFilterAlgVd implements OfflineFilterAlg {
	private static final int MAX_GOOD_APS = 10;
	public static final String ALGORITHM_NAME = "OfflineFilterAlgVd";

	@Override
	public String getAlgorithmName() {
		return ALGORITHM_NAME;
	}

	@Override
	public WifiMapData filterData(WifiMapData rawData) {
		ArrayList<APStatistic> apStatisticList = (ArrayList<APStatistic>) StatisticTools
				.statistic(StatisticTools.surveyAccessPoint(rawData)).values();

		// Remove all ap which have minSignalStrength = maxSignalStrength
		for (int i = apStatisticList.size() - 1; i >= 0; --i) {
			if (Math.abs(apStatisticList.get(i).min
					- apStatisticList.get(i).max) < 1e-7)
				apStatisticList.remove(i);
		}

		// Accept the first MAX_GOOD_APS with highest appearance
		Collections.sort(apStatisticList, APStatistic.sortByAppearance);
		for (int i = apStatisticList.size() - 1; i >= MAX_GOOD_APS; --i) {
			apStatisticList.remove(i);
		}

		// remove Outliers
		// to get P{ave - z * dev <= x <= ave + z * dev} = 90%
		double z = 1.2;
		WifiMapData filteredData = new WifiMapData();
		filteredData.setLocationId(rawData.getLocationId());
		filteredData.setOS(rawData.getOS());
		filteredData.setVersion(WifiMapData.VERSION_1);
		filteredData.setUserId(rawData.getUserId());
		filteredData.setFiltered(true);
		filteredData.addFilterAlgName(this.getAlgorithmName());
		
		for (ScanningPoint scanningPoint : rawData.getSpsList()) {
			ScanningPoint filteredSP = new ScanningPoint();
			for (AccessPoint accessPoint : scanningPoint.getApsList()) {
				for (APStatistic apStatistic : apStatisticList) {
					if (apStatistic.accessPoint.equals(accessPoint)) {
						double dev = Math.sqrt(apStatistic.variance);
						if (Math.abs(accessPoint.getSignalStrength()
								- apStatistic.average) <= z * dev) {
							try {
								AccessPoint chosenAP = accessPoint.clone();
								filteredSP.getApsList().add(chosenAP);
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			if (filteredSP.getApsList().size() > 0)
				filteredData.getSpsList().add(filteredSP);
		}

		return filteredData;
	}

}
