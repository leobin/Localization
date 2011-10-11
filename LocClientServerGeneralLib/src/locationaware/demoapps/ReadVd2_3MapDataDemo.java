package locationaware.demoapps;

import java.io.IOException;

import locationaware.clientserver.MapData;
import locationaware.wifi.dataminingtools.APStatistic;
import locationaware.wifi.mapdata.Vd2_3MapData;

/**
 * @author 
 * Read small mapdata
 *
 */
public class ReadVd2_3MapDataDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String smallMapDirPath = "E:/Users/Dinh/Desktop/data";
		String smallMapFileName = "40_15_3.mapdata";
	
		Vd2_3MapData smallMap = (Vd2_3MapData) MapData.readMapData(smallMapDirPath, smallMapFileName);
		System.out.println("Information in " + smallMapFileName + " is: ");
		for (APStatistic apStatistic : smallMap.getStatistics().values()) {
			System.out.println(apStatistic.accessPoint.getAccessPointName() + "	-	" + apStatistic.average);
		}
		
	}

}
