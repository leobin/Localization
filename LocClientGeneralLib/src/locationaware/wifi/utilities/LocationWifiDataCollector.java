package locationaware.wifi.utilities;

import java.io.File;
import java.util.Calendar;

import locationaware.clientserver.MapData;
import locationaware.eventlistener.NewWifiSPEventListener;
import locationaware.myevent.NewWifiSPEvent;
import locationaware.myevent.StopEvent;
import locationaware.wifi.OSDetector;
import locationaware.wifi.ScanningPoint;
import locationaware.wifi.ScanningThread;
import locationaware.wifi.mapdata.WifiMapData;

/**
 * @author Dinh
 * This collector is responsible for collecting wifi information, and store in a wifimapdata file
 */
public class LocationWifiDataCollector implements NewWifiSPEventListener {
	/**
	 * number of scanning points collected up to now
	 */
	private int numberOfSPs;
	private long startTimeInMillis;
	
	/**
	 * minimum time of this collection
	 */
	private long minTimeInMillis = Long.MAX_VALUE;
	
	/**
	 * minimum numbers of scanning points which we expects to get
	 */
	private int minNumberOfSPs = Integer.MAX_VALUE;
	
	/**
	 * set it true if you want append the collected information to the old wifimapdata file
	 */
	private boolean isAppend = false;
	
	/**
	 * it is true if the collector is in collecting process, false otherwise 
	 */
	private boolean isActive = false;
	
	/**
	 * the collected wifi information will be stored here before write to the file
	 */
	private WifiMapData wifiMapData;
	
	/**
	 * thread is responsible for get scanning points 
	 */
	private ScanningThread scanningThread = null;
	
	
	private Thread checkingStopConditionsThread = null;
	
	/**
	 * the file path you want to save wifimapdata
	 */
	private String saveDataFilePath;
	
	public WifiMapData getWifiMapData() {
		return wifiMapData;
	}

	public void setWifiMapData(WifiMapData wifiMapData) {
		this.wifiMapData = wifiMapData;
	}

	public void startCollecting(String saveDataFilePath, String locationId, String userId, boolean append, double frequency) {
		if (isActive) {
			System.out.println("==> Collecting thread is running.");
			return;
		}
		
		this.saveDataFilePath = saveDataFilePath;

		this.startTimeInMillis = Calendar.getInstance().getTimeInMillis();
		this.isAppend = append;
		boolean fileExist = saveDataFilePath != null && (new File(saveDataFilePath)).exists();

		if (isAppend == false || !fileExist) {
			wifiMapData = new WifiMapData();
			
			wifiMapData.setLocationId(locationId);
			wifiMapData.setOS(OSDetector.getOS());
			wifiMapData.setUserId(userId);
			wifiMapData.setFiltered(false);
			
		} else {
			wifiMapData = (WifiMapData) WifiMapData.readMapData(saveDataFilePath);
		}
		
		this.scanningThread = new ScanningThread();
		this.scanningThread.setDaemon(true);
		this.scanningThread.setFrequency(frequency);
		this.scanningThread.addNewWifiSPEventListener(this);
		
		System.out.println("==> Start collecting data.");
		this.isActive = true;
		this.numberOfSPs = 0;
		this.scanningThread.start();
		
		this.checkingStopConditionsThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if ((Calendar.getInstance().getTimeInMillis()- startTimeInMillis >= minTimeInMillis)
							&& (numberOfSPs >= minNumberOfSPs)) {
						stopCollecting();
						break;
					}
				}
			}
		});
		
		this.checkingStopConditionsThread.start();
	}
	
	public void stopCollecting() {
		if (!this.isActive) {
			System.out.println("==> Collecting thread is already stopped.");
		}

		if (this.scanningThread != null && this.scanningThread.isAlive()) {
			this.scanningThread.queueEvent(new StopEvent(new Object()));

			try {
				this.scanningThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Integer elapsedTime = (int) ((Calendar.getInstance()
					.getTimeInMillis() - this.startTimeInMillis) / 1000);
			wifiMapData.setDurationTimeInSeconds(elapsedTime);
			
			if (saveDataFilePath != null) {
				MapData.writeMapData(wifiMapData, saveDataFilePath);
			}
			
			// inform number of entry, just for testing.
			System.out
					.println("==> ScanningThread: " + numberOfSPs + " entry.");
			System.out.println("==> Scanning Thread interrupted.");
		}
		
		this.scanningThread = null;
		this.checkingStopConditionsThread = null;
		isActive = false;
		System.out.println("==> Stop collecting data.");
	}
	
	public ScanningThread getScanningThread() {
		return scanningThread;
	}

	public void setScanningThread(ScanningThread scanningThread) {
		this.scanningThread = scanningThread;
	}
	
    public boolean isAppend() {
		return isAppend;
	}

	public void setAppend(boolean isAppend) {
		this.isAppend = isAppend;
	}
	
    public int getNumberOfSPs() {
		return numberOfSPs;
	}
        
	public int getMinNumberOfSPs() {
		return minNumberOfSPs;
	}

	public void setMinNumberOfSPs(int numberOfSPs) {
		this.minNumberOfSPs = numberOfSPs;
	}

	public int getMinTimeInSecond() {
		return (int) (minTimeInMillis / 1000);
	}

	public void setMinTimeInSecond(int seconds) {
		this.minTimeInMillis = seconds * 1000;
	}
    
	public boolean isActive() {
		return isActive;
	}
	
	

	@Override
	public void handleEvent(NewWifiSPEvent evt) {
		Object object = evt.getSource();
		
		if (object instanceof ScanningPoint) {
			ScanningPoint scanningPoint = (ScanningPoint) object;
			
			if (scanningPoint.getApsList().size() > 0) {
				wifiMapData.getSpsList().add(scanningPoint);
				this.numberOfSPs++;
				System.out.println(scanningPoint.getApsList().size() + " APs were seen");
				System.out.println(scanningPoint.toString());
				System.out.println("Number of scanning points got: " + this.numberOfSPs);
				System.out.println();
			}
		} else {
			System.err.println("No scanning point ...");
		}
	}

}
