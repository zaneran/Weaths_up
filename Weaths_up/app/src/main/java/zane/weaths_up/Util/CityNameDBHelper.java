package zane.weaths_up.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import zane.weaths_up.Model.DBItem;
import zane.weaths_up.database.CityNameDB;

/**
 * Created by zaneran on 10/10/2016.
 */
public class CityNameDBHelper {

    private Context context;
    private CityNameDB cityNameDB;
    private SQLiteDatabase database;
    private String CityName;
    private String Lat;
    private String Lng;


    public CityNameDBHelper(Context context){
        this.context = context;
        cityNameDB = new CityNameDB(this.context, null, 1);
        database = cityNameDB.getWritableDatabase();
    }

    //Query CityName DB to confirm existence.
    public int CityNameDBQuerier(String CityName) {
        //0 : not exist
        int itemLoction = 0;
        int i = 1;

        Cursor cursor = database.query("CityNameTable", null, null, null, null, null, null, null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    this.CityName = cursor.getString(cursor.getColumnIndex("CityName"));
                    if (this.CityName.length() == CityName.length()) {
                        if (this.CityName.equals(CityName)){
                            itemLoction = i;
                            break;
                        }
                    }
                    i++;
                }while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return itemLoction;
    }

    //Insert CityName to DB
    public void CityNameDBInserter(DBItem dbItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put("CityName",dbItem.getCityName());
        contentValues.put("Lat", dbItem.getLat());
        contentValues.put("Lng", dbItem.getLng());
        database.insert("CityNameTable", null, contentValues);
    }

    //Delete Specific data from DB
    public void CityNameDBDeleter(String CityName){
        database.delete("CityNameTable", "CityName = ?", new String[]{CityName});
        Toast.makeText(context, "City Record Deleted!", Toast.LENGTH_SHORT).show();
    }

    //Get All citynames from CityNameDB
    public ArrayList<DBItem> CityNameDBGetter(){
        ArrayList<DBItem> CityNameList = new ArrayList<DBItem>();
        Cursor cursor = database.query("CityNameTable", null, null, null, null, null, null, null);
        try{
            if (cursor.moveToFirst()){
                do {
                    CityName = cursor.getString(cursor.getColumnIndex("CityName"));
                    Lat = cursor.getString(cursor.getColumnIndex("Lat"));
                    Lng = cursor.getString(cursor.getColumnIndex("Lng"));
                    DBItem dbItem = new DBItem(CityName, Lat, Lng);
                    CityNameList.add(dbItem);
                }while (cursor.moveToNext());
            }
        } finally{
            cursor.close();
        }
        return CityNameList;
    }
}
