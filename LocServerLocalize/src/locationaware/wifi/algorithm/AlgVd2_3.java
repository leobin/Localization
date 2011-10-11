package locationaware.wifi.algorithm;

/*
 * Define SMALL_VARIANCE. Take into consideration all APs with small variance as Good APs.
 * 1. Calculate missing APs. Remove all locations which miss to much Good APs.
 * 2. Calculate Distance from unknown points to missing APs.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import localization.data.entity.LocationDatabaseObject;
import locationaware.Algorithm;
import locationaware.ResultOfDetection;
import locationaware.clientserver.LocationData;
import locationaware.clientserver.MapData;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.dataminingtools.LocationEvaluation;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

import com.localization.server.ServerAPI;

public class AlgVd2_3 implements Algorithm {
	private static final double SMALL_VARIANCE = 30;
	private static final double VERY_NEGATIVE = -1e20;
	public static final String ALGORITHM_CLASSNAME = AlgVd2_3.class.getName();
	public static final String ALGORITHM_NAME = "AlgVD2_3";
	
	private double maxAbsenceAP = 3;
	Map<String, Vd2_3MapData> cache;
	private WifiMapData currentTest;
//	private LinkedList<LocationEvaluation> intermediateResult;
	private int maxNOCandidate = 1;
	private int maxSizeOfList = 10;

	public AlgVd2_3() {
		init();
	}

	@Override
	public synchronized void reset() {
//		intermediateResult = new LinkedList<LocationEvaluation>();
		currentTest = new WifiMapData();
	}

//	public synchronized String detectLocation() {
//		String res = helper(currentTest);
//		if (res.length() > 0) {
//			boolean added = false;
//			for (LocationEvaluation at : intermediateResult) {
//				if (at.name.equals(res)) {
//					at.score++;
//					added = true;
//					break;
//				}
//			}
//			if (!added) {
//				intermediateResult.add(new LocationEvaluation(res, 1));
//			}
//		}
//		Collections.sort(intermediateResult);
//		// show(intermediateResult, 3);
//		if (intermediateResult.size() == 0)
//			return "Out of map ...";
//		else
//			return intermediateResult.getFirst().name;
//	}

//	public synchronized String getPeek() {
//		Collections.sort(intermediateResult);
//		if (intermediateResult.size() == 0)
//			return "Out of map ...";
//		else
//			return intermediateResult.getFirst().name;
//	}

//	private String detectLocationSMALL(
//			ArrayList<ScanningPoint> listScanningPoints) {
//		reset();
//		for (ScanningPoint s : listScanningPoints) {
//			feedLocationData(s);
//		}
//		detectLocation();
//		return getPeek();
//	}

	private ArrayList<String> helper(WifiMapData unknown) {
		Vd2_3MapData unknownStat = new Vd2_3MapData(unknown);
		// debug("here stat");
		// debug(unknownStat, unknownStat.fullScan.size());
		LinkedList<LocationEvaluation> possible = new LinkedList<LocationEvaluation>();
		ArrayList<String> possibleLocations = new ArrayList<String>();
		
		for (String locationID : cache.keySet()) {
			LocationEvaluation eval = new LocationEvaluation(locationID);
			possible.add(eval);
		}

		if (possible.isEmpty()) {
			System.out.println("+++++++++++++++++++++++++++++++++");
			return possibleLocations;
		}
		
		{
			// Remove the ones that has too little low-var Aps
			for (LocationEvaluation eval : possible) {
				eval.score = nHavingLowVariance(cache.get(eval.name),
						unknownStat);
			}
			Collections.sort(possible);
			// debug(possible);
			
			double min = possible.getFirst().score - maxAbsenceAP;
			while (!possible.isEmpty() && possible.getLast().score < min)
				possible.removeLast();
		}

		{
			// Remove the ones that miss too much Low-var APs
			for (LocationEvaluation eval : possible) {
				eval.score = nMissingLowVariance(cache.get(eval.name),
						unknownStat);
			}
			Collections.sort(possible);
			// debug(possible);
			double min = possible.getFirst().score - maxAbsenceAP;
			while (!possible.isEmpty() && possible.getLast().score < min)
				possible.removeLast();
		}

		{
			// Sort the average this distance
			for (LocationEvaluation eval : possible) {
				eval.score = differenceAverage(cache.get(eval.name),
						unknownStat);
			}
			Collections.sort(possible);
			while (!possible.isEmpty()
					&& possible.getLast().score < VERY_NEGATIVE) {
				possible.removeLast();
			}
		}
		
		for (int i = 0; i < possible.size(); ++i) {
			possibleLocations.add(possible.get(i).name);
		}
		
		return possibleLocations;
	}

	private double nHavingLowVariance(Vd2_3MapData loc,
			Vd2_3MapData unknownStat) {
		double res = 0;
		for (APStatistic ap : loc.getStatistics().values()) {
			if (ap.variance < SMALL_VARIANCE) {
				boolean ok = false;
				for (APStatistic curAP : unknownStat.getStatistics().values()) {
					if (ap.accessPoint.equals(curAP.accessPoint)
							&& curAP.variance < SMALL_VARIANCE) {
						ok = true;
						break;
					}
				}
				if (ok) {
					++res;
				}
			}
		}
		return res;
	}

	private double differenceAverage(Vd2_3MapData loc,
			Vd2_3MapData unknownStat) {
		double res = 0;
		int cnt = 0;
		for (APStatistic ap : loc.getStatistics().values()) {
			if (ap.variance < SMALL_VARIANCE) {
				for (APStatistic curAP : unknownStat.getStatistics().values()) {
					if (ap.accessPoint.equals(curAP.accessPoint)
							&& curAP.variance < SMALL_VARIANCE) {
						res += (ap.average - curAP.average)
								* (ap.average - curAP.average);
						++cnt;
					}
				}
			}
		}
		if (cnt != 0) {
			return -res / cnt;
		} else
			return Double.NEGATIVE_INFINITY;
	}

	private double nMissingLowVariance(Vd2_3MapData loc,
			Vd2_3MapData unknownStat) {
		double res = 0;
		for (APStatistic ap : loc.getStatistics().values()) {
			if (ap.variance < SMALL_VARIANCE) {
				boolean ok = false;
				for (APStatistic curAP : unknownStat.getStatistics().values()) {
					if (ap.accessPoint.equals(curAP.accessPoint)
							&& curAP.variance < SMALL_VARIANCE) {
						ok = true;
						break;
					}
				}
				if (!ok) {
					--res;
				}
			}
		}
		return res;
	}
	
	@Override
	public synchronized void feedLocationData(LocationData s) {
		while (currentTest.getSpsList().size() >= maxSizeOfList) {
			currentTest.getSpsList().remove(0);
		}
		currentTest.getSpsList().add((ScanningPoint) s);
	}
	
	@Override
	public void addLocation(LocationDatabaseObject locationDO) {
		
		if (locationDO != null) {
			new ServerAPI();
			MapData mapData = MapData.readMapData(locationDO.getLocationDataPath(ServerAPI.getDataTypeIDByAlgorithmClassName(ALGORITHM_CLASSNAME)));
//			new ServerAPI();
//			System.out.println(locationDO.getLocationDataPath(ServerAPI.getDataTypeIDByAlgorithmClassName(ALGORITHM_CLASSNAME)));
//			System.out.println(mapData);
			if (mapData == null) {
				return;
			} else {
				if (mapData instanceof Vd2_3MapData) {
					cache.put(locationDO.getLocationId(), (Vd2_3MapData) mapData);
				}
			}
		}
	}
	
	@Override
	public void addLocations(Vector<LocationDatabaseObject> listLocations) {
		for (LocationDatabaseObject locationDO : listLocations) {
			addLocation(locationDO);
		}
	}
	
	@Override
	public void clearMaps() {
		// TODO Auto-generated method stub
		cache.clear();
		reset();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		intermediateResult = new LinkedList<LocationEvaluation>();
		currentTest = new WifiMapData();
		cache = new TreeMap<String, Vd2_3MapData>();
	}
	
//	@Override
//	public ResultOfDetection detectLocation(Vector<ScanningPoint> listScanningPoints) {
//		synchronized (listScanningPoints) {
//			if (listScanningPoints.size() == 0) {
//				return new ResultOfDetection(ResultOfDetection.ERROR, "No ScanningPoint for detection");
//			}
//
//			Map<String, Integer> count = new TreeMap<String, Integer>();
//			ArrayList<ScanningPoint> subList = new ArrayList<ScanningPoint>();
//			for (int skip = 0; skip < listScanningPoints.size(); skip++) {
//				int at = 0;
//				subList.clear();
//				for (ScanningPoint scanningPoint : listScanningPoints) {
//					if (at != skip)
//						subList.add(scanningPoint);
//					++at;
//				}
//				String myResult = detectLocationSMALL(subList);
//				if (count.containsKey(myResult))
//					count.put(myResult, count.get(myResult) + 1);
//				else
//					count.put(myResult, 1);
//			}
//			String myResult = null;
//			Integer max = 0;
//			for (String temp : count.keySet())
//				if (count.get(temp) > max) {
//					max = count.get(temp);
//					myResult = temp;
//				}
//
//			if (myResult.equals("Out of map ..."))
//				return new ResultOfDetection(ResultOfDetection.OUT_OF_MAP, myResult);
//			else {
//				Vector<String> listLocationID = new Vector<String>();
//				listLocationID.add(myResult);
//				return new ResultOfDetection(ResultOfDetection.INSIDE_LOCATION,
//						listLocationID);
//			}
//		}
//	}

	@Override
	public String getAlgorithmClassName() {
		// TODO Auto-generated method stub
		return ALGORITHM_CLASSNAME;
	}

	public void setMaxNOCandidate(int maxNOCandidate) {
		this.maxNOCandidate = maxNOCandidate;
	}

	public int getMaxNOCandidate() {
		return maxNOCandidate;
	}

	@Override
	public ResultOfDetection detectLocation() {
		// TODO Auto-generated method stub
		ArrayList<String> possibleLocation = helper(currentTest);
		
		ResultOfDetection result = new ResultOfDetection();
		for (int i = 0; i < maxNOCandidate && i < possibleLocation.size(); ++i) {
			result.getListLocationID().add(possibleLocation.get(i));
		}
		
		if (result.getListLocationID().isEmpty()) {
			result.setId(ResultOfDetection.OUT_OF_MAP);
		} else {
			result.setId(ResultOfDetection.INSIDE_LOCATION);
		}
		
		return result;
	}

	@Override
	public boolean isReadyToLocalize() {
		// TODO Auto-generated method stub
		return (currentTest.getSpsList().size() == maxSizeOfList);
	}

	public void setMaxSizeOfList(int maxSizeOfList) {
		this.maxSizeOfList = maxSizeOfList;
	}

	public int getMaxSizeOfList() {
		return maxSizeOfList;
	}

}