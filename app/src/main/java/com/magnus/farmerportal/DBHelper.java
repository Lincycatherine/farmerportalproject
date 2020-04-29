package com.magnus.farmerportal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = DBHelper.class.getSimpleName();
    public static final String DB_NAME = "consumer.db";
    public static final String ColumnID = "id";
    public static final String USER_TABLE = "users";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_CNFRMPASS = "cnfrmpassword";
    public static final String PHONE_NUMBER = "phone";
    public static final String LOCATION = "address";

    public  SQLiteDatabase db ;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 8);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + USER_TABLE +
                "(" + ColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_NAME + " TEXT, " + COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " + COLUMN_CNFRMPASS + " TEXT ," +PHONE_NUMBER +" TEXT, "+LOCATION+
                "TEXT "+")");    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    /* Storing User details*/

    public void addUser(String name, String username, String password, String cnfrmpassword,String phone,String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_CNFRMPASS, cnfrmpassword);
        values.put(PHONE_NUMBER, phone);
        db.insert(USER_TABLE, null, values);
        values.put(LOCATION,location);
        db.close();
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + USER_TABLE, null);
    }
}
