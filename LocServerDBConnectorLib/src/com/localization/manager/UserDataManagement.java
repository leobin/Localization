package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import localization.data.entity.UserDatabaseObject;
import localization.data.entity.UserDatabaseObject;
import localization.data.entity.contentobject.UserDataObject;

public class UserDataManagement {
	public static UserDatabaseObject getUserByID(String userID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		UserDatabaseObject user = createEntityManager.find(
				UserDatabaseObject.class, userID);
		return user;
	}

	public static ArrayList<UserDatabaseObject> getAllUserByUserType(
			int usertype) {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String whereClause = " 1";
		if (usertype != UserDatabaseObject.USER_TYPE_ALL) {
			whereClause = " usertype = " + usertype;
		}
		String query = "Select * from user where " + whereClause;
		
		Query createQuery = createEntityManager.createNativeQuery(query,
				UserDatabaseObject.class);
		@SuppressWarnings("unchecked")
		List<UserDatabaseObject> resultList = createQuery.getResultList();

		return new ArrayList<UserDatabaseObject>(resultList);
	}
	
	public static String addNewUser(UserDatabaseObject user) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(user);
		createEntityManager.getTransaction().commit();
		return user.getUserId();

	}

	public static void removeUser(UserDatabaseObject user) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		UserDatabaseObject user1 = createEntityManager.merge(user);
		createEntityManager.remove(user1);
		createEntityManager.getTransaction().commit();
	}

	public static void updateUser(UserDatabaseObject user) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.merge(user);
		createEntityManager.getTransaction().commit();
	}
	
	public static ArrayList<UserDataObject> createListObject(ArrayList<UserDatabaseObject> listUser){
		ArrayList<UserDataObject> returnListUser = new ArrayList<UserDataObject>();
		for (int i = 0; i < listUser.size(); i++){
			UserDatabaseObject databaseObject = listUser.get(i);
			returnListUser.add(new UserDataObject(databaseObject));
		}
		return returnListUser;
	}
	
	public static ArrayList<UserDataObject> createListObject(List<UserDatabaseObject> listUser){
		ArrayList<UserDataObject> returnListUser = new ArrayList<UserDataObject>();
		for (int i = 0; i < listUser.size(); i++){
			UserDatabaseObject databaseObject = listUser.get(i);
			returnListUser.add(new UserDataObject(databaseObject));
		}
		return returnListUser;
	}
	
	public static void removeUser (String id){
	 	removeUser(getUserByID(id));
	}
	
	
	public static ArrayList<UserDatabaseObject> getListObjectFromListId(String[] ids){
		ArrayList<UserDatabaseObject> returnList = new ArrayList<UserDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getUserByID(ids[i]));
		}
		return returnList;
	}

	public static void updateUser(UserDataObject parameter) {
		UserDatabaseObject instance = getUserByID(parameter.getUserId());
		instance.update(parameter);
	}
}
