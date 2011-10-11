package localization.data.entity.contentobject;

import java.io.Serializable;
import java.util.List;

import localization.data.entity.LocationDatabaseObject;

import com.localization.manager.LocationDataManagement;
import com.localization.manager.PointDataManagement;
import com.localization.server.ServerConfig;

public class LocationDataObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String locationId = null;

	private int contentZoom;

	private String googleLocationId;

	private String locationDescription;

	private String locationName;

	private String locationDataPath;

	private String buildMacAdressID;

	private List<LocationDataObject> locations;

	private UserDataObject user;

	private List<MacAddressDataObject> macAddresses;

	private List<PointDataObject> points;

	private AlgorithmDataObject algorithm;

	private List<AlgorithmDataObject> algorithms;

	public String getBuildMacAdressID() {
		return buildMacAdressID;
	}

	public void setBuildMacAdressID(String buildMacAdressID) {
		this.buildMacAdressID = buildMacAdressID;
	}

	private LocationDataObject parentLocation;

	private String parentLocationId = null;

	public List<AlgorithmDataObject> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<AlgorithmDataObject> algorithms) {
		this.algorithms = algorithms;
	}

	public String toString() {
		return this.locationName;
	}

	public LocationDataObject() {
	}

	public LocationDataObject(LocationDatabaseObject location) {
		this.locationId = location.getLocationId();
		this.contentZoom = location.getContentZoom();
		this.googleLocationId = location.getGoogleLocationId();
		this.locationDescription = location.getLocationDescription();
		this.locationName = location.getLocationName();
		this.buildMacAdressID = "" + location.getMacaddressIDForBuildDevice();
		this.locations = LocationDataManagement.createListObject(location
				.getLocations(), this);
		if (location.getAlgorithm() != null) {
			this.algorithm = new AlgorithmDataObject(location.getAlgorithm());
		}
		// location parent will be add in client because
		// this.parentLocation = new
		// LocationDataObject(location.getParentLocation());
		this.points = PointDataManagement
				.createListObject(location.getPoints());
		LocationDatabaseObject parentLocation = location.getParentLocation();
		if (parentLocation != null)
			this.parentLocationId = parentLocation.getLocationId();
		else
			this.parentLocationId = null;
	}

	public String getLocationId() {
		return this.locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public int getContentZoom() {
		return this.contentZoom;
	}

	public void setContentZoom(int contentZoom) {
		this.contentZoom = contentZoom;
	}

	public String getGoogleLocationId() {
		return this.googleLocationId;
	}

	public void setGoogleLocationId(String googleLocationId) {
		this.googleLocationId = googleLocationId;
	}

	public String getLocationDescription() {
		return this.locationDescription;
	}

	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public LocationDataObject getParentLocation() {
		return this.parentLocation;
	}

	public void setParentLocation(LocationDataObject location) {
		this.parentLocation = location;
	}

	public List<LocationDataObject> getLocations() {
		return this.locations;
	}

	public void setLocations(List<LocationDataObject> locations) {
		this.locations = locations;
	}

	public UserDataObject getUser() {
		return this.user;
	}

	public void setUser(UserDataObject user) {
		this.user = user;
	}

	public List<MacAddressDataObject> getMacAddresses() {
		return this.macAddresses;
	}

	public void setMacAddresses(List<MacAddressDataObject> macAddresses) {
		this.macAddresses = macAddresses;
	}

	public List<PointDataObject> getPoints() {
		return this.points;
	}

	public void setPoints(List<PointDataObject> points) {
		this.points = points;
	}

	public AlgorithmDataObject getAlgorithm() {
		return this.algorithm;
	}

	public void setAlgorithm(AlgorithmDataObject algorithm) {
		this.algorithm = algorithm;
	}

	public void setLocationDataPath(String locationDataPath) {
		this.locationDataPath = locationDataPath;
	}

	public String getLocationDataPath(String dataTypeID) {
		LocationDataObject rootLocation = this;

		while (rootLocation.parentLocation != null) {
			rootLocation = rootLocation.parentLocation;
		}

		return ServerConfig.dirData + "/" + this.getLocationId() + "_"
				+ this.getUser().getUserId() + "_" + dataTypeID
				+ ServerConfig.extensionMapDataFile;
	}

	@Override
	public boolean equals(Object arg0) {

		return (this.locationId == ((LocationDataObject) arg0).locationId);
	}

	public void setParentLocationId(String parentLocationId) {
		this.parentLocationId = parentLocationId;
	}

	public String getParentLocationId() {
		return parentLocationId;
	}
}