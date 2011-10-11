package locationaware.wifi.dataminingtools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import locationaware.wifi.mapdata.WifiMapData;

public class ExcelStatistics {
	public static WritableFont bold = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false);
	public static WritableCellFormat labelFormat = new WritableCellFormat(bold);
	
	public static void writeAPDistribution(WritableSheet sheet, APStatistic APstat, double range, int cur_row) throws RowsExceededException, WriteException
	{
		double min = 0;
		double max = range;
		Label APname = new Label(0, cur_row, APstat.accessPoint.getAccessPointName(), labelFormat);
		sheet.addCell(APname);
		cur_row++;
		int cur_col = 0;
		while (max <= 100)
		{
			Label label = new Label(cur_col, cur_row, String.valueOf(min) + "-" + String.valueOf(max));
			sheet.addCell(label);
			min += range;
			max += range;
			
			int count = 0;
			for (double signal : APstat.signalList)
			{
				if ((signal <= max) && (signal > min))
					count++;
			}
			Number countnumber = new Number(cur_col, cur_row + 1, count);
			sheet.addCell(countnumber);
			cur_col++;
		}
	}
	public static void writeDistributionSheet(WritableSheet sheet, ArrayList<APStatistic> listAPstat) throws RowsExceededException, WriteException
	{
		int cur_row = 0;		
		for (APStatistic APstat : listAPstat)
		{
			writeAPDistribution(sheet, APstat, 5, cur_row);
			cur_row += 3;
		}
	}
	public static void writeAcessPointData(WritableSheet sheet, int col, APStatistic APstat) throws RowsExceededException, WriteException
	{	
		int cur_row = 0;
		Label APname = new Label(col, cur_row, APstat.accessPoint.getAccessPointName(), labelFormat);
		cur_row++;
		sheet.addCell(APname);
		for(Double signal : APstat.signalList)
		{
			Number number = new Number(col, cur_row, signal);
			sheet.addCell(number);
			cur_row++;
		}
	}
	public static void writeDataSheet(WritableSheet sheet, ArrayList<APStatistic> listAPstat) throws RowsExceededException, WriteException
	{
		int cur_col = 0;
		for (APStatistic APstat : listAPstat)
		{
			writeAcessPointData(sheet, cur_col, APstat);
			cur_col++;
		}
	}
	
	public static void writeSortedDataSheet(WritableSheet sheet,
			ArrayList<APStatistic> listAPstat) throws RowsExceededException, WriteException {
		int cur_col = 0;
		for (APStatistic APstat : listAPstat)
		{
			Collections.sort(APstat.signalList);
			writeAcessPointData(sheet, cur_col, APstat);
			cur_col++;
		}		
	}
	
	public static void writeAPStat(WritableSheet sheet, APStatistic APstat, int cur_row) throws RowsExceededException, WriteException
	{
		Label name = new Label(0, cur_row, APstat.accessPoint.getAccessPointName());
		Label macAddr = new Label(1, cur_row, "" + APstat.accessPoint.getMACAddress());
		Label authentication = new Label(2, cur_row, APstat.accessPoint.getAuthentication());
		Label encryption = new Label(3, cur_row, APstat.accessPoint.getEncryption());
		Number channel = new Number(4, cur_row, APstat.accessPoint.getiChannel());
		Label networkType = new Label(5, cur_row, APstat.accessPoint.getNetworkType());
		Label radioType = new Label(6, cur_row, APstat.accessPoint.getRadioType());
		Number average = new Number(7, cur_row, APstat.average); 
		Number appearance = new Number(8, cur_row, APstat.appearance); 
		Number variance = new Number(9, cur_row, APstat.variance); 
		Number min = new Number(10, cur_row, APstat.min); 
		Number max = new Number(11, cur_row, APstat.max);
		sheet.addCell(name);
		sheet.addCell(macAddr);
		sheet.addCell(authentication);
		sheet.addCell(encryption);
		sheet.addCell(channel);
		sheet.addCell(networkType);
		sheet.addCell(radioType);
		sheet.addCell(average);
		sheet.addCell(appearance);
		sheet.addCell(variance);
		sheet.addCell(min);
		sheet.addCell(max);
	}
	public static void writeStatSheet(WritableSheet sheet, ArrayList<APStatistic> listAPstat) throws RowsExceededException, WriteException
	{
		Label name = new Label(0, 0, "AP name", labelFormat);
		sheet.addCell(name);
		Label macAddr = new Label(1, 0, "MAC Address", labelFormat);
		sheet.addCell(macAddr);
		Label authentication = new Label(2, 0, "Authentication", labelFormat);
		sheet.addCell(authentication);
		Label encryption = new Label(3, 0, "Encryption", labelFormat);
		sheet.addCell(encryption);
		Label channel = new Label(4, 0, "Channel", labelFormat);
		sheet.addCell(channel);
		Label networkType = new Label(5, 0, "Network Type", labelFormat);
		sheet.addCell(networkType);
		Label radioType = new Label(6, 0, "Radio Type", labelFormat);
		sheet.addCell(radioType);
		Label average = new Label(7, 0, "Average", labelFormat);
		sheet.addCell(average);
		Label appearance = new Label(8, 0, "Appearance", labelFormat);
		sheet.addCell(appearance);
		Label variance = new Label(9, 0, "Variance", labelFormat);
		sheet.addCell(variance);
		Label min = new Label(10, 0, "Min", labelFormat);
		sheet.addCell(min);
		Label max = new Label(11, 0, "Max", labelFormat);
		sheet.addCell(max);
		int cur_row = 1;
		for (APStatistic APstat : listAPstat)
		{
			writeAPStat(sheet, APstat, cur_row);
			cur_row++;
		}
	}
	
	public static void convertMap2Excel(String srcDir, String filename, String destDir) throws IOException, RowsExceededException, WriteException
	{
		WifiMapData location = (WifiMapData) WifiMapData.readMapData(srcDir, filename);
		ArrayList<APStatistic> listAPstat = (ArrayList<APStatistic>) StatisticTools.statistic(StatisticTools.surveyAccessPoint(location)).values();
		
		WritableWorkbook workbook = Workbook.createWorkbook(new File(destDir + "\\" + filename.substring(0, filename.length() - 7) + "xls"));
		WritableSheet dataSheet = workbook.createSheet("Data", 0);
		WritableSheet sortedDataSheet = workbook.createSheet("Sorted Data", 1);
		WritableSheet statSheet = workbook.createSheet("Statistics", 2);
		WritableSheet distributionSheet = workbook.createSheet("Distribution", 3);
		
		writeDataSheet(dataSheet, listAPstat);
		writeSortedDataSheet(sortedDataSheet, listAPstat);
		writeStatSheet(statSheet, listAPstat);
		writeDistributionSheet(distributionSheet, listAPstat);
		workbook.write(); 
		workbook.close();
	}
}
