package locationaware.wifi.dataminingtools;

import static locationaware.wifi.dataminingtools.DebugTools.debug;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import locationaware.wifi.AccessPoint;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.mapdata.WifiMapData;

public class StatisticTools {
	
	public static Map<AccessPoint, LinkedList<Double>> surveyAccessPoint(WifiMapData location) {
		TreeMap<AccessPoint, LinkedList<Double>> res = new TreeMap<AccessPoint, LinkedList<Double>>(AccessPoint.sortByMACAddress);
		
		for (ScanningPoint scanningPoint : location.getSpsList()) {
			for (AccessPoint curAP : scanningPoint.getApsList()) {
				if (!res.containsKey(curAP))
					res.put(curAP, new LinkedList<Double>());
				
				res.get(curAP).add(curAP.getSignalStrength());
			}
		}
		return res;
	}
	
	public static TreeMap<Long, APStatistic> statistic(Map<AccessPoint, LinkedList<Double>> allAP) {
		TreeMap<Long, APStatistic> res = new TreeMap<Long, APStatistic>();

		for (Map.Entry<AccessPoint, LinkedList<Double>> cur : allAP.entrySet()) {
			res.put(cur.getKey().getMACAddress(), new APStatistic(cur.getKey(), cur.getValue()));
		}

		return res;
	}

	public static double getAverage(List<Double> list) {
		double sum = 0;
		for (Double double1 : list) {
			sum += double1;
		}
		return sum / list.size();
	}

	public static double getVariance(List<Double> list) {
		double exp = getAverage(list);
		double res = 0;
		for (Double d : list) {
			res += (d - exp) * (d - exp);
		}
		return res / list.size();
	}

	public static void printAllAccessPoint(Map<String, LinkedList<Double>> allAP) {
		for (Map.Entry<String, LinkedList<Double>> cur : allAP.entrySet()) {
			debug(cur.getKey(), cur.getValue().size(),
					getAverage(cur.getValue()));
		}
	}
}
