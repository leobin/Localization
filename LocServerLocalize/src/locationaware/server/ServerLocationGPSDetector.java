package locationaware.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;

import localization.data.entity.LocationDatabaseObject;
import locationaware.Algorithm;
import locationaware.ResultOfDetection;
import locationaware.clientserver.Location;
import locationaware.clientserver.MapData;
import locationaware.gps.GPSCoordinate;
import locationaware.gps.algorithm.AlgGPS;
import locationaware.gps.mapdata.GPSMapData;

import com.localization.manager.LocationDataManagement;
import com.localization.server.ServerAPI;

/**
 * @author Dinh
 * This is server program that will handle the localization (using GPS signal) requests of user
 */
public class ServerLocationGPSDetector {
	/**
	 * current GPS coordinate of client
	 */
	private GPSCoordinate gpsCoordinateForDetection;

	/**
	 * mapping root location with its algorithm (solver) for localization 
	 */
	private TreeMap<String, Algorithm> listSolver = null;
	
	/**
	 * mapping root location with list of candidate locations (results) of user
	 */
	private TreeMap<String, ArrayList<Location>> listDetectedLocation = null;

	public Algorithm createSolver(String algorithmClassName) {
		try {
			Algorithm solver = (Algorithm) Class.forName(algorithmClassName)
					.newInstance();
			return solver;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ServerLocationGPSDetector() {
		createListSolver();
	}

	public void createListSolver() {
		ArrayList<LocationDatabaseObject> listRootLocations = LocationDataManagement
				.getAllRootLocation();
		listSolver = new TreeMap<String, Algorithm>();

		for (LocationDatabaseObject rootLocationDO : listRootLocations) {
			AlgGPS algGPS = new AlgGPS();

			ArrayList<LocationDatabaseObject> childLocationDOs = new ArrayList<LocationDatabaseObject>();
			childLocationDOs.add(rootLocationDO);
			algGPS.addLocation(rootLocationDO);

			int i = 0;
			while (i < childLocationDOs.size()) {
				for (LocationDatabaseObject locationDO : childLocationDOs
						.get(i).getLocations()) {
					childLocationDOs.add(locationDO);
					algGPS.addLocation(locationDO);
				}
				i++;
			}

			listSolver.put(rootLocationDO.getLocationId(), algGPS);
		}
	}

	public boolean haveGPSMap(LocationDatabaseObject locationDO) {
		GPSMapData gpsMapData = (GPSMapData) MapData.readMapData(locationDO
				.getLocationDataPath(ServerAPI
						.getDataTypeIdByDataTypeClassName(GPSMapData.class
								.getName())));
		return (gpsMapData != null);
	}

	// private void updateListSolver() {
	//
	// ArrayList<LocationDatabaseObject> listRootLocation = ServerAPI
	// .getlistRootLocations();
	//
	// listSolver = new TreeMap<String, Algorithm>();
	//
	// for (LocationDatabaseObject rootLocation : listRootLocation) {
	// AlgorithmDatabaseObject algorithmDO = rootLocation.getAlgorithm();
	//
	// Algorithm solver = createSolver(algorithmDO.getAlgorithmClassName());
	//
	// listSolver.put(rootLocation.getLocationId(), solver);
	// }
	//
	// for (LocationDatabaseObject locationDO : listLocations) {
	// LocationDatabaseObject rootLocation = locationDO;
	// while (rootLocation.getParentLocation() != null) {
	// rootLocation = rootLocation.getParentLocation();
	// }
	//
	// listSolver.get(rootLocation.getLocationId()).addLocation(locationDO);
	// // System.out.println(locationDO.getLocationName());
	// }
	// }

	public void run(ObjectInputStream in, ObjectOutputStream out) {
		ResultOfDetection resultOfDetection;

		try {
			Object object = in.readObject();
			while (object instanceof GPSCoordinate) {
				gpsCoordinateForDetection = (GPSCoordinate) object;
				System.out.println(gpsCoordinateForDetection);

				listDetectedLocation = new TreeMap<String, ArrayList<Location>>();

				for (String rootLocationID : listSolver.keySet()) {

					listSolver.get(rootLocationID).feedLocationData(gpsCoordinateForDetection);
					resultOfDetection = listSolver.get(rootLocationID)
							.detectLocation();
					this.listDetectedLocation.put(rootLocationID,
							new ArrayList<Location>());
					if (resultOfDetection.getId() == ResultOfDetection.INSIDE_LOCATION) {
						for (String locationId : resultOfDetection
								.getListLocationID()) {
							Location location = 
									ServerAPI
											.searchLocationByLocationId(locationId).createLocation();
							this.listDetectedLocation.get(rootLocationID).add(
									location);
						}
					} else {
					}
				}

				System.out.println(this.listDetectedLocation);
				out.writeObject(this.listDetectedLocation);
				object = in.readObject();
			}

		} catch (IOException e) {
			System.out.println("Read failed");
			return;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
