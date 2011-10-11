package locationaware.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import locationaware.clientserver.MapData;
import locationaware.eventlistener.ChangeMacEventListener;
import locationaware.myevent.ChangeMacEvent;
import locationaware.protocol.Constant;
import locationaware.wifi.mapdata.Vd2_3MapData;
import locationaware.wifi.mapdata.WifiMapData;

/**
 * @author Dinh
 * Client program is responsible for sending the map data collected by client to server
 */
public class ClientMapDataSender {
	
	/**
	 * The address of server used for getting map data from client
	 */
	private String serverHost;
	
	/**
	 * port of server program will handle these map data
	 */
	private int port;
	
	/**
	 * true if user want replace all map data collected by old device (this old device have MAC which are different of the new one)
	 */
	private Boolean replaceRootMACIfDif = true;
	
	
	/**
	 * vector of listeners which listen events that MAC of current device is different with the device which was used to collect data
	 */
	private Vector<ChangeMacEventListener> vectorNewWifiSPEventListeners = new Vector<ChangeMacEventListener>();
	
	public void addChangeMacEventListener(ChangeMacEventListener listener) {
		vectorNewWifiSPEventListeners.add(listener);
	}
	
	public void removeChangeMacEventListener(ChangeMacEventListener listener) {
		vectorNewWifiSPEventListeners.remove(listener);
	}	
	
	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public String getServerHost() {
		return serverHost;
	}

	/**
	 * @param mapData : this map will be sent to server
	 * @return true if sending mapdata succeed, false otherwise
	 */
	public boolean sendMapData(MapData mapData) {
		Socket socket;
		try {
			socket = new Socket(serverHost, port);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			out.writeInt(Constant.CALL_SERVER_MAP_DATA);
			out.writeObject(mapData);
			
			if (mapData instanceof WifiMapData || mapData instanceof Vd2_3MapData) {
				Boolean isMacAddressChange = in.readBoolean();
				
				if (isMacAddressChange) {
					for (ChangeMacEventListener listener : vectorNewWifiSPEventListeners) {
						listener.handleEvent(new ChangeMacEvent(this));
					}
				}
				
				out.writeBoolean(replaceRootMACIfDif);
				out.flush();
			}
			
			Boolean isSuccess = in.readBoolean(); 
			
			out.close();
			in.close();
			socket.close();
			
			return isSuccess;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public void setReplaceRootMACIfDif(Boolean replaceRootMACIfDif) {
		this.replaceRootMACIfDif = replaceRootMACIfDif;
	}

	public Boolean getReplaceRootMACIfDif() {
		return replaceRootMACIfDif;
	}
	
}
