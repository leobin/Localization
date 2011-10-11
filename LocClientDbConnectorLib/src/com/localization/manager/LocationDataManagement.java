/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import localization.data.entity.contentobject.LocationDataObject;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;

/**
 * 
 * @author leobin
 */
public class LocationDataManagement {

    /*
     * get all root location
     */
    public static ArrayList<LocationDataObject> getAllRootLocation() {
        return (ArrayList<LocationDataObject>) CallServerFunction.callServerFunction(ObjectMapping.LOCATION_MANAGER_OBJECT, ObjectMapping.GET_ALL_FUNCTION, null, null);
//		EntityManagerFactory entityManagerFactory = LocalizationDataManager
//				.getEntityManagerFactory();
//		EntityManager createEntityManager = entityManagerFactory
//				.createEntityManager();
//		String query = "Select * from location where parent_location_id is null";
//		Query createQuery = createEntityManager.createNativeQuery(query,
//				LocationDatabaseObject.class);
//		@SuppressWarnings("unchecked")
//		List<LocationDatabaseObject> resultList = createQuery.getResultList();
//
//		return new ArrayList<LocationDatabaseObject>(resultList);
    }

    /**
     * get all root location of one user
     * @param userID
     * @return
     */
    public static ArrayList<LocationDataObject> getAllRootLocationByUserID(
            String userID) {
        return (ArrayList<LocationDataObject>) CallServerFunction.callServerFunction(ObjectMapping.LOCATION_MANAGER_OBJECT, ObjectMapping.GET_ALL_BY_ID_FUNCTION, userID, null);
//		EntityManagerFactory entityManagerFactory = LocalizationDataManager
//				.getEntityManagerFactory();
//		EntityManager createEntityManager = entityManagerFactory
//				.createEntityManager();
//		String query = "Select * from location where parent_location_id is null AND user_id = " + userID + " ORDER BY location_id";
//		Query createQuery = createEntityManager.createNativeQuery(query,
//				LocationDatabaseObject.class);
//		try {
//			@SuppressWarnings("unchecked")
//			List<LocationDatabaseObject> resultList = createQuery
//					.getResultList();
//
//			return new ArrayList<LocationDatabaseObject>(resultList);
//		} catch (Exception e) {
//			return null;
//		}
    }

    public static String addNewLocation(LocationDataObject location) {
        return (String) CallServerFunction.callServerFunction(ObjectMapping.LOCATION_MANAGER_OBJECT, ObjectMapping.ADD_NEW_FUNCTION, location, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.persist(location);
//		createEntityManager.getTransaction().commit();

    }

    public static void removeLocation(String location) {
        CallServerFunction.callServerFunction(ObjectMapping.LOCATION_MANAGER_OBJECT, ObjectMapping.REMOVE_FUNCTION, location, null);
//        EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//        createEntityManager.getTransaction().begin();
//        LocationDatabaseObject location1 = createEntityManager.merge(location);
//        createEntityManager.remove(location1);
//        createEntityManager.getTransaction().commit();
    }

    public static void updateLocation(LocationDataObject location) {
        CallServerFunction.callServerFunction(ObjectMapping.LOCATION_MANAGER_OBJECT, ObjectMapping.UPDATE_FUNCTION, location, null);
//        EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//        createEntityManager.getTransaction().begin();
//        createEntityManager.merge(location);
//        createEntityManager.getTransaction().commit();
    }


    public static String[] getIdArrayFromListObject(List<LocationDataObject> algorithms) {
        String[] returnList = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            LocationDataObject algorithmDataObject = algorithms.get(i);
            returnList[i] = algorithmDataObject.getLocationId();
        }
        return returnList;
    }
}
