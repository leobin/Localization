/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import locationaware.clientserver.LocationData;
import locationaware.wifi.osindependentapi.AccessPointStub;
import locationaware.wifi.osindependentapi.ScanningPointStub;

/**
 * A ScanningPoint consists of a list of AccessPoint and its signal strength
 * (similar to when we call iwlist)
 * Future work: should extend from ScanningPointStub
 * 
 * @author Xuan Nam
 */
public class ScanningPoint extends LocationData implements Serializable {

	private static final long serialVersionUID = 3588301463672849388L;
	private Vector<AccessPoint> apsList = new Vector<AccessPoint>();
	private Date creationDate;
	
	public Vector<AccessPoint> getApsList() {
		return apsList;
	}

	public void setApsList(Vector<AccessPoint> apsList) {
		this.apsList = apsList;
	}

	public ScanningPoint() {
		creationDate = Calendar.getInstance().getTime();
	}

//	public ScanningPoint(int n) {
//		Random rand = new Random();
//		for (int i = 0; i < n; ++i) {
//			apsList.add(new AccessPoint("AP" + i, "MAC" + i, rand.nextDouble()));
//		}
//	}

	@Override
	public String toString() {
		String tmp = "{";
		for (int i = 0; i < apsList.size(); i++) {
			tmp += "[" + apsList.get(i).toString() + "], ";
		}
		return tmp + "}";
	}

	/**
	 * Check if this ScanningPoint contains the AccessPoint named
	 * accessPointName
	 * 
	 * @param accessPointName
	 * @return
	 */
	public boolean haveAPName(String accessPointName) {
		for (AccessPoint accessPoint : apsList) {
			if (accessPoint.getAccessPointName().equals(accessPointName)) {
				return true;
			}
		}

		return false;
	}
	
	@Override 
	public ScanningPoint clone() throws CloneNotSupportedException {
		ScanningPoint newSP = new ScanningPoint();
		if (this.apsList != null) {
			newSP.apsList = new Vector<AccessPoint>();
			for (int i = 0; i < this.apsList.size(); i++) {
				newSP.apsList.add(this.apsList.get(i).clone());
			}
		} else {
			newSP.apsList = null;
		}
		return newSP;
	};

	public static ScanningPoint convertFromScanningPointStub(
			ScanningPointStub scanningPointStub) {
		ScanningPoint scanningPoint = new ScanningPoint();

		if (scanningPointStub == null) {
			return null;
		}

		for (AccessPointStub accessPointStub : scanningPointStub.apStubsList) {
			AccessPoint accessPoint = AccessPoint.convertFromAccessPointStub(accessPointStub);
			scanningPoint.apsList.add(accessPoint);
		}

		return scanningPoint;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}
}
