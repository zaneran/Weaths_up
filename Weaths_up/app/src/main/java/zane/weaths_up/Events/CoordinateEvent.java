package zane.weaths_up.Events;

/**
 * Created by zaneran on 10/9/2016.
 */
public class CoordinateEvent {
    private Double lat, lng;

    public CoordinateEvent(Double lat, Double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat(){
        return lat;
    }

    public Double getLng(){
        return lng;
    }
}
