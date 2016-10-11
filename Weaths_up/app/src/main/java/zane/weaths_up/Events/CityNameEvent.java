package zane.weaths_up.Events;

/**
 * Created by zaneran on 10/8/2016.
 */
public class CityNameEvent {
    private String CityName;
    private boolean isLocal;

    public CityNameEvent(String CityName, boolean isLocal){
        this.CityName = CityName;
        this.isLocal = isLocal;
    }

    public String getCityName(){
        return CityName;
    }

    public boolean getisLocal(){
        return isLocal;
    }
}
