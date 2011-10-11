package localization.data.entity.contentobject;

import localization.data.entity.LocationMacAddressMappingPK;
import localization.data.entity.LocationMacaddressMappingDatabaseObject;

public class LocationMacaddressMappingDataObject {
	private String clientMacAddress;
	private String buildMacAddress;
	private double a;
	private double b;
	public LocationMacaddressMappingDataObject(String clientMacAddress,
			String serverMacAddress, double a, double b) {
		super();
		this.clientMacAddress = clientMacAddress;
		this.buildMacAddress = serverMacAddress;
		this.a = a;
		this.b = b;
	}

	public LocationMacaddressMappingDataObject(
			LocationMacaddressMappingDatabaseObject databaseObject) {
		this.clientMacAddress = databaseObject.getId().getClientMacAddressId();
		this.buildMacAddress = databaseObject.getId().getBuildMacAddressId();
		this.a = databaseObject.getA();
		this.b = databaseObject.getB();
		// TODO Auto-generated constructor stub
	}

	public String getClientMacAddress() {
		return clientMacAddress;
	}
	public void setClientMacAddress(String clientMacAddress) {
		this.clientMacAddress = clientMacAddress;
	}
	public String getServerMacAddress() {
		return buildMacAddress;
	}
	public void setServerMacAddress(String serverMacAddress) {
		this.buildMacAddress = serverMacAddress;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}

	public LocationMacAddressMappingPK getLocationMacaddressMappingId() {
		LocationMacAddressMappingPK id = new LocationMacAddressMappingPK();
		id.setBuildMacAddressId(buildMacAddress);
		id.setClientMacAddressId(clientMacAddress);
		return id;
	}
	
	
}
