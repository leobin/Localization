package locationaware.myevent;

import java.util.EventObject;

/**
 * @author Dinh
 * The event that a new scanning point has already collected
 */
public class NewWifiSPEvent extends EventObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2431417443258284456L;

	public NewWifiSPEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
