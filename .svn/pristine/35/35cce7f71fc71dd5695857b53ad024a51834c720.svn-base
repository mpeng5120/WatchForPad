package com.wifi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlHelper extends SQLiteOpenHelper {
 
    private static final String DB_NAME = "mydata.db"; //数据库名称
    public static final String TABLE_NAME  = "Alldevice"; //表名
    public static final String TABLE_two   = "Activedevice"; //表名
    public static final String TABLE_three = "HangUpdevice"; //表名   
    private static final int version = 1; //数据库版本
     
    public MySqlHelper(Context context) {
    	
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	//Create table
//"create table aa(id Integer primary key autoincrement,
//    	lawtitle varchar(50),
//    	lawZhang varchar(50),
//    	zhangContent varchar(50),
//    	lawTiao varchar(50),
//    	tiaoContent varchar(2000));"; 
    	Log.d("MySqlHelper", "onCreate");
    	String all_device = "CREATE TABLE "+TABLE_NAME + "("
    						  + "id INTEGER PRIMARY KEY,"
    						  + "number INTEGER,"
    						  + "name TEXT," 
    						  + "wifi TEXT,"
    						  + "otherinfo TEXT,"
    						  + "status BOOLEAN);";
//    	String active_device = "CREATE TABLE "+TABLE_NAME + "("
//				  + "id INTEGER PRIMARY KEY,"
//				  + "number INTEGER,"
//				  + "name TEXT," 
//				  + "wifi TEXT,"
//				  + "oterinfo TEXT);";
//    	String hangup_device = "CREATE TABLE "+TABLE_NAME + "("
//				  + "id INTEGER PRIMARY KEY,"
//				  + "number INTEGER,"
//				  + "name TEXT," 
//				  + "wifi TEXT,"
//				  + "oterinfo TEXT);";
    	db.execSQL(all_device);
//    	db.execSQL(active_device);
//    	db.execSQL(hangup_device);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    	Log.d("MySqlHelper", "onUpgrade");
 
    }
 
}