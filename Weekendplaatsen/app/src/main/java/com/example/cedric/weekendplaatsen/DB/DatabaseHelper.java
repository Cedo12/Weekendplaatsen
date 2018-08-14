package com.example.cedric.weekendplaatsen.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.cedric.weekendplaatsen.Model.WeekendPlaats;

import java.util.ArrayList;

/**
 * Created by cedric on 10/6/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    public static final int database_version = 2;
    public static final String DATABASE_NAME="weekendplaatsen.db";

    //tablename
    public static final String TABLE_NAME_WEEKENDPLAATSEN = "weekendplaatsen";

    //kolomnnames
    public static final String COL_1="weekend_id";
    public static final String COL_2="email";
    public static final String COL_3="naam";
    public static final String COL_4="jeugdvereniging";
    public static final String COL_5="verantwoordelijke";
    public static final String COL_6="nummer_verantwoordelijke";
    public static final String COL_7="straat";
    public static final String COL_8="gemeente";
    public static final String COL_9="postcode";
    public static final String COL_10="provincie";
    public static final String COL_11="afstand";
    public static final String COL_12="website";

    public static synchronized DatabaseHelper getInstance(Context c){
        if(instance ==null){
            instance = new DatabaseHelper(c.getApplicationContext());
        }
        return  instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public int insertWeekendplaatsen(WeekendPlaats w){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,w.getEmail());
        contentValues.put(COL_3,w.getNaam());
        contentValues.put(COL_4,w.getJeugdbeweging());
        contentValues.put(COL_5,w.getVerantwoordelijke());
        contentValues.put(COL_6,w.getNummer_verantwoordelijke());
        contentValues.put(COL_7,w.getStraat());
        contentValues.put(COL_8,w.getGemeente());
        contentValues.put(COL_9,w.getPostcode());
        contentValues.put(COL_10,w.getProvincie());
        contentValues.put(COL_11,w.getAfstand());
        contentValues.put(COL_12,w.getWebsite());

        long result = db.insert(TABLE_NAME_WEEKENDPLAATSEN,null,contentValues);
        return (int)result;
    }

    public Integer deleteweekendplaats(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_WEEKENDPLAATSEN, "email = ?", new String[] {email});
    }

    public ArrayList<WeekendPlaats> getWeekendplaatsen(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<WeekendPlaats> favorieten = new ArrayList<>();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME_WEEKENDPLAATSEN,null);
        if(res.getCount() == 0){
            return null;
        }else{
            while(res.moveToNext()){
                WeekendPlaats wp = new WeekendPlaats(res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6),res.getString(7),res.getString(8),res.getString(9),res.getInt(10),res.getString(11));
                favorieten.add(wp);
            }
        }

        return favorieten;
    }

    public Boolean isWeekendplaatsexist (WeekendPlaats wp){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+ TABLE_NAME_WEEKENDPLAATSEN + " WHERE " + COL_2 + "='" + wp.getEmail() +"'",null);
        if(res.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME_WEEKENDPLAATSEN+" (" +COL_1 + " INTEGER PRIMARY KEY, " +COL_2 + " VARCHAR(255), "+COL_3+" VARCHAR(255), "+COL_4+" VARCHAR(255), "+COL_5+" VARCHAR(255), "+COL_6+" VARCHAR(255), "+COL_7+" VARCHAR(255), "+COL_8+" VARCHAR(255), "+COL_9+" VARCHAR(255),"+COL_10+" VARCHAR(255),"+COL_11+" DOUBLE(255,3),"+COL_12+" VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_WEEKENDPLAATSEN);
        onCreate(db);
    }
}
