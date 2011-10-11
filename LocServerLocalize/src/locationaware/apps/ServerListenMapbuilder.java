		package locationaware.apps;

import locationaware.server.ServerLocationAware;

import com.localization.server.ServerConfig;

/**
 * @author Dinh
 * This is the server program that handle all requests of mapbuilder
 */
public class ServerListenMapbuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int portCallFunction = 10002;
//		if (args.length == 2) {
//			ServerConfig.dirData = args[0];
//			portCallFunction = Integer.parseInt(args[1]);
//		} else {
//			System.out.println("Usage mapbuilder DirectoryMapData port");
//			System.out.println("Example: ./mapbuilder /tmp 10002");
//			System.exit(-1);
//		}
		
		//hack code
		ServerConfig.dirData = "/Users/leobin/Workspace/University/Data";
		System.out.println("Directory to save MapData is " + ServerConfig.dirData);
		System.out.println("port of map builder server is " + portCallFunction);

		ServerLocationAware server = new ServerLocationAware();
		server.listenMapBuilderServer(portCallFunction);
	}

}
