package zane.weaths_up.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zane.weaths_up.Model.HourlyItem;
import zane.weaths_up.R;

/**
 * Created by zaneran on 10/1/2016.
 */
public class HourlyAdaptor extends ArrayAdapter<HourlyItem> {
    private Context mContext;

    public HourlyAdaptor(Context context, List<HourlyItem> objects){
        super(context, 0, objects);
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.hourly_items, parent, false);
        }

        HourlyCell hourlyCell = new HourlyCell();
        hourlyCell.date = (TextView) convertView.findViewById(R.id.Date);
        hourlyCell.icon = (ImageView) convertView.findViewById(R.id.Icon);
        hourlyCell.weather = (TextView) convertView.findViewById(R.id.Weather);
        hourlyCell.temperature = (TextView) convertView.findViewById(R.id.Temperature);

        HourlyItem hourlyItem = getItem(position);

        hourlyCell.date.setText(hourlyItem.getDate());
        //hourlyCell.icon.set
        hourlyCell.weather.setText(hourlyItem.getWeather());
        hourlyCell.temperature.setText(hourlyItem.getTemperature());

        return convertView;
    }

    private class HourlyCell{
        TextView date;
        ImageView icon;
        TextView weather;
        TextView temperature;
    }
}
