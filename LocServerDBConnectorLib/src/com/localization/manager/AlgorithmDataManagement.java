package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.UserDatabaseObject;
import localization.data.entity.contentobject.AlgorithmDataObject;
import localization.data.entity.contentobject.UserDataObject;

public class AlgorithmDataManagement {
	public static AlgorithmDatabaseObject getAlgorithmByID(String AlgorithmID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		AlgorithmDatabaseObject algorithm = createEntityManager.find(
				AlgorithmDatabaseObject.class, AlgorithmID);
		return algorithm;
	}

	/*
	 * get all root algorithm
	 */
	public static ArrayList<AlgorithmDatabaseObject> getAllAlgorithm() {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String query = "Select * from algorithm where true";
		Query createQuery = createEntityManager.createNativeQuery(query,
				AlgorithmDatabaseObject.class);
		@SuppressWarnings("unchecked")
		List<AlgorithmDatabaseObject> resultList = createQuery.getResultList();

		return new ArrayList<AlgorithmDatabaseObject>(resultList);
	}

	public static String addNewAlgorithm(AlgorithmDatabaseObject algorithm) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(algorithm);
		createEntityManager.getTransaction().commit();
		return algorithm.getAlgorithmId();

	}

	public static void removeAlgorithm(AlgorithmDatabaseObject algorithm) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		AlgorithmDatabaseObject algorithm1 = createEntityManager
				.merge(algorithm);
		createEntityManager.remove(algorithm1);
		createEntityManager.getTransaction().commit();
	}

	public static void updateAlgorithm(AlgorithmDatabaseObject algorithm) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.merge(algorithm);
		createEntityManager.getTransaction().commit();
	}
	
	public static ArrayList<AlgorithmDataObject> createListObject(ArrayList<AlgorithmDatabaseObject> listAlgorithm){
		ArrayList<AlgorithmDataObject> returnListAlgorithm = new ArrayList<AlgorithmDataObject>();
		for (int i = 0; i < listAlgorithm.size(); i++){
			AlgorithmDatabaseObject algorithmDatabaseObject = listAlgorithm.get(i);
			returnListAlgorithm.add(new AlgorithmDataObject(algorithmDatabaseObject));
		}
		return returnListAlgorithm;
	}
	
	public static ArrayList<AlgorithmDataObject> createListObject(List<AlgorithmDatabaseObject> listAlgorithm){
		ArrayList<AlgorithmDataObject> returnListAlgorithm = new ArrayList<AlgorithmDataObject>();
		for (int i = 0; i < listAlgorithm.size(); i++){
			AlgorithmDatabaseObject algorithmDatabaseObject = listAlgorithm.get(i);
			returnListAlgorithm.add(new AlgorithmDataObject(algorithmDatabaseObject));
		}
		return returnListAlgorithm;
	}
	
	public static void removeAlgorithm (String id){
	 	removeAlgorithm(getAlgorithmByID(id));
	}
	
	public static ArrayList<AlgorithmDatabaseObject> getListObjectFromListId(String[] ids){
		ArrayList<AlgorithmDatabaseObject> returnList = new ArrayList<AlgorithmDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getAlgorithmByID(ids[i]));
		}
		return returnList;
	}
	

	public static void updateAlgorithm(AlgorithmDataObject parameter) {
		AlgorithmDatabaseObject instance = getAlgorithmByID(parameter.getAlgorithmId());
		instance.update(parameter);
	}
}
