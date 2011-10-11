/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.wifi;

import java.util.EventObject;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import locationaware.eventlistener.NewWifiSPEventListener;
import locationaware.myevent.NewWifiSPEvent;
import locationaware.myevent.StopEvent;

/**
 * @author Dinh
 * This thread is responsible for collecting wifi information of all AccessPoints the device can see.  
 */
public class ScanningThread extends Thread {
	/**
	 * the list of eventlisteners which listen the event that a new scanning point has already collected
	 */
	private Vector<NewWifiSPEventListener> vectorNewWifiSPEventListeners = new Vector<NewWifiSPEventListener>();
	
	/**
	 * use to receive stop event
	 */
	private LinkedBlockingQueue<EventObject> eventQueue = new LinkedBlockingQueue<EventObject>();
    
	
	/**
	 * The frequency of getting wifi information
	 */
	private double frequency = 1.0;

    private long sleepTime = 1000;
    
    public ScanningThread() {
    }
    
	public void addNewWifiSPEventListener(NewWifiSPEventListener listener) {
		vectorNewWifiSPEventListeners.add(listener);
	}
	
	public void removeNewWifiSPEventListener(NewWifiSPEventListener listener) {
		vectorNewWifiSPEventListeners.remove(listener);
	}	

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
        this.sleepTime = (long)(1000/frequency);
    }
    
	public LinkedBlockingQueue<EventObject> getEventQueue() {
        return eventQueue;
    }

	public void setEventQueue(LinkedBlockingQueue<EventObject> eventQueue) {
        this.eventQueue = eventQueue;
    }

	public void queueEvent(EventObject event) {
        synchronized (eventQueue) {
            // add an event to eventQueue
            eventQueue.offer(event);
            // Notify all thread that are waiting on this eventQueue
            eventQueue.notifyAll();
        }
    }

    @Override
    public void run() {
        EventObject evt;

        while (true) {
            try {
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            try {
                synchronized (eventQueue) {
                    evt = (EventObject) eventQueue.poll();
                }
                
                if (evt == null) {
                    ScanningPoint sp = WifiScanner.scanPointWifi();
//                	ScanningPoint sp = new ScanningPoint(5);
                    
                    if (sp != null) {
	                    //notify all listeners that we have new scanning points
	                	for (int i = 0; i < vectorNewWifiSPEventListeners.size(); i++) {
	                		vectorNewWifiSPEventListeners.get(i).handleEvent(new NewWifiSPEvent(sp));
	                	}
                    } else {
	                	for (int i = 0; i < vectorNewWifiSPEventListeners.size(); i++) {
	                		vectorNewWifiSPEventListeners.get(i).handleEvent(new NewWifiSPEvent(new Object()));
	                	}
                    }

                } else if (evt.getClass() == StopEvent.class) {
                    // thread stop.
                    this.interrupt();
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

}
