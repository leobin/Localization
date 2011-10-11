package localization.data.entity.contentobject;

import com.localization.manager.AlgorithmDataManagement;
import com.localization.manager.LocationDataManagement;
import com.localization.manager.MacAddressDataManagement;
import com.localization.manager.PointDataManagement;
import com.localization.other.ObjectMapping;
import java.util.List;
import com.localization.server.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The persistent class for the location database table.
 * 
 */
public class LocationDataObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String locationId;
	private int contentZoom;
	private String googleLocationId;
	private String locationDescription;
	private String locationName;
	private String locationDataPath;
	private LocationDataObject parentLocation;
	private String parentLocationId = null;
	private List<LocationDataObject> locations;
	private UserDataObject user;
	private List<MacAddressDataObject> macAddresses;
	private List<PointDataObject> points;
	private AlgorithmDataObject algorithm;
	private List<AlgorithmDataObject> algorithms;

	public List<AlgorithmDataObject> getAlgorithms() {
		// only root location has algorithm
		if (this.algorithms == null && this.getParentLocation() == null) {
			this.algorithms = (List<AlgorithmDataObject>) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_ALGORITHMS, null);
		}
		return this.algorithms;

	}

	public void setAlgorithms(List<AlgorithmDataObject> algorithms) {
		this.algorithms = algorithms;
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_SET_ALGORITHMS,
				AlgorithmDataManagement.getIdArrayFromListObject(algorithms));
	}

	public String toString() {
		return this.locationName;
	}

	public LocationDataObject() {
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
		if (this.parentLocation == null) {
			this.parentLocation = (LocationDataObject) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_PARENT_LOCATION, null);
		}
		return this.parentLocation;
		// return this.parentLocation;
	}

	public void setParentLocation(LocationDataObject parentLocation) {
		this.parentLocation = parentLocation;
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_SET_PARENT_LOCATION,
				parentLocation.getLocationId());
	}

	@SuppressWarnings("unchecked")
	public List<LocationDataObject> getLocations() {
		if (this.locations == null) {
			this.locations = (List<LocationDataObject>) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_LOCATIONS, null);
		}
		return this.locations;
	}

	public void setLocations(List<LocationDataObject> locations) {
		this.locations = locations;
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_SET_LOCATIONS,
				LocationDataManagement.getIdArrayFromListObject(locations));
	}

	public UserDataObject getUser() {
		if (this.user == null) {
			this.user = (UserDataObject) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_USER, null);
		}
		// return this.user;
		return this.user;
	}

	public void setUser(UserDataObject user) {
		this.user = user;
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_USER,
				user.getUserId());

	}

	public List<MacAddressDataObject> getMacAddresses() {
		if (this.macAddresses == null) {
			this.macAddresses = (List<MacAddressDataObject>) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_MAC_ADDRESS, null);
		}
		return this.macAddresses;

		// return this.macAddresses;
	}

	public void setMacAddresses(List<MacAddressDataObject> listMacAddress) {
		this.macAddresses = listMacAddress;
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_SET_MACADDRESSES,
				MacAddressDataManagement
						.getIdArrayFromListObject(listMacAddress));
	}

	@SuppressWarnings("unchecked")
	public List<PointDataObject> getPoints() {
		if (this.points == null) {
			ArrayList<PointDataObject> callServerObjectFunction = (ArrayList<PointDataObject>) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_POINTS, null);
			this.points = callServerObjectFunction;
		}
		return this.points;
	}

	public void setPoints(List<PointDataObject> points) {
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_SET_POINTS,
				PointDataManagement.getIdArrayFromListObject(points));
	}

	public AlgorithmDataObject getAlgorithm() {
		// only root location have algorithm link
		if (this.algorithm == null && this.getParentLocation() == null) {
			this.algorithm = (AlgorithmDataObject) CallServerFunction
					.callServerObjectFunction(this.locationId,
							ObjectMapping.LOCATION_OBJECT,
							ObjectMapping.LOCATION_GET_ALGORITHM, null);
		}
		return this.algorithm;
	}

	public void setAlgorithm(AlgorithmDataObject algorithm) {
		this.algorithm = algorithm;
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_SET_ALGORITHM,
				algorithm.getAlgorithmId());
	}

	public void setLocationDataPath(String locationDataPath) {
		this.locationDataPath = locationDataPath;
	}

	public String getLocationDataPath(String dataTypeID) {
		// return (String)
		// CallServerFunction.callServerObjectFunctionTwoParameter(ObjectMapping.LOCATION_OBJECT,
		// ObjectMapping.G, null, null);

		LocationDataObject rootLocation = this;

		while (rootLocation.parentLocation != null) {
			rootLocation = rootLocation.parentLocation;
		}

		return CommonConfig.dirData + "/" + this.getLocationId() + "_"
				+ this.getUser().getUserId() + "_" + dataTypeID
				+ CommonConfig.extensionMapDataFile;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) {
			return false;
		}
		return (this.locationId == ((LocationDataObject) arg0).locationId);
	}

	public void addAlgorithm(AlgorithmDataObject algorithm) {
		this.algorithms.add(algorithm);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_ADD_ALGORITHM,
				algorithm.getAlgorithmId());
	}

	public void removeAlgorithm(AlgorithmDataObject algorithm) {
		this.algorithms.remove(algorithm);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_REMOVE_ALGORITHM,
				algorithm.getAlgorithmId());
	}

	public void addLocation(LocationDataObject location) {
		this.locations.add(location);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_ADD_LOCATION, location.getLocationId());
	}

	public void removeLocation(LocationDataObject location) {
		this.locations.remove(location);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_REMOVE_LOCATION,
				location.getLocationId());
	}

	public void addMacaddress(MacAddressDataObject macaddress) {
		this.macAddresses.add(macaddress);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_ADD_MACADDRESS,
				macaddress.getMacAddressId());
	}

	public void removeMacaddress(MacAddressDataObject macaddress) {
		this.macAddresses.remove(macaddress);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_REMOVE_MACADDRESS,
				macaddress.getMacAddressId());
	}

	public void addPoint(PointDataObject point) {
		this.points.add(point);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_ADD_POINT, point.getPointId());
	}

	public void removePoint(PointDataObject point) {
		this.points.remove(point);
		CallServerFunction.callServerObjectFunction(this.locationId,
				ObjectMapping.LOCATION_OBJECT,
				ObjectMapping.LOCATION_REMOVE_POINT, point.getPointId());
	}

	public void setParentLocationId(String parentLocationId) {
		this.parentLocationId = parentLocationId;
	}

	public String getParentLocationId() {
		return parentLocationId;
	}
}
