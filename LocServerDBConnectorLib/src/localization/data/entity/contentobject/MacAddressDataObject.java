package localization.data.entity.contentobject;

import java.io.Serializable;
import java.util.List;

import com.localization.manager.LocationDataManagement;

import localization.data.entity.MacAddressDatabaseObject;

/**
 * The persistent class for the mac_address database table.
 * 
 */
public class MacAddressDataObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String macAddressId = null;

	private List<LocationDataObject> locations;

	public MacAddressDataObject() {
	}

	public MacAddressDataObject(MacAddressDatabaseObject macaddress) {
		this.macAddressId = macaddress.getMacAddressId();
		this.locations = LocationDataManagement.createListObject(macaddress
				.getLocations());
	}

	public String getMacAddressId() {
		return this.macAddressId;
	}

	public void setMacAddressId(long macAddressId) {
		this.macAddressId = "" + macAddressId;
	}

	public List<LocationDataObject> getLocations() {
		return this.locations;
	}

	public void setLocations(List<LocationDataObject> locations) {
		this.locations = locations;
	}

	// /**
	// * convert macaddress tring to long value base 10
	// *
	// * @param MACAdd
	// * @return
	// */
	// public static long convertMAC(String MACAdd) {
	// long mac = 0;
	// mac = Long.parseLong(MACAdd.replace(":", ""), 16);
	// return mac;
	// }

}