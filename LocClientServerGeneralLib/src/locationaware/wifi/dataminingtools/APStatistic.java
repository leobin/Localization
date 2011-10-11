package locationaware.wifi.dataminingtools;

import static locationaware.wifi.dataminingtools.DebugTools.prettyString;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import locationaware.wifi.AccessPoint;

public class APStatistic implements Serializable {
	private static final long serialVersionUID = -6681546857378479517L;
	public AccessPoint accessPoint;
	public List<Double> signalList;
	public double average, variance, min, max;
	public int appearance;
		
	public static Comparator<APStatistic> sortByAppearance = new Comparator<APStatistic>() {
		public int compare(APStatistic a, APStatistic b) {
			if (a.appearance != b.appearance) return b.appearance - a.appearance;
			
			if (a.accessPoint.getMACAddress() < b.accessPoint.getMACAddress()) {
				return -1;
			} else if (a.accessPoint.getMACAddress() == b.accessPoint.getMACAddress()) {
				return 0;
			} else {
				return 1;
			}
		}
	};
	
	public static Comparator<APStatistic> sortByAverage = new Comparator<APStatistic>() {
		public int compare(APStatistic a, APStatistic b) {
			if (Math.abs(a.average - b.average) > 1e-7) return Double.compare(b.average, a.average);
			if (a.accessPoint.getMACAddress() < b.accessPoint.getMACAddress()) {
				return -1;
			} else if (a.accessPoint.getMACAddress() == b.accessPoint.getMACAddress()) {
				return 0;
			} else {
				return 1;
			}
		}
	};
	
	public APStatistic(AccessPoint accessPoint, LinkedList<Double> signalList) {
		try {
			this.accessPoint = accessPoint.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.signalList = signalList;
		appearance = this.signalList.size();
		average = StatisticTools.getAverage(this.signalList);
		variance = StatisticTools.getVariance(this.signalList);
		min = Collections.min(this.signalList);
		max = Collections.max(this.signalList);
	}
	public String toString() {
		return prettyString(accessPoint.toString(), average, signalList.size(), variance, min, max);
	}
}
