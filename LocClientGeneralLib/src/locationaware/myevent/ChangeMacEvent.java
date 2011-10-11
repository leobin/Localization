package locationaware.myevent;

import java.util.EventObject;

/**
 * @author Dinh
 * The event that MAC of current device is different with the device which was used to collect data
 */
public class ChangeMacEvent extends EventObject{

	public ChangeMacEvent(Object source) {
		super(source);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4806924616911507752L;

}
