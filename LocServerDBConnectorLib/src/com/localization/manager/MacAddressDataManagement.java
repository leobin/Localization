package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import localization.data.entity.DataTypeDatabaseObject;
import localization.data.entity.MacAddressDatabaseObject;
import localization.data.entity.UserDatabaseObject;
import localization.data.entity.contentobject.MacAddressDataObject;
import localization.data.entity.contentobject.UserDataObject;

public class MacAddressDataManagement {
	public static MacAddressDatabaseObject getMacAddressByID(String MacAddressID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		MacAddressDatabaseObject MacAddress = createEntityManager.find(
				MacAddressDatabaseObject.class, MacAddressID);
		return MacAddress;
	}

	/*
	 * get all root MacAddress
	 */
	public static ArrayList<MacAddressDatabaseObject> getAllMacAddress() {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String query = "Select * from mac_address where true";
		Query createQuery = createEntityManager.createNativeQuery(query,
				MacAddressDatabaseObject.class);
		@SuppressWarnings("unchecked")
		List<MacAddressDatabaseObject> resultList = createQuery.getResultList();

		return new ArrayList<MacAddressDatabaseObject>(resultList);
	}

	public static void addNewMacAddress(MacAddressDatabaseObject MacAddress) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(MacAddress);
		createEntityManager.getTransaction().commit();

	}

	public static void removeMacAddress(MacAddressDatabaseObject MacAddress) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		MacAddressDatabaseObject MacAddress1 = createEntityManager
				.merge(MacAddress);
		createEntityManager.remove(MacAddress1);
		createEntityManager.getTransaction().commit();
	}

	public static void updateMacAddress(MacAddressDatabaseObject MacAddress) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.merge(MacAddress);
		createEntityManager.getTransaction().commit();
	}

	public static ArrayList<MacAddressDataObject> createListObject(
			ArrayList<MacAddressDatabaseObject> listMacAddress) {
		ArrayList<MacAddressDataObject> returnListMacAddress = new ArrayList<MacAddressDataObject>();
		for (int i = 0; i < listMacAddress.size(); i++) {
			MacAddressDatabaseObject databaseObject = listMacAddress.get(i);
			returnListMacAddress.add(new MacAddressDataObject(databaseObject));
		}
		return returnListMacAddress;
	}

	public static ArrayList<MacAddressDataObject> createListObject(
			List<MacAddressDatabaseObject> listMacAddress) {
		ArrayList<MacAddressDataObject> returnListMacAddress = new ArrayList<MacAddressDataObject>();
		for (int i = 0; i < listMacAddress.size(); i++) {
			MacAddressDatabaseObject databaseObject = listMacAddress.get(i);
			returnListMacAddress.add(new MacAddressDataObject(databaseObject));
		}
		return returnListMacAddress;
	}

	public static void removeMacAddress(String id) {
		removeMacAddress(getMacAddressByID(id));
	}

	public static ArrayList<MacAddressDatabaseObject> getListObjectFromListId(
			String[] ids) {
		ArrayList<MacAddressDatabaseObject> returnList = new ArrayList<MacAddressDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getMacAddressByID(ids[i]));
		}
		return returnList;
	}
	

	public static void updateMacAddress(MacAddressDataObject parameter) {
		MacAddressDatabaseObject instance = getMacAddressByID(parameter.getMacAddressId());
		instance.update(parameter);
	}
}
