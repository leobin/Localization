package locationaware.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import locationaware.protocol.Constant;
import locationaware.wifi.mapdata.Vd2_3MapData;

/**
 * @author Dinh
 *
 * This class is used for sending a calibration request to the server
 */
public class ClientCalibration {
	/**
	 * Address of server
	 */
	private String serverHost;
	
	
	/**
	 * Port of server program which handle calibration request of client program
	 */
	private int port;
	
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
	 * This function used to calibrate
	 * 
	 * @param listVd2_3MapData: list of Vd2_3MapData used for calibration
	 * @return true if calibration succeed, false otherwise.
	 */
	public boolean calirate(ArrayList<Vd2_3MapData> listVd2_3MapData) {
		Socket socket;
		try {
			socket = new Socket(serverHost, port);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			out.writeInt(Constant.CALL_SERVER_CALIRATE);
			out.writeObject(listVd2_3MapData);
			
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

}
