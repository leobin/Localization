package localization.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.localization.manager.LocalizationDataManager;
import com.localization.manager.LocationDataManagement;
import com.localization.manager.MacAddressDataManagement;
import com.localization.manager.UserDataManagement;

import localization.data.entity.contentobject.UserDataObject;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
public class UserDatabaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int USER_TYPE_ADMIN = 0;
	public static final int USER_TYPE_MAP_BUILDER = 1;
	public static final int USER_TYPE_ALGORITHM_CREATOR = 2;
	public static final int USER_TYPE_CLIENT = 3;
	public static final int USER_TYPE_ALL = -1;

	public static final String[] LIST_USER_TYPE_NAME = { "Admin", "Mapbuilder",
			"Algorithm Creator", "Client" };

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private String userId;

	@Column(name = "password")
	private String password;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_type")
	private int userType;

	// bi-directional many-to-one association to Location
	@OneToMany(mappedBy = "user")
	private List<LocationDatabaseObject> locations;

	public UserDatabaseObject() {
	}

	public String toString() {
		return this.userName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
		UserDataManagement.updateUser(this);
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
		UserDataManagement.updateUser(this);
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
		UserDataManagement.updateUser(this);
	}

	public int getUserType() {
		return this.userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
		UserDataManagement.updateUser(this);
	}

	public List<LocationDatabaseObject> getLocations() {
		return this.locations;
	}

	public void setLocations(List<LocationDatabaseObject> locations) {
		this.locations = locations;
		UserDataManagement.updateUser(this);
	}

	public void update(UserDataObject user) {
		this.userName = user.getUserName();
		this.userType = user.getUserType();
		if (user.getPassword() != null && !user.getPassword().equals("")) {
			this.password = user.getPassword();
		}
		UserDataManagement.updateUser(this);
		
	}
	

	public UserDatabaseObject(UserDataObject user) {
		this.userName = user.getUserName();
		this.userType = user.getUserType();
		if (user.getPassword() != null && !user.getPassword().equals("")) {
			this.password = user.getPassword();
		}
	}


	public void removeLocation(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.remove(locationByID);		
		UserDataManagement.updateUser(this);
	}

	public void addLocation(String locationID) {
		LocationDatabaseObject locationByID = LocationDataManagement.getLocationByID(locationID);
		this.locations.add(locationByID);
		UserDataManagement.updateUser(this);
	}

}