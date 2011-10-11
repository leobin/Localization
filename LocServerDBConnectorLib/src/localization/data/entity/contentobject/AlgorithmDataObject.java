package localization.data.entity.contentobject;

import java.io.Serializable;
import java.util.List;

import com.localization.manager.LocationDataManagement;

import localization.data.entity.AlgorithmDatabaseObject;

/**
 * The persistent class for the algorithm database table.
 * 
 */
public class AlgorithmDataObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String algorithmId = null;

	private String algorithmClassName;

	private String algorithmDescription;

	private String algorithmName;

	private List<LocationDataObject> locations;

	private DataTypeDataObject dataType;

	private List<LocationDataObject> listLocations;

	public List<LocationDataObject> getListLocations() {
		return listLocations;
	}

	public void setListLocations(List<LocationDataObject> listLocations) {
		this.listLocations = listLocations;
	}

	public AlgorithmDataObject() {
	}

	public AlgorithmDataObject(AlgorithmDatabaseObject algorithm) {
		this.algorithmId = algorithm.getAlgorithmId();
		this.algorithmClassName = algorithm.getAlgorithmClassName();
		this.algorithmDescription = algorithm.getAlgorithmDescription();
		this.algorithmName = algorithm.getAlgorithmName();
		this.dataType = new DataTypeDataObject(algorithm.getDataType());
		// this.locations =
		// LocationDataManagement.createListObject(algorithm.getLocations());
		// this.listLocations =
		// LocationDataManagement.createListObject(algorithm.getListLocations());
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
		return this.locations;
	}

	public void setLocations(List<LocationDataObject> locations) {
		this.locations = locations;
	}

	public DataTypeDataObject getDataType() {
		return this.dataType;
	}

	public void setDataType(DataTypeDataObject dataType) {
		this.dataType = dataType;
	}

}