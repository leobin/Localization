package localization.data.entity.contentobject;

import java.io.Serializable;
import java.util.List;

import com.localization.manager.AlgorithmDataManagement;

import localization.data.entity.DataTypeDatabaseObject;

public class DataTypeDataObject implements Serializable{
	private static final long serialVersionUID = 1L;


	private String dataTypeId = null;

	private String dataTypeClassName;

	private String dataTypeDescription;

	private String dataTypeName;

	private List<AlgorithmDataObject> algorithms;

	public DataTypeDataObject() {
	}

	public DataTypeDataObject(DataTypeDatabaseObject datatype) {
		this.dataTypeId = datatype.getDataTypeId();
		this.dataTypeClassName = datatype.getDataTypeClassName();
		this.dataTypeDescription = datatype.getDataTypeDescription();
		this.dataTypeName = datatype.getDataTypeName();
		
//		this.algorithms = AlgorithmDataManagement.createListObject(datatype.getAlgorithms());
	}

	public String toString() {
		return this.dataTypeName;
	}

	public String getDataTypeId() {
		return this.dataTypeId;
	}

	public void setDataTypeId(String dataTypeId) {
		this.dataTypeId = dataTypeId;
	}

	public String getDataTypeClassName() {
		return this.dataTypeClassName;
	}

	public void setDataTypeClassName(String dataTypeClassName) {
		this.dataTypeClassName = dataTypeClassName;
	}

	public String getDataTypeDescription() {
		return this.dataTypeDescription;
	}

	public void setDataTypeDescription(String dataTypeDescription) {
		this.dataTypeDescription = dataTypeDescription;
	}

	public String getDataTypeName() {
		return this.dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public List<AlgorithmDataObject> getAlgorithms() {
		return this.algorithms;
	}

	public void setAlgorithms(List<AlgorithmDataObject> algorithms) {
		this.algorithms = algorithms;
	}
}