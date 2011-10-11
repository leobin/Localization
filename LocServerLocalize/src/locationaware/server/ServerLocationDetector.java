package locationaware.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

import localization.data.entity.LocationDatabaseObject;
import localization.data.entity.LocationMacAddressMappingPK;
import localization.data.entity.LocationMacaddressMappingDatabaseObject;
import locationaware.clientserver.MapData;
import locationaware.protocol.Constant;
import locationaware.wifi.convertAlg.LinearRegression;
import locationaware.wifi.mapdata.Vd2_3MapData;
import Jama.Matrix;

import com.localization.manager.LocationDataManagement;
import com.localization.manager.LocationMacaddressMappingDataManagement;
import com.localization.server.ServerAPI;

/**
 * @author Dinh
 * As localization user send a request (can be calibration request or localization request), this Runnable will handle that request
 */
public class ServerLocationDetector implements Runnable{

	private Socket client;
	
	public ServerLocationDetector(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(this.client.getOutputStream());
			in = new ObjectInputStream(this.client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			int callSwitch = in.readInt();
			switch (callSwitch) {
			case Constant.CALL_SERVER_GPS:
				new ServerLocationGPSDetector().run(in, out);
				break;

			case Constant.CALL_SERVER_WIFI:
				new ServerLocationWifiDetector().run(in, out);
				break;
				
			case Constant.CALL_SERVER_CALIRATE:
				try {
					@SuppressWarnings("unchecked")
					ArrayList<Vd2_3MapData> rawListVd2_3MapDataClient = (ArrayList<Vd2_3MapData>) in.readObject();
					
					TreeMap<Long, ArrayList<Vd2_3MapData>> listVd2_3MapDataServer = new TreeMap<Long, ArrayList<Vd2_3MapData>>();
					TreeMap<Long, ArrayList<Vd2_3MapData>> listVd2_3MapDataClient = new TreeMap<Long, ArrayList<Vd2_3MapData>>();
					
					for (Vd2_3MapData vd2_3MapDataClient : rawListVd2_3MapDataClient) {
						LocationDatabaseObject locationDatabaseObject = LocationDataManagement.getLocationByID(vd2_3MapDataClient.getLocationId());
						Vd2_3MapData vd2_3MapDataServer =  (Vd2_3MapData) MapData.readMapData(locationDatabaseObject.getLocationDataPath(ServerAPI.getDataTypeIdByDataTypeClassName(Vd2_3MapData.DATATYPE_CLASSNAME)));
						
						Long serverMAC = new Long(vd2_3MapDataServer.getScannerMAC());
						if (!listVd2_3MapDataServer.containsKey(serverMAC)) {
							listVd2_3MapDataServer.put(serverMAC, new ArrayList<Vd2_3MapData>());
							listVd2_3MapDataClient.put(serverMAC, new ArrayList<Vd2_3MapData>());
						}						
						
						listVd2_3MapDataServer.get(serverMAC).add(vd2_3MapDataServer);
						listVd2_3MapDataClient.get(serverMAC).add(vd2_3MapDataClient);

					}
					
					for (Long serverMAC : listVd2_3MapDataServer.keySet()) {
						Matrix b = (new LinearRegression()).calibrate(listVd2_3MapDataClient.get(serverMAC), listVd2_3MapDataServer.get(serverMAC));
						
						LocationMacAddressMappingPK temp = new LocationMacAddressMappingPK();
						temp.setBuildMacAddressId("" + serverMAC.longValue());
						temp.setClientMacAddressId("" + rawListVd2_3MapDataClient.get(0).getScannerMAC());
						
						LocationMacaddressMappingDatabaseObject temp1 = LocationMacaddressMappingDataManagement.getLocationMacaddressMappingByID(temp);
						if (temp1 == null) {
							temp1 = new LocationMacaddressMappingDatabaseObject();
							temp1.setId(temp);
							temp1.setA(b.get(1, 0));
							temp1.setB(b.get(0, 0));
							LocationMacaddressMappingDataManagement.addNewLocationMacaddressMapping(temp1);
						} else {
							temp1.setA(b.get(1, 0));
							temp1.setB(b.get(0, 0));
							LocationMacaddressMappingDataManagement.updateLocationMacaddressMapping(temp1);
						}						
					}
					
					out.writeBoolean(true);
					out.flush();
					
//					out.close();
//					in.close();
//					client.close();
					return;
				} catch (IOException e) {
					System.out.println("in or out failed");
					return;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				out.writeBoolean(false);
				out.flush();

				break;
				
			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
