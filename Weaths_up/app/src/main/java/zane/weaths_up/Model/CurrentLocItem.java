package zane.weaths_up.Model;

/**
 * Created by zaneran on 10/17/2016.
 */
public class CurrentLocItem {
    public static String lat;
    public static String lng;
    public static String cityname;

    public void setLat(String lat){
        this.lat = lat;
    }

    public void setLng(String lng){
        this.lng = lng;
    }

    public void setCityname(String cityname){
        this.cityname = cityname;
    }

    public String getLat(){
        return  lat;
    }

    public String getLng(){
        return lng;
    }

    public String getCityname(){
        return cityname;
    }
}
