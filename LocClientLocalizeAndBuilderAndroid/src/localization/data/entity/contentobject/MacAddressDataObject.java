package localization.data.entity.contentobject;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.io.Serializable;
import java.util.List;

public class MacAddressDataObject implements Serializable  {
private static final long serialVersionUID = 1L;

    private String macAddressId;
    private List<LocationDataObject> locations;

    public MacAddressDataObject() {
    }

    public String getMacAddressId() {
        return this.macAddressId;
    }

    public void setMacAddressId(String macAddressId) {
        this.macAddressId = "" + convertMAC(macAddressId);
    }

    public List<LocationDataObject> getLocations() {
        return (List<LocationDataObject>) CallServerFunction.callServerObjectFunction(this.macAddressId, ObjectMapping.MAC_ADDRESS_OBJECT, ObjectMapping.MACADDRESS_GET_LOCATIONS, null);
    }

    public void setLocations(String[] listLocationID) {
        CallServerFunction.callServerObjectFunction(this.macAddressId, ObjectMapping.MAC_ADDRESS_OBJECT, ObjectMapping.MACADDRESS_SET_LOCATIONS, locations);
    }

    /**
     * convert macaddress tring to long value base 10
     *
     * @param MACAdd
     * @return
     */
    public static long convertMAC(String MACAdd) {
        long mac = 0;
        mac = Long.parseLong(MACAdd.replace(":", ""), 16);
        return mac;
    }

    public void addLocation(String locationID) {
        CallServerFunction.callServerObjectFunction(this.macAddressId, ObjectMapping.MAC_ADDRESS_OBJECT, ObjectMapping.MACADDRESS_ADD_LOCATION, locationID);
    }

    public void removeLocation(String locationID) {
        CallServerFunction.callServerObjectFunction(this.macAddressId, ObjectMapping.MAC_ADDRESS_OBJECT, ObjectMapping.MACADDRESS_REMOVE_LOCATION, locationID);
    }
}
