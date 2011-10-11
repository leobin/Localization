package com.localization.manager;

import java.util.ArrayList;
import java.util.List;

import localization.data.entity.contentobject.MacAddressDataObject;

public class MacAddressDataManagement {

    public static MacAddressDataObject getMacAddressByID(String MacAddressID) {
        return null;
    }

    /*
     * get all root MacAddress
     */
    public static ArrayList<MacAddressDataObject> getAllMacAddress() {
        return null;
    }

    public static void addNewMacAddress(MacAddressDataObject MacAddress) {
    }

    public static void removeMacAddress(MacAddressDataObject MacAddress) {
    }

    public static void updateMacAddress(MacAddressDataObject MacAddress) {
    }

    public static String[] getIdArrayFromListObject(List<MacAddressDataObject> algorithms) {
        String[] returnList = new String[algorithms.size()];
        for (int i = 0; i < algorithms.size(); i++) {
            MacAddressDataObject algorithmDataObject = algorithms.get(i);
            returnList[i] = algorithmDataObject.getMacAddressId();
        }
        return returnList;
    }
}
