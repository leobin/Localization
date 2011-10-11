package com.localization.server;

public class ObjectMapping {
	public static final int ALGORITHM_MANAGER_OBJECT = 1;
	public static final int DATATYPE_MANAGER_OBJET = 2;
	public static final int LOCATION_MANAGER_OBJECT = 3;
	public static final int POINT_MANAGER_OBJECT = 4;
	public static final int USER_MANAGER_OBJECT = 5;
	public static final int ALGORITHM_OBJECT = 6;
	public static final int DATA_TYPE_OBJECT = 7;
	public static final int LOCATION_OBJECT = 8;
	public static final int POINT_OBJECT = 9;
	public static final int USER_OBJECT = 10;
	public static final int MAC_ADDRESS_OBJECT = 11;
	public static final int SERVER_API_OBJECT = 12;

	public static final int GET_ALL_FUNCTION = 1;
	public static final int ADD_NEW_FUNCTION = 2;
	public static final int REMOVE_FUNCTION = 3;
	public static final int UPDATE_FUNCTION = 4;

	public static final int GET_ALL_BY_ID_FUNCTION = 5;
	public static final int ADD_LIST_POINT_FUNCTION = 6;
	public static final int ADD_LIST_POINT_TO_LOCATION_FUNCTION = 7;
	public static final int GET_USER_BY_TYPE_LOCATION = 8;

	public static final int SEARCH_LOCATION_BY_MAC_ADDRESS = 1;
	public static final int SEARCH_LOCATION_BY_MAC_ADDRESS_GROUP_BY_USER = 2;
	public static final int SEARCH_LOCATION_BY_LOCATION_ID = 3;
	public static final int SEARCH_MACADDRESS_ID = 4;
	public static final int SEARCH_USER_BY_NAME = 5;
	public static final int SEARCH_LOCATION_BY_LOCATION_NAME_AND_USER_NAME = 6;
	public static final int SEARCH_DATA_TYPE_BY_DATA_TYPE_CLASS_NAME = 7;
	public static final int GET_DATA_TYPE_ID_BY_ALGORITHM_CLASS_NAME = 8;
	public static final int GET_DATA_TYPE_ID_BY_DATATYPE_CLASS_NAME = 9;
	public static final int GET_ROOT_LOCATION = 10;
	public static final int GET_FILE_DATA_PATH = 11;
	public static final int CHECK_LOGIN_USER = 12;

	// Algorithm
	public static final int ALGORITHM_GET_DATA_TYPE = 1;
	public static final int ALGORITHM_GET_LIST_LOCATIONS = 2;
	public static final int ALGORITHM_GET_LOCATIONS = 3;
	
	public static final int ALGORITHM_SET_LOCATIONS = 4;
	public static final int ALGORITHM_SET_DATATYPE = 6;
	public static final int ALGORITHM_SET_LIST_LOCATIONS = 7;
	
	public static final int ALGORITHM_ADD_LOCATION = 5;
	public static final int ALGORITHM_ADD_LOCATION_TO_LIST_LOCATIONS = 8;
	public static final int ALGORITHM_REMOVE_LOCATION_OF_LIST_LOCATIONS = 9;
	public static final int ALGORITHM_REMOVE_LOCATION_OF_LOCATIONS = 10;

	// DATA TYPE
	public static final int DATATYPE_GET_ALGORITHMS = 4;
	
	public static final int DATATYPE_SET_ALGORITHMS = 5;
	public static final int DATATYPE_ADD_ALGORITHM = 6;
	public static final int DATATYPE_REMOVE_ALGORITHM = 7;

	// LOCATION
	public static final int LOCATION_GET_ALGORITHM = 5;
	public static final int LOCATION_GET_ALGORITHMS = 6;
	public static final int LOCATION_GET_LOCATIONS = 7;
	public static final int LOCATION_GET_MAC_ADDRESS = 8;
	public static final int LOCATION_GET_PARENT_LOCATION = 9;
	public static final int LOCATION_GET_POINTS = 10;
	public static final int LOCATION_GET_USER = 11;

	public static final int LOCATION_SET_ALGORITHM = 12;

	public static final int LOCATION_SET_ALGORITHMS = 13;
	public static final int LOCATION_ADD_ALGORITHM = 14;
	public static final int LOCATION_REMOVE_ALGORITHM = 15;

	public static final int LOCATION_SET_LOCATIONS = 16;
	public static final int LOCATION_ADD_LOCATION = 17;
	public static final int LOCATION_REMOVE_LOCATION = 18;

	public static final int LOCATION_SET_MACADDRESSES = 19;
	public static final int LOCATION_ADD_MACADDRESS = 20;
	public static final int LOCATION_REMOVE_MACADDRESS = 21;

	public static final int LOCATION_SET_POINTS = 22;
	public static final int LOCATION_ADD_POINT = 23;
	public static final int LOCATION_REMOVE_POINT = 24;

	public static final int LOCATION_SET_PARENT_LOCATION = 25;

	public static final int LOCATION_SET_USER = 26;

	// MAC_ADDRESS
	public static final int MACADDRESS_GET_LOCATIONS = 12;
	public static final int MACADDRESS_SET_LOCATIONS = 13;
	public static final int MACADDRESS_ADD_LOCATION = 14;
	public static final int MACADDRESS_REMOVE_LOCATION = 15;
	// POINT
	public static final int POINT_GET_LOCATION = 13;
	public static final int POINT_SET_LOCATION = 14;
	// USER
	public static final int USER_GET_LOCATIONS = 14;
	public static final int USER_SET_LOCATIONS = 15;
	public static final int USER_ADD_LOCATION = 16;
	public static final int USER_REMOVE_LOCATION = 17;

}
