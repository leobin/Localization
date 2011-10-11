package locationaware.apps;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import localization.data.entity.contentobject.LocationDataObject;

import com.localization.server.ServerAPI;

public class GetDataFromServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		UserDataObject user = (new ServerAPI()).checkLoginUser("nhu", "demo124");
		//List<LocationDataObject> locationList = user.getLocations();
		LocationDataObject location = (new ServerAPI()).searchLocationByLocationId(484 + "");
		
        FileOutputStream fileOutputStream = new FileOutputStream("E:/Users/Dinh/Desktop/nhu.location");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                fileOutputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                bufferedOutputStream);
        objectOutputStream.writeObject(location);
        objectOutputStream.close();

	}

}
