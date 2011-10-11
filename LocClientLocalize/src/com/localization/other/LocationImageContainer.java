/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

/**
 *
 * @author leobin
 */
public class LocationImageContainer {

	private Hashtable<Long, BufferedImage> listBufferImage;
	private static LocationImageContainer instance = null;

	private LocationImageContainer() {
		listBufferImage = new Hashtable<Long, BufferedImage>();
	}

	/**
	 * get instance of location image container
	 * implement of singleton pattern
	 * @return
	 */
	public LocationImageContainer getInstance() {
		if (instance == null) {
			instance = new LocationImageContainer();
		}
		return instance;
	}

	public void put(String locationID, BufferedImage image){
		
	}
}
