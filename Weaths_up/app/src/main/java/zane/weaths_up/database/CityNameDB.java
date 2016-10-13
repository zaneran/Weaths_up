package zane.weaths_up.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zaneran on 10/10/2016.
 */
public class CityNameDB extends SQLiteOpenHelper {

    public static final String DBname = "CityNameDB.db";
    public static final String CREATE_DB = "create table CityNameTable (CityName text primary key, Lat text, Lng text)";
    private Context mcontext;


    public CityNameDB(Context context, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context, DBname, null, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
        Log.i("DB", "Created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Deal");
        onCreate(db);
    }

}
