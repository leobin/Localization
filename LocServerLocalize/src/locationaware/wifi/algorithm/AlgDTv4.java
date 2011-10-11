package locationaware.wifi.algorithm;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import localization.data.entity.LocationDatabaseObject;
import locationaware.Algorithm;
import locationaware.ResultOfDetection;
import locationaware.clientserver.LocationData;
import locationaware.clientserver.MapData;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

import com.localization.server.ServerAPI;

public class AlgDTv4 implements Algorithm{

	public static String ALGORITHM_CLASSNAME = AlgDTv4.class.getName();
	public static String ALGORITHM_NAME  = "AlgDTv4";

	private boolean trainingWeighted = false;
	private boolean testingWeighted = true;
	private int maxAP = 15;
	private int maxCandidates = 1;
	private int blockSizeTesting = 10;
	
	//training 
	private Map<String, Vd2_3MapData> cache = null;
	
	//testing
	private WifiMapData currentTest = null;
		
	public AlgDTv4() {
		init();
	}
	
	private double calculateEuclideDistance(String locationID, Vd2_3MapData currentStat) {
		double res = Double.MAX_VALUE;
		
		Vd2_3MapData locationStat = cache.get(locationID);
		
		for (long macAddress : currentStat.getStatistics().keySet()) {
			APStatistic locationAPStat = locationStat.getStatistics().get(macAddress);
			APStatistic currentAPStat = currentStat.getStatistics().get(macAddress);
			
			if (locationAPStat == null) {
				continue;
			}
		
			if (res == Double.MAX_VALUE) {
				res = 0.0;
			}
			
			double wTraining = (double) locationAPStat.appearance / (double) locationStat.getNumberOfEntries();
			double wTesting = (double) currentAPStat.appearance / (double) blockSizeTesting;
			if (trainingWeighted && testingWeighted)
				res += Math.min(wTraining, wTesting) * Math.pow(currentAPStat.average - locationAPStat.average, 2);
			else if (trainingWeighted)
				res += wTraining * Math.pow(currentAPStat.average - locationAPStat.average, 2);
			else if (testingWeighted)
				res += wTesting * Math.pow(currentAPStat.average - locationAPStat.average, 2);
			else
				res += Math.pow(currentAPStat.average - locationAPStat.average, 2);
		}
		
		return res;
	}
	
	@Override
	public void init() {
		currentTest = new WifiMapData();
		cache = new TreeMap<String, Vd2_3MapData>();
	}

	@Override
	public void clearMaps() {
		// TODO Auto-generated method stub
		cache.clear();
		reset();
	}

	@Override
	public void addLocation(LocationDatabaseObject locationDO) {
		// TODO Auto-generated method stub
		
		if (locationDO != null) {
			new ServerAPI();
			MapData mapData = MapData.readMapData(locationDO.getLocationDataPath(ServerAPI.getDataTypeIDByAlgorithmClassName(ALGORITHM_CLASSNAME)));
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
	public void addLocations(Vector<LocationDatabaseObject> listLocationDOs) {
		for (LocationDatabaseObject locationDO : listLocationDOs) {
			addLocation(locationDO);
		}
	}

	@Override
	public String getAlgorithmClassName() {
		return ALGORITHM_CLASSNAME;
	}

	@Override
	public void feedLocationData(LocationData locationData) {
		while (currentTest.getSpsList().size() >= blockSizeTesting) {
			currentTest.getSpsList().remove(0);
		}
		currentTest.getSpsList().add((ScanningPoint) locationData);
	}

	@Override
	public synchronized void reset() {
		currentTest.getSpsList().clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultOfDetection detectLocation() {
		
		if (!isReadyToLocalize()) {
			return null;
		}

		Vd2_3MapData currentStat = new Vd2_3MapData(currentTest);
		
		TreeMap<String, Double> distanceFromCurrentLocation = new TreeMap<String, Double>();
		
		for (String locationID : cache.keySet()) {
			distanceFromCurrentLocation.put(locationID, calculateEuclideDistance(locationID, currentStat));
		}
		
		@SuppressWarnings("rawtypes")
		List list = new LinkedList(distanceFromCurrentLocation.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

			@Override
			public int compare(Entry<String, Double> o1,
					Entry<String, Double> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		ResultOfDetection result = new ResultOfDetection();
		
		for (int i = 0; i < maxCandidates && i < list.size(); ++i){
			Entry<String, Double> entry_i = (Entry<String, Double>)list.get(i);
			if (entry_i.getValue() == Double.MAX_VALUE) {
				break;
			}
			result.getListLocationID().add(entry_i.getKey());			
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
		return currentTest.getSpsList().size() >= blockSizeTesting;
	}

	public void setMaxCandidates(int maxCandidates) {
		this.maxCandidates = maxCandidates;
	}

	public int getMaxCandidates() {
		return maxCandidates;
	}
	
	public int getBlockSizeTesting() {
		return blockSizeTesting;
	}

	public void setBlockSizeTesting(int blockSizeTesting) {
		this.blockSizeTesting = blockSizeTesting;
	}

	public boolean isTrainingWeighted() {
		return trainingWeighted;
	}

	public void setTrainingWeighted(boolean trainingWeighted) {
		this.trainingWeighted = trainingWeighted;
	}

	public boolean isTestingWeighted() {
		return testingWeighted;
	}

	public void setTestingWeighted(boolean testingWeighted) {
		this.testingWeighted = testingWeighted;
	}

	public void setMaxAP(int maxAP) {
		this.maxAP = maxAP;
	}

	public int getMaxAP() {
		return maxAP;
	}

}
