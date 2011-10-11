package localization.data.entity.contentobject;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the user database table.
 * 
 */
public class UserDataObject implements Serializable{
private static final long serialVersionUID = 1L;

    public static final int USER_TYPE_ADMIN = 0;
    public static final int USER_TYPE_MAP_BUILDER = 1;
    public static final int USER_TYPE_ALGORITHM_CREATOR = 2;
    public static final int USER_TYPE_CLIENT = 3;
    public static final int USER_TYPE_ALL = -1;
    public static final String[] LIST_USER_TYPE_NAME = {"Admin", "Mapbuilder", "Algorithm Creator", "Client"};
    private String userId;
    private String password;
    private String userName;
    private int userType;
    // bi-directional many-to-one association to Location
    private List<LocationDataObject> locations;

    public UserDataObject() {
    }

    public String toString() {
        return this.userName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return this.userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<LocationDataObject> getLocations() {
        return (List<LocationDataObject>) CallServerFunction.callServerObjectFunction(this.userId, ObjectMapping.USER_OBJECT, ObjectMapping.USER_GET_LOCATIONS, null);

    }

    public void setLocations(List<LocationDataObject> locations) {
        CallServerFunction.callServerObjectFunction(this.userId, ObjectMapping.USER_OBJECT, ObjectMapping.USER_SET_LOCATIONS, locations);
    }

    public void addLocation(String locationID) {
        CallServerFunction.callServerObjectFunction(this.userId, ObjectMapping.USER_OBJECT, ObjectMapping.USER_ADD_LOCATION, locationID);
    }

    public void removeLocation(String locationID) {
        CallServerFunction.callServerObjectFunction(this.userId, ObjectMapping.USER_OBJECT, ObjectMapping.USER_REMOVE_LOCATION, locationID);
    }
}
