package locationaware.apps;

import java.io.File;
import java.util.ArrayList;

import locationaware.client.ClientCalibration;
import locationaware.clientserver.MapData;
import locationaware.wifi.mapdata.Vd2_3MapData;

import com.localization.other.ApplicationConfiguration;

public class ClientCalibrationConsole {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String mapFilePath1 = "E:\\Users\\Dinh\\Desktop\\Localization Project\\LocClientApplications\\MapBuilder\\mapdata\\428_15_3.mapdata";
        String mapFilePath2 = "E:\\Users\\Dinh\\Desktop\\Localization Project\\LocClientApplications\\MapBuilder\\mapdata\\463_15_3.mapdata";
        String mapFilePath3 = "E:\\Users\\Dinh\\Desktop\\Localization Project\\LocClientApplications\\MapBuilder\\mapdata\\929_15_3.mapdata";
        
        ArrayList<String> listMapFilePaths = new ArrayList<String>();
		listMapFilePaths.add(mapFilePath1);
		listMapFilePaths.add(mapFilePath2);
		listMapFilePaths.add(mapFilePath3);
        
        ArrayList<Vd2_3MapData> listMapData = new ArrayList<Vd2_3MapData>();
       
        for (String mapDataFilePath : listMapFilePaths) {
            File mapDataFile = new File(mapDataFilePath);

            if (mapDataFile.exists()) {
                MapData mapData = MapData.readMapData(mapDataFilePath);
                listMapData.add((Vd2_3MapData) mapData);
            }
        }

        if (!listMapData.isEmpty()) {
            ClientCalibration caliration = new ClientCalibration();
            caliration.setServerHost(ApplicationConfiguration.load().serverHost);
            caliration.setPort(ApplicationConfiguration.load().localizePort);

            if (caliration.calirate(listMapData)) {
            	System.out.println("Calibration success");
            } else {
                System.out.println("Failed to calibrate");
            }
        }
	}

}
