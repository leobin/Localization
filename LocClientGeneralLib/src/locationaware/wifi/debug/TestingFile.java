/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi.debug;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

import localization.data.entity.contentobject.LocationDataObject;
import locationaware.clientserver.Location;
import locationaware.wifi.ScanningPoint;

/**
 * Just for recording the localization process of client, so you can reconstruct it if you want
 * @author Dinh
 */
public class TestingFile implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7991746659950921088L;
	
	public LocationDataObject selectTestLocation = null;
    public ArrayList<TreeMap<Location, ArrayList<Location>>> listResult = new ArrayList<TreeMap<Location, ArrayList<Location>>>();
    public ArrayList<ScanningPoint> listScanningPoint = new ArrayList<ScanningPoint>();
    public boolean isConvert;
    public double frequency;
    
    public static void writeTestfile(TestingFile test, String testFilePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(testFilePath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    bufferedOutputStream);
            objectOutputStream.writeObject(test);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static TestingFile readTestingFile(String testFilePath) {
//		if (!testFilePath.endsWith(".testfile")) {
//			return null;
//		}
		
		File fileInput = new File(testFilePath);
		
		if (!fileInput.exists()) {
			return null;
		}
		
		try {
			FileInputStream fileInputStream = new FileInputStream(
					fileInput);
			BufferedInputStream bufferedInputStream = new BufferedInputStream(
					fileInputStream);
			ObjectInputStream objectInputStream = new ObjectInputStream(
					bufferedInputStream);
			TestingFile test = (TestingFile) objectInputStream.readObject();
			objectInputStream.close();
			return test;
		} catch (Exception e) {
			return null;
		}
    }
}
