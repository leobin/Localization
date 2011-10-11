/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import com.localization.application.panel.LocationManagementPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import localization.data.entity.contentobject.LocationDataObject;
import localization.data.entity.contentobject.PointDataObject;

/**
 *
 * @author leobin
 */
public class ImageHandle {

    public final static int BORDER_SIZE = 0;
    private static final int DEFAULT_POINT_CIRCLE_RADIUS = 2;

    /**
     * create blank image at the temp folder
     * @param width
     * @param height
     * @param filePath
     * @return
     */
    public static File createNewBlankImage(int width, int height, String filePath) {
        int imageWidth = width + 2 * BORDER_SIZE;
        int imageHeight = height + 2 * BORDER_SIZE;
        BufferedImage bimage = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        File outputFile = new File(filePath + ".jpg");
        Graphics g = bimage.createGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, imageWidth, imageHeight);
        g.setColor(Color.BLACK);

        g.fillRect(BORDER_SIZE, BORDER_SIZE,
                width - BORDER_SIZE, height - BORDER_SIZE);
        try {
            ImageIO.write(bimage, "jpg", outputFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

    /**
     * Create image for location with image for this sub location and is drawing point
     * @param width
     * @param height
     * @param filePath
     * @return
     */
    public static BufferedImage createImageForLocation(LocationDataObject location, LocationDataObject selectedLocation, ArrayList<Point> listNewPoints, int valueZoomNow) {
        int contentZoom = location.getContentZoom();
        int minWidth = getMinImageWidthForLocation((List<PointDataObject>) location.getPoints());
        int minHeight = getMinImageHeightForLocation((List<PointDataObject>) location.getPoints());

        int width = valueZoomNow * contentZoom * (getImageWidthForLocation((List<PointDataObject>) location.getPoints()) - minWidth);
        int height = valueZoomNow * contentZoom * (getImageHeightForLocation((List<PointDataObject>) location.getPoints()) - minHeight);
        BufferedImage bimage =
                new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bimage.createGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        drawContentLocation(g, location, minWidth, minHeight, valueZoomNow);

        g.setColor(Color.ORANGE);
        List<LocationDataObject> locations = location.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            LocationDataObject locationDatabaseObject = locations.get(i);
            drawShapeLocation(g, locationDatabaseObject, minWidth, minHeight, valueZoomNow);
        }


        //draw select location
        if (selectedLocation != null) {
            g.setColor(Color.GREEN);
            drawShapeLocation(g, selectedLocation, minWidth, minHeight, valueZoomNow);
        }

        drawListPoint(g, listNewPoints);


        return bimage;
    }

    /**
     * draw location
     * @param g
     * @param location
     */
    private static void drawShapeLocation(Graphics g, LocationDataObject location, int minWidth, int minHeight, int zoomValue) {
        List<PointDataObject> points = location.getPoints();
        double valueZoomNow = zoomValue * 1.0 / location.getContentZoom();
//        for (int i = 0; i < points.size(); i++) {
//            PointDatabaseObject startPoint = points.get(i);
//            PointDatabaseObject endPoint = points.get(i + 1 == points.size() ? 0 : i + 1);
//
//            g.drawLine(startPoint.getCoordinateX(), startPoint.getCoordinateY(),
//                    endPoint.getCoordinateX(), endPoint.getCoordinateY());
//        }

//        List<PointDatabaseObject> points = location.getPoints();
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            PointDataObject point = points.get(i);
            xPoints[i] = (int) (point.getCoordinateX() * valueZoomNow);
            yPoints[i] = (int) (point.getCoordinateY() * valueZoomNow);
        }
        g.drawPolygon(xPoints, yPoints, points.size());

    }

    public static void drawListPoint(Graphics g, ArrayList<Point> listNewPoints) {
        //TODO: draw list new point with new image
        if (listNewPoints != null) {
            for (int i = 0; i < listNewPoints.size(); i++) {
                Point point = listNewPoints.get(i);
                g.drawOval(point.x, point.y, DEFAULT_POINT_CIRCLE_RADIUS, DEFAULT_POINT_CIRCLE_RADIUS);
                if (i + 1 < listNewPoints.size()) {
                    Point nextPoint = listNewPoints.get(i + 1);
                    g.drawLine(point.x, point.y, nextPoint.x, nextPoint.y);
                }
            }
        }
    }

    /**
     * draw location
     * @param g
     * @param location
     */
    private static void drawContentLocation(Graphics g, LocationDataObject location, int minWidth, int minHeight, int valueZoomNow) {
        List<PointDataObject> points = location.getPoints();
        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            PointDataObject point = points.get(i);
            xPoints[i] = (point.getCoordinateX() - minWidth) * valueZoomNow;
            yPoints[i] = (point.getCoordinateY() - minHeight) * valueZoomNow;
        }
        g.fillPolygon(xPoints, yPoints, points.size());

    }

    /**
     *
     * @param listPoints
     * @return
     */
    private static int getImageWidthForLocation(List<PointDataObject> listPoints) {
        int result = 0;
        for (int i = 0; i < listPoints.size(); i++) {
            PointDataObject point = listPoints.get(i);
            if ((result < point.getCoordinateX())) {
                result = point.getCoordinateX();
            }
        }
        return result;
    }

    /**
     *
     * @param listPoints
     * @return
     */
    public static int getMinImageWidthForLocation(List<PointDataObject> listPoints) {
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < listPoints.size(); i++) {
            PointDataObject point = listPoints.get(i);
            if ((result > point.getCoordinateX())) {
                result = point.getCoordinateX();
            }
        }
        return result;
    }

    /**
     *
     * @param listPoints
     * @return
     */
    private static int getImageHeightForLocation(List<PointDataObject> listPoints) {
        int result = 0;
        for (int i = 0; i < listPoints.size(); i++) {
            PointDataObject point = listPoints.get(i);
            if ((result < point.getCoordinateY())) {
                result = point.getCoordinateY();
            }
        }
        return result;
    }

    /**
     *
     * @param listPoints
     * @return
     */
    public static int getMinImageHeightForLocation(List<PointDataObject> listPoints) {
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < listPoints.size(); i++) {
            PointDataObject point = listPoints.get(i);
            if ((result > point.getCoordinateY())) {
                result = point.getCoordinateY();
            }
        }
        return result;
    }

    /**
     * no need to use in this new version
     * @param image
     * @param filePath
     * @return
     */
    public static File copyImageToFilePath(File image, String filePath) {
        BufferedImage uploadImage = null;
        try {
            uploadImage = ImageIO.read(image);
        } catch (IOException ex) {
            System.out.println("error in ImageHandle.copyImageToFilePath.readfile");
        }

        int imageWidth = uploadImage.getWidth() + 2 * BORDER_SIZE;
        int imageHeight = uploadImage.getHeight() + 2 * BORDER_SIZE;
        BufferedImage bimage = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_INT_RGB);
        File outputFile = new File(filePath + ".jpg");
        Graphics g = bimage.createGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, imageWidth, imageHeight);
        g.drawImage(uploadImage, BORDER_SIZE, BORDER_SIZE, null);
        try {
            ImageIO.write(bimage, "jpg", outputFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outputFile;
    }

    /**
     * load image file
     * @param folder
     * @param name
     * @return
     */
    public static File loadImageFileFromFolderAndName(String folder, String name) {
        FileHandle.getFullPath(folder, name + ".jpg");
        return new File(FileHandle.getFullPath(folder, name + ".jpg"));
    }
}
