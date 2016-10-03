package zane.weaths_up.Events;

import android.location.Location;

/**
 * Created by zaneran on 10/1/2016.
 */
public class LastLocationEvent {

        private Location location;

        public LastLocationEvent(Location location) {
            this.location = location;
        }

        public Location getLocation() {
            return location;
        }
}
