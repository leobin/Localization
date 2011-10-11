package locationaware.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Dinh
 * This class hold the server socket for map user and map builder
 */
public class ServerLocationAware {
	ServerSocket serverForMapUser;
//	ServerSocket serverForMapData;
//	ServerSocket serverForClientCallObject;
//	ServerSocket serverForClientCallFunction;
//	ServerSocket serverForLocalizationUsedGPS;
	ServerSocket serverForMapbuilder;
	
	
//	public void listenSocketLocalizationUsedGPS(int port) {
//		try {
//			serverForLocalizationUsedGPS = new ServerSocket(port);
//		} catch (IOException e) {
//			System.out.println("Could not listen on port " + port);
//			System.exit(-1);
//		}
//		while (true) {
//			ServerLocationGPSDetector detector;
//			try {
//				detector = new ServerLocationGPSDetector(
//						serverForLocalizationUsedGPS.accept());
//				Thread detectingThread = new Thread(detector);
//				detectingThread.start();
//			} catch (IOException e) {
//				System.out.println("Accept failed: " + port);
//				System.exit(-1);
//			}
//		}
//	}

	public void listenSocketMapUser(int port) {
		try {
			serverForMapUser = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
			System.exit(-1);
		}
		while (true) {
			ServerLocationDetector detector;
			try {
				detector = new ServerLocationDetector(
						serverForMapUser.accept());
				Thread detectingThread = new Thread(detector);
				detectingThread.start();
			} catch (IOException e) {
				System.out.println("Accept failed: " + port);
				System.exit(-1);
			}
		}
	}

//	public void listenSocketMapBuilder(int port) {
//		try {
//			serverForMapData = new ServerSocket(port);
//		} catch (IOException e) {
//			System.out.println("Could not listen on port " + port);
//			System.exit(-1);
//		}
//		while (true) {
//			ServerMapDataReceiver receiver;
//			try {
//				receiver = new ServerMapDataReceiver(
//						serverForMapData.accept());
//				Thread receivingThread = new Thread(receiver);
//				receivingThread.start();
//			} catch (IOException e) {
//				System.out.println("Accept failed: " + port);
//				System.exit(-1);
//			}
//		}
//	}

//	public void listenSocketClientCallObject(int port) {
//		try {
//			serverForClientCallObject = new ServerSocket(port);
//		} catch (IOException e) {
//			System.out.println("Could not listen on port " + port);
//			System.exit(-1);
//		}
//		while (true) {
//			ServerHandlerClientCallObjectFunction handler;
//			try {
//				handler = new ServerHandlerClientCallObjectFunction(
//						serverForClientCallObject.accept());
//				Thread receivingThread = new Thread(handler);
//				receivingThread.start();
//			} catch (IOException e) {
//				System.out.println("Accept failed: " + port);
//				System.exit(-1);
//			}
//		}
//	}

//	public void listenSocketClientCallFunction(int port) {
//		try {
//			serverForClientCallFunction = new ServerSocket(port);
//		} catch (IOException e) {
//			System.out.println("Could not listen on port " + port);
//			System.exit(-1);
//		}
//		while (true) {
//			ServerHandlerClientCallFunction handler;
//			try {
//				handler = new ServerHandlerClientCallFunction(
//						serverForClientCallFunction.accept());
//				Thread receivingThread = new Thread(handler);
//				receivingThread.start();
//			} catch (IOException e) {
//				System.out.println("Accept failed: " + port);
//				System.exit(-1);
//			}
//		}
//	}

	protected void finalize() {
		// Objects created in run method are finalized when
		// program terminates and thread exits
		try {
			serverForMapUser.close();
			serverForMapbuilder.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	public void listenMapBuilderServer(int port) {
		try {
			serverForMapbuilder = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not listen on port " + port);
			System.exit(-1);
		}
		while (true) {
			ServerMapbuilder handler;
			try {
				handler = new ServerMapbuilder(
						serverForMapbuilder.accept());
				Thread receivingThread = new Thread(handler);
				receivingThread.start();
			} catch (IOException e) {
				System.out.println("Accept failed: " + port);
				System.exit(-1);
			}
		}

	}
}
