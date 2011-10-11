package com.localization.server;

import java.util.ArrayList;
import java.util.List;


import localization.data.entity.contentobject.DataTypeDataObject;
import localization.data.entity.contentobject.LocationDataObject;
import localization.data.entity.contentobject.MacAddressDataObject;
import localization.data.entity.contentobject.UserDataObject;

import com.localization.other.ObjectMapping;

public class ServerAPI {

    public LocationDataObject searchLocationByLocationId(String locationId) {
        return (LocationDataObject) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_LOCATION_BY_LOCATION_ID, locationId, null);

    }

    public ArrayList<UserDataObject> searchLocationByMacAddressGroupByUser(
            Long[] listMacAddress) {
        return (ArrayList<UserDataObject>) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_LOCATION_BY_MAC_ADDRESS_GROUP_BY_USER, listMacAddress, null);
    }

    /**
     * search location macaddress
     *
     * @param listMacAddress
     * @return
     */
    public ArrayList<LocationDataObject> searchLocationByMacAddress(
            Long[] listMacAddress) {
        return (ArrayList<LocationDataObject>) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_LOCATION_BY_MAC_ADDRESS, listMacAddress, null);
//        long[] listLongMacAddress = new long[listMacAddress.size()];
//        for (int i = 0; i < listMacAddress.size(); ++i) {
//            listLongMacAddress[i] = MacAddressDatabaseObject.convertMAC(listMacAddress.get(i));
//        }
//        return searchLocationByMacAddress(listLongMacAddress);
    }

    public MacAddressDataObject searchMacAddressByID(Long strMACAddress) {
        return (MacAddressDataObject) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_MACADDRESS_ID, strMACAddress, null);
    }

    public UserDataObject searchUserByName(String userName) {
        return (UserDataObject) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_USER_BY_NAME, userName, null);
    }

    public List<LocationDataObject> searchLocationByLocationNameAndUserName(
            String locationName, String userName) {
        return (List<LocationDataObject>) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_LOCATION_BY_LOCATION_NAME_AND_USER_NAME, locationName, userName);
    }

    public List<DataTypeDataObject> searchDataTypeByDataTypeClassName(
            String dataTypeClassName) {
        return (List<DataTypeDataObject>) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.SEARCH_DATA_TYPE_BY_DATA_TYPE_CLASS_NAME, dataTypeClassName, null);
    }

    public String getDataTypeIDByAlgorithmClassName(
            String algorithmClassName) {
        return (String) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.GET_DATA_TYPE_ID_BY_ALGORITHM_CLASS_NAME, algorithmClassName, null);
    }

    public String getDataTypeIdByDataTypeClassName(String dataTypeClassName) {
        return (String) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.GET_DATA_TYPE_ID_BY_DATATYPE_CLASS_NAME, dataTypeClassName, null);
    }

    public ArrayList<LocationDataObject> getRootLocation(
            ArrayList<LocationDataObject> listLocation) {
        return (ArrayList<LocationDataObject>) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.GET_ROOT_LOCATION, listLocation, null);
    }

    public String getFileDataPath(LocationDataObject locationDO,
            DataTypeDataObject dataTypeDO) {
        return locationDO.getLocationDataPath(dataTypeDO.getDataTypeId());
    }

    /**
     * validate login user
     *
     * @param username
     * @param password
     * @return
     */
    public UserDataObject checkLoginUser(String username, String password) {
        return (UserDataObject) CallServerFunction.callServerFunction(ObjectMapping.SERVER_API_OBJECT, ObjectMapping.CHECK_LOGIN_USER, username, password);
    }
}
