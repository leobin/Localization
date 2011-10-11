package locationaware.gps;

import java.io.Serializable;

import locationaware.clientserver.LocationData;

/**
 * @author 
 * 3D GPS Coordinate
 *
 */
public class GPSCoordinate extends LocationData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1945763077802139053L;
	
	private double altitude;
	private double latitude;
	private double longitude;
	
	public GPSCoordinate(double altitude, double latitude, double longitude) {
		// TODO Auto-generated constructor stub
		this.altitude = altitude;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLongitude() {
		return longitude;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String out = "(" + altitude + ", " + latitude + ", " + longitude + ")";
		return out;
	}
}
