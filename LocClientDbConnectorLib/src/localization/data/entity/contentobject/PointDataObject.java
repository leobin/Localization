package localization.data.entity.contentobject;

import com.localization.other.ObjectMapping;
import com.localization.server.CallServerFunction;
import java.io.Serializable;


/**
 * The persistent class for the point database table.
 * 
 */
public class PointDataObject implements Serializable {
private static final long serialVersionUID = 1L;

    private String pointId;
    private int coordinateX;
    private int coordinateY;
    private int pointOrder;

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
        return (LocationDataObject) CallServerFunction.callServerObjectFunction(this.pointId, ObjectMapping.POINT_OBJECT, ObjectMapping.POINT_GET_LOCATION,null);
    }

    public void setLocation(String locationId) {
        CallServerFunction.callServerObjectFunction(this.pointId, ObjectMapping.POINT_OBJECT, ObjectMapping.POINT_GET_LOCATION,location);
    }
}
