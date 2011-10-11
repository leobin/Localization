package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import localization.data.entity.DataTypeDatabaseObject;
import localization.data.entity.contentobject.DataTypeDataObject;

public class DataTypeDataManagement {
	
	public static DataTypeDatabaseObject getDataTypeByID(String DataTypeID) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		DataTypeDatabaseObject DataType = createEntityManager.find(
				DataTypeDatabaseObject.class, DataTypeID);
		return DataType;
	}
	public static ArrayList<DataTypeDatabaseObject> getAllDataType() {
		EntityManagerFactory entityManagerFactory = LocalizationDataManager
				.getEntityManagerFactory();
		EntityManager createEntityManager = entityManagerFactory
				.createEntityManager();
		String query = "Select * from data_type where true";
		Query createQuery = createEntityManager.createNativeQuery(query,
				DataTypeDatabaseObject.class);
		@SuppressWarnings("unchecked")
		List<DataTypeDatabaseObject> resultList = createQuery.getResultList();

		return new ArrayList<DataTypeDatabaseObject>(resultList);
	}

	public static String addNewDataType(DataTypeDatabaseObject datatype) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.persist(datatype);
		createEntityManager.getTransaction().commit();
		return datatype.getDataTypeId();
	}

	public static void removeDataType(DataTypeDatabaseObject datatype) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		DataTypeDatabaseObject datatype1 = createEntityManager.merge(datatype);
		createEntityManager.remove(datatype1);
		createEntityManager.getTransaction().commit();
	}

	public static void updateDataType(DataTypeDatabaseObject datatype) {
		EntityManager createEntityManager = LocalizationDataManager
				.getEntityManagerFactory().createEntityManager();
		createEntityManager.getTransaction().begin();
		createEntityManager.merge(datatype);
		createEntityManager.getTransaction().commit();
	}
	
	public static ArrayList<DataTypeDataObject> createListObject(ArrayList<DataTypeDatabaseObject> listDataType){
		ArrayList<DataTypeDataObject> returnListDataType = new ArrayList<DataTypeDataObject>();
		for (int i = 0; i < listDataType.size(); i++){
			DataTypeDatabaseObject databaseObject = listDataType.get(i);
			returnListDataType.add(new DataTypeDataObject(databaseObject));
		}
		return returnListDataType;
	}
	
	public static ArrayList<DataTypeDataObject> createListObject(List<DataTypeDatabaseObject> listDataType){
		ArrayList<DataTypeDataObject> returnListDataType = new ArrayList<DataTypeDataObject>();
		for (int i = 0; i < listDataType.size(); i++){
			DataTypeDatabaseObject databaseObject = listDataType.get(i);
			returnListDataType.add(new DataTypeDataObject(databaseObject));
		}
		return returnListDataType;
	}
	
	public static void removeDataType (String id){
	 	removeDataType(getDataTypeByID(id));
	}
	
	public static ArrayList<DataTypeDatabaseObject> getListObjectFromListId(String[] ids){
		ArrayList<DataTypeDatabaseObject> returnList = new ArrayList<DataTypeDatabaseObject>();
		for (int i = 0; i < ids.length; i++) {
			returnList.add(getDataTypeByID(ids[i]));
		}
		return returnList;
	}

	public static void updateDataType(DataTypeDataObject parameter) {
		DataTypeDatabaseObject instance = getDataTypeByID(parameter.getDataTypeId());
		instance.update(parameter);
	}
}
