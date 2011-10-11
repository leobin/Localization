/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import localization.data.entity.PointDatabaseObject;
import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.PointDatabaseObject;
import localization.data.entity.UserDatabaseObject;
import localization.data.entity.contentobject.PointDataObject;
import localization.data.entity.contentobject.UserDataObject;

/**
 * 
 * @author leobin
 */
public class PointDataManagement {
	public static PointDatabaseObject getPointByID(String PointID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		PointDatabaseObject Point = createEntityManager.find(
				PointDatabaseObject.class, PointID);
		return Point;
	}

	public static void addNewPoint(PointDatabaseObject point) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(point);
		createEntityManager.getTransaction().commit();

	}

	public static void addListPoint(ArrayList<PointDatabaseObject> listPoint) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		for (int i = 0; i < listPoint.size(); i++) {
			PointDatabaseObject point = listPoint.get(i);
			createEntityManager.persist(point);
		}
		createEntityManager.getTransaction().commit();

	}

	public static void removePoint(PointDatabaseObject point) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.remove(point);
		createEntityManager.getTransaction().commit();
	}

	public static void updatePoint(PointDatabaseObject point) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.merge(point);
		createEntityManager.getTransaction().commit();
	}

	public static void addListPointToLocation(
			ArrayList<PointDatabaseObject> listPoint,
			LocationDatabaseObject location) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		for (int i = 0; i < listPoint.size(); i++) {
			PointDatabaseObject point = listPoint.get(i);
			point.setLocation(location);
			createEntityManager.persist(point);
		}
		createEntityManager.getTransaction().commit();
		location.setPoints(listPoint);
		LocationDataManagement.updateLocation(location);
		// LocationDataManagement.updateLocation(location);
		System.out.println(location.getPoints().size());
	}

	public static ArrayList<PointDataObject> createListObject(
			ArrayList<PointDatabaseObject> listPoint) {
		ArrayList<PointDataObject> returnListPoint = new ArrayList<PointDataObject>();
		for (int i = 0; i < listPoint.size(); i++) {
			PointDatabaseObject databaseObject = listPoint.get(i);
			returnListPoint.add(new PointDataObject(databaseObject));
		}
		return returnListPoint;
	}

	public static ArrayList<PointDataObject> createListObject(
			List<PointDatabaseObject> listPoint) {
		ArrayList<PointDataObject> returnListPoint = new ArrayList<PointDataObject>();
		for (int i = 0; i < listPoint.size(); i++) {
			PointDatabaseObject databaseObject = listPoint.get(i);
			returnListPoint.add(new PointDataObject(databaseObject));
		}
		return returnListPoint;
	}

	public static void removePoint(String id) {
		removePoint(getPointByID(id));
	}

	public static ArrayList<PointDatabaseObject> getListObjectFromListId(
			String[] ids) {
		ArrayList<PointDatabaseObject> returnList = new ArrayList<PointDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getPointByID(ids[i]));
		}
		return returnList;
	}

	public static void updatePoint(PointDataObject parameter) {
		PointDatabaseObject instance = getPointByID(parameter.getPointId());
		instance.update(parameter);
	}

	public static void addListDataPointToLocation(
			ArrayList<PointDataObject> listPoint,
			LocationDatabaseObject location) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		ArrayList<PointDatabaseObject> listDatabasePoint = new ArrayList<PointDatabaseObject>();
		for (int i = 0; i < listPoint.size(); i++) {
			PointDatabaseObject point = new PointDatabaseObject(
					listPoint.get(i));
			point.setLocation(location);
			createEntityManager.persist(point);
			listDatabasePoint.add(point);
		}
		createEntityManager.getTransaction().commit();
		location.setPoints(listDatabasePoint);
		LocationDataManagement.updateLocation(location);
		// LocationDataManagement.updateLocation(location);
		System.out.println(location.getPoints().size());
	}
}
