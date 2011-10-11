package locationaware.apps;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.DataTypeDatabaseObject;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

import com.localization.manager.LocalizationDataManager;
import com.localization.server.ServerAPI;

/**
 * @author Dinh
 * Example for inserting data to database
 */
public class CreateDatabase {

	static EntityManager entityManager = LocalizationDataManager
			.getEntityManagerFactory().createEntityManager();
	static ServerAPI serverAPI = new ServerAPI();

//	public static void createDatabase(File rawDataDir) {
//		if (rawDataDir != null && rawDataDir.isDirectory()) {
//			File[] allFiles = rawDataDir.listFiles();
//			for (int i = 0; i < allFiles.length; ++i) {
//				if (allFiles[i].isDirectory()) {
//					createDatabase(allFiles[i]);
//					continue;
//				} else if (allFiles[i].getName().endsWith(
//						CommonConfig.extensionMapDataFile)) {
//
//					entityManager.getTransaction().begin();
//
//					Vd2_3MapData smallMapVd2_3 = (Vd2_3MapData) MapData.readMapData(allFiles[i].getAbsolutePath());
//
//					UserDatabaseObject user = serverAPI.searchUserByName(smallMapVd2_3
//							.getCreator());
//
//					if (user == null) {
//						user = new UserDatabaseObject();
//						user.setUserName(smallMapVd2_3.getCreator());
//						user.setUserType("mapbuilder");
//						user.setPassword("xyz");
//						user.setLocations(new ArrayList<LocationDatabaseObject>());
//						entityManager.persist(user);
//						
//						LocationDatabaseObject rootLocation = new LocationDatabaseObject();
//						rootLocation.setLocationDescription("Root Location");
//						rootLocation.setLocationName("Nha I - KHTN");
//						rootLocation.setUser(user);
//						entityManager.persist(rootLocation);
//						
//						entityManager.getTransaction().commit();
//						entityManager.getTransaction().begin();
//					}
//
//					LocationDatabaseObject location = new LocationDatabaseObject();
//					location.setUser(user);
//					location.setLocationName(smallMapVd2_3.getLocationName());
//					location.setLocationDataPath(allFiles[i].getAbsolutePath());
//					location.setParentLocation(serverAPI.searchLocationByLocationNameAndUserName("Nha I - KHTN", user.getUserName()).get(0));
//					entityManager.persist(location);
//					
//					user.getLocations().add(location);
//					entityManager.merge(user);
//
//					ArrayList<String> listMACAdd = new ArrayList<String>();
//					for (APStatistic apStatistic : smallMapVd2_3.getStatistics()) {
//						listMACAdd.add(apStatistic.accessPoint.getMACAddress());
//					}
//
//					MacAddressDatabaseObject macAddress = null;
//					for (String strMACAddress : listMACAdd) {
//						macAddress = serverAPI
//								.searchMacAddressByID(strMACAddress);
//
//						if (macAddress == null) {
//							macAddress = new MacAddressDatabaseObject();
//							macAddress.setMacAddressId(strMACAddress);
//							macAddress.setLocations(new ArrayList<LocationDatabaseObject>());
//							entityManager.persist(macAddress);
//						}
//
//						macAddress.getLocations().add(location);
//						entityManager.merge(macAddress);
//					}
//
//					entityManager.getTransaction().commit();
//				}
//			}
//		}
//	}
	
//	private static void addPoint(PointDatabaseObject point, LocationDatabaseObject location, int coordinateX, int coordinateY) {
//		entityManager.getTransaction().begin();
//		point = new PointDatabaseObject();
//		point.setCoordinateX(coordinateX);
//		point.setCoordinateY(coordinateY);
//		point.setLocation(location);
//		entityManager.persist(point);
//		entityManager.merge(location);
//		entityManager.getTransaction().commit();
//	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
				
		//create DataType Vd2_3MapData
		entityManager.getTransaction().begin();
		DataTypeDatabaseObject dataTypeDO1 =  new DataTypeDatabaseObject();
		dataTypeDO1.setAlgorithms(new ArrayList<AlgorithmDatabaseObject>());
		dataTypeDO1.setDataTypeClassName(Vd2_3MapData.class.getName());
		dataTypeDO1.setDataTypeName("Vd2_3MapData");
		entityManager.persist(dataTypeDO1);		
		entityManager.getTransaction().commit();
		
		//create DataType WifiMapData
		entityManager.getTransaction().begin();
		DataTypeDatabaseObject dataTypeDO2 =  new DataTypeDatabaseObject();
		ArrayList<AlgorithmDatabaseObject> listAlgorithmDO2 = new ArrayList<AlgorithmDatabaseObject>();
		dataTypeDO2.setAlgorithms(listAlgorithmDO2);
		dataTypeDO2.setDataTypeClassName(WifiMapData.class.getName());
		dataTypeDO2.setDataTypeName("WifiMapData");
		entityManager.persist(dataTypeDO2);
		entityManager.getTransaction().commit();

//		//create Algorithm AlgVd2_3
//		entityManager.getTransaction().begin();	
//		AlgorithmDatabaseObject algorithmDO = new AlgorithmDatabaseObject();
//		algorithmDO.setAlgorithmClassName(AlgVd2_3.ALGORITHM_CLASSNAME);
//		algorithmDO.setAlgorithmName(AlgVd2_3.ALGORITHM_NAME);
//		algorithmDO.setDataType(dataTypeDO1);
//		entityManager.persist(algorithmDO);
//		entityManager.merge(dataTypeDO1);
//		entityManager.getTransaction().commit();
		
//		PointDatabaseObject point01, point02, point03, point04;
//		int sizeCell = 15;
//		int sizeXFloor = 17;
//		int sizeYFloor = 6;
//		int numberFloor = 11;
		
