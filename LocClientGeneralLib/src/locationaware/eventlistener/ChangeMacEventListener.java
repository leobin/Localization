package locationaware.eventlistener;

import java.util.EventListener;

import locationaware.myevent.ChangeMacEvent;

/**
 * @author Dinh
 * This is interface of listeners which listen events that MAC of current device is different with the device which was used to collect data
 */
public interface ChangeMacEventListener extends EventListener{
	public abstract void handleEvent(ChangeMacEvent evt);
}
