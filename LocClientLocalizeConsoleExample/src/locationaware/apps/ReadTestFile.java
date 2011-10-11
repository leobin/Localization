package locationaware.apps;

import java.io.File;

import locationaware.wifi.debug.TestingFile;

public class ReadTestFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File folder = new File("E:/Users/Dinh/Desktop/25");
		
		File[] listFile = folder.listFiles();
		
		for (File file : listFile) {
			TestingFile testingFile = TestingFile.readTestingFile(file.getAbsolutePath());
			
			System.out.println(testingFile.selectTestLocation.getLocationName() + "-" + testingFile.listScanningPoint.size());			
		}
		
	}

}
