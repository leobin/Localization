package locationaware.wifi.convertAlg;

import java.util.ArrayList;

import locationaware.wifi.mapdata.Vd2_3MapData;
import Jama.Matrix;

/**
 * @author Dinh
 * Interface of algorithm that convert signal of one device to another device
 */
public interface ConvertAlgorithm {
	 abstract Matrix calibrate(ArrayList<Vd2_3MapData> devXMap, ArrayList<Vd2_3MapData> devYMap);
	 abstract double convert(double signal, Matrix b);
}
