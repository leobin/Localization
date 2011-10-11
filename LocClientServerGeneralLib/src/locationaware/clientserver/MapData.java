package locationaware.clientserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;


/**
 * @author songuku
 * Superclass of GPSMapData and WifiMapData
 */
public abstract class MapData implements Serializable{
	private static final long serialVersionUID = -2561181172934596192L;
	public static final String VERSION_1 = "1.0";
	public static final String EXTENSIONMAPDATAFILE = ".mapdata";
	
	protected String locationId;
	protected String dataTypeClassName;
	protected String abstractVersion = VERSION_1;
	protected String version;
	protected Date creationDate;
	protected String userId;	// the same value as the userID who owns the locationID
	
	/**
	 * @return the userID who built this map.  
	 * Assuming the mapbuilder (wifi/gps builder) is the same as the owner of the locationID
	 * 
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the version of this superclass (should NOT use for now)
	 */
	public String getAbstractVersion() {
		return abstractVersion;
	}

	public void setAbstractVersion(String version) {
		this.abstractVersion = version;
	}

	/**
	 * @return the version of the child class
	 */
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the locationID that this mapdata is built for
	 */
	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	
	/**
	 * @return wifi or gps mapdata, to be set by the child class
	 */
	public String getDataTypeClassName() {
		return dataTypeClassName;
	}

	/**
	 * Read a map from a file
	 * @param mapDataFilePath the path and filename
	 * @return the MapData
	 */
	public static MapData readMapData(String mapDataFilePath) {
		if (!mapDataFilePath.endsWith(EXTENSIONMAPDATAFILE)) {
			return null;
		}
		
		File fileInput = new File(mapDataFilePath);
		
		if (!fileInput.exists()) {
			return null;
		}
		
		try {
			FileInputStream fileInputStream = new FileInputStream(
					fileInput);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					fileInputStream);
			ObjectInputStream objectInputStream = new ObjectInputStream(
					bufferedInputStream);
			MapData mapData = (MapData) objectInputStream.readObject();
			objectInputStream.close();
			return mapData;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Read a map from a file
	 * 
	 * @param mapDataDirectoryPath the directory contains the file
	 * @param mapDataFileName the file name
	 * @return
	 */
	public static MapData readMapData(String mapDataDirectoryPath, String mapDataFileName) {
		return readMapData(mapDataDirectoryPath + "/" + mapDataFileName);
	}

	/**
	 * Write a MapData to a file
	 * @param mapData
	 * @param mapDataFilePath
	 */
	public static void writeMapData(MapData mapData, String mapDataFilePath) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
					mapDataFilePath);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					bufferedOutputStream);
			objectOutputStream.writeObject(mapData);
			objectOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write a MapData to a file
	 * @param mapData
	 * @param mapDataDirectoryPath
	 * @param mapDataFileName
	 */
	public static void writeMapData(MapData mapData, String mapDataDirectoryPath,
			String mapDataFileName) {
		writeMapData(mapData, mapDataDirectoryPath + "/" + mapDataFileName);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
