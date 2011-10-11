package locationaware.apps;

import locationaware.server.ServerLocationAware;

import com.localization.server.ServerConfig;

/**
 * @author Dinh
 * This is server program that handle all requests of localization of client
 */
public class ServerLocalization {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int portMapUser = 10001;
//		if (args.length == 2) {
//			ServerConfig.dirData = args[0];
//			portMapUser = Integer.parseInt(args[1]);
//		} else {
//			System.out.println("Usage mapuser DirectoryMapData port");
//			System.out.println("Example: ./mapuser /tmp 10001");
//			System.exit(-1);
//		}
//		
		//hack code
		ServerConfig.dirData = "/Users/leobin/Workspace/University/Data";
		System.out.println("Directory to save MapData is " + ServerConfig.dirData);
		System.out.println("port of map user server is " + portMapUser);

		
		ServerLocationAware server = new ServerLocationAware();		
		server.listenSocketMapUser(portMapUser);
	}

}
