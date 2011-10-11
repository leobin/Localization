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
public class LocationDataObject implements Serializable{
private static final long serialVersionUID = 1L;

    private String locationId;
    private int contentZoom;
    private String googleLocationId;
    private String locationDescription;
    private String locationName;
    private String locationDataPath;
    private String buildMacAdressID;
    private LocationDataObject parentLocation;
    private String parentLocationId = null;
    private List<LocationDataObject> locations;
    private UserDataObject user;
    private List<MacAddressDataObject> macAddresses;
    private List<PointDataObject> points;
    private AlgorithmDataObject algorithm;
    private List<AlgorithmDataObject> algorithms;

    public List<AlgorithmDataObject> getAlgorithms() {
        return (List<AlgorithmDataObject>) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_ALGORITHMS, null);

    }

    public void setAlgorithms(List<AlgorithmDataObject> algorithms) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_ALGORITHMS, AlgorithmDataManagement.getIdArrayFromListObject(algorithms));
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
        return (LocationDataObject) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_PARENT_LOCATION, null);
//      return this.parentLocation;
    }

    public void setParentLocation(String parentLocationID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_PARENT_LOCATION, parentLocationID);
    }

    public List<LocationDataObject> getLocationsOnline() {
    	locations = (List<LocationDataObject>) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_LOCATIONS, null); 
        return locations;
    }
    
    public List<LocationDataObject> getLocations() {
    	return locations;
    }

    public void setLocationsOnline(List<LocationDataObject> locations) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_LOCATIONS, LocationDataManagement.getIdArrayFromListObject(locations));
    }
    
    public void setLocations(List<LocationDataObject> locations) {
    	this.locations = locations;
    }

    public UserDataObject getUser() {
        return (UserDataObject) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_USER, null);
//        return this.user;
    }

    public void setUser(String userID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_USER, userID);

    }

    public List<MacAddressDataObject> getMacAddresses() {
        return (List<MacAddressDataObject>) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_MAC_ADDRESS, null);

//        return this.macAddresses;
    }

    public void setMacAddresses(List<MacAddressDataObject> listMacAddress) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_MACADDRESSES, MacAddressDataManagement.getIdArrayFromListObject(listMacAddress));
    }

    public List<PointDataObject> getPoints() {
        return (ArrayList<PointDataObject>) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_POINTS, null);

//        return this.points;
    }

    public void setPoints(List<PointDataObject> points) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_POINTS, PointDataManagement.getIdArrayFromListObject(points));
    }

    public AlgorithmDataObject getAlgorithm() {
        return (AlgorithmDataObject) CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_GET_ALGORITHM, null);
    }

    public void setAlgorithm(String algorithm) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_SET_ALGORITHM, algorithm);
    }

    public void setLocationDataPath(String locationDataPath) {
        this.locationDataPath = locationDataPath;
    }

    public String getLocationDataPath(String dataTypeID) {
//        return (String) CallServerFunction.callServerObjectFunctionTwoParameter(ObjectMapping.LOCATION_OBJECT, ObjectMapping.G, null, null);

        LocationDataObject rootLocation = this;

        while (rootLocation.parentLocation != null) {
            rootLocation = rootLocation.parentLocation;
        }

        return CommonConfig.dirData + "/" + this.getLocationId() + "_" + this.getUser().getUserId() + "_" + dataTypeID + CommonConfig.extensionMapDataFile;
    }

    @Override
    public boolean equals(Object arg0) {

        return (this.locationId == ((LocationDataObject) arg0).locationId);
    }

    public void addAlgorithm(String algorithmID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_ADD_ALGORITHM, algorithm);
    }

    public void removeAlgorithm(String algorithmID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_REMOVE_ALGORITHM, algorithmID);
    }

    public void addLocation(String location) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_ADD_LOCATION, location);
    }

    public void removeLocation(String locationID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_REMOVE_LOCATION, locationID);
    }

    public void addMacaddress(String macaddress) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_ADD_MACADDRESS, macaddress);
    }

    public void removeMacaddress(String macaddressID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_REMOVE_MACADDRESS, macaddressID);
    }

    public void addPoint(String point) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_ADD_POINT, point);
    }

    public void removePoint(String pointID) {
        CallServerFunction.callServerObjectFunction(this.locationId, ObjectMapping.LOCATION_OBJECT, ObjectMapping.LOCATION_REMOVE_POINT, pointID);
    }

	public void setParentLocationId(String parentLocationId) {
		this.parentLocationId = parentLocationId;
	}

	public String getParentLocationId() {
		return parentLocationId;
	}

	public void setBuildMacAdressID(String buildMacAddressID) {
		this.buildMacAdressID = buildMacAddressID;
	}

	public String getBuildMacAdressID() {
		return buildMacAdressID;
	}
}
