package zane.weaths_up.Events;

import android.location.Location;

/**
 * Created by zaneran on 10/1/2016.
 */
public class CurrentLocationEvent {
    private Location location;

    public CurrentLocationEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
