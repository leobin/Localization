package locationaware.wifi.unused.evaluationtools;

import java.util.LinkedList;

import locationaware.wifi.dataminingtools.StatisticTools;

public class ResultEvaluation {
	LinkedList<Double> data;
	public ResultEvaluation() {
		data = new LinkedList<Double>();
	}
	
	public int getSize() {
		return data.size();
	}
	
	public double getAverage() {
		return StatisticTools.getAverage(data);
	}
	
	public double getVariance() {
		return StatisticTools.getVariance(data);
	}
	
	public double add(String myResult, String expectedResult) {
		double cur = difference(myResult, expectedResult);
		data.add(cur);
		return cur;
	}
	public double addXP(String myResult, String expectedResult) {
		double cur = differenceXP(myResult, expectedResult);
		data.add(cur);
		return cur;
	}
	
	public double difference(String myResult, String expectedResult) {
		Position myPosition = Position.getPosition(myResult.split("\\.")[0]);
		Position expectedPosition = Position.getPosition(expectedResult.split("@")[0]);
		return myPosition.getDistanceTo(expectedPosition);
	}

	private double differenceXP(String myResult, String expectedResult) {
		try {
			Position myPosition = Position.getPositionXP(myResult.split("_")[1]);
			Position expectedPosition = Position.getPositionXP(expectedResult.split("_")[2]);
			return myPosition.getDistanceTo(expectedPosition);
		} catch (Exception e) {
//			debug(myResult, expectedResult);
			return Double.POSITIVE_INFINITY;
		}
	}
}
