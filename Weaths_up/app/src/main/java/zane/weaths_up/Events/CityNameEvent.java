package zane.weaths_up.Events;

/**
 * Created by zaneran on 10/8/2016.
 */
public class CityNameEvent {
    private String CityName;

    public CityNameEvent(String CityName){
        this.CityName = CityName;
    }

    public String getCityName(){
        return CityName;
    }
}
