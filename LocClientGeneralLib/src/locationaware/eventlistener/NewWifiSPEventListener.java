package locationaware.eventlistener;

import java.util.EventListener;

import locationaware.myevent.NewWifiSPEvent;

/**
 * @author Dinh
 * This is interface of EventListeners which listen the events that a new scanning point has already collected
 */
public interface NewWifiSPEventListener extends EventListener{
	public abstract void handleEvent(NewWifiSPEvent evt);
}
