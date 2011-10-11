package locationaware.wifi.dataminingtools;

import java.util.Arrays;

public class DebugTools { 
	public static String prettyString(Object...os) {
		return Arrays.deepToString(os);
	}
	public static void debug(Object...os) {
		System.out.println(Arrays.deepToString(os));
	}
}
