package locationaware.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.MacAddressDatabaseObject;
import locationaware.clientserver.MapData;
import locationaware.protocol.Constant;
import locationaware.wifi.AccessPoint;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

import com.localization.manager.LocalizationDataManager;
import com.localization.manager.LocationDataManagement;
import com.localization.server.CallServerFunction;
import com.localization.server.ServerAPI;
import com.localization.server.ServerConfig;

/**
 * @author Dinh
 * As map builder user send a request (query database, send mapdata to server), this runnable will handle that request 
 */
public class ServerMapbuilder implements Runnable {

	private Socket client;

	public ServerMapbuilder(Socket accept) {
		this.client = accept;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());

			int routeFunction = in.readInt();
			switch (routeFunction) {
			case Constant.CALL_SERVER_OBJECT:
				callServerObject(in, out);
				break;
			case Constant.CALL_SERVER_FUNCTION:
				callServerFunction(in, out);
				break;
			case Constant.CALL_SERVER_MAP_DATA:
				callMapdataFunction(in, out);
				break;
			default:
				System.out.println("Not implemented the route call function " + routeFunction);
				break;
			}

			return;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("in or out failed");
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // finally {
			// try {
			// if (!this.client.isClosed()) {
			// in.close();
			// out.close();
			// this.client.close();
			// }
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
	}

	private void callServerObject(ObjectInputStream in, ObjectOutputStream out)
			throws IOException, ClassNotFoundException {
		String objectId1 = (String) in.readObject();
		int objectType = in.readInt();
		int functionName = in.readInt();
		Object parameter = in.readObject();
		Object writeObject = CallServerFunction.callServerObjectFunction(
				objectType, functionName, objectId1, parameter);

		out.writeObject(writeObject);

	}

	private void callServerFunction(ObjectInputStream in, ObjectOutputStream out)
			throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		int objectType1 = in.readInt();
		int function = in.readInt();
		Object parameterFunction = in.readObject();
		Object parameterFunction2 = in.readObject();

		Object writeObjectFunction = CallServerFunction.callServerFunction(
				objectType1, function, parameterFunction, parameterFunction2);

		out.writeObject(writeObjectFunction);
	}

	public void callMapdataFunction(ObjectInputStream in, ObjectOutputStream out) {
		MapData mapData;
		try {
			mapData = (MapData) in.readObject();

			if (mapData instanceof WifiMapData
					|| mapData instanceof Vd2_3MapData) {
				LocationDatabaseObject locationDatabaseObject = ServerAPI
						.searchLocationByLocationId(mapData.getLocationId());

				LocationDatabaseObject rootLocation = LocationDataManagement
						.getRootLocationByLocationObject(locationDatabaseObject);
				Long rootLocationMAC = rootLocation
						.getMacaddressIDForBuildDevice();
				Long newMapMAC;

				if (mapData instanceof WifiMapData) {
					newMapMAC = ((WifiMapData) mapData).getScannerMAC();
				} else {
					newMapMAC = ((Vd2_3MapData) mapData).getScannerMAC();
				}

				System.out.println(rootLocationMAC + " : " + newMapMAC);
				out.writeBoolean(rootLocationMAC.compareTo(newMapMAC) != 0);
				out.flush();

				boolean replaceRootMAC = in.readBoolean();

				if (!replaceRootMAC) {
					out.writeBoolean(true);
					out.flush();
					return;
				}
				
				if (rootLocationMAC.compareTo(newMapMAC) != 0) {
					System.out.println(rootLocationMAC + " : " + newMapMAC);
					if (mapData instanceof Vd2_3MapData) {
						Vd2_3MapData vd2_3MapData = (Vd2_3MapData) mapData;
						if (rootLocation.getMacaddressIDForBuildDevice() == 0
								|| rootLocation.getMacaddressIDForBuildDevice() != vd2_3MapData
										.getScannerMAC()) {
							rootLocation.setMacaddressIDForBuildDevice(vd2_3MapData
									.getScannerMAC());
							LocationDataManagement.updateLocation(rootLocation);
						}
					} else if (mapData instanceof WifiMapData) {
						WifiMapData wifiMapData = (WifiMapData) mapData;
						if (rootLocation.getMacaddressIDForBuildDevice() == 0
								|| rootLocation.getMacaddressIDForBuildDevice() != wifiMapData
										.getScannerMAC()) {
							rootLocation.setMacaddressIDForBuildDevice(wifiMapData
									.getScannerMAC());
							LocationDataManagement.updateLocation(rootLocation);
						}
					}
				
					//need implement delete all wifi map
					String dataTypeId = ServerAPI.getDataTypeIdByDataTypeClassName(Vd2_3MapData.class.getName());
					LocationDataManagement.deleleAllMapData(rootLocation, dataTypeId);
					dataTypeId = ServerAPI.getDataTypeIdByDataTypeClassName(WifiMapData.class.getName());
					LocationDataManagement.deleleAllMapData(rootLocation, dataTypeId);
				}
			}
			
			String datatypeId = ServerAPI
					.getDataTypeIdByDataTypeClassName(mapData
							.getDataTypeClassName());
			File dirUser = new File(ServerConfig.dirData + "/" + mapData.getUserId() + "/");
			if (!dirUser.exists()) {
				dirUser.mkdirs();
			}
			MapData.writeMapData(mapData,
					dirUser.getAbsolutePath() + "/" + mapData.getLocationId() + "_" + datatypeId
							+ ServerConfig.extensionMapDataFile);

			if (mapData instanceof WifiMapData
					|| mapData instanceof Vd2_3MapData) {
				EntityManager entityManager = LocalizationDataManager
						.getEntityManagerFactory().createEntityManager();
				LocationDatabaseObject locationDatabaseObject = ServerAPI
						.searchLocationByLocationId(mapData.getLocationId());
				ArrayList<Long> listMacAddress = new ArrayList<Long>();

				if (mapData instanceof WifiMapData) {
					WifiMapData wifiMapData = (WifiMapData) mapData;
					for (ScanningPoint scanningPoint : wifiMapData.getSpsList()) {
						for (AccessPoint accessPoint : scanningPoint
								.getApsList()) {
							if (!listMacAddress.contains(accessPoint
									.getMACAddress())) {
								listMacAddress.add(accessPoint.getMACAddress());
							}
						}
					}
				} else {
					Vd2_3MapData vd2_3MapData = (Vd2_3MapData) mapData;
					for (APStatistic apStatistic : vd2_3MapData.getStatistics()
							.values()) {
						if (!listMacAddress.contains(apStatistic.accessPoint
								.getMACAddress())) {
							listMacAddress.add(apStatistic.accessPoint
									.getMACAddress());
						}
					}
				}

				MacAddressDatabaseObject macAddress = null;
				// Boolean checkReAdd = false;
				for (Long strMACAddress : listMacAddress) {
					macAddress = ServerAPI.searchMacAddressByID(strMACAddress);
					// entityManager.getTransaction().begin();

					if (macAddress == null) {
						System.out.println("macAddress = null");
						macAddress = new MacAddressDatabaseObject();
						macAddress.setMacAddressId(strMACAddress);
						entityManager.persist(macAddress);
						macAddress
								.setLocations(new ArrayList<LocationDatabaseObject>());
						System.out.println("end add");
						// checkReAdd = true;
					}

					boolean contains = false;

					for (LocationDatabaseObject locationDatabaseObject2 : macAddress
							.getLocations()) {
						// System.out.println(locationDatabaseObject2.getLocationId());
						if (locationDatabaseObject2.getLocationId().equals(
								locationDatabaseObject.getLocationId())) {
							contains = true;
						}
					}

					if (!contains) {
						System.out.println(macAddress.getMacAddressId() + ":"
								+ locationDatabaseObject.getLocationId());
						macAddress.getLocations().add(locationDatabaseObject);
						macAddress.addLocation(locationDatabaseObject
								.getLocationId());
						// LocationDataManagement.updateLocation(location);
						// entityManager.merge(macAddress);
					}
				}
				// entityManager.getTransaction().commit();
			}

			out.writeBoolean(true);
			out.flush();
			// System.out.println("++++++++++++++++++++++++++++++");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			out.writeBoolean(false);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
