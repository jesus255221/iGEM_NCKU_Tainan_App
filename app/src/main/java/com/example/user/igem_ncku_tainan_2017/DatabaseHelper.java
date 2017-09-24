/*package com.example.user.igem_ncku_tainan_2017;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Database";//Name of database
    private static final int DB_VERSION = 1;//Version of database


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION); //indicate name of database and version
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE GPS"//Database initialize
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "LATITUDE REAL, "
                + "LONGITUDE REAL, "
                + "DATE TEXT);"
        );
        insertGPS(sqLiteDatabase, 22.9990, 120.2196, null);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Data Update function
    private static void insertGPS(SQLiteDatabase sqLiteDatabase, double latitude,
                                  double longitude, Date date) {
        ContentValues GPSValue = new ContentValues();
        GPSValue.put("LATITUDE", latitude);
        GPSValue.put("LONGITUDE", longitude);
        GPSValue.put("DATE", date.toString());
    }
}*/
