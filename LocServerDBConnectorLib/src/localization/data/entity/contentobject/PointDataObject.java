package localization.data.entity.contentobject;

import java.io.Serializable;

import localization.data.entity.PointDatabaseObject;


/**
 * The persistent class for the point database table.
 * 
 */
public class PointDataObject implements Serializable{
	private static final long serialVersionUID = 1L;

	private String pointId = null;

	private int coordinateX;

	private int coordinateY;

	private int pointOrder;

	public PointDataObject(PointDatabaseObject point) {
		this.pointId = point.getPointId();
		this.coordinateX = point.getCoordinateX();
		this.coordinateY = point.getCoordinateY();
		this.pointOrder = point.getPointOrder();
	}

	
	public int getPointOrder() {
		return pointOrder;
	}

	public void setPointOrder(int pointOrder) {
		this.pointOrder = pointOrder;
	}

	private LocationDataObject location;

	public PointDataObject() {
	}

	public String getPointId() {
		return this.pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public int getCoordinateX() {
		return this.coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
	}

	public int getCoordinateY() {
		return this.coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
	}

	public LocationDataObject getLocation() {
		return this.location;
	}

	public void setLocation(LocationDataObject location) {
		this.location = location;
	}

}