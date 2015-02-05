package com.wifi.scan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.isea.list2excel.ExpListObjExcelVS;
import com.wifi.data.AlmWatchAdapter;
import com.wifi.data.BaseData;
import com.wifi.data.Config;
import com.wifi.data.DetailData;
import com.wifi.data.WatchAdapter;
import com.wifi.database.DBUtil;
import com.wifi.exchange.ReceiveMsg;
import com.wifi.exchange.SendBroadCastToMain;
import com.wifi.exchange.UdpServerHelper;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WatchActivity extends Activity {

	private ListView main_Watch_ListView;
	private ListView main_almWatch_ListView;
	private Timer timer;
	private int index=0;
	private int number=0;
	private UpdateUiReceiver BCR;
	private ReceiveMsg  RM ;
	private WatchAdapter listItemAdapter ;
	private AlmWatchAdapter almlistItemAdapter ;
	private String TAG= "WatchActivity";
	private SendBroadCastToMain sbc = null;
	public static DetailData Data =null;
	private static Timer savetimer;
	private SharedPreferences preferenceswatch;//程序运行次数
	private WifiManager manager  ;
	private WifiManager.MulticastLock lock;
	private long mExitTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch);
		preferenceswatch = getSharedPreferences("countwatch",MODE_WORLD_READABLE); 
		int count = preferenceswatch.getInt("countwatch", 0); 
		//判断程序与第几次运行，如果是第一次运行则重装文件系统（trwatchitem.xml） 
		System.out.println("count="+count);
		if (count == 0) { 
			copyFromRawToSD();// 将预先放好的资源拷贝到sd，以备后续使用			
		} 
		Editor editor = preferenceswatch.edit(); 
		//存入数据 
		editor.putInt("countwatch", ++count); 
		//提交修改 
		editor.commit();
		
		Config.checkBoxpref1= getSharedPreferences("checkBoxp1",MODE_WORLD_READABLE);
		Config.checkBoxpref2= getSharedPreferences("checkBoxp2",MODE_WORLD_READABLE);
		Config.checkBoxpref3= getSharedPreferences("checkBoxp3",MODE_WORLD_READABLE);
		Config.checkBoxpref4= getSharedPreferences("checkBoxp4",MODE_WORLD_READABLE);
		Config.spinnerpref1= getSharedPreferences("spinnerp1",MODE_WORLD_READABLE);
		Config.spinnerpref2= getSharedPreferences("spinnerp2",MODE_WORLD_READABLE);
		Config.spinnerpref3= getSharedPreferences("spinnerp3",MODE_WORLD_READABLE);
		Config.spinnerpref4= getSharedPreferences("spinnerp4",MODE_WORLD_READABLE);
		Config.dateWeekpref1= getSharedPreferences("dateWeekp1",MODE_WORLD_READABLE);
		Config.dateWeekpref2= getSharedPreferences("dateWeekp2",MODE_WORLD_READABLE);
		Config.dateWeekpref3= getSharedPreferences("dateWeekp3",MODE_WORLD_READABLE);
		Config.dateWeekpref4= getSharedPreferences("dateWeekp4",MODE_WORLD_READABLE);
		Config.timepref1= getSharedPreferences("timep1",MODE_WORLD_READABLE);
		Config.timepref2= getSharedPreferences("timep2",MODE_WORLD_READABLE);
		Config.timepref3= getSharedPreferences("timep3",MODE_WORLD_READABLE);
		Config.timepref4= getSharedPreferences("timep4",MODE_WORLD_READABLE);
		Data = new DetailData();
		main_Watch_ListView = (ListView)findViewById(R.id.listView_watch);
		if(main_Watch_ListView==null){
			return;
		}
		
		main_almWatch_ListView=(ListView)findViewById(R.id.listView_abwatch);
		if(main_almWatch_ListView==null){
			return;
		}
		DBUtil DB = new DBUtil(WatchActivity.this);
		Config.devicelist = DB.QueryAll();
		
		for(int i=0;i<Config.devicelist.size();i++){
			if(Config.devicelist.get(i).getStatus().equals("否")){
				Config.devicelist.remove(i);
				i--;
			}
		}

		try{
			if(savetimer==null){
			savetimer = new Timer();
			savetimer.schedule(new saveTask(),
		               2000,        //initial delay
		               60*1000);  //subsequent rate
			}
	    }catch(Exception e){
		   e.printStackTrace();
	    }
		listItemAdapter = new WatchAdapter(WatchActivity.this, Config.devicelist);
		main_Watch_ListView.setAdapter(listItemAdapter);
		
		almlistItemAdapter = new AlmWatchAdapter(WatchActivity.this,Config.alarmdevicelist);
		main_almWatch_ListView.setAdapter(almlistItemAdapter);
		
		main_Watch_ListView
		.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// 选中红色表示
				//listItemAdapter.setSelectItem(position);
				//listItemAdapter.notifyDataSetChanged();
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					mExitTime = System.currentTimeMillis();
				} else {
					Intent It = new Intent();
					BaseData listData =  (BaseData) main_Watch_ListView.getItemAtPosition(position);
					Log.d(TAG," the  select ip :"+listData.getIpAdd());
					It.putExtra("nowmachinenum", listData.getDeviceName());
					It.setAction(listData.getIpAdd());
					It.setClass(WatchActivity.this,DetailWatchActivity.class);
					startActivity(It);
					closeActivity();
				}
				}
		});
		BCR = new UpdateUiReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateListView");
        registerReceiver(BCR, filter);
		this.init();
		
	}
	
	/**
	 * 
	 * @Title: copyFromRawToSD
	 * @Description: 将程序中的raw文件夹下的文件都拷贝到SD中的raw文件夹中
	 * @return void
	 * @throws
	 */
	private void copyFromRawToSD() {
		String trwatchPATH = Config.trwatchPATH;
		File rawDir = new File(trwatchPATH);
		if (!rawDir.exists()) {
			rawDir.mkdirs();
		}
		Field[] raw = R.raw.class.getFields();// 利用反射得到所有文件，不用知道具体的名称
		for (Field r : raw) {
			try {
				System.out.println("R.raw." + r.getName());
				int id = getResources().getIdentifier(r.getName(), "raw",
						getPackageName());
				if (r.getName().contains("trwatchitem")) {
					String path = trwatchPATH + r.getName() + ".xml";
					BufferedOutputStream outBuff = new BufferedOutputStream(
							(new FileOutputStream(new File(path))));
					BufferedInputStream inBuff = new BufferedInputStream(
							getResources().openRawResource(id));
					byte[] buff = new byte[20 * 1024];
					int len;
					while ((len = inBuff.read(buff)) > 0) {
						outBuff.write(buff, 0, len);
					}
					outBuff.flush();
					outBuff.close();
					inBuff.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	private void savework(String fileName){
		List<BaseData> list = new ArrayList<BaseData>();
		list =Config.devicelist;
		String xmlPath = Config.trwatchPATH+"trwatchitem.xml";  
        String filePath = Config.trwatchPATH;  
		try {
			ExpListObjExcelVS.expListObjFile(list, xmlPath, filePath, fileName);
			Toast.makeText(WatchActivity.this,"已成功保存监视信息到sd卡", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(WatchActivity.this,"保存监视信息到sd卡失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		String udiskPATH = "/mnt/sda1/";
        File file = new File(udiskPATH);
		if (file.exists()){
			try {
				ExpListObjExcelVS.expListObjFile(list, xmlPath, udiskPATH, fileName);
				Toast.makeText(WatchActivity.this,"已成功保存监视信息到U盘", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(WatchActivity.this,"保存监视信息到U盘失败", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		}
	}
	 class saveTask extends TimerTask {
	        public void run() {
	        	try{
	        		final Calendar calendar=Calendar.getInstance();
	                int cday=calendar.get(Calendar.DAY_OF_MONTH);
	                int cweek = calendar.get(Calendar.DAY_OF_WEEK);
	                int chour=calendar.get(Calendar.HOUR_OF_DAY);
	                int cmin=calendar.get(Calendar.MINUTE);
	                String date = new SimpleDateFormat("yyyy-MM-dd  HH：mm").format(calendar.getTime());
	                
	                int checkBox1 = Config.checkBoxpref1.getInt("checkBoxp1", 0); 
	                int spinner1 = Config.spinnerpref1.getInt("spinnerp1", 0);
	                int dateWeek1 = Config.dateWeekpref1.getInt("dateWeekp1", 0);
	                String time1 = Config.timepref1.getString("timep1", "");
	                int hour1=0; int min1=0;
	                if(!time1.equals("")){
	                	hour1=Integer.parseInt(time1.split(":")[0]);
	                	min1=Integer.parseInt(time1.split(":")[1]);
	                }
	                System.out.println("checkBox1="+Config.checkBoxpref1.getInt("checkBoxp1", 0));
	                System.out.println("spinner1="+Config.spinnerpref1.getInt("spinnerp1", 0));
	                System.out.println("dateWeek1="+Config.dateWeekpref1.getInt("dateWeekp1", 0));
	                System.out.println("hour1="+hour1);
	                System.out.println("min1="+min1);
	                if(checkBox1==1){
	                	if(spinner1==1){
	                		if(cday==dateWeek1&&chour==hour1&&cmin==min1){
	 	                	   System.out.println("如果每月保存一次");
	 	                	   savework("生产情况"+date);
	 	                    }
	                	}else if(spinner1==2){
	                		if(cweek==dateWeek1&&chour==hour1&&cmin==min1){
		 	                   System.out.println("如果每周保存一次");
		 	                   savework("生产情况"+date);
		 	                }
	                	}else if(spinner1==3){
	                		if(chour==hour1&&cmin==min1){
			 	               System.out.println("如果每天保存一次");
			 	               savework("生产情况"+date);
			 	            }
	                	}	                	
	                }
	                
	                int checkBox2 = Config.checkBoxpref2.getInt("checkBoxp2", 0); 
	                int spinner2 = Config.spinnerpref2.getInt("spinnerp2", 0);
	                int dateWeek2 = Config.dateWeekpref2.getInt("dateWeekp2", 0);
	                String time2 = Config.timepref2.getString("timep2", "");
	                int hour2=0; int min2=0;
	                if(!time2.equals("")){
	                	hour2=Integer.parseInt(time2.split(":")[0]);
	                	min2=Integer.parseInt(time2.split(":")[1]);
	                }
	                if(checkBox2==1){
	                	if(spinner2==1){
	                		if(cday==dateWeek2&&chour==hour2&&cmin==min2){
	 	                	   System.out.println("如果每月保存一次");
	 	                	   savework("生产情况"+date);
	 	                    }
	                	}else if(spinner2==2){
	                		if(cweek==dateWeek2&&chour==hour2&&cmin==min2){
		 	                   System.out.println("如果每周保存一次");
		 	                   savework("生产情况"+date);
		 	                }
	                	}else if(spinner2==3){
	                		if(chour==hour2&&cmin==min2){
			 	               System.out.println("如果每天保存一次");
			 	               savework("生产情况"+date);
			 	            }
	                	}	                	
	                }
	                
	                int checkBox3 = Config.checkBoxpref3.getInt("checkBoxp3", 0); 
	                int spinner3 = Config.spinnerpref3.getInt("spinnerp3", 0);
	                int dateWeek3 = Config.dateWeekpref3.getInt("dateWeekp3", 0);
	                String time3 = Config.timepref3.getString("timep3", "");
	                int hour3=0; int min3=0;
	                if(!time3.equals("")){
	                	hour3=Integer.parseInt(time3.split(":")[0]);
	                	min3=Integer.parseInt(time3.split(":")[1]);
	                }
	                if(checkBox3==1){
	                	if(spinner3==1){
	                		if(cday==dateWeek3&&chour==hour3&&cmin==min3){
	 	                	   System.out.println("如果每月保存一次");
	 	                	   savework("生产情况"+date);
	 	                    }
	                	}else if(spinner3==2){
	                		if(cweek==dateWeek3&&chour==hour3&&cmin==min3){
		 	                   System.out.println("如果每周保存一次");
		 	                   savework("生产情况"+date);
		 	                }
	                	}else if(spinner3==3){
	                		if(chour==hour3&&cmin==min3){
			 	               System.out.println("如果每天保存一次");
			 	               savework("生产情况"+date);
			 	            }
	                	}	                	
	                }
	                
	                int checkBox4 = Config.checkBoxpref4.getInt("checkBoxp4", 0); 
	                int spinner4 = Config.spinnerpref4.getInt("spinnerp4", 0);
	                int dateWeek4 = Config.dateWeekpref4.getInt("dateWeekp4", 0);
	                String time4 = Config.timepref4.getString("timep4", "");
	                int hour4=0; int min4=0;
	                if(!time4.equals("")){
	                	hour4=Integer.parseInt(time4.split(":")[0]);
	                	min4=Integer.parseInt(time4.split(":")[1]);
	                }
	                if(checkBox4==1){
	                	if(spinner4==1){
	                		if(cday==dateWeek4&&chour==hour4&&cmin==min4){
	 	                	   System.out.println("如果每月保存一次");
	 	                	   savework("生产情况"+date);
	 	                    }
	                	}else if(spinner4==2){
	                		if(cweek==dateWeek4&&chour==hour4&&cmin==min4){
		 	                   System.out.println("如果每周保存一次");
		 	                   savework("生产情况"+date);
		 	                }
	                	}else if(spinner4==3){
	                		if(chour==hour4&&cmin==min4){
			 	               System.out.println("如果每天保存一次");
			 	               savework("生产情况"+date);
			 	            }
	                	}	                	
	                }
	           }catch(Exception e){
	 		        e.printStackTrace();
	 	       }
	        }
	    }
	private void closeActivity()
	{
		this.finish();
	}
	private void init()
	{
		Log.d(TAG,"init.start receive!!");
//		RM = new ReceiveMsg(WatchActivity.this);
//		RM.start();

			 manager = (WifiManager) this
	                .getSystemService(Context.WIFI_SERVICE);
			
	         lock= manager.createMulticastLock("test wifi");
	    
	}

	@Override
	public void onResume() {
		super.onResume();

		try{
	    	listViewScroll(5);
	    }catch(Exception e){
		   e.printStackTrace();
	    }
		Log.e(TAG,"ONRESUME ");
		if(Config.Userver==null)
		{

	        Config.Userver = new UdpServerHelper(WatchActivity.this, manager,lock);
	        Thread a1 = new Thread(Config.Userver);
	        a1.start();
		}  
		 sbc = new SendBroadCastToMain(WatchActivity.this,listItemAdapter,almlistItemAdapter);
		Thread a2 = new Thread(sbc);
        a2.start();
	}
	public void listViewScroll(int seconds) {
		try{
		    timer = new Timer();
		    timer.schedule(new RemindTask(),
		               500,        //initial delay
		               5000);  //subsequent rate
	    }catch(Exception e){
		   e.printStackTrace();
	    }
	}
	
	  class RemindTask extends TimerTask {

	        public void run() {
	        	try{

	        	//main_Watch_ListView.smoothScrollToPosition(index);
	        	Runnable pointShowRunnable = new Runnable() {
					@Override
					public void run() {
						try{
							main_Watch_ListView.requestFocusFromTouch();//模拟手触功能
				        	main_Watch_ListView.setSelection(index);
					    } catch (Exception e) {
						    e.printStackTrace();
					    }
					}
					
				};
				runOnUiThread(pointShowRunnable);

				number=main_Watch_ListView.getLastVisiblePosition()-main_Watch_ListView.getFirstVisiblePosition();
				index +=number;
	        	if(index >= main_Watch_ListView.getCount()) 
	        	{    
	        		index = 0;
	        	}
	        }catch(Exception e){
	 		   e.printStackTrace();
	 	    }
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_watch, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_mian:
			startActivity(new Intent().setClass(WatchActivity.this,UserActivity.class));
			this.finish();
			break;
		case R.id.menu_save:
			startActivity(new Intent().setClass(WatchActivity.this,SaveWatchActivity.class));
			this.finish();
			break;
		default:
			// 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		}
		// 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(sbc!=null)
		{
			
			sbc.destroy();
			sbc =null;
		}
		if(timer!=null)
		{
			timer.cancel();
			timer = null;
		}
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#
()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		RM.Destroy();
		unregisterReceiver(BCR);
//		timer.cancel();
		Log.d(TAG,"ONDESTROY!!");
	}
	private void updateList(){
//		listItemAdapter = new WatchAdapter(WatchActivity.this, Config.devicelist);
//		main_Watch_ListView.setAdapter(listItemAdapter);
		if(listItemAdapter==null||almlistItemAdapter == null
				||Config.devicelist == null||Config.alarmdevicelist == null)
			return;
		Config.alarmdevicelist.clear();
		listItemAdapter.notifyDataSetChanged();
		try{
		for(int i=0;i<Config.devicelist.size();i++){
			if(Config.devicelist.get(i).getRunningStatus().equals("警报")){
				Config.alarmdevicelist.add(Config.devicelist.get(i));	
				Log.d(TAG,"alarm list add !");
			}
		}
	    } catch (Exception e) {
	    		e.printStackTrace();
	    }
//		almlistItemAdapter = new AlmWatchAdapter(WatchActivity.this, Config.devicelist);
//		main_almWatch_ListView.setAdapter(almlistItemAdapter);
		almlistItemAdapter.notifyDataSetChanged();
		
	}
	
	
	 private class UpdateUiReceiver extends BroadcastReceiver
	    {
	        @Override
	        public void onReceive(Context context, Intent intent)
	        {
	            String action = intent.getAction();
	            // mute
	            if(action.equals("updateListView"))
	            {
	                Log.i(TAG,"--------------recieved----1---------");
	                updateList();
	            }
	        }
	    }
 }
