package zane.weaths_up.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import zane.weaths_up.database.CityNameDB;

/**
 * Created by zaneran on 10/10/2016.
 */
public class CityNameDBHelper {

    private Context context;
    private CityNameDB cityNameDB;
    private SQLiteDatabase database;
    private String CityName;

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
        cursor.close();
        return itemLoction;
    }

    //Insert CityName to DB
    public void CityNameDBInserter(String CityName){
        ContentValues contentValues = new ContentValues();
        contentValues.put("CityName",CityName);
        database.insert("CityNameTable", null, contentValues);
    }

    //Delete Specific data from DB
    public void CityNameDBDeleter(String CityName){
        database.delete("CityNameTable", "CityName = ?", new String[]{CityName});
        Toast.makeText(context, "City Record Deleted!", Toast.LENGTH_SHORT).show();
    }

    //Get All results from CityNameDB
    public ArrayList<String> CityNameDBGetter(){
        ArrayList<String> CityNameList = new ArrayList<String>();
        Cursor cursor = database.query("CityNameTable", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                CityName = cursor.getString(cursor.getColumnIndex("CityName"));
                CityNameList.add(CityName);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return CityNameList;
    }
}
