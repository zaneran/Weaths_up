package zane.weaths_up.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zane.weaths_up.Model.DailyItem;
import zane.weaths_up.R;

/**
 * Created by zaneran on 10/1/2016.
 */
public class DailyAdaptor extends ArrayAdapter<DailyItem> {

    private Context mContext;

    public DailyAdaptor(Context context, List<DailyItem> objects){
        super(context, 0, objects);
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.daily_item, parent, false);
        }

        DailyCell dailyCell = new DailyCell();
        dailyCell.date = (TextView) convertView.findViewById(R.id.Date);
        dailyCell.icon = (ImageView) convertView.findViewById(R.id.Icon);
        dailyCell.weather = (TextView) convertView.findViewById(R.id.Weather);
        dailyCell.temperatureMin = (TextView) convertView.findViewById(R.id.TemperatureMin);
        dailyCell.temperatureMax = (TextView) convertView.findViewById(R.id.TemperatureMax);

        DailyItem dailyItem = getItem(position);

        dailyCell.date.setText(dailyItem.getdate());
        //dailyCell.icon.set
        dailyCell.weather.setText(dailyItem.getWeather());
        dailyCell.temperatureMin.setText(dailyItem.getTemperatureMin());
        dailyCell.temperatureMax.setText(dailyItem.getTemperatureMax());

        return convertView;
    }

    private class DailyCell{
        TextView date;
        ImageView icon;
        TextView weather;
        TextView temperatureMin;
        TextView temperatureMax;
    }
}
