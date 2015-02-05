/**
 * 
 */
package com.wifi.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.wifi.data.BaseData;
import com.wifi.data.Config;
import com.wifi.scan.UserActivity;

import jxl.Sheet;
import jxl.Workbook;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author Administrator
 *
 */
public class ExcelToDb implements Runnable{
	
	private ArrayList<BaseData> list = new ArrayList<BaseData>();
	private BaseData data;
	private Context ct ;
	
	
	/**
	 * @param userActivity
	 */
	public ExcelToDb(Context context) {
		// TODO Auto-generated constructor stub
		ct = context;
	}
	
	public void run() {
		try {
			PutExcelTodb();			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			Intent intent = new Intent();
			intent.setAction("updateList");
//			intent.putExtra(name, value);
//			UserActivity.update();
			ct.sendBroadcast(intent);//传递过去
		}
	
	}
	
	private void PutExcelTodb()
	{

		Log.i("ExcelToDb"," thead start!!!");
		MySqlHelper database = new MySqlHelper(ct);// 这段代码放到Activity类中才用this
		SQLiteDatabase db = null;
		db = database.getReadableDatabase();
		//导入之前先清除表的数据
		db.delete(MySqlHelper.TABLE_NAME, null, null);
		try {
			Workbook book = Workbook.getWorkbook(new File(
					Config.trwatchPATH+"setting.xls"));
			// get a Sheet object.
			Sheet sheet = book.getSheet(0);
			int a = sheet.getRows();// 列
			int b = sheet.getColumns();// 行
			System.out.println(a + "--" + b);
			// get 1st-Column,1st-Row content.
			// 遍历打印所有的数据
			for (int i = 0; i < a; i++) {
				data = new BaseData();
				data.setId(sheet.getCell(0, i).getContents());
				data.setDevNumber(sheet.getCell(1, i).getContents());
				data.setDeviceName(sheet.getCell(2, i).getContents());
				data.setIpAdd(sheet.getCell(3, i).getContents());
				data.setInfo(sheet.getCell(4, i).getContents());	
				data.setStatus(sheet.getCell(5, i).getContents());
				list.add(data);
			}
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {

			String sql = "insert into "+MySqlHelper.TABLE_NAME+"(number,name,wifi,otherinfo,status) values("
					+"'"
					+ list.get(i).getDevNumber()
					+ "','"
					+ list.get(i).getDeviceName()
					+ "','"
					+ list.get(i).getIpAdd()
					+ "','"
					+ list.get(i).getInfo()
					+ "','"
					+ list.get(i).getStatus() + "');";
			db.execSQL(sql);// 执行SQL语句
		}
		
	
	}

	


	
}
