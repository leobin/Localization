package com.localization.server;

import java.util.ArrayList;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.DataTypeDatabaseObject;
import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.MacAddressDatabaseObject;
import localization.data.entity.PointDatabaseObject;
import localization.data.entity.UserDatabaseObject;
import localization.data.entity.contentobject.AlgorithmDataObject;
import localization.data.entity.contentobject.DataTypeDataObject;
import localization.data.entity.contentobject.LocationDataObject;
import localization.data.entity.contentobject.MacAddressDataObject;
import localization.data.entity.contentobject.PointDataObject;
import localization.data.entity.contentobject.UserDataObject;

import com.localization.manager.AlgorithmDataManagement;
import com.localization.manager.DataTypeDataManagement;
import com.localization.manager.LocationDataManagement;
import com.localization.manager.MacAddressDataManagement;
import com.localization.manager.PointDataManagement;
import com.localization.manager.UserDataManagement;

public class CallServerFunction {
	public static Object callServerFunction(int objectType, int function,
			Object parameter, Object parameter2) {
		switch (objectType) {
		case ObjectMapping.ALGORITHM_MANAGER_OBJECT:
			return handleAlgorithmManagerObject(function, parameter, parameter2);
		case ObjectMapping.DATATYPE_MANAGER_OBJET:
			return handleDatatypeManagerObject(function, parameter, parameter2);
		case ObjectMapping.LOCATION_MANAGER_OBJECT:
			return handleLocationManagerObject(function, parameter, parameter2);
		case ObjectMapping.POINT_MANAGER_OBJECT:
			return handlePointManagerObject(function, parameter, parameter2);
		case ObjectMapping.USER_MANAGER_OBJECT:
			return handleUserManagerObject(function, parameter, parameter2);
		case ObjectMapping.SERVER_API_OBJECT:
			return handleServerAPIObject(function, parameter, parameter2);
		}
		return null;
	}

	private static Object handleServerAPIObject(int function, Object parameter,
			Object parameter2) {
		// TODO Auto-generated method stub
		switch (function) {
		case ObjectMapping.SEARCH_LOCATION_BY_MAC_ADDRESS:
			return LocationDataManagement.createListObject(ServerAPI
					.searchLocationByMacAddress((Long[])parameter ));
		case ObjectMapping.SEARCH_LOCATION_BY_MAC_ADDRESS_GROUP_BY_USER:
			return UserDataManagement
					.createListObject(ServerAPI
							.searchLocationByMacAddressGroupByUser((Long[]) parameter));
		case ObjectMapping.SEARCH_LOCATION_BY_LOCATION_ID:
			return new LocationDataObject(
					ServerAPI.searchLocationByLocationId((String) parameter));
		case ObjectMapping.SEARCH_MACADDRESS_ID:
			return new MacAddressDataObject(
					ServerAPI.searchMacAddressByID((Long) parameter));
		case ObjectMapping.SEARCH_USER_BY_NAME:
			return new UserDataObject(
					ServerAPI.searchUserByName((String) parameter));
		case ObjectMapping.SEARCH_LOCATION_BY_LOCATION_NAME_AND_USER_NAME:
			return LocationDataManagement.createListObject(ServerAPI
					.searchLocationByLocationNameAndUserName(
							(String) parameter, (String) parameter2));
		case ObjectMapping.SEARCH_DATA_TYPE_BY_DATA_TYPE_CLASS_NAME:
			return DataTypeDataManagement.createListObject(ServerAPI
					.searchDataTypeByDataTypeClassName((String) parameter));
		case ObjectMapping.GET_DATA_TYPE_ID_BY_ALGORITHM_CLASS_NAME:
			return ServerAPI
					.getDataTypeIDByAlgorithmClassName((String) parameter);
		case ObjectMapping.GET_DATA_TYPE_ID_BY_DATATYPE_CLASS_NAME:
			return ServerAPI
					.getDataTypeIdByDataTypeClassName((String) parameter);
		case ObjectMapping.GET_ROOT_LOCATION:
			return LocationDataManagement.createListObject(ServerAPI
					.getRootLocation(LocationDataManagement
							.getListObjectFromListId((String[]) parameter)));
		case ObjectMapping.GET_FILE_DATA_PATH:
			return ServerAPI.getFileDataPath(
					(LocationDatabaseObject) parameter,
					(DataTypeDatabaseObject) parameter2);
		case ObjectMapping.CHECK_LOGIN_USER:
			return new UserDataObject(ServerAPI.checkLoginUser(
					(String) parameter, (String) parameter2));
		}
		return null;
	}

