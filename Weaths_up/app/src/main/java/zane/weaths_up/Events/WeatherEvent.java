package zane.weaths_up.Events;

import android.location.Location;

import java.util.ArrayList;

import zane.weaths_up.Model.CurrentItem;
import zane.weaths_up.Model.DailyItem;
import zane.weaths_up.Model.HourlyItem;

/**
 * Created by zaneran on 10/1/2016.
 */
public class WeatherEvent {

    private Location location;
    private ArrayList<HourlyItem> hourlyItemArrayList;
    private ArrayList<DailyItem> dailyItemArrayList;
    private CurrentItem currentItem;

    public WeatherEvent(CurrentItem currentItem,
                        ArrayList<HourlyItem> hourlyItemArrayList,
                        ArrayList<DailyItem> dailyItemArrayList) {
        this.currentItem = currentItem;
        this.hourlyItemArrayList = new ArrayList<HourlyItem>();
        this.dailyItemArrayList = new ArrayList<DailyItem>();
        this.hourlyItemArrayList.addAll(hourlyItemArrayList);
        this.dailyItemArrayList.addAll(dailyItemArrayList);
    }

    public CurrentItem getCurrentItem() {
        return currentItem;
    }

    public ArrayList<DailyItem> getDailyItemArrayList(){
        return dailyItemArrayList;
    }

    public ArrayList<HourlyItem> getHourlyItemArrayList(){
        return hourlyItemArrayList;
    }
}
