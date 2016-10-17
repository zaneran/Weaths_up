package zane.weaths_up.Events;

/**
 * Created by zaneran on 10/8/2016.
 */
public class CityNameEvent {
    private String CityName;
    private String Loc_type;
    private String Lat;
    private String Lng;

    public CityNameEvent(String CityName,String Lat, String Lng, String Loc_type){
        this.Lat = Lat;
        this.Lng = Lng;
        this.CityName = CityName;
        this.Loc_type = Loc_type;
    }

    public String getCityName(){
        return CityName;
    }

    public String getLat() {
        return Lat;
    }

    public String getLng() {
        return Lng;
    }

    public String getLoc_type(){
        return Loc_type;
    }
}
