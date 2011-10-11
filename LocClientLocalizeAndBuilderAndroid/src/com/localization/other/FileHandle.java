/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.localization.other;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leobin
 */
public class FileHandle {

	public static final String DATA_FOLDER = "data";
	public static final String TEMP_IMAGE_FOLDER = "temp-image";
	private String mapDir;

	public FileHandle(String mapName) {
		this.mapDir = mapName;
	}

	public static String getCurrentDir() {
		File dir1 = new File(".");
		try {
			System.out.println("Current dir : " + dir1.getCanonicalPath());
			return (dir1.getCanonicalPath());
		} catch (Exception e) {
			System.out.println("Can not get current dir please check your app");
			e.printStackTrace();
		}
		return null;
	}

	public static void createDir(String fileDir) {
		File dir = new File(fileDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	public static String getFullPath(String fileDir, String fileName) {
		createDir(getCurrentDir() + "\\" + DATA_FOLDER + "\\" + fileDir);
		return (getCurrentDir() + "\\" + DATA_FOLDER + "\\" + fileDir + '\\' + fileName);
	}

	/**
	 * get temp-image folder
	 * @param fileName
	 * @return
	 */
	public static String getTempImageFolder(String fileName) {
		createDir(getCurrentDir() + "\\" + DATA_FOLDER + "\\" + TEMP_IMAGE_FOLDER);
		return (getCurrentDir() + "\\" + DATA_FOLDER + "\\" + TEMP_IMAGE_FOLDER + '\\' + fileName);
	}

	/**
	 *
	 * @param fileDir
	 * @param fileName
	 * @return
	 */
	public static String getDirectoryForSubmap(String fileDir, String fileName) {
		return (fileDir + "\\" + "submap" + '\\' + fileName);
	}
}
