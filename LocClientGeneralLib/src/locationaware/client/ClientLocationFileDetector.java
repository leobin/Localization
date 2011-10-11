package locationaware.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Vector;

import locationaware.clientserver.Location;
import locationaware.eventlistener.NewResultComingEventListener;
import locationaware.myevent.NewResultComingEvent;
import locationaware.protocol.Constant;
import locationaware.wifi.ScanningPoint;

/**
 * @author Dinh
 *
 *	This class used to simulate detection of client. 
 *  It will send scanning points in a list of scanning points which have collected in prior.
 *  Server will use information for these scanning points to locate position where they have collected.
 */
public class ClientLocationFileDetector {
	/**
	 * Thread is responsible for receiving results returned by server.
	 */
	private Thread receivingThread = null;
	
	
	/**
	 * True if this instance is in process of localization, false otherwise
	 */
	private boolean isActive = false;
	
	/**
	 * this timer used to schedule the task sending scanning points to server 
	 */
	private Timer timer;
	
	/**
	 * this task is sending scanning points to server
	 */
	private TimerSendSPsTask timerTask;
	
	/**
	 * list of scanning points will be sent to server for localization
	 */
	private ArrayList<ScanningPoint> listScanningPoints = new ArrayList<ScanningPoint>();
	
	/**
	 * MACaddress of device which have collect the list of scanning points
	 */
	private Long scannerMac;
	
	private int spIndex;

	/**
	 * The confidence level of localization
	 */
	private double confidence = 100.0;
	
	/**
	 * The frequency of sending scanning points to server
	 */
	private double frequency = 1.0;
	
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
	 * true if user want use wifi signal to localize, false otherwise
	 */
	private boolean useWifi = true;
	
	
	/**
	 * true if user want use GPS signal to localize, false otherwise
	 */
	private boolean useGPS = true;
	
	/**
	 * true if user want use 3G signal to localize, false otherwise
	 */
	private boolean use3G = true;
	
	/**
	 * true if users want the process of localization run on their devices, false if they want this process run on server
	 */
	private Boolean detectAtClient = false;
	
	/**
	 * true if user want convert signal from their device to signal of device used for collecting data map of server, before localizing
	 * false otherwise.
	 */
	private Boolean convert = false;
	
	/**
	 * these are results returned by server
	 */
	private TreeMap<Location, ArrayList<Location>> listDetectedLocation;
	
	/**
	 * vector of listeners which listen the action events that server return a new result of localization
	 */
	private Vector<NewResultComingEventListener> vectorNewResultComingEventListeners = new Vector<NewResultComingEventListener>();

	public void addNewResultComingEventListener(
			NewResultComingEventListener listener) {
		vectorNewResultComingEventListeners.add(listener);
	}

	public void removeNewResultComingEventListener(
			NewResultComingEventListener listener) {
		vectorNewResultComingEventListeners.remove(listener);
	}

	public boolean isActive() {
		return isActive;
	}

	public Boolean getConvert() {
		return convert;
	}

	public void setConvert(Boolean convert) {
		this.convert = convert;
	}

	private boolean detectByWifi() {
		if (this.isActive) {
			System.out.println("=> Detection thread is running.");
		}

		try {
			// Create socket connection
			this.socket = new Socket(serverHost, port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());

			this.out.writeInt(Constant.CALL_SERVER_WIFI);

			this.out.writeObject(this.convert);

			if (convert) {
				this.out.writeObject(this.scannerMac);
			}

			this.out.writeObject(detectAtClient);

			if (detectAtClient) {

			} else {

			}

		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + serverHost);
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("No I/O");
			e.printStackTrace();
			return false;
		}

		System.out.println("==> Start detect location.");
		this.isActive = true;
		this.spIndex = 0;
		timer = new Timer();
		timerTask = new TimerSendSPsTask();
		this.timer.schedule(timerTask, (long)(1000/frequency), (long)(1000/frequency));

		this.receivingThread = new Thread(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				try {
					Object object = in.readObject();
					while (object instanceof TreeMap<?, ?>) {
						listDetectedLocation = (TreeMap<Location, ArrayList<Location>>) object;

						for (Location root : listDetectedLocation.keySet()) {

							System.out.print(root.getLocationName() + ": ");

							ArrayList<Location> lisLocations = listDetectedLocation
									.get(root);
							for (Location location : lisLocations) {
								if (location == null)
									continue;
								System.out.print(location.getLocationName()
										+ "; ");
							}

							System.out.println();
						}

						for (int i = 0; i < vectorNewResultComingEventListeners
								.size(); ++i) {
							vectorNewResultComingEventListeners.get(i)
									.handleEvent(
											new NewResultComingEvent(
													new Object()));
						}

						object = in.readObject();
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

	private void stopDetectByWifi() {
		if (!isActive) {
			System.out.println("==> Detection is already stopped.");
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					timerTask.cancel();
					timer.purge();
					timer.cancel();
					
					out.flush();
					out.writeObject(new String());

					if (receivingThread != null && receivingThread.isAlive()) {
						receivingThread.join();
					}

					out.close();
					in.close();
					socket.close();

					receivingThread = null;
					socket = null;

					isActive = false;
					System.out.println("==> Stop detect location.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	/**
	 * @param listScanningPoints : list of scanning points will be sent to server for localization
	 * @param scannerMac : MACaddress of device collecting the listScanningPoints
	 * @return true if starting succeed, false otherwise
	 */
	public boolean startDetectLocation(ArrayList<ScanningPoint> listScanningPoints, Long scannerMac) {
		this.listScanningPoints = listScanningPoints;
		this.scannerMac = scannerMac;
		return detectByWifi();
	}

	/**
	 * Stop localization
	 */
	public void stopDectectLocation() {
		stopDetectByWifi();
	}

	public TreeMap<Location, ArrayList<Location>> getListDetectedLocation() {
		return this.listDetectedLocation;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
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

	public void setUseWifi(boolean useWifi) {
		this.useWifi = useWifi;
	}

	public boolean isUseWifi() {
		return useWifi;
	}

	public void setUseGPS(boolean useGPS) {
		this.useGPS = useGPS;
	}

	public boolean isUseGPS() {
		return useGPS;
	}

	public void setUse3G(boolean use3G) {
		this.use3G = use3G;
	}

	public boolean isUse3G() {
		return use3G;
	}

	public void setDetectAtClient(boolean detectAtClient) {
		this.detectAtClient = detectAtClient;
	}

	public boolean isDetectAtClient() {
		return detectAtClient;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setListScanningPoints(ArrayList<ScanningPoint> listScanningPoints) {
		this.listScanningPoints = listScanningPoints;
	}

	public ArrayList<ScanningPoint> getListScanningPoints() {
		return listScanningPoints;
	}

	public void setScannerMac(Long scannerMac) {
		this.scannerMac = scannerMac;
	}

	public Long getScannerMac() {
		return scannerMac;
	}

	class TimerSendSPsTask extends TimerTask {

		@Override
		public void run() {
			if (spIndex >= listScanningPoints.size()) {
				stopDectectLocation();
				return;
			}
			
			ScanningPoint scanningPoint = listScanningPoints.get(spIndex);

			try {
				System.out.println(scanningPoint);
				out.writeObject(scanningPoint);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			spIndex++;
		}
		
	}

}
