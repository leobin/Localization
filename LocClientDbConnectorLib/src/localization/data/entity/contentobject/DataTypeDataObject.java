package localization.data.entity.contentobject;

import com.localization.manager.AlgorithmDataManagement;
import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the data_type database table.
 * 
 */
public class DataTypeDataObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dataTypeId;
	private String dataTypeClassName;
	private String dataTypeDescription;
	private String dataTypeName;
	private List<AlgorithmDataObject> algorithms;

	public DataTypeDataObject() {
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
		if (this.algorithms == null) {
			this.algorithms = (List<AlgorithmDataObject>) CallServerFunction
					.callServerObjectFunction(this.dataTypeId,
							ObjectMapping.DATA_TYPE_OBJECT,
							ObjectMapping.DATATYPE_GET_ALGORITHMS, null);
		}
		return this.algorithms;

	}

	public void setAlgorithms(List<AlgorithmDataObject> algorithms) {
		this.algorithms = algorithms;

		CallServerFunction.callServerObjectFunction(this.dataTypeId,
				ObjectMapping.DATA_TYPE_OBJECT,
				ObjectMapping.DATATYPE_SET_ALGORITHMS,
				AlgorithmDataManagement.getIdArrayFromListObject(algorithms));
	}

	public void addAlgorithm(AlgorithmDataObject algorithm) {
		this.algorithms.add(algorithm);

		CallServerFunction.callServerObjectFunction(this.dataTypeId,
				ObjectMapping.DATA_TYPE_OBJECT,
				ObjectMapping.DATATYPE_ADD_ALGORITHM,
				algorithm.getAlgorithmId());
	}

	public void removeAlgorithm(AlgorithmDataObject algorithm) {
		this.algorithms.remove(algorithm);
		CallServerFunction.callServerObjectFunction(this.dataTypeId,
				ObjectMapping.DATA_TYPE_OBJECT,
				ObjectMapping.DATATYPE_REMOVE_ALGORITHM,
				algorithm.getAlgorithmId());
	}
}
