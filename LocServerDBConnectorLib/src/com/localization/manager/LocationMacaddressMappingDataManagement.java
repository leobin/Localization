package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.LocationMacAddressMappingPK;
import localization.data.entity.LocationMacaddressMappingDatabaseObject;
import localization.data.entity.UserDatabaseObject;
import localization.data.entity.contentobject.LocationMacaddressMappingDataObject;
import localization.data.entity.contentobject.UserDataObject;

public class LocationMacaddressMappingDataManagement {

	public static LocationMacaddressMappingDatabaseObject getLocationMacaddressMappingByID(
			LocationMacAddressMappingPK LocationMacaddressMappingID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		LocationMacaddressMappingDatabaseObject LocationMacaddressMapping = createEntityManager
				.find(LocationMacaddressMappingDatabaseObject.class,
						LocationMacaddressMappingID);
		return LocationMacaddressMapping;
	}

	public static ArrayList<LocationMacaddressMappingDatabaseObject> getAllLocationMacaddressMapping() {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String query = "Select * from data_type where true";
		Query createQuery = createEntityManager.createNativeQuery(query,
				LocationMacaddressMappingDatabaseObject.class);
		@SuppressWarnings("unchecked")
		List<LocationMacaddressMappingDatabaseObject> resultList = createQuery
				.getResultList();

		return new ArrayList<LocationMacaddressMappingDatabaseObject>(
				resultList);
	}

	public static void addNewLocationMacaddressMapping(
			LocationMacaddressMappingDatabaseObject locationMacaddressMapping) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(locationMacaddressMapping);
		createEntityManager.getTransaction().commit();
	}

	public static void removeLocationMacaddressMapping(
			LocationMacaddressMappingDatabaseObject locationMacaddressMapping) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		LocationMacaddressMappingDatabaseObject locationMacaddressMapping1 = createEntityManager
				.merge(locationMacaddressMapping);
		createEntityManager.remove(locationMacaddressMapping1);
		createEntityManager.getTransaction().commit();
	}

	public static void updateLocationMacaddressMapping(
			LocationMacaddressMappingDatabaseObject locationMacaddressMapping) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.merge(locationMacaddressMapping);
		createEntityManager.getTransaction().commit();
	}

	public static ArrayList<LocationMacaddressMappingDataObject> createListObject(
			ArrayList<LocationMacaddressMappingDatabaseObject> listLocationMacaddressMapping) {
		ArrayList<LocationMacaddressMappingDataObject> returnListLocationMacaddressMapping = new ArrayList<LocationMacaddressMappingDataObject>();
		for (int i = 0; i < listLocationMacaddressMapping.size(); i++) {
			LocationMacaddressMappingDatabaseObject databaseObject = listLocationMacaddressMapping
					.get(i);
			returnListLocationMacaddressMapping
					.add(new LocationMacaddressMappingDataObject(databaseObject));
		}
		return returnListLocationMacaddressMapping;
	}

	public static ArrayList<LocationMacaddressMappingDataObject> createListObject(
			List<LocationMacaddressMappingDatabaseObject> listLocationMacaddressMapping) {
		ArrayList<LocationMacaddressMappingDataObject> returnListLocationMacaddressMapping = new ArrayList<LocationMacaddressMappingDataObject>();
		for (int i = 0; i < listLocationMacaddressMapping.size(); i++) {
			LocationMacaddressMappingDatabaseObject databaseObject = listLocationMacaddressMapping
					.get(i);
			returnListLocationMacaddressMapping
					.add(new LocationMacaddressMappingDataObject(databaseObject));
		}
		return returnListLocationMacaddressMapping;
	}

	public static void removeLocationMacaddressMapping(LocationMacAddressMappingPK id) {
		removeLocationMacaddressMapping(getLocationMacaddressMappingByID(id));
	}

	public static ArrayList<LocationMacaddressMappingDatabaseObject> getListObjectFromListId(
			LocationMacAddressMappingPK[] ids) {
		ArrayList<LocationMacaddressMappingDatabaseObject> returnList = new ArrayList<LocationMacaddressMappingDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getLocationMacaddressMappingByID(ids[i]));
		}
		return returnList;
	}

	public static void updateLocationMacaddressMapping(
			LocationMacaddressMappingDataObject parameter) {
		LocationMacaddressMappingDatabaseObject instance = getLocationMacaddressMappingByID(parameter
				.getLocationMacaddressMappingId());
		instance.update(parameter);
	}
}
