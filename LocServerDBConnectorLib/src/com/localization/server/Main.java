package com.localization.server;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.MacAddressDatabaseObject;
import localization.data.entity.UserDatabaseObject;

import com.localization.manager.LocalizationDataManager;
import com.localization.manager.LocationDataManagement;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ArrayList<LocationDatabaseObject> allRootLocation = LocationDataManagement.getAllRootLocation();
//		
//		ServerAPI serverAPI = new ServerAPI();
//		EntityManager entityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//		
//		entityManager.getTransaction().begin();
//		UserDatabaseObject user = new UserDatabaseObject();
//		user.setUserName("admin");
//		user.setUserType("mapbuilder");
//		user.setPassword("demo124");
//		user.setLocations(new ArrayList<LocationDatabaseObject>());
//		entityManager.persist(user);
//		entityManager.getTransaction().commit();
		ArrayList<LocationDatabaseObject> allRootLocationByUserID = LocationDataManagement.getAllRootLocation();
		for (LocationDatabaseObject locationDatabaseObject : allRootLocationByUserID) {
			ArrayList<AlgorithmDatabaseObject> listAlgorithm = new ArrayList<AlgorithmDatabaseObject>();
			listAlgorithm.add(locationDatabaseObject.getAlgorithm());
			locationDatabaseObject.setAlgorithms(listAlgorithm);
			LocationDataManagement.updateLocation(locationDatabaseObject);
		}
		
//		System.out.println(allRootLocationByUserID.size());
//		System.out.println(serverAPI.checkLoginUser("group01", "demo124").getUserName());
//		LocationDatabaseObject location = new LocationDatabaseObject();
//		location.setLocationName("test");
//		location.setLocationDescription("test");
//		LocationDataManagement.addNewLocation(location);
//		
//		LocationDataManagement.updateLocation(location);
//		
//		LocationDataManagement.removeLocation(location);
		//test();
		//System.out.println(allRootLocation.size());
	}
	/**
	 * @param args
	 */
	public static void test() {
		EntityManager entityManager = LocalizationDataManager.getEntityManagerFactory()
				.createEntityManager();
		entityManager.getTransaction().begin();
//		EventType eventType = new EventType();
//		eventType.setEventTypeHandlerClass("handler class");
//		eventType.setEventTypeName("typeName");
		UserDatabaseObject user = new UserDatabaseObject();
		user.setUserName("map builder test");
		user.setUserType(UserDatabaseObject.USER_TYPE_CLIENT);
		user.setLocations(new ArrayList<LocationDatabaseObject>());
		entityManager.persist(user);
		
		
		
		MacAddressDatabaseObject macAddress = new MacAddressDatabaseObject();
		macAddress.setMacAddressId(13);
		entityManager.persist(macAddress);
		MacAddressDatabaseObject macAddress2 = new MacAddressDatabaseObject();
		macAddress2.setMacAddressId(11);
		entityManager.persist(macAddress2);
		
		LocationDatabaseObject location1 = new LocationDatabaseObject();
		location1.setUser(user);
		location1.setLocationName("location new 1");
		//update location 1 information to server
		entityManager.persist(location1);
		
		
		user.getLocations().add(location1);
		//update location1 and user to database
		entityManager.persist(location1);
		entityManager.persist(user);
		
		
		LocationDatabaseObject location2 = new LocationDatabaseObject();
		location2.setUser(user);
		location2.setLocationName("location new 1");
		//update location 1 information to server
		entityManager.persist(location2);
		
		
		user.getLocations().add(location2);
		//update location1 and user to database
		entityManager.persist(location2);
		entityManager.persist(user);
		
		//add location for macaddress 
		macAddress.setLocations(new ArrayList<LocationDatabaseObject>());
		macAddress.getLocations().add(location1);
		//update to database
		entityManager.persist(location1);
		entityManager.persist(macAddress);
		
		macAddress.getLocations().add(location2);
		//update to database
		entityManager.persist(location1);
		entityManager.persist(macAddress);
		
		macAddress2.setLocations(new ArrayList<LocationDatabaseObject>());
		macAddress2.getLocations().add(location1);
		//update to database
		entityManager.persist(location1);
		entityManager.persist(macAddress);

		macAddress2.getLocations().add(location2);
		//update to database
		entityManager.persist(location1);
		entityManager.persist(macAddress);
		
//
		
//		entityManager.persist(eventType);
		entityManager.getTransaction().commit();
//
//		entityManager.close();
		
		
		
		//search by mac address
//		ServerAPI test = new ServerAPI();
//		long[] listMacAddress = { 1, 2 };
//		ArrayList<User> results = test
//				.searchLocationByMacAddressGroupByuser(listMacAddress);
//		for (int i = 0; i < results.size(); i++) {
//			User user = results.get(i);
//			System.out.println(user.getuserName());
//			System.out.println(user.getLocations().get(0).getLocationName());
//		}
	}

}
