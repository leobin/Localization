package locationaware.wifi.convertAlg;

import java.util.ArrayList;

import locationaware.wifi.mapdata.Vd2_3MapData;
import Jama.Matrix;

/**
 * @author Dinh
 * The convert algorithm that use linear regression
 */
public class LinearRegression implements ConvertAlgorithm{

	@Override
	public Matrix calibrate(ArrayList<Vd2_3MapData> listDevXMap, ArrayList<Vd2_3MapData> listDevYMap) {
		// signalDevY = a*signalDevX + b
		
		if (listDevXMap == null || listDevYMap == null) {
			return null;
		}
		
//		if (listDevXMap.getStatistics() == null || listDevYMap.getStatistics() == null) {
//			return null;
//		}
		
		ArrayList<Double> devXSignal = new ArrayList<Double>();
		ArrayList<Double> devYSignal = new ArrayList<Double>();
		
		for (int i = 0; i < listDevXMap.size(); i++) {
			Vd2_3MapData devXMap = listDevXMap.get(i);
			Vd2_3MapData devYMap = listDevYMap.get(i);
			
			if (devXMap == null || devYMap == null) {
				continue;
			}
			
			if (devXMap.getStatistics() == null || devYMap.getStatistics() == null) {
				continue;
			}

			for (long mac : devXMap.getStatistics().keySet()) {
				double signalX = devXMap.getStatistics().get(mac).average;
				double signalY;
				if (devYMap.getStatistics().get(mac) != null) {
					signalY = devYMap.getStatistics().get(mac).average;
					devXSignal.add(new Double(signalX));
					devYSignal.add(new Double(signalY));
				}
			}
	
		}
						
		int numberOfSample = devXSignal.size();
		Matrix X = new Matrix(numberOfSample,2);
		Matrix Y = new Matrix(numberOfSample,1);
		
		for (int i = 0; i < numberOfSample; i++) {
			X.set(i, 0, 1);
			X.set(i, 1, devXSignal.get(i));
			Y.set(i, 0, devYSignal.get(i));
		}
		
		Matrix b = ((((X.transpose()).times(X)).inverse()).times(X.transpose())).times(Y);
		
		return b;
	}

	@Override
	public double convert(double signalX, Matrix b) {
		return b.get(0, 0) + signalX*b.get(1, 0);
	}

}
