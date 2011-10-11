package locationaware.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import locationaware.clientserver.Location;
import locationaware.eventlistener.NewResultComingEventListener;
import locationaware.gps.GPSCoordinate;
import locationaware.myevent.NewResultComingEvent;
import locationaware.protocol.Constant;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * @author Dinh
 * This is client program using GPS signal to locate position.
 */
public class ClientLocationGPSDetector {
	/**
	 * Android activity that contains this program
	 */
	private Activity activity;
	
    /**
     * The manager location of Android OS 
     */
    private LocationManager locationManager;
    
    
	/**
	 * This listener listens events that the location of android device changes
	 */
	private MyLocationListener locationListener;
	
	/**
	 * Thread is responsible for receiving results returned by server.
	 */
	private Thread receivingThread = null;
	
	/**
	 * True if this instance is in process of localization, false otherwise
	 */
	private boolean isActive = false;
	
	/**
	 * The confidence level of localization
	 */
	private double confidence = 100.0;
	
	/**
	 * The address of server used for localization
	 */
	private String serverHost;
	
	/**
	 * port of server program will handle localization request of client
	 */
	private int port;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	/**
	 * these are results returned by server
	 */
	private TreeMap<String, ArrayList<Location>> listDetectedLocation;	
	
	/**
	 * vector of listeners which listen the action events that server return a new result of localization
	 */
	private Vector<NewResultComingEventListener> vectorNewResultComingEventListeners = new Vector<NewResultComingEventListener>();			
	
	public ClientLocationGPSDetector(Activity activity) {
		this.activity = activity;
	}
	
	public void addNewResultComingEventListener(NewResultComingEventListener listener) {
		vectorNewResultComingEventListeners.add(listener);
	}
	
	public void removeNewResultComingEventListener(NewResultComingEventListener listener) {
		vectorNewResultComingEventListeners.remove(listener);
	}	
	
	public boolean isActive() {
		return isActive;
	}
		
	public boolean startDetectLocation() {
		if (this.isActive) {
			System.out.println("=> Detection thread is running.");
		}
		
		try {
			// Create socket connection
			this.socket = new Socket(serverHost, port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
			this.out.writeInt(Constant.CALL_SERVER_GPS);
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + serverHost);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("No I/O");
			e.printStackTrace();
			return false;
		}
		
        locationManager = (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);    
        
        locationListener = new MyLocationListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = locationManager.getBestProvider(criteria, true);

        locationManager.requestLocationUpdates(
                provider, 
                0, 
                0, 
                locationListener);

		
		System.out.println("==> Start detect location.");
		this.isActive = true;
		
		this.receivingThread = new Thread(new Runnable() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					while (true) {
						listDetectedLocation = (TreeMap<String, ArrayList<Location>>) in.readObject();
						
						for (String root : listDetectedLocation.keySet()) {
							
							System.out.print(root + ": ");
							
							ArrayList<Location> lisLocations = listDetectedLocation.get(root);
							for (Location location : lisLocations ) {
								System.out.print(location.getLocationName() + "; ");
							}
							
							System.out.println();
						}
						
						for (int i = 0; i < vectorNewResultComingEventListeners.size(); ++i) {
							vectorNewResultComingEventListeners.get(i).handleEvent(new NewResultComingEvent(new Object()));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		this.receivingThread.setDaemon(true);
		this.receivingThread.start();
		
		return true;
	}
	
	public void stopDectectLocation() {
		if (!isActive) {
			System.out.println("==> Detection is already stopped.");
			return;
		}
		
		try {
			this.out.writeObject(new String());
			this.out.close();
			this.in.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		if (this.receivingThread != null && this.receivingThread.isAlive()) {
//			try {
				this.receivingThread.interrupt();
//				this.receivingThread.join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
		}
		
		this.receivingThread = null;
		this.socket = null;
		this.isActive = false;
		System.out.println("==> Stop detect location.");
	}
	
	public TreeMap<String, ArrayList<Location>> getListDetectedLocation() {
		return this.listDetectedLocation;
	}
		
	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
		
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getConfidence() {
		return confidence;
	}
	
	private class MyLocationListener implements LocationListener {

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLocationChanged(android.location.Location location) {
			// TODO Auto-generated method stub
			GPSCoordinate gpsCoordinate = new GPSCoordinate(location.getAltitude(), location.getLatitude(), location.getLongitude());
			try {
				out.writeObject(gpsCoordinate);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


}
