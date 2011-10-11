package locationaware.wifi.dataminingtools;

public class LocationEvaluation implements Comparable<LocationEvaluation> {
	public String name;
	public double score;
	public LocationEvaluation(String name) {
		this.name = name;
	}
	public LocationEvaluation(String name, double score) {
		this.name = name;
		this.score = score;
	}
	public int compareTo(LocationEvaluation o) {
		if (Math.abs(score - o.score) > 1e-7) return Double.compare(o.score, score);
		return name.compareTo(o.name);
	}
	public String toString() {
		return "(" + name + ", " + score + ")";
	}
}
