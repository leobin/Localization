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
	// bi-directional many-to-one association to Location
	// list locations use this algorithm as main algorithm
	private List<LocationDataObject> locations;
	// bi-directional many-to-one association to DataType
	private DataTypeDataObject dataType;
	// bi-directional many-to-many association to MacAddress
	// this is for list all location
	private List<LocationDataObject> listLocations;

	public List<LocationDataObject> getListLocations() {
		return (List<LocationDataObject>) CallServerFunction
				.callServerObjectFunction(this.algorithmId,
						ObjectMapping.ALGORITHM_OBJECT,
						ObjectMapping.ALGORITHM_GET_LIST_LOCATIONS, null);
	}

	public void setListLocations(List<LocationDataObject> listLocations) {
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_SET_LIST_LOCATIONS,
				LocationDataManagement.getIdArrayFromListObject(listLocations));
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
		if (this.locations == null) {
			this.locations = (List<LocationDataObject>) CallServerFunction
					.callServerObjectFunction(this.algorithmId,
							ObjectMapping.ALGORITHM_OBJECT,
							ObjectMapping.ALGORITHM_GET_LOCATIONS, null);
		}
		return this.locations;
	}

	public void setLocations(List<LocationDataObject> locations) {
		this.locations = locations;
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_SET_LOCATIONS,
				LocationDataManagement.getIdArrayFromListObject(locations));
	}

	public DataTypeDataObject getDataType() {
		if (this.dataType == null) {
			this.dataType = (DataTypeDataObject) CallServerFunction
					.callServerObjectFunction(this.algorithmId,
							ObjectMapping.ALGORITHM_OBJECT,
							ObjectMapping.ALGORITHM_GET_DATA_TYPE, null);
		}
		return this.dataType;

	}

	public void setDataType(DataTypeDataObject dataType) {
		this.dataType = dataType;
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_SET_DATATYPE, dataType.getDataTypeId());
	}

	public void addLocation(LocationDataObject location) {
		this.locations.add(location);
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_ADD_LOCATION, location.getLocationId());
	}

	public void addLocationToListLocation(LocationDataObject location) {
		this.listLocations.add(location);
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_ADD_LOCATION_TO_LIST_LOCATIONS,
				location.getLocationId());
	}

	public void removeLocation(LocationDataObject location) {
		this.locations.remove(location);
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_REMOVE_LOCATION_OF_LOCATIONS,
				location.getLocationId());
	}

	public void removeLocationOfListLocation(LocationDataObject location) {
		this.listLocations.remove(location);
		CallServerFunction.callServerObjectFunction(this.algorithmId,
				ObjectMapping.ALGORITHM_OBJECT,
				ObjectMapping.ALGORITHM_REMOVE_LOCATION_OF_LIST_LOCATIONS,
				location.getLocationId());
	}
}
