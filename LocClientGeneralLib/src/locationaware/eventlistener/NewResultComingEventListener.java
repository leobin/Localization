package locationaware.eventlistener;

import java.util.EventListener;

import locationaware.myevent.NewResultComingEvent;

/**
 * @author Dinh
 * This is interface of EventListeners that listen the action events that server return a new result of localization
 */
public interface NewResultComingEventListener extends EventListener{
	public abstract void handleEvent(NewResultComingEvent evt);
}
