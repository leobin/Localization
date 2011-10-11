package com.localization.manager;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.util.ArrayList;

import java.util.List;

import localization.data.entity.contentobject.DataTypeDataObject;

public class DataTypeDataManagement {

    public static ArrayList<DataTypeDataObject> getAllDataType() {
        return (ArrayList<DataTypeDataObject>) CallServerFunction.callServerFunction(ObjectMapping.DATATYPE_MANAGER_OBJET, ObjectMapping.GET_ALL_FUNCTION, null, null);
//		EntityManagerFactory entityManagerFactory = LocalizationDataManager
//				.getEntityManagerFactory();
//		EntityManager createEntityManager = entityManagerFactory
//				.createEntityManager();
//		String query = "Select * from data_type where true";
//		Query createQuery = createEntityManager.createNativeQuery(query,
//				DataTypeDatabaseObject.class);
//		@SuppressWarnings("unchecked")
//		List<DataTypeDatabaseObject> resultList = createQuery.getResultList();
//
//		return new ArrayList<DataTypeDatabaseObject>(resultList);
    }

    public static String addNewDataType(DataTypeDataObject datatype) {
       return  (String) CallServerFunction.callServerFunction(ObjectMapping.DATATYPE_MANAGER_OBJET, ObjectMapping.ADD_NEW_FUNCTION, datatype, null);
//		EntityManager createEntityManager = LocalizationDataManager
//				.getEntityManagerFactory().createEntityManager();
//		createEntityManager.getTransaction().begin();
//		createEntityManager.persist(datatype);
//		createEntityManager.getTransaction().commit();

    }

    public static void removeDataType(String datatype) {
        CallServerFunction.callServerFunction(ObjectMapping.DATATYPE_MANAGER_OBJET, ObjectMapping.REMOVE_FUNCTION, datatype, null);
//        EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//        createEntityManager.getTransaction().begin();
//        DataTypeDatabaseObject datatype1 = createEntityManager.merge(datatype);
//        createEntityManager.remove(datatype1);
//        createEntityManager.getTransaction().commit();
    }

    public static void updateDataType(DataTypeDataObject datatype) {
        CallServerFunction.callServerFunction(ObjectMapping.DATATYPE_MANAGER_OBJET, ObjectMapping.UPDATE_FUNCTION, datatype, null);
//        EntityManager createEntityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
//        createEntityManager.getTransaction().begin();
//        createEntityManager.merge(datatype);
//        createEntityManager.getTransaction().commit();
    }

    public static String[] getIdArrayFromListObject(List<DataTypeDataObject> DataTypes) {
        String[] returnList = new String[DataTypes.size()];
        for (int i = 0; i < DataTypes.size(); i++) {
            DataTypeDataObject DataTypeDataObject = DataTypes.get(i);
            returnList[i] = DataTypeDataObject.getDataTypeId();
        }
        return returnList;
    }
}
