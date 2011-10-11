package com.localization.manager;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.util.ArrayList;
import java.util.List;
import localization.data.entity.contentobject.AlgorithmDataObject;

public class AlgorithmDataManagement {
    /*
     * get all root algorithm
     */

    public static ArrayList<AlgorithmDataObject> getAllAlgorithm() {
        return (ArrayList<AlgorithmDataObject>) CallServerFunction.callServerFunction(ObjectMapping.ALGORITHM_MANAGER_OBJECT, ObjectMapping.GET_ALL_FUNCTION, null, null);

//		EntityManagerFactory entityManagerFactory = LocalizationDataManager
//				.getEntityManagerFactory();
//		EntityManager createEntityManager = entityManagerFactory
//				.createEntityManager();
//		String query = "Select * from algorithm where true";
//		Query createQuery = createEntityManager.createNativeQuery(query,
//				AlgorithmDatabaseObject.class);
//		@SuppressWarnings("unchecked")
//		List<AlgorithmDatabaseObject> resultList = createQuery.getResultList();
//                Object temp = null;
//                return (ArrayList<AlgorithmDatabaseObject>) temp;
//		//return new ArrayList<AlgorithmDatabaseObject>(resultList);
    }

    public static String addNewAlgorithm(AlgorithmDataObject algorithm) {
        return (String) CallServerFunction.callServerFunction(ObjectMapping.ALGORITHM_MANAGER_OBJECT, ObjectMapping.ADD_NEW_FUNCTION, algorithm, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.persist(algorithm);
//		createEntityManager.getTransaction().commit();

    }

    public static void removeAlgorithm(String algorithm) {
        CallServerFunction.callServerFunction(ObjectMapping.ALGORITHM_MANAGER_OBJECT, ObjectMapping.REMOVE_FUNCTION, algorithm, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		AlgorithmDatabaseObject algorithm1 = createEntityManager.merge(algorithm);
//		createEntityManager.remove(algorithm1);
//		createEntityManager.getTransaction().commit();
    }

    public static void updateAlgorithm(AlgorithmDataObject algorithm) {
        CallServerFunction.callServerFunction(ObjectMapping.ALGORITHM_MANAGER_OBJECT, ObjectMapping.UPDATE_FUNCTION, algorithm, null);

        //		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.merge(algorithm);
//		createEntityManager.getTransaction().commit();
    }

    public static String[] getIdArrayFromListObject(List<AlgorithmDataObject> algorithms) {
        String[] returnList = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            AlgorithmDataObject algorithmDataObject = algorithms.get(i);
            returnList[i] = algorithmDataObject.getAlgorithmId();
        }
        return returnList;
    }
}
