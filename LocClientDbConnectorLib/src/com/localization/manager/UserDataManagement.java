package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import localization.data.entity.contentobject.UserDataObject;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;

public class UserDataManagement {

    public static UserDataObject getUserByID(String userID) {
        return (UserDataObject) CallServerFunction.callServerFunction(ObjectMapping.USER_MANAGER_OBJECT, ObjectMapping.GET_ALL_BY_ID_FUNCTION, userID, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		UserDatabaseObject user = createEntityManager.find(
//				UserDatabaseObject.class, userID);
//		return user;
    }

    public static ArrayList<UserDataObject> getAllUserByUserType(
            int usertype) {
        return (ArrayList<UserDataObject>) CallServerFunction.callServerFunction(ObjectMapping.USER_MANAGER_OBJECT, ObjectMapping.GET_ALL_FUNCTION, usertype, null);

        //		EntityManagerFactory entityManagerFactory = LocalizationDataManager
//				.getEntityManagerFactory();
//		EntityManager createEntityManager = entityManagerFactory
//				.createEntityManager();
//		String whereClause = " 1";
//		if (usertype != UserDatabaseObject.USER_TYPE_ALL) {
//			whereClause = " usertype = " + usertype;
//		}
//		String query = "Select * from user where " + whereClause;
//
//		Query createQuery = createEntityManager.createNativeQuery(query,
//				UserDatabaseObject.class);
//		@SuppressWarnings("unchecked")
//		List<UserDatabaseObject> resultList = createQuery.getResultList();
//
//		return new ArrayList<UserDatabaseObject>(resultList);
    }

    public static String addNewUser(UserDataObject user) {
        return (String) CallServerFunction.callServerFunction(ObjectMapping.USER_MANAGER_OBJECT, ObjectMapping.ADD_NEW_FUNCTION, user, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.persist(user);
//		createEntityManager.getTransaction().commit();

    }

    public static void removeUser(String user) {
        CallServerFunction.callServerFunction(ObjectMapping.USER_MANAGER_OBJECT, ObjectMapping.REMOVE_FUNCTION, user, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		UserDatabaseObject user1 = createEntityManager.merge(user);
//		createEntityManager.remove(user1);
//		createEntityManager.getTransaction().commit();
    }

    public static void updateUser(UserDataObject user) {
        CallServerFunction.callServerFunction(ObjectMapping.USER_MANAGER_OBJECT, ObjectMapping.UPDATE_FUNCTION, user, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.merge(user);
//		createEntityManager.getTransaction().commit();
    }

    public static String[] getIdArrayFromListObject(List<UserDataObject> algorithms) {
        String[] returnList = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            UserDataObject algorithmDataObject = algorithms.get(i);
            returnList[i] = algorithmDataObject.getUserId();
        }
        return returnList;
    }
}
