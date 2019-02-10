package com.example.ylmz.recyclevieweventfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyHelper extends SQLiteOpenHelper {

    public static final String TAG =MyHelper.class.getSimpleName();
    public static final int DATABASE_VERSION=2;
    public static final String DATABASE_NAME="info.db";
    public static final String TABLE_NAME="events";
    public static final String KEY_ID="id";
    public static final String KEY_EVENTID="eventid";
    public static final String KEY_CITY="city";
    public static final String KEY_PHOTO="picture";
    public static final String KEY_TITLE="title";
    public static final String KEY_DESC="description";
    public static final String KEY_TIME="time";
    public static final String KEY_CATEGORY="category";



    public MyHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EVENTID + " TEXT," + KEY_CITY + " TEXT,"
                + KEY_PHOTO + " TEXT," + KEY_TITLE + " TEXT," + KEY_DESC + " TEXT," + KEY_TIME + " TEXT"+ ")";

        String CREATE_TABLE2 = "CREATE TABLE " + "details" + "("
                + "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "eventid" + " TEXT," + "description" + " TEXT,"
                + "image" + " TEXT," + "title" + " TEXT" + ")";




        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
        Log.d("Veritabanı okay","tablo okay");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " +TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " +"details");

        onCreate(db);
    }







    public void addinfos(String eventid,String city,String photo,String title,String desc,String time){

        SQLiteDatabase DB=this.getWritableDatabase();


        ContentValues values = new ContentValues();

        values.put(KEY_EVENTID,eventid);
        values.put(KEY_CITY,city);
        values.put(KEY_PHOTO,photo);
        values.put(KEY_TITLE,title);
        values.put(KEY_DESC,desc);
        values.put(KEY_TIME,time);


        long id =DB.insert(TABLE_NAME,null,values);

        DB.close();
        Log.d("yeni satır eklendi",String.valueOf(id));



    }
}
