package zane.weaths_up.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import zane.weaths_up.R;

/**
 * Created by zaneran on 10/13/2016.
 */
public class LocationAdaptor extends BaseAdapter {

    private ArrayList<HashMap<String, String>> locationlist;
    private Context context;
    private LayoutInflater layoutInflater = null;

    public LocationAdaptor(ArrayList<HashMap<String, String>> locationlist, Context context){
        this.context = context;
        this.locationlist = new ArrayList<>();
        this.locationlist.addAll(locationlist);
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return locationlist.size();
    }

    @Override
    public Object getItem(int position) {
        return locationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            //inflate layout and pass value to convertview.
            convertView = layoutInflater.inflate(R.layout.loc_items, null);
            holder.locationname = (TextView) convertView.findViewById(R.id.locationname);
            holder.delete_checkbox = (CheckBox) convertView.findViewById(R.id.delete_checkbox);
            convertView.setTag(holder);
        } else {
            //get holder
            holder = (ViewHolder) convertView.getTag();
        }
        holder.locationname.setText(locationlist.get(position).get("content"));
        holder.delete_checkbox.setChecked(locationlist.get(position).get("flag").equals("true"));
        return convertView;
    }

    public final class ViewHolder{
       public TextView locationname;
       public CheckBox delete_checkbox;
    }
}
