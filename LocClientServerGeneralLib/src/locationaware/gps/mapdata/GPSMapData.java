package locationaware.gps.mapdata;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TreeMap;

import locationaware.clientserver.MapData;
import locationaware.gps.GPSCoordinate;

/**
 * @author 
 * The GPS Map Data to be uploaded to server
 *
 */
public class GPSMapData extends MapData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 26814815366352219L;
	public static final String VERSION_1 = "1.0";
	
	// mapping each GPS Coordinate to a PointID
	private TreeMap<String, GPSCoordinate> mappingGPStoPoint = new TreeMap<String, GPSCoordinate>();
	
	public GPSMapData() {
		creationDate = Calendar.getInstance().getTime();
		version = VERSION_1;
		dataTypeClassName = GPSMapData.class.getName();
	}

	/**
	 * @return the mapping from GPS Coordinate to PointID
	 */
	public TreeMap<String, GPSCoordinate> getMappingGPStoPoint() {
		return mappingGPStoPoint;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String out = "";
		for (String pointID : mappingGPStoPoint.keySet()) {
			out += pointID + ": " + mappingGPStoPoint.get(pointID).toString() + "\n";
		}
		return out;
	}
	
}
