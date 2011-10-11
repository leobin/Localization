/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi.mapdata;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import locationaware.clientserver.MapData;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.WifiScanner;

/**
 * THe big Wifi MapData (stored on client side) getting directly from a list of scanning points
 * @author Xuan Nam
 */
public class WifiMapData extends MapData implements Serializable {

	private static final long serialVersionUID = -8161955464238585545L;
	public static final String VERSION_1 = "1.0";
	
	private String OS;
	private boolean isFiltered = false;
	private Vector<String> filterAlgNameList = new Vector<String>();	// all filtered algorithm saved here, everytime apply a new filter to this map, add the filter algorithm name here
	private Vector<ScanningPoint> spsList = new Vector<ScanningPoint>();
	private long scannerMAC = 0;
	private Integer durationTimeInSeconds = 0;
	
	public WifiMapData() {
		// TODO Auto-generated constructor stub
		creationDate = Calendar.getInstance().getTime();
		version = VERSION_1;
		dataTypeClassName = WifiMapData.class.getName();
		scannerMAC = WifiScanner.getScannerMac();
	}
		
	public String getOS() {
		return OS;
	}

	public void setOS(String oS) {
		OS = oS;
	}
	
    public Date getCreationDate() {
		return creationDate;
	}
	
    public Vector<ScanningPoint> getSpsList() {
		return spsList;
	}

	public void setSpsList(Vector<ScanningPoint> spsList) {
		this.spsList = spsList;
	}
    
    public Integer getDurationTimeInSeconds() {
		return durationTimeInSeconds;
	}

	public void setDurationTimeInSeconds(Integer durationTimeInSeconds) {
		this.durationTimeInSeconds = durationTimeInSeconds;
	}

	@Override
    public String toString() {
        String tmp = "Location ID: " + locationId + "\n";
        tmp += "Version: " + version + "\n";
        tmp += "Creation Date: " + creationDate + "\n";
        tmp += "Duration time: " + durationTimeInSeconds + "s\n";
        tmp += "Operating System: " + OS + "\n";
        tmp += "User ID: " + userId + "\n";
        if (isFiltered) {
        	tmp += "Have been filtered by filter algorithm: " + filterAlgNameList + "\n";
        } else
        	tmp += "Haven't been filtered yet \n";
        tmp += "List of ScanningPoints: \n";
        for (ScanningPoint scanningPoint : spsList) {
        	tmp += scanningPoint.toString() + "\n";
        }
        return tmp;
    }
    
    
    @Override
    public WifiMapData clone() throws CloneNotSupportedException {
    	WifiMapData newWifiMapData = new WifiMapData();
    	newWifiMapData.locationId = this.locationId;
    	newWifiMapData.version = this.version;
    	newWifiMapData.OS = this.OS;
    	newWifiMapData.userId = this.userId;
    	newWifiMapData.isFiltered = this.isFiltered;
    	newWifiMapData.filterAlgNameList = new Vector<String>();
    	newWifiMapData.filterAlgNameList.addAll(this.filterAlgNameList);
    	newWifiMapData.durationTimeInSeconds = this.durationTimeInSeconds;
    	for (int i = 0; i < this.spsList.size(); i++) {
    		newWifiMapData.spsList.add(this.spsList.get(i).clone());
    	}
    	return newWifiMapData;
    }

	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}

	public boolean isFiltered() {
		return isFiltered;
	}

	/**
	 * If apply a filter, should add the name of the filter to this list
	 * @param filterAlgName
	 */
	public void addFilterAlgName(String filterAlgName) {
		this.filterAlgNameList.add(filterAlgName);
	}

	/**
	 * @return all filtered algorithm saved here, 
	 * everytime apply a new filter to this map, add the filter algorithm name here
	 */
	public Vector<String> getFilterAlgNameList() {
		return filterAlgNameList;
	}

	public long getScannerMAC() {
		return scannerMAC;
	}
	
	public void setScannerMAC(long scannerMAC) {
		this.scannerMAC = scannerMAC;
	}
}