	private static Object handleUserManagerObject(int function,
			Object parameter, Object parameter2) {
		// TODO Auto-generated method stub
		switch (function) {
		case ObjectMapping.GET_ALL_BY_ID_FUNCTION:
			return new UserDataObject(
					UserDataManagement.getUserByID((String) parameter));
		case ObjectMapping.GET_ALL_FUNCTION:
			return UserDataManagement.createListObject(UserDataManagement
					.getAllUserByUserType((Integer) parameter));
		case ObjectMapping.ADD_NEW_FUNCTION:
			return UserDataManagement.addNewUser(new UserDatabaseObject(
					(UserDataObject) parameter));
		case ObjectMapping.REMOVE_FUNCTION:
			UserDataManagement.removeUser((String) parameter);
			break;
		case ObjectMapping.UPDATE_FUNCTION:
			UserDataManagement.updateUser((UserDataObject) parameter);
			break;
		}
		return null;
	}

	private static Object handlePointManagerObject(int function,
			Object parameter, Object parameter2) {
		switch (function) {
		case ObjectMapping.ADD_LIST_POINT_FUNCTION:
			PointDataManagement.addListPoint(PointDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.ADD_LIST_POINT_TO_LOCATION_FUNCTION:
			PointDataManagement
					.addListDataPointToLocation(
							(ArrayList<PointDataObject>) parameter,
							LocationDataManagement
									.getLocationByID((String) parameter2));
			break;
		case ObjectMapping.ADD_NEW_FUNCTION:
			PointDataManagement.addNewPoint(new PointDatabaseObject(
					(PointDataObject) parameter));
			break;
		case ObjectMapping.REMOVE_FUNCTION:
			PointDataManagement.removePoint((String) parameter);
			break;
		case ObjectMapping.UPDATE_FUNCTION:
			PointDataManagement.updatePoint((PointDataObject) parameter);
			break;
		}
		return null;

	}

	private static Object handleLocationManagerObject(int function,
			Object parameter, Object parameter2) {
		switch (function) {
		case ObjectMapping.GET_ALL_FUNCTION:
			return LocationDataManagement
					.createListObject(LocationDataManagement
							.getAllRootLocation());
		case ObjectMapping.GET_ALL_BY_ID_FUNCTION:
			return LocationDataManagement
					.createListObject(LocationDataManagement
							.getAllRootLocationByUserID((String) parameter));
		case ObjectMapping.ADD_NEW_FUNCTION:
			return LocationDataManagement
					.addNewLocation(new LocationDatabaseObject(
							(LocationDataObject) parameter));
		case ObjectMapping.REMOVE_FUNCTION:
			LocationDataManagement.removeLocation((String) parameter);
			break;
		case ObjectMapping.UPDATE_FUNCTION:
			LocationDataManagement
					.updateLocation((LocationDataObject) parameter);
			break;
		}
		return null;
	}

	private static Object handleDatatypeManagerObject(int function,
			Object parameter, Object parameter2) {
		switch (function) {
		case ObjectMapping.GET_ALL_FUNCTION:
			return DataTypeDataManagement
					.createListObject(DataTypeDataManagement.getAllDataType());
		case ObjectMapping.ADD_NEW_FUNCTION:
			return DataTypeDataManagement
					.addNewDataType(new DataTypeDatabaseObject(
							(DataTypeDataObject) parameter));
		case ObjectMapping.REMOVE_FUNCTION:
			DataTypeDataManagement.removeDataType((String) parameter);
			break;
		case ObjectMapping.UPDATE_FUNCTION:
			DataTypeDataManagement
					.updateDataType((DataTypeDataObject) parameter);
			break;
		}
		return null;
	}

	private static Object handleAlgorithmManagerObject(int function,
			Object parameter, Object parameter2) {
		switch (function) {
		case ObjectMapping.GET_ALL_FUNCTION:
			return AlgorithmDataManagement
					.createListObject(AlgorithmDataManagement.getAllAlgorithm());
		case ObjectMapping.ADD_NEW_FUNCTION:
			return AlgorithmDataManagement
					.addNewAlgorithm(new AlgorithmDatabaseObject(
							(AlgorithmDataObject) parameter));
		case ObjectMapping.REMOVE_FUNCTION:
			AlgorithmDataManagement.removeAlgorithm((String) parameter);
			break;
		case ObjectMapping.UPDATE_FUNCTION:
			AlgorithmDataManagement
					.updateAlgorithm((AlgorithmDataObject) parameter);
			break;
		}
		return null;

	}

	public static Object callServerObjectFunction(int objectType, int function,
			String objectID, Object parameter) {
		switch (objectType) {
		case ObjectMapping.ALGORITHM_OBJECT:
			return handleAlgorithmObject(objectID, function, parameter);
		case ObjectMapping.DATA_TYPE_OBJECT:
			return handleDatatypeObject(objectID, function, parameter);
		case ObjectMapping.LOCATION_OBJECT:
			return handleLocationObject(objectID, function, parameter);
		case ObjectMapping.MAC_ADDRESS_OBJECT:
			return handleMacaddressObject(objectID, function, parameter);
		case ObjectMapping.POINT_OBJECT:
			return handlePointObject(objectID, function, parameter);
		case ObjectMapping.USER_OBJECT:
			return handleUserObject(objectID, function, parameter);
		}
		return null;
	}

	private static Object handleUserObject(String objectID, int function,
			Object parameter) {
		UserDatabaseObject user = UserDataManagement.getUserByID(objectID);
		switch (function) {
		case ObjectMapping.USER_GET_LOCATIONS:
			return LocationDataManagement.createListObject(user.getLocations());
		case ObjectMapping.USER_SET_LOCATIONS:
			user.setLocations(LocationDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.USER_ADD_LOCATION:
			user.addLocation((String) parameter);
			break;
		case ObjectMapping.USER_REMOVE_LOCATION:
			user.removeLocation((String) parameter);
			break;
		}
		return null;
	}

	private static Object handlePointObject(String objectID, int function,
			Object parameter) {
		PointDatabaseObject Point = PointDataManagement.getPointByID(objectID);
		switch (function) {
		case ObjectMapping.POINT_GET_LOCATION:
			return new LocationDataObject(Point.getLocation());
		case ObjectMapping.POINT_SET_LOCATION:
			Point.setLocation(LocationDataManagement
					.getLocationByID((String) parameter));
			break;
		}
		return null;
	}

	private static Object handleMacaddressObject(String objectID, int function,
			Object parameter) {
		MacAddressDatabaseObject MacAddress = MacAddressDataManagement
				.getMacAddressByID(objectID);
		switch (function) {
		case ObjectMapping.MACADDRESS_GET_LOCATIONS:
			return LocationDataManagement.createListObject(MacAddress
					.getLocations());
		case ObjectMapping.MACADDRESS_SET_LOCATIONS:
			MacAddress.setLocations(LocationDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.MACADDRESS_ADD_LOCATION:
			MacAddress.addLocation((String) parameter);
			break;
		case ObjectMapping.MACADDRESS_REMOVE_LOCATION:
			MacAddress.removeLocation((String) parameter);
			break;
		}
		return null;

	}

	private static Object handleLocationObject(String objectID, int function,
			Object parameter) {
		LocationDatabaseObject location = null;
		if (objectID != null) {
			location = LocationDataManagement.getLocationByID(objectID);
		}
		switch (function) {
		case ObjectMapping.LOCATION_GET_ALGORITHM:
			return new AlgorithmDataObject(location.getAlgorithm());
		case ObjectMapping.LOCATION_GET_ALGORITHMS:
			if (objectID != null) {
				return AlgorithmDataManagement.createListObject(location
						.getAlgorithms());
			}
		case ObjectMapping.LOCATION_GET_LOCATIONS:
			return LocationDataManagement.createListObject(location
					.getLocations());
		case ObjectMapping.LOCATION_GET_MAC_ADDRESS:
			return MacAddressDataManagement.createListObject(location
					.getMacAddresses());
		case ObjectMapping.LOCATION_GET_PARENT_LOCATION:
			if (location.getParentLocation() != null) {
				return new LocationDataObject(location.getParentLocation());
			} else {
				return null;
			}
		case ObjectMapping.LOCATION_GET_POINTS:
			return PointDataManagement.createListObject(location.getPoints());
		case ObjectMapping.LOCATION_GET_USER:
			return new UserDataObject(location.getUser());

		case ObjectMapping.LOCATION_SET_ALGORITHM:
			location.setAlgorithm((String) parameter);
			break;

		case ObjectMapping.LOCATION_SET_ALGORITHMS:
			location.setAlgorithms(AlgorithmDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.LOCATION_ADD_ALGORITHM:
			location.addAlgorithm((String) parameter);
			break;
		case ObjectMapping.LOCATION_REMOVE_ALGORITHM:
			location.removeAlgorithm((String) parameter);
			break;

		case ObjectMapping.LOCATION_SET_LOCATIONS:
			location.setLocations(LocationDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.LOCATION_ADD_LOCATION:
			location.addLocation((String) parameter);
			break;
		case ObjectMapping.LOCATION_REMOVE_LOCATION:
			location.removeLocation((String) parameter);

			break;
		case ObjectMapping.LOCATION_SET_MACADDRESSES:
			location.setMacAddresses(MacAddressDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.LOCATION_ADD_MACADDRESS:
			location.addMacaddress((String) parameter);
			break;
		case ObjectMapping.LOCATION_REMOVE_MACADDRESS:
			location.removeMacaddress((String) parameter);
			break;

		case ObjectMapping.LOCATION_SET_POINTS:
			location.setPoints(PointDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.LOCATION_ADD_POINT:
			location.addPoint((String) parameter);
			break;
		case ObjectMapping.LOCATION_REMOVE_POINT:
			location.removePoint((String) parameter);
			break;

		case ObjectMapping.LOCATION_SET_PARENT_LOCATION:
			location.setParentLocation((String) parameter);
			break;
		case ObjectMapping.LOCATION_SET_USER:
			location.setUser((String) parameter);
			break;
		}
		return null;

	}

	private static Object handleDatatypeObject(String objectID, int function,
			Object parameter) {
		DataTypeDatabaseObject DataType = DataTypeDataManagement
				.getDataTypeByID(objectID);
		switch (function) {
		case ObjectMapping.DATATYPE_GET_ALGORITHMS:
			return AlgorithmDataManagement.createListObject(DataType
					.getAlgorithms());
		case ObjectMapping.DATATYPE_SET_ALGORITHMS:
			DataType.setAlgorithms(AlgorithmDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.DATATYPE_ADD_ALGORITHM:
			DataType.addAlgorithm((String) parameter);
			break;
		case ObjectMapping.DATATYPE_REMOVE_ALGORITHM:
			DataType.removeAlgorithm((String) parameter);
			break;
		}
		return null;

	}

	private static Object handleAlgorithmObject(String objectID, int function,
			Object parameter) {
		AlgorithmDatabaseObject Algorithm = AlgorithmDataManagement
				.getAlgorithmByID(objectID);
		switch (function) {
		case ObjectMapping.ALGORITHM_GET_DATA_TYPE:
			return new DataTypeDataObject(Algorithm.getDataType());
		case ObjectMapping.ALGORITHM_GET_LIST_LOCATIONS:
			return LocationDataManagement.createListObject(Algorithm
					.getListLocations());
		case ObjectMapping.ALGORITHM_GET_LOCATIONS:
			return LocationDataManagement.createListObject(Algorithm
					.getLocations());

		case ObjectMapping.ALGORITHM_SET_LOCATIONS:
			Algorithm.setLocations(LocationDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.ALGORITHM_SET_DATATYPE:
			Algorithm.setDataType(DataTypeDataManagement
					.getDataTypeByID((String) parameter));
			break;
		case ObjectMapping.ALGORITHM_SET_LIST_LOCATIONS:
			Algorithm.setListLocations(LocationDataManagement
					.getListObjectFromListId((String[]) parameter));
			break;
		case ObjectMapping.ALGORITHM_ADD_LOCATION:
			Algorithm.addLocatin((String) parameter);
			break;
		case ObjectMapping.ALGORITHM_ADD_LOCATION_TO_LIST_LOCATIONS:
			Algorithm.addLocationToList((String) parameter);
			break;
		case ObjectMapping.ALGORITHM_REMOVE_LOCATION_OF_LOCATIONS:
			Algorithm.removeLocation((String) parameter);
			break;
		case ObjectMapping.ALGORITHM_REMOVE_LOCATION_OF_LIST_LOCATIONS:
			Algorithm.removeLocationOfList((String) parameter);
			break;
		}
		return null;

	}
}
