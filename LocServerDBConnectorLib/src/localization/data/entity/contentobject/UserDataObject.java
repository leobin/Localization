package localization.data.entity.contentobject;

import java.io.Serializable;
import java.util.List;

import com.localization.manager.LocationDataManagement;

import localization.data.entity.UserDatabaseObject;

/**
 * The persistent class for the user database table.
 * 
 */
public class UserDataObject implements Serializable {
	public static final int USER_TYPE_ADMIN = 0;
	public static final int USER_TYPE_MAP_BUILDER = 1;
	public static final int USER_TYPE_ALGORITHM_CREATOR = 2;
	public static final int USER_TYPE_CLIENT = 3;
	public static final int USER_TYPE_ALL = -1;
	private static final long serialVersionUID = 1L;

	public static final String[] LIST_USER_TYPE_NAME = { "Admin", "Mapbuilder",
			"Algorithm Creator", "Client" };

	private String userId = null;

	private String password;

	private String userName;

	private int userType;

	private List<LocationDataObject> locations;

	public UserDataObject() {
	}

	public UserDataObject(UserDatabaseObject user) {
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.userType = user.getUserType();
		this.locations = LocationDataManagement.createListObject(user
				.getLocations());
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
		return this.locations;
	}

	public void setLocations(List<LocationDataObject> locations) {
		this.locations = locations;
	}

}