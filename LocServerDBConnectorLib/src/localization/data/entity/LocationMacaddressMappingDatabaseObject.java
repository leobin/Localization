package localization.data.entity;

import java.io.Serializable;
import javax.persistence.*;

import localization.data.entity.contentobject.LocationMacaddressMappingDataObject;


/**
 * The persistent class for the location_mac_address_mapping database table.
 * 
 */
@Entity
@Table(name="location_mac_address_mapping")
public class LocationMacaddressMappingDatabaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocationMacAddressMappingPK id;

	private double a;

	private double b;

    public LocationMacaddressMappingDatabaseObject() {
    }

	public LocationMacAddressMappingPK getId() {
		return this.id;
	}

	public void setId(LocationMacAddressMappingPK id) {
		this.id = id;
	}
	
	public double getA() {
		return this.a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return this.b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public void update(LocationMacaddressMappingDataObject parameter) {
		// TODO Auto-generated method stub
		this.a = parameter.getA();
		this.b = parameter.getB();
	}

}