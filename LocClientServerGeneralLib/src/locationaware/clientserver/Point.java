package locationaware.clientserver;

import java.io.Serializable;

/**
 * @author 
 * A 2D-point
 *
 */
public class Point implements Serializable, Comparable<Point> {

	private static final long serialVersionUID = 6716839311669357725L;

	private String pointId;
	private int coordinateX;
	private int coordinateY;
	private int pointOrder;	// >=1
	
	public Point() {

	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public String getPointId() {
		return pointId;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public int getCoordinateY() {
		return coordinateY;
	}

	public void setPointOrder(int pointOrder) {
		this.pointOrder = pointOrder;
	}

	/**
	 * @return the order of this point in the location
	 */
	public int getPointOrder() {
		return pointOrder;
	}

	@Override
	public int compareTo(Point o) {
		// TODO Auto-generated method stub
		return this.pointOrder - o.pointOrder;
	}

}
