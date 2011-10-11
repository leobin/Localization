package locationaware.apps;

import java.io.File;

import locationaware.clientserver.MapData;
import locationaware.wifi.mapdata.WifiMapData;
import locationaware.wifi.unused.utilities.FilterData;

/**
 * @author Dinh
 * This application demonstrate how to filter a wifimapdata using class FilterData
 */
public class FilterWifiMapDataDemo {

	public static void filterData(File rawDataDir, File filteredDataDir,
			FilterData filter) {
		if (rawDataDir != null && rawDataDir.isDirectory()) {
			filteredDataDir.mkdirs();
			File[] allFiles = rawDataDir.listFiles();
			WifiMapData filteredData;
			for (int i = 0; i < allFiles.length; ++i) {
				if (allFiles[i].isDirectory()) {
					File subSmallMapDir = new File(
							filteredDataDir.getAbsolutePath() + "/"
									+ allFiles[i].getName());
					filterData(allFiles[i], subSmallMapDir, filter);
					continue;
				} else if (allFiles[i].getName().endsWith(
						MapData.EXTENSIONMAPDATAFILE)) {
					filteredData = filter.filterData(allFiles[i].getAbsolutePath());
					WifiMapData.writeMapData(filteredData, filteredDataDir.getAbsolutePath(), filter.getFilteredDataFileName(allFiles[i].getName()));
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		String rawDataDirPath = "E:/Location Data";
		String filteredDataDirPath = "E:/Location Filtered Data";
		int filterAlgID = 0;
		FilterData filter = new FilterData();

		filter.setFilterAlg(filterAlgID);
		
		System.out.println("Filtering ...");

		File rawDataDir = new File(rawDataDirPath);
		File filteredDataDir = new File(filteredDataDirPath);

		filterData(rawDataDir, filteredDataDir, filter);

		System.out.println("Done.");
	}	
	
}
