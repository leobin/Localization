package locationaware.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import localization.data.entity.AlgorithmDatabaseObject;
import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.LocationMacAddressMappingPK;
import localization.data.entity.LocationMacaddressMappingDatabaseObject;
import locationaware.Algorithm;
import locationaware.ResultOfDetection;
import locationaware.clientserver.Location;
import locationaware.wifi.AccessPoint;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.convertAlg.LinearRegression;
import Jama.Matrix;

import com.localization.manager.LocationMacaddressMappingDataManagement;
import com.localization.server.ServerAPI;

/**
 * @author Dinh
 * This is server program that will handle the localization (using wifi signal) requests of user
 */
public class ServerLocationWifiDetector {

	private Vector<ScanningPoint> listSPForDetection = new Vector<ScanningPoint>();
	private int maxSizeOfList = 10;
	Boolean convert = false;
	Long clientMACAdd = null;
	private TreeMap<LocationDatabaseObject, Algorithm> listSolver = null;
	private TreeMap<LocationDatabaseObject, Matrix> listConvertParam = null;
	private TreeMap<Location, ArrayList<Location>> listDetectedLocation = null;
	ArrayList<Long> listMACAddress = null;

	public static ArrayList<Long> getListMACAddress(
			Vector<ScanningPoint> listScanningPoints) {
		ArrayList<Long> listMACAddress = new ArrayList<Long>();

		for (ScanningPoint scanningPoint : listScanningPoints) {
			for (AccessPoint accessPoint : scanningPoint.getApsList()) {
				if (!listMACAddress.contains(accessPoint.getMACAddress())) {
					listMACAddress.add(accessPoint.getMACAddress());
				}
			}
		}

		return listMACAddress;
	}

