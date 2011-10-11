package locationaware.clientserver;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author songuku
 * A location created by userId, identify by locationId
 */
public class Location implements Serializable, Comparable<Location> {

	private static final long serialVersionUID = 841918510490799011L;

	private String locationId;
	private String googleLocationId;	// to link with location on google map
	private String locationDescription;
	private String locationName;
	private String userId;				// the one who created this location
	private String userName;
	private ArrayList<Point> listPoints = new ArrayList<Point>();	// all vertices by the order
	
	public Location() {

	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setGoogleLocationId(String googleLocationId) {
		this.googleLocationId = googleLocationId;
	}

	public String getGoogleLocationId() {
		return googleLocationId;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getLocationDescription() {
		return locationDescription;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the owner of this location
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	
	/**
	 * @return the vertices of the location by the order
	 */
	public ArrayList<Point> getListPoints() {
		return listPoints;
	}

	public void setListPoints(ArrayList<Point> listPoints) {
		this.listPoints = listPoints;
	}

	@Override
	public int compareTo(Location o) {
		// TODO Auto-generated method stub
		return this.locationId.compareTo(o.locationId);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.locationName;
	}
}
