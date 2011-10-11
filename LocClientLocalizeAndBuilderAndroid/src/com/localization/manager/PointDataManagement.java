/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.manager;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.util.ArrayList;

import java.util.List;

import localization.data.entity.contentobject.LocationDataObject;
import localization.data.entity.contentobject.PointDataObject;

/**
 *
 * @author leobin
 */
public class PointDataManagement {

	public static void addNewPoint(PointDataObject point) {
                CallServerFunction.callServerFunction(ObjectMapping.POINT_MANAGER_OBJECT, ObjectMapping.ADD_NEW_FUNCTION, point, null);

//		EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.persist(point);
//		createEntityManager.getTransaction().commit();

	}

	public static void addListPoint(ArrayList<PointDataObject> listPoint) {
                CallServerFunction.callServerFunction(ObjectMapping.POINT_MANAGER_OBJECT, ObjectMapping.ADD_LIST_POINT_FUNCTION, listPoint, null);
//		EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		for (int i = 0; i < listPoint.size(); i++) {
//			PointDatabaseObject point = listPoint.get(i);
//			createEntityManager.persist(point);
//		}
//		createEntityManager.getTransaction().commit();

	}

	public static void removePoint(String point) {
                CallServerFunction.callServerFunction(ObjectMapping.POINT_MANAGER_OBJECT, ObjectMapping.REMOVE_FUNCTION, point, null);
//		EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.remove(point);
//		createEntityManager.getTransaction().commit();
	}

	public static void updatePoint(PointDataObject point) {
                CallServerFunction.callServerFunction(ObjectMapping.POINT_MANAGER_OBJECT, ObjectMapping.UPDATE_FUNCTION, point, null);
//		EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.merge(point);
//		createEntityManager.getTransaction().commit();
	}

            public static void addListPointToLocation(ArrayList<PointDataObject> listPoint, LocationDataObject location) {
                CallServerFunction.callServerFunction(ObjectMapping.POINT_MANAGER_OBJECT, ObjectMapping.ADD_LIST_POINT_TO_LOCATION_FUNCTION, listPoint, location.getLocationId());
//		EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		for (int i = 0; i < listPoint.size(); i++) {
//			PointDatabaseObject point = listPoint.get(i);
//			point.setLocation(location);
//			createEntityManager.persist(point);
//		}
//		createEntityManager.getTransaction().commit();
//		location.setPoints(listPoint);
//		LocationDataManagement.updateLocation(location);
//		//LocationDataManagement.updateLocation(location);
//		System.out.println(location.gMacAddressDataManagement.javaetPoints().size());
	}


    public static String[] getIdArrayFromListObject(List<PointDataObject> algorithms) {
        String[] returnList = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            PointDataObject algorithmDataObject = algorithms.get(i);
            returnList[i] = algorithmDataObject.getPointId();
        }
        return returnList;
    }
}
