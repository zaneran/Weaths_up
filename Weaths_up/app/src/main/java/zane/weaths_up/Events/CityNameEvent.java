package zane.weaths_up.Events;

/**
 * Created by zaneran on 10/8/2016.
 */
public class CityNameEvent {
    private String CityName;
    private boolean isLocal;
    private String Lat;
    private String Lng;

    public CityNameEvent(String CityName,String Lat, String Lng, boolean isLocal){
        this.Lat = Lat;
        this.Lng = Lng;
        this.CityName = CityName;
        this.isLocal = isLocal;
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

    public boolean getisLocal(){
        return isLocal;
    }
}
