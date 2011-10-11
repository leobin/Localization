package localization.data.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import localization.data.entity.contentobject.LocationDataObject;
import locationaware.clientserver.Location;

import com.localization.manager.AlgorithmDataManagement;
import com.localization.manager.LocationDataManagement;
import com.localization.manager.MacAddressDataManagement;
import com.localization.manager.PointDataManagement;
import com.localization.manager.UserDataManagement;
import com.localization.server.ServerConfig;

/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@Table(name = "location")
public class LocationDatabaseObject implements Serializable, Comparable<LocationDatabaseObject> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private String locationId;

	@Column(name = "content_zoom")
	private int contentZoom;

	@Column(name = "google_location_id")
	private String googleLocationId;
	
	@Column(name = "build_mac_address_id")
	private long macaddressIDForBuildDevice;

	public long getMacaddressIDForBuildDevice() {
		return macaddressIDForBuildDevice;
	}

	public void setMacaddressIDForBuildDevice(long macaddressIDForBuildDevice) {
		this.macaddressIDForBuildDevice = macaddressIDForBuildDevice;
	}

	@Lob()
	@Column(name = "location_description")
	private String locationDescription;

	@Column(name = "location_name")
	private String locationName;

	@SuppressWarnings("unused")
	@Column(name = "location_data_path")
	private String locationDataPath;

	// bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name = "parent_location_id")
	private LocationDatabaseObject parentLocation;

	// bi-directional many-to-one association to Location
	@OneToMany(mappedBy = "parentLocation")
	private List<LocationDatabaseObject> locations;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDatabaseObject user;

	// bi-directional many-to-many association to MacAddress
	@ManyToMany(mappedBy = "locations")
	private List<MacAddressDatabaseObject> macAddresses;

	// bi-directional many-to-one association to Point
	@OneToMany(mappedBy = "location")
	@OrderBy("pointOrder")
	private List<PointDatabaseObject> points;

	// bi-directional many-to-one association to Algorithm
	@ManyToOne
	@JoinColumn(name = "algorithm_id")
	private AlgorithmDatabaseObject algorithm;

	@ManyToMany
	@JoinTable(name = "location_algorithm", joinColumns = { @JoinColumn(name = "location_id") }, inverseJoinColumns = { @JoinColumn(name = "algorithm_id") })
	private List<AlgorithmDatabaseObject> algorithms;

	public List<AlgorithmDatabaseObject> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<AlgorithmDatabaseObject> algorithms) {
		this.algorithms = algorithms;
		LocationDataManagement.updateLocation(this);
	}

	public String toString() {
		return this.locationName;
	}

	public LocationDatabaseObject() {
	}

	public String getLocationId() {
		return this.locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
		LocationDataManagement.updateLocation(this);
	}

	public int getContentZoom() {
		return this.contentZoom;
	}

	public void setContentZoom(int contentZoom) {
		this.contentZoom = contentZoom;
		LocationDataManagement.updateLocation(this);
	}

	public String getGoogleLocationId() {
		return this.googleLocationId;
	}

	public void setGoogleLocationId(String googleLocationId) {
		this.googleLocationId = googleLocationId;
		LocationDataManagement.updateLocation(this);
	}

	public String getLocationDescription() {
		return this.locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
		LocationDataManagement.updateLocation(this);
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
		LocationDataManagement.updateLocation(this);
	}

	public LocationDatabaseObject getParentLocation() {
		return this.parentLocation;
	}

	public void setParentLocation(LocationDatabaseObject location) {
		this.parentLocation = location;
		this.parentLocation.getLocations().add(location);
		LocationDataManagement.updateLocation(this);
		LocationDataManagement.updateLocation(this.parentLocation);
	}

	public List<LocationDatabaseObject> getLocations() {
		
		return this.locations;
	}

	public void setLocations(List<LocationDatabaseObject> locations) {
		this.locations = locations;
		LocationDataManagement.updateLocation(this);
	}

	public UserDatabaseObject getUser() {
		return this.user;
	}

	public void setUser(UserDatabaseObject user) {
		this.user = user;
		LocationDataManagement.updateLocation(this);
	}

	public List<MacAddressDatabaseObject> getMacAddresses() {
		return this.macAddresses;
	}

	public void setMacAddresses(List<MacAddressDatabaseObject> macAddresses) {
		this.macAddresses = macAddresses;
		LocationDataManagement.updateLocation(this);
	}

	public List<PointDatabaseObject> getPoints() {
		return this.points;
	}

	public void setPoints(List<PointDatabaseObject> points) {
		this.points = points;
		LocationDataManagement.updateLocation(this);
	}

	public AlgorithmDatabaseObject getAlgorithm() {
		return this.algorithm;
	}

	public void setAlgorithm(AlgorithmDatabaseObject algorithm) {
		this.algorithm = algorithm;
		LocationDataManagement.updateLocation(this);
	}

	public void setLocationDataPath(String locationDataPath) {
		this.locationDataPath = locationDataPath;
		LocationDataManagement.updateLocation(this);
	}

	public String getLocationDataPath(String dataTypeID) {
//		LocationDatabaseObject rootLocation = this;
//
//		while (rootLocation.parentLocation != null) {
//			rootLocation = rootLocation.parentLocation;
//		}

		return ServerConfig.dirData + "/" + this.getUser().getUserId() + "/" + this.getLocationId() + "_" + dataTypeID
		+ ServerConfig.extensionMapDataFile;
	}

	@Override
	public boolean equals(Object arg0) {

		return (this.locationId == ((LocationDatabaseObject) arg0).locationId);
	}

	public void update(LocationDataObject location) {
		this.contentZoom = location.getContentZoom();
		this.googleLocationId = location.getGoogleLocationId();
		this.locationDescription = location.getLocationDescription();
		this.locationName = location.getLocationName();
		LocationDataManagement.updateLocation(this);
	}
	
	public LocationDatabaseObject(LocationDataObject location) {
		this.contentZoom = location.getContentZoom();
		this.googleLocationId = location.getGoogleLocationId();
		this.locationDescription = location.getLocationDescription();
		this.locationName = location.getLocationName();
		//LocationDataManagement.addNewLocation(this);
	}

	public void setUser(String userId) {
		UserDatabaseObject userByID = UserDataManagement.getUserByID(userId);
		this.user = userByID;
		LocationDataManagement.updateLocation(this);
	}

	public void setParentLocation(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.parentLocation = locationByID;
		locationByID.getLocations().add(this);
		LocationDataManagement.updateLocation(this);
		LocationDataManagement.updateLocation(locationByID);
		LocationDataManagement.updateLocation(this.parentLocation);
		
	}

	public void setAlgorithm(String algorithmID) {
		AlgorithmDatabaseObject algorithmByID = AlgorithmDataManagement.getAlgorithmByID(algorithmID);
		this.algorithm = algorithmByID;
		LocationDataManagement.updateLocation(this);
	}


	public void addAlgorithm(String algorithmID) {
		// TODO Auto-generated method stub
		AlgorithmDatabaseObject algorithmByID = AlgorithmDataManagement.getAlgorithmByID(algorithmID);
		this.algorithms.add(algorithmByID);
		LocationDataManagement.updateLocation(this);
	}

	public void removeAlgorithm(String algorithmID) {
		AlgorithmDatabaseObject algorithmByID = AlgorithmDataManagement.getAlgorithmByID(algorithmID);
		this.algorithms.remove(algorithmByID);
		LocationDataManagement.updateLocation(this);
	}

	public void addPoint(String pointID) {
		PointDatabaseObject pointByID = PointDataManagement.getPointByID(pointID);
		this.points.add(pointByID);
		LocationDataManagement.updateLocation(this);
	}

	public void removePoint(String pointID) {
		PointDatabaseObject pointByID = PointDataManagement.getPointByID(pointID);
		this.points.remove(pointByID);
		LocationDataManagement.updateLocation(this);
	}

	public void addMacaddress(String macaddressID) {
		MacAddressDatabaseObject macAddressByID = MacAddressDataManagement.getMacAddressByID(macaddressID);
		this.macAddresses.add(macAddressByID);
		LocationDataManagement.updateLocation(this);
		
	}

	public void removeMacaddress(String macaddressID) {
		MacAddressDatabaseObject macAddressByID = MacAddressDataManagement.getMacAddressByID(macaddressID);
		this.macAddresses.remove(macAddressByID);
		LocationDataManagement.updateLocation(this);
		
	}

	public void removeLocation(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.add(locationByID);
		LocationDataManagement.updateLocation(this);
	}

	public void addLocation(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.remove(locationByID);
		LocationDataManagement.updateLocation(this);
	}

	@Override
	public int compareTo(LocationDatabaseObject arg0) {
		// TODO Auto-generated method stub
		return this.getLocationId().compareTo(arg0.getLocationId());
	}

	public Location createLocation() {
		Location location = new Location();
		location.setLocationId(this.locationId);
		location.setGoogleLocationId(this.googleLocationId);
		location.setLocationDescription(this.locationDescription);
		location.setLocationName(this.locationName);
		location.setUserId(this.getUser().getUserId());
		location.setUserName(this.getUser().getUserName());
		for (PointDatabaseObject pointDO : this.getPoints()) {
			location.getListPoints().add(pointDO.createPoint());
		}
		Collections.sort(location.getListPoints());
		
		return location;
	}

}