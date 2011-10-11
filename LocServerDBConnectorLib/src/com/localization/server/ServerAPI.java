package com.localization.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.DataTypeDatabaseObject;
import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.MacAddressDatabaseObject;
import localization.data.entity.UserDatabaseObject;

import com.localization.manager.LocalizationDataManager;

public class ServerAPI {

	public static ArrayList<LocationDatabaseObject> getListRootLocation() {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();

		String selectQuery = "SELECT * "
				+ " FROM  `location` "
				+ " WHERE `parent_location_id` IS NULL ";

		Query query = entityManager.createNativeQuery(selectQuery,
				LocationDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<LocationDatabaseObject> results = query.getResultList();

		entityManager.close();
		return new ArrayList<LocationDatabaseObject>(results);

	}

	public static ArrayList<LocationDatabaseObject> getAllLocations() {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();

		String selectQuery = "SELECT * "
				+ " FROM  `location` "
				+ " WHERE 1";

		Query query = entityManager.createNativeQuery(selectQuery,
				LocationDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<LocationDatabaseObject> results = query.getResultList();

		entityManager.close();
		return new ArrayList<LocationDatabaseObject>(results);

	}

	
	/**
	 * search location by list macaddress get from scanning point
	 * 
	 * @param listMacAddress
	 * @return
	 */
	public static  ArrayList<LocationDatabaseObject> searchLocationByMacAddress(
			Long[] listMacAddress) {
		System.out.println("------------------------------------------");
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		
		String selectQuery = "SELECT l.* "
				+ " FROM  mac_address_location AS ml,  location AS l "
				+ " WHERE l.location_id = ml.location_id ";
		String macaddressChecker = "";
		// create search query
		for (int i = 0; i < listMacAddress.length; i++) {
			long macaddressID = listMacAddress[i];
			macaddressChecker += " OR  mac_address_id = '" + macaddressID
					+ "' ";
		}

		// create search query
		macaddressChecker = "AND (" + macaddressChecker.substring(3) + " )";
		selectQuery += macaddressChecker;
		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				LocationDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<LocationDatabaseObject> results = query.getResultList();

		entityManager.close();
		return new ArrayList<LocationDatabaseObject>(results);
	}

	public static  LocationDatabaseObject searchLocationByLocationId(String locationId) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT *" + " FROM location"
				+ " WHERE location_id = '" + locationId + "'";

		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				LocationDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<LocationDatabaseObject> results = query.getResultList();

		entityManager.close();

		if (results.size() > 0)
			return results.get(0);
		else
			return null;

	}

	public static  ArrayList<UserDatabaseObject> searchLocationByMacAddressGroupByUser(
			Long[] listMacAddress) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT mb.* "
				+ " FROM  mac_address_location AS ml,  location AS l, user AS u "
				+ " WHERE l.location_id = ml.location_id "
				+ " AND u.user_id = l.user_id ";
		String macaddressChecker = "";
		// create search query
		for (int i = 0; i < listMacAddress.length; i++) {
			long macaddressID = listMacAddress[i];
			macaddressChecker += " OR  mac_address_id = '" + macaddressID
					+ "' ";
		}

		// create search query
		macaddressChecker = "AND (" + macaddressChecker.substring(3) + " )";
		selectQuery += macaddressChecker;
		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				UserDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<UserDatabaseObject> results = query.getResultList();

		entityManager.close();
		return new ArrayList<UserDatabaseObject>(results);
	}

//	public static  ArrayList<UserDatabaseObject> searchLocationByMacAddressGroupByUser(
//			ArrayList<String> listMacAddress) {
//		long[] listLongMacAddress = new long[listMacAddress.size()];
//		for (int i = 0; i < listMacAddress.size(); ++i) {
//			listLongMacAddress[i] = MacAddressDatabaseObject
//					.convertMAC(listMacAddress.get(i));
//		}
//		return searchLocationByMacAddressGroupByUser(listLongMacAddress);
//	}

//	/**
//	 * search location macaddress
//	 * 
//	 * @param listMacAddress
//	 * @return
//	 */
//	public static  ArrayList<LocationDatabaseObject> searchLocationByMacAddress(
//			ArrayList<String> listMacAddress) {
//		long[] listLongMacAddress = new long[listMacAddress.size()];
//		for (int i = 0; i < listMacAddress.size(); ++i) {
//			listLongMacAddress[i] = MacAddressDatabaseObject
//					.convertMAC(listMacAddress.get(i));
//		}
//		return searchLocationByMacAddress(listLongMacAddress);
//	}

