package com.wifi.database;

import java.util.ArrayList;
import java.util.List;

import com.wifi.data.BaseData;

import android.app.DownloadManager.Query;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler.Value;
import android.util.Log;

public class DBUtil {
	
	private  MySqlHelper myHelper;
	public DBUtil(Context context){
		super();
		myHelper = new MySqlHelper(context);
		
	}
	
	/*
	 * 查询	
	 * */
	
	public ArrayList<BaseData> QueryAll(){
		SQLiteDatabase db = myHelper.getReadableDatabase();		
		ArrayList<BaseData> data = new ArrayList<BaseData>();
		Cursor cs = db.query(MySqlHelper.TABLE_NAME, null, null, null, null, null, null);
		while(cs.moveToNext()){
			BaseData  bd = new BaseData();
			bd.setId(cs.getString(cs.getColumnIndex("id")));
			bd.setDevNumber(cs.getString(cs.getColumnIndex("number")));
			bd.setIpAdd(cs.getString(cs.getColumnIndex("wifi")));
			bd.setDeviceName(cs.getString(cs.getColumnIndex("name")));
			bd.setInfo(cs.getString(cs.getColumnIndex("otherinfo")));	
			bd.setStatus(cs.getString(cs.getColumnIndex("status")));	
			data.add(bd);			
		}
		db.close();	
		
		return data;
		
	}
	
	
	/*
	 * 插入
	 * */
	public boolean  Insert(BaseData bd){
		
		SQLiteDatabase db = myHelper.getWritableDatabase();
		String sql = "insert into" +myHelper.TABLE_NAME
				      +"(id,number,name,wifi,otherinfo)"+"Values("+"'"+bd.getId()+","+bd.getDevNumber()+","
				      +bd.getDeviceName()+","+bd.getIpAdd()+","+bd.getInfo()+"'"+")";
		try {            
			db.execSQL(sql); 
			return true;
			} catch (SQLException e){  
					Log.e("err", "insert failed"); 
				return false;
				}finally{
					db.close();
				}
	}
	
	/*
	 * 更新
	 * 
	 * */
	
	public void Update(BaseData bd,int id)
	{
		SQLiteDatabase db = myHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();		
		cv.put("number", bd.getDevNumber());
		cv.put("wifi",bd.getIpAdd() );
		cv.put("name",bd.getDeviceName() );
		cv.put("otherinfo",bd.getInfo() );
		cv.put("status", bd.getStatus());
		int rows = db.update(myHelper.TABLE_NAME, cv, "id=?", new String[] { id + "" });	
		Log.d("mpeng"," the rows is "+rows +"bd.getSta()"+bd.getStatus());
		db.close();
 
	}
	
	
	/*删除
	 * */
	
	public void Delete(int id){
		SQLiteDatabase db = myHelper.getWritableDatabase();
		int rows = db.delete(myHelper.TABLE_NAME, "_id=?", new String[] { id + "" });
		db.close();
	}
	/**
	 * 删除表中的数据
	 *  
	 */

	public boolean deleteTableByDBName( String TableName) { 
	SQLiteDatabase db =myHelper.getWritableDatabase(); 
	db.delete(TableName, null, null); 
	db.close();

	return false; 
	} 
	/**
	 * 根据名称查询
	 */
	 
    /**
     * query by ip 
     */
	
	 /**
     * query by id 
     */

}
