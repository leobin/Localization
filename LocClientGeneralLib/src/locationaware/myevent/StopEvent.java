/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware.myevent;

import java.util.EventObject;

/**
 * This is the event which will be sent to ScanningThread to stop it. 
 * @author Xuan Nam
 */
@SuppressWarnings("serial")
public class StopEvent extends EventObject {
    public static final int MYEVENT_STOP = 9001;

    public StopEvent(Object source) {
        super(source);
//        System.out.println("StopEvent is created.");
    }
}


