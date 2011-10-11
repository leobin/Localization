package locationaware.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.MacAddressDatabaseObject;
import locationaware.clientserver.MapData;
import locationaware.wifi.AccessPoint;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

import com.localization.manager.LocalizationDataManager;
import com.localization.server.ServerAPI;
import com.localization.server.ServerConfig;

/**
 * @author Dinh
 * This class is not used anymore
 */
public class ServerMapDataReceiver implements Runnable {
	
	private Socket client;
	
	public ServerMapDataReceiver(Socket accept) {
		this.client = accept;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());
			
			MapData mapData = (MapData) in.readObject();
			String datatypeId = ServerAPI.getDataTypeIdByDataTypeClassName(mapData.getDataTypeClassName());
			MapData.writeMapData(mapData, ServerConfig.dirData + "/" + mapData.getLocationId() + "_" + mapData.getUserId() + "_" + datatypeId + ServerConfig.extensionMapDataFile);			
			
			if (mapData instanceof WifiMapData || mapData instanceof Vd2_3MapData) {
				EntityManager entityManager = LocalizationDataManager.getEntityManagerFactory().createEntityManager();
				LocationDatabaseObject locationDatabaseObject = ServerAPI.searchLocationByLocationId(mapData.getLocationId());
				ArrayList<Long> listMacAddress = new ArrayList<Long>(); 
				
				if (mapData instanceof WifiMapData) {
					WifiMapData wifiMapData = (WifiMapData) mapData;
					for (ScanningPoint scanningPoint : wifiMapData.getSpsList()) {
						for (AccessPoint accessPoint : scanningPoint.getApsList()) {
							if (!listMacAddress.contains(accessPoint.getMACAddress())) {
								listMacAddress.add(accessPoint.getMACAddress());
							}
						}
					}
				} else {
					Vd2_3MapData vd2_3MapData = (Vd2_3MapData) mapData;
					for (APStatistic apStatistic : vd2_3MapData.getStatistics().values()) {
						if (!listMacAddress.contains(apStatistic.accessPoint.getMACAddress())) {
							listMacAddress.add(apStatistic.accessPoint.getMACAddress());
						}						
					}
				}
				
				MacAddressDatabaseObject macAddress = null;
				for (Long strMACAddress : listMacAddress) {
					macAddress = ServerAPI
							.searchMacAddressByID(strMACAddress);
					
					entityManager.getTransaction().begin();

					if (macAddress == null) {
						macAddress = new MacAddressDatabaseObject();
						macAddress.setMacAddressId(strMACAddress);
						macAddress.setLocations(new ArrayList<LocationDatabaseObject>());
						entityManager.persist(macAddress);
					}

					if (!macAddress.getLocations().contains(locationDatabaseObject)) {
						macAddress.getLocations().add(locationDatabaseObject);
						entityManager.merge(macAddress);
					}
					
					entityManager.getTransaction().commit();
				}
			}
			
			out.writeBoolean(true);
			
			out.close();
			in.close();
			client.close();
			return;
		} catch (IOException e) {
			System.out.println("in or out failed");
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			out.writeBoolean(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}