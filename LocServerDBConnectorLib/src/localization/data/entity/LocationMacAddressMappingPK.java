package localization.data.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the location_mac_address_mapping database table.
 * 
 */
@Embeddable
public class LocationMacAddressMappingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="client_mac_address_id")
	private String clientMacAddressId;

	@Column(name="build_mac_address_id")
	private String buildMacAddressId;

    public LocationMacAddressMappingPK() {
    }
	public String getClientMacAddressId() {
		return this.clientMacAddressId;
	}
	public void setClientMacAddressId(String clientMacAddressId) {
		this.clientMacAddressId = clientMacAddressId;
	}
	public String getBuildMacAddressId() {
		return this.buildMacAddressId;
	}
	public void setBuildMacAddressId(String buildMacAddressId) {
		this.buildMacAddressId = buildMacAddressId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LocationMacAddressMappingPK)) {
			return false;
		}
		LocationMacAddressMappingPK castOther = (LocationMacAddressMappingPK)other;
		return 
			this.clientMacAddressId.equals(castOther.clientMacAddressId)
			&& this.buildMacAddressId.equals(castOther.buildMacAddressId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.clientMacAddressId.hashCode();
		hash = hash * prime + this.buildMacAddressId.hashCode();
		
		return hash;
    }
}