	public Algorithm createSolver(String algorithmClassName) {
		try {
			System.out.println(algorithmClassName);
			Algorithm solver = (Algorithm) Class.forName(algorithmClassName).newInstance();
			System.out.println(solver.getAlgorithmClassName());
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

	public ServerLocationWifiDetector() {
	}

	public int getMaxSizeOfList() {
		return maxSizeOfList;
	}

	public void setMaxSizeOfList(int maxSizeOfList) {
		this.maxSizeOfList = maxSizeOfList;
	}

	private boolean updateListSolver() {
		if (listMACAddress != null) {
			ArrayList<Long> newListMACAddress = getListMACAddress(this.listSPForDetection);
			boolean hasNewMACAddress = false;
			
			for (Long newMacAddress : newListMACAddress) {
				if (!listMACAddress.contains(newMacAddress)) {
					hasNewMACAddress = true;
					break;
				}
			}
			
			if (!hasNewMACAddress) {
				return false;
			} else {
				listMACAddress = newListMACAddress;
			}
		} else {
			listMACAddress = getListMACAddress(this.listSPForDetection);
		}
		
		ArrayList<LocationDatabaseObject> listLocations = ServerAPI
				.searchLocationByMacAddress(listMACAddress.toArray(new Long[listMACAddress.size()]));

		// for (LocationDatabaseObject locationDO : listLocations) {
		// System.out.println(locationDO.getLocationName());
		// }

		ArrayList<LocationDatabaseObject> listRootLocation = ServerAPI
				.getRootLocation(listLocations);

		listSolver = new TreeMap<LocationDatabaseObject, Algorithm>();

		for (LocationDatabaseObject rootLocation : listRootLocation) {
			AlgorithmDatabaseObject algorithmDO = rootLocation.getAlgorithm();
			System.out.println(algorithmDO.getAlgorithmClassName());
			System.out.println(rootLocation.getLocationName());
			Algorithm solver = createSolver(algorithmDO.getAlgorithmClassName());

			listSolver.put(rootLocation, solver);
		}

		for (LocationDatabaseObject locationDO : listLocations) {
			LocationDatabaseObject rootLocation = locationDO;
			while (rootLocation.getParentLocation() != null) {
				rootLocation = rootLocation.getParentLocation();
			}

			listSolver.get(rootLocation)
					.addLocation(locationDO);
			// System.out.println(locationDO.getLocationName());
		}
		
		return true;
	}

	private void updateListConvertParam() {
		for (LocationDatabaseObject rootLocation : listSolver.keySet()) {
			if (!listConvertParam.keySet().contains(rootLocation)) {				
				LocationMacAddressMappingPK temp = new LocationMacAddressMappingPK();
				temp.setBuildMacAddressId("" + rootLocation.getMacaddressIDForBuildDevice());
				temp.setClientMacAddressId("" + clientMACAdd);
				LocationMacaddressMappingDatabaseObject locationMacaddressMappingByID = LocationMacaddressMappingDataManagement.getLocationMacaddressMappingByID(temp);
				
				if (locationMacaddressMappingByID != null) {
					Matrix b = new Matrix(2, 1);

					b.set(0, 0,locationMacaddressMappingByID.getB());
					b.set(1, 0,locationMacaddressMappingByID.getA());
					
					listConvertParam.put(rootLocation, b);
					
				} else {
					listConvertParam.put(rootLocation, null);
				}
				
			}
		}
	}

	public void run(ObjectInputStream in, ObjectOutputStream out) {
		ScanningPoint scanningPoint = null;
		ResultOfDetection resultOfDetection;
		Boolean detectAtClient = null;

		try {
			convert = (Boolean) in.readObject();

			if (convert) {
				clientMACAdd = (Long) in.readObject();
				listConvertParam = new TreeMap<LocationDatabaseObject, Matrix>();
			}

			detectAtClient = (Boolean) in.readObject();
		} catch (IOException e) {
			System.out.println("in or out failed");
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (detectAtClient) {

		} else {

			try {
				Object object = in.readObject();
				while (object instanceof ScanningPoint) {
					scanningPoint = (ScanningPoint) object;
					System.out.println(scanningPoint);

					while (this.listSPForDetection.size() >= this.maxSizeOfList) {
						this.listSPForDetection.remove(0);
					}
					this.listSPForDetection.add(scanningPoint);

					if (this.listSPForDetection.size() == maxSizeOfList) {
						if (updateListSolver() && convert) {
							updateListConvertParam();
						}
						
						listDetectedLocation = new TreeMap<Location, ArrayList<Location>>();
						for (LocationDatabaseObject rootLocation : listSolver.keySet()) {

							if (convert) {
								LinearRegression linearRegression = new LinearRegression();
								for (ScanningPoint locationData : this.listSPForDetection) {
									ScanningPoint convertSP = locationData.clone();

									if (listConvertParam.get(rootLocation) != null) {
										for (AccessPoint accessPoint : convertSP.getApsList()) {
											accessPoint.setSignalStrength(linearRegression.convert(accessPoint.getSignalStrength(), listConvertParam.get(rootLocation)));
										}
									}

									listSolver.get(rootLocation).feedLocationData(convertSP);
								}
							} else {

								for (ScanningPoint locationData : this.listSPForDetection) {
									listSolver.get(rootLocation).feedLocationData(locationData);
								}
							}

							resultOfDetection = listSolver.get(rootLocation).detectLocation();
							Location rootLocationData = rootLocation.createLocation();
							
							this.listDetectedLocation.put(rootLocationData, new ArrayList<Location>());

							if (resultOfDetection.getId() == ResultOfDetection.INSIDE_LOCATION) {
								for (String locationId : resultOfDetection.getListLocationID()) {
									Location location = ServerAPI.searchLocationByLocationId(locationId).createLocation();
									this.listDetectedLocation.get(rootLocationData).add(location);
								}
								
								if (convert && listConvertParam.get(rootLocation) == null) {
									this.listDetectedLocation.get(rootLocationData).add(null);
								}
							} else {
							}
						}

						System.out.println(this.listDetectedLocation);
						out.writeObject(this.listDetectedLocation);
						out.flush();
					}

					object = in.readObject();
				}
				
				out.writeObject(new String());
			} catch (IOException e) {
				//System.out.println("Read failed");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
