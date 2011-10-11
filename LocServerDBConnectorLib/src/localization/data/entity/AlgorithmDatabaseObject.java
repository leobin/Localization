package localization.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.localization.manager.AlgorithmDataManagement;
import com.localization.manager.LocationDataManagement;

import localization.data.entity.contentobject.AlgorithmDataObject;


/**
 * The persistent class for the algorithm database table.
 * 
 */
@Entity
@Table(name="algorithm")
public class AlgorithmDatabaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="algorithm_id")
	private String algorithmId;

	@Column(name="algorithm_class_name")
	private String algorithmClassName;

	@Column(name="algorithm_description")
	private String algorithmDescription;

	@Column(name="algorithm_name")
	private String algorithmName;

	//bi-directional many-to-one association to Location
	//list locations use this algorithm as main algorithm
	@OneToMany(mappedBy="algorithm")
	private List<LocationDatabaseObject> locations;

	//bi-directional many-to-one association to DataType
    @ManyToOne
	@JoinColumn(name="data_type_id")
	private DataTypeDatabaseObject dataType;
    
	//bi-directional many-to-many association to MacAddress
    //this is for list all location
	@ManyToMany(mappedBy="algorithms")
	private List<LocationDatabaseObject> listLocations;


	public List<LocationDatabaseObject> getListLocations() {
		return listLocations;
	}

	public void setListLocations(List<LocationDatabaseObject> listLocations) {
		this.listLocations = listLocations;
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public AlgorithmDatabaseObject() {
    }

    public String toString(){
    	return this.algorithmName;
    }
    
	public String getAlgorithmId() {
		return this.algorithmId;
	}

	public void setAlgorithmId(String algorithmId) {
		this.algorithmId = algorithmId;
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public String getAlgorithmClassName() {
		return this.algorithmClassName;
	}

	public void setAlgorithmClassName(String algorithmClassName) {
		this.algorithmClassName = algorithmClassName;
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public String getAlgorithmDescription() {
		return this.algorithmDescription;
	}

	public void setAlgorithmDescription(String algorithmDescription) {
		this.algorithmDescription = algorithmDescription;
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public String getAlgorithmName() {
		return this.algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public List<LocationDatabaseObject> getLocations() {
		return this.locations;
	}

	public void setLocations(List<LocationDatabaseObject> locations) {
		this.locations = locations;
		AlgorithmDataManagement.updateAlgorithm(this);
	}
	
	public DataTypeDatabaseObject getDataType() {
		return this.dataType;
	}

	public void setDataType(DataTypeDatabaseObject dataType) {
		this.dataType = dataType;
		AlgorithmDataManagement.updateAlgorithm(this);
	}
	
	public void update(AlgorithmDataObject algorithm) {
		this.algorithmClassName = algorithm.getAlgorithmClassName();
		this.algorithmDescription = algorithm.getAlgorithmDescription();
		this.algorithmName = algorithm.getAlgorithmName();
		AlgorithmDataManagement.updateAlgorithm(this);
	}
	
	public AlgorithmDatabaseObject(AlgorithmDataObject algorithm) {
		this.algorithmClassName = algorithm.getAlgorithmClassName();
		this.algorithmDescription = algorithm.getAlgorithmDescription();
		this.algorithmName = algorithm.getAlgorithmName();
	}

	public void addLocatin(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.add(locationByID);
		AlgorithmDataManagement.addNewAlgorithm(this);
	}

	public void addLocationToList(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.listLocations.add(locationByID);
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public void removeLocation(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.remove(locationByID);
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	public void removeLocationOfList(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.listLocations.remove(locationByID);
		AlgorithmDataManagement.updateAlgorithm(this);
	}

	
}