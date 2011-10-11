/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.localization.server.ServerConfig;

import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.contentobject.LocationDataObject;

/**
 * 
 * @author leobin
 */
public class LocationDataManagement {

	public static LocationDatabaseObject getLocationByID(String LocationID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		LocationDatabaseObject Location = createEntityManager.find(
				LocationDatabaseObject.class, LocationID);
		return Location;
	}

	public static void deleteMapData(
			LocationDatabaseObject locationDatabaseObject, String dataTypeId) {
		File mapFile = new File(ServerConfig.dirData + "/"
				+ locationDatabaseObject.getLocationId() + "_"
				+ locationDatabaseObject.getUser().getUserId() + "_"
				+ dataTypeId + ServerConfig.extensionMapDataFile);

		if (mapFile.exists()) {
			mapFile.delete();
		}
	}

	public static void deleleAllMapData(
			LocationDatabaseObject locationDatabaseObject, String dataTypeId) {
		deleteMapData(locationDatabaseObject, dataTypeId);
		for (LocationDatabaseObject subLocationDatabaseObject : locationDatabaseObject
				.getLocations()) {
			deleleAllMapData(subLocationDatabaseObject, dataTypeId);
		}
	}

	/*
	 * get all root location
	 */
	public static ArrayList<LocationDatabaseObject> getAllRootLocation() {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String query = "Select * from location where parent_location_id is null";
		Query createQuery = createEntityManager.createNativeQuery(query,
				LocationDatabaseObject.class);
		@SuppressWarnings("unchecked")
		List<LocationDatabaseObject> resultList = createQuery.getResultList();

		return new ArrayList<LocationDatabaseObject>(resultList);
	}

	/**
	 * get all root location of one user
	 * 
	 * @param userID
	 * @return
	 */
	public static ArrayList<LocationDatabaseObject> getAllRootLocationByUserID(
			String userID) {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String query = "Select * from location where parent_location_id is null AND user_id = "
				+ userID + " ORDER BY location_id";
		Query createQuery = createEntityManager.createNativeQuery(query,
				LocationDatabaseObject.class);
		try {
			@SuppressWarnings("unchecked")
			List<LocationDatabaseObject> resultList = createQuery
					.getResultList();

			return new ArrayList<LocationDatabaseObject>(resultList);
		} catch (Exception e) {
			return null;
		}
	}

	public static LocationDatabaseObject getRootLocationByLocationObject(
			LocationDatabaseObject locationDatabaseObject) {
		LocationDatabaseObject rootLocation = locationDatabaseObject;
		LocationDatabaseObject temp;

		if (rootLocation == null) {
			return null;
		}

		while ((temp = rootLocation.getParentLocation()) != null) {
			rootLocation = temp;
		}

		return rootLocation;
	}

	public static String addNewLocation(LocationDatabaseObject location) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(location);
		createEntityManager.getTransaction().commit();
		return location.getLocationId();

	}

	public static void removeLocation(LocationDatabaseObject location) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		LocationDatabaseObject location1 = createEntityManager.merge(location);
		createEntityManager.remove(location1);
		createEntityManager.getTransaction().commit();
	}

	public static void updateLocation(LocationDatabaseObject location) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.flush();
		createEntityManager.merge(location);
		createEntityManager.getTransaction().commit();
	}

	public static ArrayList<LocationDataObject> createListObject(
			ArrayList<LocationDatabaseObject> listLocation) {
		ArrayList<LocationDataObject> returnListLocation = new ArrayList<LocationDataObject>();
		for (int i = 0; i < listLocation.size(); i++) {
			LocationDatabaseObject databaseObject = listLocation.get(i);
			returnListLocation.add(new LocationDataObject(databaseObject));
		}
		return returnListLocation;
	}

	public static ArrayList<LocationDataObject> createListObject(
			List<LocationDatabaseObject> listLocation) {
		ArrayList<LocationDataObject> returnListLocation = new ArrayList<LocationDataObject>();
		for (int i = 0; i < listLocation.size(); i++) {
			LocationDatabaseObject databaseObject = listLocation.get(i);
			returnListLocation.add(new LocationDataObject(databaseObject));
		}
		return returnListLocation;
	}

	public static void removeLocation(String id) {
		removeLocation(getLocationByID(id));
	}

	public static ArrayList<LocationDatabaseObject> getListObjectFromListId(
			String[] ids) {
		ArrayList<LocationDatabaseObject> returnList = new ArrayList<LocationDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getLocationByID(ids[i]));
		}
		return returnList;
	}

	public static void updateLocation(LocationDataObject parameter) {
		LocationDatabaseObject instance = getLocationByID(parameter
				.getLocationId());
		instance.update(parameter);
	}

	public static List<LocationDataObject> getListDataObject(
			List<LocationDatabaseObject> locations) {
		// TODO Auto-generated method stub
		ArrayList<LocationDataObject> returnListLocation = new ArrayList<LocationDataObject>();
		for (int i = 0; i < locations.size(); i++) {
			LocationDatabaseObject databaseObject = locations.get(i);
			returnListLocation.add(new LocationDataObject(databaseObject));
		}
		return returnListLocation;
	}

	public static List<LocationDataObject> createListObject(
			List<LocationDatabaseObject> listLocation,
			LocationDataObject parentLocation) {
		// TODO Auto-generated method stub
		ArrayList<LocationDataObject> returnListLocation = new ArrayList<LocationDataObject>();
		for (int i = 0; i < listLocation.size(); i++) {
			LocationDatabaseObject databaseObject = listLocation.get(i);
			LocationDataObject locationDataObject = new LocationDataObject(
					databaseObject);
			locationDataObject.setParentLocation(parentLocation);
			returnListLocation.add(locationDataObject);
		}
		return returnListLocation;
	}

}
