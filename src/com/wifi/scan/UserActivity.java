package com.wifi.scan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.wifi.data.BaseData;
import com.wifi.data.Config;
import com.wifi.data.DeviceAdapter;
import com.wifi.database.DBUtil;
import com.wifi.database.ExcelToDb;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends Activity {

		private static final String TAG = "UserActivity";
		public static final int LIST_FLUSH = 1;
		public static final int FILE_TISHI = 2;
		public static final int FILE_JINDU = 3;
		public static final int PROGRESS_FLUSH = 4;
		public static final int PROGRESS_COL = 5;
		public static final int SHOW = 0;
		
		public ListView listView = null;
		private Button update  ;
		private Button updatedata  ;
		private TextView title ;
		private TextView Localipadd;
		//private NetTool NT ;
		
		private Button Local ;
		
		
		private Timer timer;
		private int index = 0;
		
		public static ArrayList<HashMap<String, Object>> deviceList;
		
		public Handler myHandler = null;
		private UpdateUiReceiver BCR ;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_scan);
			this.getWindowManager().getDefaultDisplay().getRotation();
			BCR = new UpdateUiReceiver();
	        IntentFilter filter = new IntentFilter();
	        filter.addAction("updateList");
	        registerReceiver(BCR, filter);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			myHandler = new Handler()
			{

				public void handleMessage(Message msg)
				{
					switch(msg.what)
					{
					case 11111:
						DeviceAdapter listItemAdapter = new DeviceAdapter(UserActivity.this, Config.devicelist);
						listView.setAdapter(listItemAdapter);
						Log.d("mpeng","handler set adapter");
						Reminder(5);
					break;
					case 22222:
							ExcelToDb runnable = new ExcelToDb(UserActivity.this);
							Thread uu =   new Thread(runnable);
							uu.start();	
						break;
					default:
						Log.e(TAG,"message is empty!!!");
						break;
					}
					
					super.handleMessage(msg);
				}
			
			};
			//NT = new NetTool(this.getApplicationContext());
			this.init();
			this.initData();
		
			
		}
        public void initData()
        {
        	Config.devicelist = new ArrayList<BaseData>();
        }
		public ArrayList<HashMap<String, Object>> getDevicelist() {
			return deviceList;			
		}
		
		public static void setDeviceList(ArrayList<HashMap<String, Object>> IpAddr)
		{
			deviceList = IpAddr;
		}
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		    Log.d(TAG,"Onresume");
		    deviceList = new ArrayList<HashMap<String, Object>>();
		    update();
			
		}

		@Override
		// �����˵�
		public boolean onCreateOptionsMenu(Menu menu) {
			getMenuInflater().inflate(R.menu.activity_scan, menu);
			// menu.add(Menu.NONE, Menu.FIRST + 1, 5, "ɾ��").setIcon(
			//
			// android.R.drawable.ic_menu_delete);

			// setIcon()����Ϊ�˵�����ͼ�꣬����ʹ�õ���ϵͳ�Դ���ͼ�꣬ͬѧ������һ��,��

			// android.R��ͷ����Դ��ϵͳ�ṩ�ģ������Լ��ṩ����Դ����R��ͷ��

			// menu.add(Menu.NONE, Menu.FIRST + 2, 2, "����").setIcon(
			//
			// android.R.drawable.ic_menu_edit);
			//
			// menu.add(Menu.NONE, Menu.FIRST + 3, 6, "����").setIcon(
			//
			// android.R.drawable.ic_menu_help);

			//menu.add(Menu.NONE, Menu.FIRST + 4, 1, "����").setIcon(android.R.drawable.ic_menu_manage);

			// menu.add(Menu.NONE, Menu.FIRST + 5, 4, "��ϸ").setIcon(
			//
			// android.R.drawable.ic_menu_info_details);
			//
			// menu.add(Menu.NONE, Menu.FIRST + 6, 3, "����").setIcon(
			//
			// android.R.drawable.ic_menu_send);

			return true;

		}
		public boolean onOptionsItemSelected(MenuItem item) {

			switch (item.getItemId()) {
			case R.id.menu_watch:
				int flag=0;
				for(int i=0;i<Config.devicelist.size();i++){
					if(Config.devicelist.get(i).getStatus().equals("��")){
						flag=1;
						break;
					}
				}
				if(flag==1){
					startActivity(new Intent().setClass(UserActivity.this,WatchActivity.class));
				}else{
					Toast.makeText(getApplicationContext(), "�������Ӽ����豸", 300).show();
				}
				break;
			case R.id.menu_save:
				startActivity(new Intent().setClass(UserActivity.this,SaveWatchActivity.class));
				this.finish();
				break;
			default:
				// ��û�д�����¼�����������������
				return super.onOptionsItemSelected(item);
			}
			// ����true��ʾ������˵�����¼�������Ҫ�����¼�����������ȥ��
			return true;
		}
	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        unregisterReceiver(BCR);
	    }


		// ��ʼ��
		public void init() {		
			Local = (Button)findViewById(R.id.button1);
			Local.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i(TAG,"local data update begain!");
					Message msg = new Message();
					msg.what = 22222;
					myHandler.sendMessage(msg);
				}
			});
			listView = (ListView) super.findViewById(R.id.listView);					
			
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			 update();
			

			}



		public void Reminder(int seconds) {
	        timer = new Timer();
	        timer.schedule(new RemindTask(), seconds*1000);
	    }

	    class RemindTask extends TimerTask {
	        public void run() {
	            System.out.println("Time's up!");
	            listView.smoothScrollToPosition(index);
				index +=4;
				if(index >= listView.getCount()) {
				    index = 0;
	            timer.cancel(); //Terminate the timer thread
	        }
	    }
	}
	    
	public  void  update()
	{
		Log.i(TAG,"update ui");
		DBUtil DB = new DBUtil(UserActivity.this);
		Config.devicelist = DB.QueryAll();
		DeviceAdapter listItemAdapter = new DeviceAdapter(UserActivity.this, Config.devicelist);
		listView.setAdapter(listItemAdapter);
		listItemAdapter.notifyDataSetChanged();    
	}
	    
    private class UpdateUiReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            // mute
            if (action.equals("updateList"))
            {
                Log.i("UpdateUiReceiver",
                    "--------------recieved----1---------");
                update();
               

            }
        }
    }
}
