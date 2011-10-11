/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package locationaware;

import java.util.Vector;

/**
 *
 * @author Xuan Nam
 */
public class ResultOfDetection {

    public static final int OUT_OF_MAP = 1;
    public static final int INSIDE_LOCATION = 2;
    public static final int ERROR = 3;
    /**
     * the status of this result
     */
    private int id = -1;
    
    /**
     * error message if id == 3
     */
    private String message;
    
    
    /**
     * list of candidate locationID
     */
    private Vector<String> listLocationID = new Vector<String>();

    public ResultOfDetection() {
    }

    public ResultOfDetection(int id, Vector<String> listLocationID) {
        this.id = id;
        this.listLocationID.addAll(listLocationID);
    }
    
    public ResultOfDetection(int id, String message) {
    	this.id = id;
    	this.setMessage(message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector<String> getListLocationID() {
        return listLocationID;
    }

    public void setListLocationID(Vector<String> listLocationID) {
        this.listLocationID = listLocationID;
    }

    @Override
    public String toString() {
        String result = "";
        if (id == OUT_OF_MAP) {
            result += "OUT OF MAP";
        } else if (id == INSIDE_LOCATION) {
            result += "INSIDE MAP";
            if (listLocationID.size() > 0) {
                result += " - " + listLocationID;
            }
        } else if (id == ERROR) {
            result += "ERROR" + " - " + message;
        }

        return result;
    }

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
