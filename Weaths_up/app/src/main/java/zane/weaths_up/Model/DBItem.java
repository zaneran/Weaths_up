package zane.weaths_up.Model;

/**
 * Created by zaneran on 10/11/2016.
 */
public class DBItem {
    private String CityName;
    private String Lat;
    private String Lng;

    public DBItem(String CityName, String Lat, String Lng){
        this.CityName = CityName;
        this.Lat = Lat;
        this.Lng = Lng;
    }

    public String getCityName(){
        return CityName;
    }
    public String getLat(){
        return Lat;
    }
    public String getLng(){
        return Lng;
    }
}
