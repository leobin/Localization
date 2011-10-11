package localization.data.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.localization.manager.LocationDataManagement;
import com.localization.manager.MacAddressDataManagement;

import java.util.List;

import localization.data.entity.contentobject.MacAddressDataObject;

/**
 * The persistent class for the mac_address database table.
 * 
 */
@Entity
@Table(name = "mac_address")
public class MacAddressDatabaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mac_address_id")
	private String macAddressId;

	// bi-directional many-to-many association to Location
	@ManyToMany
	@JoinTable(name = "mac_address_location", joinColumns = { @JoinColumn(name = "mac_address_id") }, inverseJoinColumns = { @JoinColumn(name = "location_id") })
	private List<LocationDatabaseObject> locations;

	public MacAddressDatabaseObject() {
	}

	public String getMacAddressId() {
		return this.macAddressId;
	}

	public void setMacAddressId(long macAddressId) {
		this.macAddressId = "" + macAddressId;
	}

	public List<LocationDatabaseObject> getLocations() {
		return this.locations;
	}

	public void setLocations(List<LocationDatabaseObject> locations) {
		this.locations = locations;
		MacAddressDataManagement.updateMacAddress(this);
	}
	
	public void update(MacAddressDataObject macaddress){
		this.macAddressId = macaddress.getMacAddressId();
		MacAddressDataManagement.updateMacAddress(this);
	}
	
	public MacAddressDatabaseObject(MacAddressDataObject macaddress){
		this.macAddressId = macaddress.getMacAddressId();
	}

//	/**
//	 * convert macaddress tring to long value base 10
//	 * 
//	 * @param MACAdd
//	 * @return
//	 */
//	public static long convertMAC(String MACAdd) {
//		long mac = 0;
//		mac = Long.parseLong(MACAdd.replace(":", ""), 16);
//		return mac;
//	}

	public void addLocation(String locationID) {
		LocationDatabaseObject location = LocationDataManagement.getLocationByID(locationID);
		this.locations.add(location);
		MacAddressDataManagement.updateMacAddress(this);
	}

	public void removeLocation(String locationID) {
		// TODO Auto-generated method stub
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.remove(locationByID);
		MacAddressDataManagement.updateMacAddress(this);
	}

}