	public static  MacAddressDatabaseObject searchMacAddressByID(long strMACAddress) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT *" + " FROM mac_address"
				+ " WHERE mac_address_id = '"
				+ strMACAddress + "'";

		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				MacAddressDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<MacAddressDatabaseObject> results = query.getResultList();

		entityManager.close();

		if (results.size() > 0)
			return results.get(0);
		else
			return null;
	}

	public static  UserDatabaseObject searchUserByName(String userName) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT *" + " FROM user"
				+ " WHERE user_name LIKE '" + userName + "'";

		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				UserDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<UserDatabaseObject> results = query.getResultList();

		entityManager.close();

		if (results.size() > 0)
			return results.get(0);
		else
			return null;
	}

	public static  List<LocationDatabaseObject> searchLocationByLocationNameAndUserName(
			String locationName, String userName) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT l.*" + " FROM user AS u, location AS l"
				+ " WHERE l.user_id = u.user_id" + " AND u.user_name LIKE '"
				+ userName + "'" + " AND l.location_name LIKE '" + locationName
				+ "'";

		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				LocationDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<LocationDatabaseObject> results = query.getResultList();

		entityManager.close();

		return results;
	}

	public static  List<DataTypeDatabaseObject> searchDataTypeByDataTypeClassName(
			String dataTypeClassName) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT *" + " FROM data_type"
				+ " WHERE data_type_class_name LIKE '" + dataTypeClassName
				+ "'";

		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				DataTypeDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<DataTypeDatabaseObject> results = query.getResultList();

		entityManager.close();

		return results;
	}

	public static  String getDataTypeIDByAlgorithmClassName(
			String algorithmClassName) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT *" + " FROM algorithm"
				+ " WHERE algorithm_class_name LIKE '" + algorithmClassName
				+ "'";

		System.out.println(selectQuery);
		Query query = entityManager.createNativeQuery(selectQuery,
				AlgorithmDatabaseObject.class);

		@SuppressWarnings("unchecked")
		List<AlgorithmDatabaseObject> results = query.getResultList();

		entityManager.close();

		return results.get(0).getDataType().getDataTypeId();
	}
	
	public static  String getDataTypeIdByDataTypeClassName(String dataTypeClassName) {
		List<DataTypeDatabaseObject> listDataTypeDO = searchDataTypeByDataTypeClassName(dataTypeClassName);
		if (listDataTypeDO.size() <= 0)
			return null;
		return listDataTypeDO.get(0).getDataTypeId();
	}
	
	public static  ArrayList<LocationDatabaseObject> getRootLocation(
			ArrayList<LocationDatabaseObject> listLocation) {
		ArrayList<LocationDatabaseObject> listRootLocation = new ArrayList<LocationDatabaseObject>();
		for (LocationDatabaseObject location : listLocation) {
			while (location.getParentLocation() != null) {
				location = location.getParentLocation();
			}
			if (!listRootLocation.contains(location)) {
				listRootLocation.add(location);
			}
		}
		return listRootLocation;
	}

	public static  String getFileDataPath(LocationDatabaseObject locationDO,
			DataTypeDatabaseObject dataTypeDO) {
		return locationDO.getLocationDataPath(dataTypeDO.getDataTypeId());
	}

	/**
	 * validate login user
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static  UserDatabaseObject checkLoginUser(String username, String password) {
		EntityManager entityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		String selectQuery = "SELECT *" + " FROM user" + " WHERE user_name = '"
				+ username + "'";

		Query query = entityManager.createNativeQuery(selectQuery,
				UserDatabaseObject.class);

		try {
			UserDatabaseObject result = (UserDatabaseObject) query
					.getSingleResult();
			entityManager.close();

			// check for valid login account
			if (result == null) {
				return null;
			} else {
				if (result.getPassword().equals(password)) {
					return result;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			return null;
		}
	}

}
