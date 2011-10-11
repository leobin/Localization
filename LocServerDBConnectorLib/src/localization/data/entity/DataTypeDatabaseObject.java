package localization.data.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.localization.manager.AlgorithmDataManagement;
import com.localization.manager.DataTypeDataManagement;

import java.util.List;

import localization.data.entity.contentobject.DataTypeDataObject;


/**
 * The persistent class for the data_type database table.
 * 
 */
@Entity
@Table(name="data_type")
public class DataTypeDatabaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="data_type_id")
	private String dataTypeId;

	@Column(name="data_type_class_name")
	private String dataTypeClassName;

	@Column(name="data_type_description")
	private String dataTypeDescription;

	@Column(name="data_type_name")
	private String dataTypeName;

	//bi-directional many-to-one association to Algorithm
	@OneToMany(mappedBy="dataType")
	private List<AlgorithmDatabaseObject> algorithms;

    public DataTypeDatabaseObject() {
    }

    public String toString()
    {
    	return this.dataTypeName;
    }
    
	public String getDataTypeId() {
		return this.dataTypeId;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
		DataTypeDataManagement.updateDataType(this);
	}

	public String getDataTypeClassName() {
		return this.dataTypeClassName;
	}

	public void setDataTypeClassName(String dataTypeClassName) {
		this.dataTypeClassName = dataTypeClassName;
		DataTypeDataManagement.updateDataType(this);
	}

	public String getDataTypeDescription() {
		return this.dataTypeDescription;
	}

	public void setDataTypeDescription(String dataTypeDescription) {
		this.dataTypeDescription = dataTypeDescription;
		DataTypeDataManagement.updateDataType(this);
	}

	public String getDataTypeName() {
		return this.dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
		DataTypeDataManagement.updateDataType(this);
	}

	public List<AlgorithmDatabaseObject> getAlgorithms() {
		return this.algorithms;
	}

	public void setAlgorithms(List<AlgorithmDatabaseObject> algorithms) {
		this.algorithms = algorithms;
		DataTypeDataManagement.updateDataType(this);
	}

	public void update(DataTypeDataObject datatype) {
		this.dataTypeClassName = datatype.getDataTypeClassName();
		this.dataTypeDescription = datatype.getDataTypeDescription();
		this.dataTypeName = datatype.getDataTypeName();
		DataTypeDataManagement.updateDataType(this);
	}
	
	public DataTypeDatabaseObject(DataTypeDataObject datatype) {
		this.dataTypeClassName = datatype.getDataTypeClassName();
		this.dataTypeDescription = datatype.getDataTypeDescription();
		this.dataTypeName = datatype.getDataTypeName();
	}

	public void addAlgorithm(String algorithmID) {
		AlgorithmDatabaseObject algorithmByID = AlgorithmDataManagement.getAlgorithmByID(algorithmID);
		this.algorithms.add(algorithmByID);
		DataTypeDataManagement.updateDataType(this);
	}

	public void removeAlgorithm(String algorithmID) {
		AlgorithmDatabaseObject algorithmByID = AlgorithmDataManagement.getAlgorithmByID(algorithmID);
		this.algorithms.remove(algorithmByID);
		DataTypeDataManagement.updateDataType(this);
	}
	
}