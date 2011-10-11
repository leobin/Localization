package locationaware.gps.algorithm;

import java.util.TreeMap;
import java.util.Vector;

import localization.data.entity.LocationDatabaseObject;
import locationaware.Algorithm;
import locationaware.ResultOfDetection;
import locationaware.clientserver.Location;
import locationaware.clientserver.LocationData;
import locationaware.clientserver.MapData;
import locationaware.gps.GPSCoordinate;
import locationaware.gps.mapdata.GPSMapData;

import com.localization.server.ServerAPI;

/**
 * @author Dinh
 * The algorithm used for localization using GPS signal of user device
 */
public class AlgGPS implements Algorithm{
	
	/**
	 * The mapping location with its GPSMapData
	 */
	TreeMap<Location, GPSMapData> cache;
	
	/**
	 * current GPS coordinate of user
	 */
	GPSCoordinate currentGPSCoordinate = null;
	
	public AlgGPS() {
		init();
	}
	
	@Override
	public void init() {
		cache = new TreeMap<Location, GPSMapData>();
	}

	@Override
	public void clearMaps() {
		cache.clear();
	}

	@Override
	public void addLocation(LocationDatabaseObject locationDO) {
		Location location = locationDO.createLocation();
		new ServerAPI();
		GPSMapData gpsMapData =  (GPSMapData) MapData.readMapData(locationDO.getLocationDataPath(ServerAPI.getDataTypeIdByDataTypeClassName(GPSMapData.class.getName())));
		cache.put(location, gpsMapData);
	}

	@Override
	public void addLocations(Vector<LocationDatabaseObject> listLocationDOs) {
		for (LocationDatabaseObject locationDatabaseObject : listLocationDOs) {
			addLocation(locationDatabaseObject);
		}
	}

	@Override
	public String getAlgorithmClassName() {
		return AlgGPS.class.getName();
	}
	
	private boolean insideLocation(Location location) {	
		int count = 0;
		int n = location.getListPoints().size();
		String pointID1, pointID2;
		GPSCoordinate point1, point2;
		
		if (cache.get(location) == null) {
			return false;
		}
		
		for (int i = 0; i < n; ++i) {
			pointID1 = location.getListPoints().get(i%n).getPointId();
			pointID2 = location.getListPoints().get((i+1)%n).getPointId();
			point1 = cache.get(location).getMappingGPStoPoint().get(pointID1);
			point2 = cache.get(location).getMappingGPStoPoint().get(pointID2);
			if (rayIntersectsSegment(point1, point2)) {
				count++;
			}
		}
		
		return ((count%2) == 1);
	}
	
	private boolean rayIntersectsSegment(GPSCoordinate point1, GPSCoordinate point2) {
		if (currentGPSCoordinate == null) {
			return false;
		}
		
		double eps = Double.MIN_VALUE;
		double tan_angle1, tan_angle2;
		
		if (Double.compare(point1.getLatitude(), point2.getLatitude()) > 0) {
			GPSCoordinate temp = point1;
			point1 = point2;
			point2 = temp;
		}
		
		if (Double.compare(currentGPSCoordinate.getLatitude(), point1.getLatitude()) == 0 || Double.compare(currentGPSCoordinate.getLatitude(), point2.getLatitude()) == 0) {
			currentGPSCoordinate.setLatitude(currentGPSCoordinate.getLatitude() + eps);
		}
		
		if (Double.compare(currentGPSCoordinate.getLatitude(), point1.getLatitude()) < 0 || Double.compare(currentGPSCoordinate.getLatitude(), point2.getLatitude()) > 0) {
			return false;
		} else if (Double.compare(currentGPSCoordinate.getLongitude(), point1.getLongitude()) > 0 && Double.compare(currentGPSCoordinate.getLongitude(), point2.getLongitude()) > 0) {
			return false;
		} else if (Double.compare(currentGPSCoordinate.getLongitude(), point1.getLongitude()) < 0 && Double.compare(currentGPSCoordinate.getLongitude(), point2.getLongitude()) < 0) {
			return true;
		} else {
			
			if (Double.compare(point1.getLongitude(), point2.getLongitude()) != 0) {
				tan_angle1 = (point2.getLatitude() - point1.getLatitude())/(point2.getLongitude() - point1.getLongitude());
			} else {
				tan_angle1 = Double.MAX_VALUE;
			}
			
			if (Double.compare(point1.getLongitude(), currentGPSCoordinate.getLongitude()) != 0) {
				tan_angle2 = (currentGPSCoordinate.getLatitude() - point1.getLatitude())/(currentGPSCoordinate.getLongitude() - point1.getLongitude());
			} else {
				tan_angle2 = Double.MAX_VALUE;
			}
			
			if (tan_angle2 >= tan_angle1) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	@Override
	public ResultOfDetection detectLocation() {
		ResultOfDetection result = new ResultOfDetection();
		
		for (Location location : cache.keySet()) {
			if (insideLocation(location)) {
				result.getListLocationID().add(location.getLocationId());
			}
		}
		
		if (result.getListLocationID().isEmpty()) {
			result.setId(ResultOfDetection.OUT_OF_MAP);
		} else {
			result.setId(ResultOfDetection.INSIDE_LOCATION);
		}
		
		return result;
	}

	@Override
	public void feedLocationData(LocationData locationData) {
		currentGPSCoordinate = (GPSCoordinate) locationData;
	}

	@Override
	public void reset() {
		currentGPSCoordinate = null;
	}

	@Override
	public boolean isReadyToLocalize() {
		return (currentGPSCoordinate != null);
	}

}
