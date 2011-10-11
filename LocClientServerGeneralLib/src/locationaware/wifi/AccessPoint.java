/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Vector;

import locationaware.wifi.osindependentapi.AccessPointStub;

/**
 * @author 
 * Information of an AccessPoint which is stored in a scanning point
 * This information is extracted from iwlist or netsh
 * Futute work: Should extend from AccessPointStub
 * 
 */
public class AccessPoint implements Serializable {

	private static final long serialVersionUID = -3346326405198337164L;
	private String accessPointName = null;
	private String networkType = null;
    private String Authentication = null;
    private String Encryption = null;
	private long MACAddress = 0;
	private double signalStrength = 0;
	private double normalizedSignalStrength = -1;
    private String RadioType = null;
    private int iChannel = 0;
    private Vector<Double> vecdBasicRates = null;
    private Vector<Double> vecdOtherRates = null;
    
    public static Comparator<AccessPoint> sortByMACAddress = new Comparator<AccessPoint>() {

		@Override
		public int compare(AccessPoint o1, AccessPoint o2) {
			if (o1.MACAddress < o2.MACAddress) {
				return -1;
			} else if (o1.MACAddress == o2.MACAddress) {
				return 0;
			} else {
				return 1;
			}
		}
	};

	public AccessPoint() {
	}

	public AccessPoint(String accessPointName, long MACAddress, double signalStrength) {
		this.accessPointName = accessPointName;
		this.MACAddress = MACAddress;
		this.signalStrength = signalStrength;
	}

	public String getAccessPointName() {
		return accessPointName;
	}

	public void setAccessPointName(String accessPointName) {
		this.accessPointName = accessPointName;
	}

	public double getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(double signalStrength) {
		this.signalStrength = signalStrength;
	}

	public long getMACAddress() {
		return MACAddress;
	}
	
	public void setMACAddress(long mACAddress) {
		MACAddress = mACAddress;
	}
	
	public void setNormalizedSignalStrength(double normalizedSignalStrength) {
		this.normalizedSignalStrength = normalizedSignalStrength;
	}

	public double getNormalizedSignalStrength() {
		return normalizedSignalStrength;
	}
	
	@Override 
	public boolean equals(Object another) {
		if (another instanceof AccessPoint) {
			return (this.MACAddress == ((AccessPoint) another).MACAddress);	
		}
		return false;
	}
	
	@Override 
	public AccessPoint clone() throws CloneNotSupportedException {
		AccessPoint newAP = new AccessPoint();
		newAP.accessPointName = this.accessPointName;
		newAP.Authentication = this.Authentication;
		newAP.Encryption = this.Encryption;
		newAP.iChannel = this.iChannel;
		newAP.MACAddress = this.MACAddress;
		newAP.networkType = this.networkType;
		newAP.RadioType = this.RadioType;
		newAP.normalizedSignalStrength = this.normalizedSignalStrength;
		newAP.signalStrength = this.signalStrength;
		if (this.vecdBasicRates != null) {
			newAP.vecdBasicRates = new Vector<Double>();
			newAP.vecdBasicRates.addAll(this.vecdBasicRates);
		} else {
			newAP.vecdBasicRates = null;
		}
		if (this.vecdOtherRates != null) {
			newAP.vecdOtherRates = new Vector<Double>();
			newAP.vecdOtherRates.addAll(this.vecdOtherRates);			
		} else {
			newAP.vecdOtherRates = null;
		}
		return newAP;
	}

	@Override
	public String toString() {
		String strReturn = "";
		strReturn += getAccessPointName() + ", ";
		strReturn += getNetworkType() + ", ";
		strReturn += getAuthentication() + ", ";
		strReturn += getEncryption() + ", ";
		strReturn += getMACAddress() + ", ";
		strReturn += getSignalStrength() + ", ";
		strReturn += getNormalizedSignalStrength() + ", ";
		strReturn += getRadioType() + ", ";
		strReturn += getiChannel() + ", ";
		strReturn += "( " + getVecdBasicRates();
		strReturn += " ), ( " + getVecdOtherRates() + " )";
		return strReturn;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setAuthentication(String authentication) {
		Authentication = authentication;
	}

	public String getAuthentication() {
		return Authentication;
	}

	public void setEncryption(String encryption) {
		Encryption = encryption;
	}

	public String getEncryption() {
		return Encryption;
	}

	public void setRadioType(String radioType) {
		RadioType = radioType;
	}

	public String getRadioType() {
		return RadioType;
	}

	public void setiChannel(int iChannel) {
		this.iChannel = iChannel;
	}

	public int getiChannel() {
		return iChannel;
	}

	public void setVecdBasicRates(Vector<Double> vecdBasicRates) {
		this.vecdBasicRates = vecdBasicRates;
	}

	public Vector<Double> getVecdBasicRates() {
		return vecdBasicRates;
	}

	public void setVecdOtherRates(Vector<Double> vecdOtherRates) {
		this.vecdOtherRates = vecdOtherRates;
	}

	public Vector<Double> getVecdOtherRates() {
		return vecdOtherRates;
	}

	/**
	 * @param accessPointStub
	 * @return
	 */
	/**
	 * If extend from AccessPointStub then no need this method
	 * 
	 * @param accessPointStub
	 * @return
	 */
	public static AccessPoint convertFromAccessPointStub(
			AccessPointStub accessPointStub) {
		AccessPoint accessPoint = new AccessPoint();
		accessPoint.setAccessPointName(accessPointStub.accessPointName);
		accessPoint.setAuthentication(accessPointStub.Authentication);
		accessPoint.setEncryption(accessPointStub.Encryption);
		accessPoint.setiChannel(accessPointStub.iChannel);
		accessPoint.setMACAddress(accessPointStub.MACAddress);
		accessPoint.setNetworkType(accessPointStub.networkType);
		accessPoint.setRadioType(accessPointStub.RadioType);
		accessPoint.setNormalizedSignalStrength(accessPointStub.normalizedSignalStrength);
		accessPoint.setSignalStrength(accessPointStub.signalStrength);
		accessPoint.setVecdBasicRates(accessPointStub.vecdBasicRates);
		accessPoint.setVecdOtherRates(accessPointStub.vecdOtherRates);
		return accessPoint;
	}

}
