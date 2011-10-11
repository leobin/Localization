package locationaware.demoapps;

import java.math.BigInteger;

/**
 * @author 
 * Create small mapdata from big mapdata
 * Vd2_3MapData.createVd2_3MapData(wifiMapDataDirPath, vd2_3MapDataDirPath);
 */
public class CreateVd2_3MapData {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

//		String wifiMapDataDirPath = "E:/Users/Dinh/Desktop/Location Data";
//		String vd2_3MapDataDirPath = "E:/Users/Dinh/Desktop/VD2_3 Map Data";
//
//		System.out.println("Building ...");
//
//		Vd2_3MapData.createVd2_3MapData(wifiMapDataDirPath, vd2_3MapDataDirPath);
//
//		System.out.println("Done.");

		String test = "FF-FF-FF-FF-FF-FF-FF-FF";
		test = test.replaceAll("\\W", "");
		System.out.println(test);
		System.out.println((new BigInteger(test, 16)).longValue());
		
//		InetAddress ip;
//		try {
//	 
//			ip = InetAddress.getLocalHost();
//			System.out.println("Current IP address : " + ip.getHostAddress());
//	 
//			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
//	 
//			byte[] mac = network.getHardwareAddress();
//	 
//			System.out.print("Current MAC address : ");
//	 
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < mac.length; i++) {
//				sb.append(String.format("%02x%s", mac[i], (i < mac.length - 1) ? ":" : ""));		
//			}
//			System.out.println(sb.toString());
//	 
//		} catch (UnknownHostException e) {
//	 
//			e.printStackTrace();
//	 
//		} catch (SocketException e){
//	 
//			e.printStackTrace();
//	 
//		}
		
//		Matrix X = new Matrix(5,4);
//		Matrix Y = new Matrix(5,1);
//		
//		
//		X.set(0, 0, 1);
//		X.set(1, 0, 1);
//		X.set(2, 0, 1);
//		X.set(3, 0, 1);
//		X.set(4, 0, 1);
//
//		X.set(0, 1, 23.37);
//		X.set(1, 1, 24.1);
//		X.set(2, 1, 24.67);
//		X.set(3, 1, 24.97);
//		X.set(4, 1, 24.8);
//		
//
//		X.set(0, 2, 24.1);
//		X.set(1, 2, 24.67);
//		X.set(2, 2, 24.97);
//		X.set(3, 2, 24.8);
//		X.set(4, 2, 24.67);
//		
//		X.set(0, 3, 24.67);
//		X.set(1, 3, 24.97);
//		X.set(2, 3, 24.8);
//		X.set(3, 3, 24.67);
//		X.set(4, 3, 24.5);
//		
//		Y.set(0, 0, 24.97);
//		Y.set(1, 0, 24.8);
//		Y.set(2, 0, 24.67);
//		Y.set(3, 0, 24.5);
//		Y.set(4, 0, 24.4);
//		
//		Matrix b = ((((X.transpose()).times(X)).inverse()).times(X.transpose())).times(Y);
//
//		System.out.println(b.get(0, 0));
//		System.out.println(b.get(1, 0));
//		System.out.println(b.get(2, 0));
//		System.out.println(b.get(3, 0));
	}

}