		//create users and its locations
//		for (int i = 1; i < 15; i++) {
//			entityManager.getTransaction().begin();
//			UserDatabaseObject user = new UserDatabaseObject();
//			if (i < 10)
//				user.setUserName("group0" + i);
//			else
//				user.setUserName("group" + i);
//			user.setUserType(UserDatabaseObject.USER_TYPE_MAP_BUILDER);
//			user.setPassword("demo124");
//			user.setLocations(new ArrayList<LocationDatabaseObject>());
//			entityManager.persist(user);
//			entityManager.getTransaction().commit();
//			
////			entityManager.getTransaction().begin();
////			LocationDatabaseObject rootLocation = new LocationDatabaseObject();
////			rootLocation.setLocationDescription("Root Location");
////			rootLocation.setLocationName("Building I - HCMUS");
////			rootLocation.setContentZoom(1);
////			rootLocation.setUser(user);
////			entityManager.persist(rootLocation);
////			entityManager.merge(user);
////			entityManager.getTransaction().commit();
////			
////			point01 = new PointDatabaseObject();
////			addPoint(point01, rootLocation, 0, 0);
////			
////			point02 = new PointDatabaseObject();
////			addPoint(point02, rootLocation, 0, numberFloor*sizeYFloor*sizeCell);
////
////			point03 = new PointDatabaseObject();
////			addPoint(point03, rootLocation, sizeXFloor*sizeCell, numberFloor*sizeYFloor*sizeCell);
////
////			point04 = new PointDatabaseObject();
////			addPoint(point04, rootLocation, sizeXFloor*sizeCell, 0);
////			
////			entityManager.getTransaction().begin();
////			LocationDatabaseObject floor05Location = new LocationDatabaseObject();
////			floor05Location.setLocationName("floor05");
////			floor05Location.setContentZoom(1);
////			floor05Location.setUser(user);
////			floor05Location.setParentLocation(rootLocation);
////			entityManager.persist(floor05Location);
////			entityManager.merge(rootLocation);
////			entityManager.merge(user);
////			entityManager.getTransaction().commit();
////			
////			point01 = new PointDatabaseObject();
////			addPoint(point01, floor05Location, 0, (numberFloor - 5)*sizeYFloor*sizeCell);
////			
////			point02 = new PointDatabaseObject();
////			addPoint(point02, floor05Location, 0, (numberFloor - 4)*sizeYFloor*sizeCell);
////
////			point03 = new PointDatabaseObject();
////			addPoint(point03, floor05Location, sizeXFloor*sizeCell, (numberFloor - 4)*sizeYFloor*sizeCell);
////
////			point04 = new PointDatabaseObject();
////			addPoint(point04, floor05Location, sizeXFloor*sizeCell, (numberFloor - 5)*sizeYFloor*sizeCell);
////			
////			for (int j = 1; j < 59; j++) {
////				entityManager.getTransaction().begin();
////				LocationDatabaseObject cellLocation = new LocationDatabaseObject();
////				if (j < 10)
////					cellLocation.setLocationName("cell0" + j);
////				else
////					cellLocation.setLocationName("cell" + j);
////				cellLocation.setUser(user);
////				cellLocation.setContentZoom(1);
////				cellLocation.setParentLocation(floor05Location);
////				entityManager.persist(cellLocation);
////				entityManager.merge(floor05Location);
////				entityManager.merge(user);
////				entityManager.getTransaction().commit();
////			}
////			
////			entityManager.getTransaction().begin();
////			LocationDatabaseObject floor07Location = new LocationDatabaseObject();
////			floor07Location.setLocationName("floor07");
////			floor07Location.setUser(user);
////			floor07Location.setContentZoom(1);
////			floor07Location.setParentLocation(rootLocation);
////			entityManager.persist(floor07Location);
////			entityManager.merge(rootLocation);
////			entityManager.merge(user);
////			entityManager.getTransaction().commit();
////
////			point01 = new PointDatabaseObject();
////			addPoint(point01, floor07Location, 0, (numberFloor - 7)*sizeYFloor*sizeCell);
////			
////			point02 = new PointDatabaseObject();
////			addPoint(point02, floor07Location, 0, (numberFloor - 6)*sizeYFloor*sizeCell);
////
////			point03 = new PointDatabaseObject();
////			addPoint(point03, floor07Location, sizeXFloor*sizeCell, (numberFloor - 6)*sizeYFloor*sizeCell);
////
////			point04 = new PointDatabaseObject();
////			addPoint(point04, floor07Location, sizeXFloor*sizeCell, (numberFloor - 7)*sizeYFloor*sizeCell);
////			
////			for (int j = 1; j < 59; j++) {
////				entityManager.getTransaction().begin();
////				LocationDatabaseObject cellLocation = new LocationDatabaseObject();
////				if (j < 10)
////					cellLocation.setLocationName("cell0" + j);
////				else
////					cellLocation.setLocationName("cell" + j);
////				cellLocation.setUser(user);
////				cellLocation.setContentZoom(1);
////				cellLocation.setParentLocation(floor07Location);
////				entityManager.persist(cellLocation);
////				entityManager.merge(floor07Location);
////				entityManager.merge(user);
////				entityManager.getTransaction().commit();
////			}			
//		}
		
//		// Create Location and MACAddress
////		String rawDataDirPath = "E:/Users/Dinh/Desktop/Small Map VD2_3";
//
//		System.out.println("Creating Location and MacAddress...");
//
////		File rawDataDir = new File(rawDataDirPath);
//		
////		createDatabase(rawDataDir);

		entityManager.close();

		System.out.println("Done.");
	}
}
