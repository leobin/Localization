package locationaware;

import java.util.Vector;

import localization.data.entity.LocationDatabaseObject;
import locationaware.clientserver.LocationData;

/**
 * @author Dinh
 * Interface of all Algorithm
 *
 */
public interface Algorithm {
	/**
	 * If there are some initialization steps, should put here
	 */
	abstract void init();
	
	
	/**
	 * clear all map of locations added to this algorithm for localization
	 */
	public void clearMaps();
	
	
	/**
	 * @param locationDO, this location will be added to this algorithm for localization
	 * 
	 */
	public void addLocation(LocationDatabaseObject locationDO);
	
	/**
	 * @param listLocationDOs, all elements of this list will be added to this algorithm for localization 
	 */
	public void addLocations(Vector<LocationDatabaseObject> listLocationDOs);
	
	/**
	 * @return class name of the algorithm
	 */
	public String getAlgorithmClassName();
	
	
	/**
	 * @param locationData: this will be fed to the algorithm. 
	 * Base on this location data, the algorithm will locate the location in the added locations which is the nearest to user 
	 */
	public void feedLocationData(LocationData locationData);
	
	
	/**
	 * Remove all location data have been fed
	 */
	public void reset();
	
	
	/**
	 * @return result of localization (locations in which the user can be)
	 */
	public ResultOfDetection detectLocation();
	
	
	/**
	 * @return true if this algorithm has been ready to localize
	 */
	public boolean isReadyToLocalize();
}
