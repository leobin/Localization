/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import com.localization.application.LocalizationClient;
import java.awt.Point;
import java.awt.Polygon;
import java.util.List;
import localization.data.entity.contentobject.LocationDataObject;
import localization.data.entity.contentobject.PointDataObject;

/**
 *
 * @author leobin
 */
public class PointValidator {

    public static final int MINIMUM_POINT_DISTANCE = 5;

    public static boolean isTheSamePoint(Point p1, Point p2) {
        if (p1.distance(p2) > 5) {
            return false;
        }
        return true;
    }

    /**
     * create new point database object from 2 coordinates
     * @param x
     * @param y
     * @return
     */
    public static PointDataObject createNewPoint(int x, int y, int order) {
        PointDataObject point = new PointDataObject();
        point.setCoordinateX(x);
        point.setCoordinateY(y);
        point.setPointOrder(order);
        return point;
    }

    /**
     * find which location contain a point (x,y).
     * If not return null
     * @param listLocation
     * @param x
     * @param y
     * @return
     */
    public static LocationDataObject findLocationContainPoint(List<LocationDataObject> listLocation, Point point) {
        for (int i = 0; i < listLocation.size(); i++) {
            LocationDataObject locationDatabaseObject = listLocation.get(i);
            double zoomValue = LocalizationClient.zoomValue * 1.0 / locationDatabaseObject.getContentZoom();
            if (checkLocationContainPoint(locationDatabaseObject, point, zoomValue, false)) {
                return locationDatabaseObject;
            }
        }
        return null;
    }

    /**
     * check if a point is inside location or not
     * @param location
     * @param x
     * @param y
     * @return
     */
    public static boolean checkLocationContainPoint(LocationDataObject location, Point point, double zoomValue, boolean isRootLocationNow) {
        List<PointDataObject> points = location.getPoints();
        int minWidth;
        int minHeight;
        if (isRootLocationNow) {
            minWidth = ImageHandle.getMinImageWidthForLocation((List<PointDataObject>) location.getPoints());
            minHeight = ImageHandle.getMinImageHeightForLocation((List<PointDataObject>) location.getPoints());
        } else {
            minWidth = 0;
            minHeight = 0;
        }
        
        int countPoint = points.size();
        int[] xPoints = new int[countPoint];
        int[] yPoints = new int[countPoint];
        for (int i = 0; i < points.size(); i++) {
            PointDataObject pointDatabaseObject = points.get(i);
            xPoints[i] = (int) ((pointDatabaseObject.getCoordinateX() - minWidth) * zoomValue);
            yPoints[i] = (int) ((pointDatabaseObject.getCoordinateY() - minHeight) * zoomValue);
        }
        Polygon polygon = new Polygon(xPoints, yPoints, countPoint);
        return polygon.contains(point);
    }
}
