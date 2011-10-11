/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import localization.data.entity.contentobject.LocationDataObject;

/**
 *
 * @author leobin
 */
public class TreeDataManagement {

    public static DefaultMutableTreeNode createLocationTreeStructure(LocationDataObject location) {
        DefaultMutableTreeNode locationNode = null;
        locationNode = new DefaultMutableTreeNode(location.getLocationName());
        locationNode.setUserObject(location);
        List<LocationDataObject> locations = location.getLocations();
        if (locations != null) {
            for (int i = 0; i < locations.size(); i++) {
                LocationDataObject locationDatabaseObject = locations.get(i);
                locationNode.add(createLocationTreeStructure(locationDatabaseObject));
            }
        }
//		location.get
        return locationNode;
    }

    public static int getRowValueOfSelectedLocation(LocationDataObject location) {
        //need to implment later
        LocationDataObject parentLocation = location.getParentLocation();
        int indexOf = parentLocation.getLocations().indexOf(location);
//        for (int)

        return 0;
    }

    public static DefaultMutableTreeNode createListLocationTreeStructure(ArrayList<LocationDataObject> allRootLocation) {
        DefaultMutableTreeNode locationNode = null;
        LocationDataObject locationTemp = new LocationDataObject();
        locationTemp.setLocationName("List all root Location");
                locationNode = new DefaultMutableTreeNode(locationTemp.getLocationName());
        locationNode.setUserObject(locationTemp);
        if (allRootLocation != null) {
            for (int i = 0; i < allRootLocation.size(); i++) {
                LocationDataObject locationDatabaseObject = allRootLocation.get(i);
                locationNode.add(createLocationTreeStructure(locationDatabaseObject));
            }
        }
//		location.get
        return locationNode;
    }
//	public DefaultMutableTreeNode createTreeForLocation(Map inputMap) {
//		DefaultMutableTreeNode temp = new DefaultMutableTreeNode(inputMap.getMapName());
//		ArrayList<LocationTemp> listOfLocation = inputMap.getAllLocations();
//		Hashtable<LocationTemp, Map> subMap = inputMap.getSubMap();
//		for (LocationTemp location : listOfLocation) {
//			Map tempSubMap = subMap.get(location);
//			if (tempSubMap != null) {
//				temp.add(getTreeRecursive(tempSubMap));
//			} else {
//				DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(location.getLocationName());
//				temp.add(tempNode);
//			}
//		}
//		return temp;
//	}
}
