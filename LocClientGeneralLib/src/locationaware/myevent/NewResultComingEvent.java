package locationaware.myevent;

import java.util.EventObject;

/**
 * @author Dinh
 * The event that server return a new result of localization
 */
public class NewResultComingEvent extends EventObject {

	public NewResultComingEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2403517278828923446L;

}
