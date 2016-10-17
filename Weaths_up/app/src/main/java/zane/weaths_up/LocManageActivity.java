package zane.weaths_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import zane.weaths_up.Util.CityNameDBHelper;
import zane.weaths_up.adaptor.LocationAdaptor;

public class LocManageActivity extends AppCompatActivity {
    private ListView location_listview;
    private LocationAdaptor locationAdaptor;
    private ArrayList<HashMap<String, String>> location_list;
    private ArrayList<String> cityname;
    private Button submit;
    private Button select_all;
    private Button inverse;
    private Button cancel;
    private CityNameDBHelper cityNameDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Cites Management");
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new navigationListener());

        location_listview = (ListView) findViewById(R.id.location_listview);
        location_list = new ArrayList<>();
        submit = (Button) findViewById(R.id.submit);
        select_all = (Button) findViewById(R.id.select_all);
        inverse = (Button) findViewById(R.id.inverse);
        cancel = (Button) findViewById(R.id.cancel);
        cityNameDBHelper = new CityNameDBHelper(getApplicationContext());

        Intent intent = getIntent();
        cityname = new ArrayList<>();
        cityname.addAll(intent.getStringArrayListExtra(MainActivity.Arraylist_Key));
        initList();
        locationAdaptor = new LocationAdaptor(location_list, this);
        location_listview.setAdapter(locationAdaptor);
        location_listview.setOnItemClickListener(new listviewListener());
        select_all.setOnClickListener(new select_allListener());
        cancel.setOnClickListener(new cancelListener());
        inverse.setOnClickListener(new inverseListener());
        submit.setOnClickListener(new submitListener());
    }

    private void initList(){
        //current cityname cant be deleted, so i from 1
        Log.i("cityname size", Integer.toString(cityname.size()));
        for (int i = 1; i < cityname.size(); i++){

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("content", cityname.get(i));
            hashMap.put("flag", "false");
            location_list.add(hashMap);
        }
    }

    private class listviewListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LocationAdaptor.ViewHolder viewHolder = (LocationAdaptor.ViewHolder) view.getTag();
            viewHolder.delete_checkbox.toggle();

            if (viewHolder.delete_checkbox.isChecked()) {
                location_list.get(position).put("flag", "true");
            }   else {
                location_list.get(position).put("flag", "false");
            }
        }
    }

    private class select_allListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            for(int i = 0; i < location_list.size(); i++){
                location_list.get(i).put("flag", "true");
            }
            locationAdaptor.notifyDataSetChanged();
        }
    }

    private class cancelListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            for (int i = 0; i < location_list.size(); i++){
                location_list.get(i).put("flag", "false");
            }
            locationAdaptor.notifyDataSetChanged();
        }
    }

    private class inverseListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            for (int i  = 0; i < location_list.size(); i++){
                if (location_list.get(i).get("flag").equals("true")){
                    location_list.get(i).put("flag", "false");
                } else {
                    location_list.get(i).put("flag", "true");
                }
            }
            locationAdaptor.notifyDataSetChanged();
        }
    }

    private class submitListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Iterator<HashMap<String, String>> iterator = location_list.iterator();
            while (iterator.hasNext()) {
                HashMap<String, String> map = iterator.next();
                if (map.get("flag").equals("true")){
                    cityNameDBHelper.CityNameDBDeleter(map.get("content"));
                    iterator.remove();
                }
            }
            locationAdaptor = new LocationAdaptor(location_list, getApplicationContext());
        }
    }

    private class navigationListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MainActivity.INTENT_KEY, true);
            startActivity(intent);
        }
    }
}
