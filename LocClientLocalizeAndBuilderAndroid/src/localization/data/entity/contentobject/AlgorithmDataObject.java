package localization.data.entity.contentobject;

import com.localization.manager.LocationDataManagement;
import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the algorithm database table.
 * 
 */
public class AlgorithmDataObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String algorithmId;
    private String algorithmClassName;
    private String algorithmDescription;
    private String algorithmName;
    //bi-directional many-to-one association to Location
    //list locations use this algorithm as main algorithm
    private List<LocationDataObject> locations;
    //bi-directional many-to-one association to DataType
    private DataTypeDataObject dataType;
    //bi-directional many-to-many association to MacAddress
    //this is for list all location
    private List<LocationDataObject> listLocations;

    public List<LocationDataObject> getListLocations() {
        return (List<LocationDataObject>) CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_GET_LIST_LOCATIONS, null);
    }

    public void setListLocations(List<LocationDataObject> listLocations) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_SET_LIST_LOCATIONS, LocationDataManagement.getIdArrayFromListObject(listLocations));
    }

    public AlgorithmDataObject() {
    }

    public String toString() {
        return this.algorithmName;
    }

    public String getAlgorithmId() {
        return this.algorithmId;
    }

    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getAlgorithmClassName() {
        return this.algorithmClassName;
    }

    public void setAlgorithmClassName(String algorithmClassName) {
        this.algorithmClassName = algorithmClassName;
    }

    public String getAlgorithmDescription() {
        return this.algorithmDescription;
    }

    public void setAlgorithmDescription(String algorithmDescription) {
        this.algorithmDescription = algorithmDescription;
    }

    public String getAlgorithmName() {
        return this.algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public List<LocationDataObject> getLocations() {
        return (List<LocationDataObject>) CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_GET_LOCATIONS, null);
    }

    public void setLocations(List<LocationDataObject> locations) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_SET_LOCATIONS, LocationDataManagement.getIdArrayFromListObject(locations));
    }

    public DataTypeDataObject getDataType() {
        return (DataTypeDataObject) CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_GET_DATA_TYPE, null);

    }

    public void setDataType(String dataType) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_SET_DATATYPE, dataType);
    }

    public void addLocation(String location) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_ADD_LOCATION, location);
    }

    public void addLocationToListLocation(String location) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_ADD_LOCATION_TO_LIST_LOCATIONS, location);
    }

    public void removeLocation(String locationId) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_REMOVE_LOCATION_OF_LOCATIONS, locationId);
    }

    public void removeLocationOfListLocation(String locationId) {
        CallServerFunction.callServerObjectFunction(this.algorithmId, ObjectMapping.ALGORITHM_OBJECT, ObjectMapping.ALGORITHM_REMOVE_LOCATION_OF_LIST_LOCATIONS, locationId);
    }
}
