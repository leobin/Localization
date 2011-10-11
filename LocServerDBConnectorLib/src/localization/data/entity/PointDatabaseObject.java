package localization.data.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import localization.data.entity.contentobject.PointDataObject;
import locationaware.clientserver.Point;

import com.localization.manager.PointDataManagement;

/**
 * The persistent class for the point database table.
 * 
 */
@Entity
@Table(name = "point")
public class PointDatabaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "point_id")
	private String pointId;

	@Column(name = "coordinate_x")
	private int coordinateX;

	@Column(name = "coordinate_y")
	private int coordinateY;

	@Column(name = "point_order")
	private int pointOrder;

	public int getPointOrder() {
		return pointOrder;
	}

	public void setPointOrder(int pointOrder) {
		this.pointOrder = pointOrder;
	}

	// bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name = "location_id")
	private LocationDatabaseObject location;

	public PointDatabaseObject() {
	}

	public String getPointId() {
		return this.pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
		PointDataManagement.updatePoint(this);
	}

	public int getCoordinateX() {
		return this.coordinateX;
	}

	public void setCoordinateX(int coordinateX) {
		this.coordinateX = coordinateX;
		PointDataManagement.updatePoint(this);
	}

	public int getCoordinateY() {
		return this.coordinateY;
	}

	public void setCoordinateY(int coordinateY) {
		this.coordinateY = coordinateY;
		PointDataManagement.updatePoint(this);
	}

	public LocationDatabaseObject getLocation() {
		return this.location;
	}

	public void setLocation(LocationDatabaseObject location) {
		this.location = location;
		PointDataManagement.updatePoint(this);
	}

	public void update(PointDataObject point) {
		this.coordinateX = point.getCoordinateX();
		this.coordinateY = point.getCoordinateY();
		this.pointOrder = point.getPointOrder();
		PointDataManagement.updatePoint(this);
	}

	public PointDatabaseObject(PointDataObject point) {
		this.coordinateX = point.getCoordinateX();
		this.coordinateY = point.getCoordinateY();
		this.pointOrder = point.getPointOrder();
	}

	public Point createPoint() {
		Point point = new Point();
		point.setPointId(this.pointId);
		point.setCoordinateX(this.coordinateX);
		point.setCoordinateY(this.coordinateY);
		point.setPointOrder(this.getPointOrder());
		return point;
	}
}