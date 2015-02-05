package com.wifi.scan;

import com.wifi.data.Config;
import com.wifi.data.DetailData;
import com.wifi.exchange.DetailMsgQueryRunnable;
import com.wifi.exchange.ReceiveMsg;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DetailWatchActivity extends Activity {

	private TextView formerstate;
	private TextView dryerstate;
	
	private TextView outcyc;
	private TextView allcyc;
	private TextView mouldcyc;
	
	private TextView nowalarm;
	
	private TextView nownumber;
	private TextView allnumber;
	
	private TextView axlx;
	private TextView axly;
	private TextView axlh;
	private TextView axlz;
	private TextView axll;
	private Button nowmachine_num;
	private Button btn_exit;
	private UpdateUiReceiver BCR;
	private final String TAG ="DetailWatchActivity";
	private DetailMsgQueryRunnable DmqRunable = null;
	private String WatchIp = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_watch);
		String nowmachinenum=getIntent().getStringExtra("nowmachinenum");
		WatchIp=getIntent().getAction();
		if(Config.Userver!=null)
			Config.Userver.Destroy();
		nownumber=(TextView)findViewById(R.id.now_number);
	

		formerstate=(TextView)findViewById(R.id.former_state);

		dryerstate=(TextView)findViewById(R.id.dryer_state);
		if(dryerstate==null){return;}
		
		outcyc=(TextView)findViewById(R.id.periodic_out);
		if(outcyc==null){return;}
		allcyc=(TextView)findViewById(R.id.periodic_full);
		if(allcyc==null){return;}
		mouldcyc=(TextView)findViewById(R.id.periodic_modeling);
		if(mouldcyc==null){return;}
		
		nowalarm=(TextView)findViewById(R.id.now_alarmmsg);
		if(nowalarm==null){return;}
		
		nownumber=(TextView)findViewById(R.id.counter_out_num);
		if(nownumber==null){return;}
		allnumber=(TextView)findViewById(R.id.counter_rejects_num);
		if(allnumber==null){return;}
		
		axlx=(TextView)findViewById(R.id.pos_foot);
		if(axlx==null){return;}
		axly=(TextView)findViewById(R.id.pos_pro_qh);
		if(axly==null){return;}
		axlh=(TextView)findViewById(R.id.pos_pro_sx);
		if(axlh==null){return;}
		axlz=(TextView)findViewById(R.id.pos_mat_qh);
		if(axlz==null){return;}
		axll=(TextView)findViewById(R.id.pos_mat_sx);
		if(axll==null){return;}
		
		formerstate.setText("***");
		dryerstate.setText("***");
		outcyc.setText("****.**");
		allcyc.setText("****.**");
		mouldcyc.setText("****.**");
		nowalarm.setText("无");
		nownumber.setText("***");
		allnumber.setText("***");		
		axlx.setText("*****.*");
		axly.setText("*****.*");
		axlz.setText("*****.*");
//		It.setAction(listData.getIpAdd());
		Log.d(TAG," the  select ip 111 :"+nowmachinenum);
		nowmachine_num=(Button)findViewById(R.id.nowmachine_num);
		if(nowmachine_num==null){
			return;
		}
		nowmachine_num.setText(nowmachine_num.getText()+nowmachinenum);
		btn_exit=(Button)findViewById(R.id.button1);
		if(btn_exit==null){
			return;
		}
		btn_exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent().setClass(DetailWatchActivity.this,WatchActivity.class));
				closeActivity();
			}
		});	
//		BCR = new UpdateUiReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("DetailWatchActivityUpdate");
//        registerReceiver(BCR, filter);
       
	}

   /* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("mpeng","onpause ");	
			
		if(DmqRunable!=null)
		{
			
			DmqRunable.destroy();
			DmqRunable = null;
		}
		//RM.Destroy();

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG,"onResume");
		if(DmqRunable == null)
				DmqRunable = new DetailMsgQueryRunnable(DetailWatchActivity.this, WatchIp);
	
		Thread a1 = new Thread(DmqRunable);
	    a1.start();
	}

private void updateUi()
   {
	   DetailData Data = new DetailData();
		formerstate.setText("456");
		dryerstate.setText("789");
		nownumber.setText("aaa");
		allnumber.setText("bbbb");
		nowalarm.setText("ccc");
		axlx.setText(WatchActivity.Data.getX_Pos());
		axly.setText(WatchActivity.Data.getY_Pos());
		axlz.setText(WatchActivity.Data.getZ_Pos());
		outcyc.setText(WatchActivity.Data.getRemoveCycle());
		allcyc.setText(WatchActivity.Data.getAllCycle());
		mouldcyc.setText(WatchActivity.Data.getMouldeCycle());
	   
   }
	
	/* (non-Javadoc)
 * @see android.app.Activity#onDestroy()
 */
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
//	unregisterReceiver(BCR);
	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_detail_watch, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_mian:
			startActivity(new Intent().setClass(DetailWatchActivity.this,UserActivity.class));
			closeActivity();
			break;
		case R.id.menu_watch:
			startActivity(new Intent().setClass(DetailWatchActivity.this,WatchActivity.class));
			closeActivity();
			break;
		case R.id.menu_save:
			startActivity(new Intent().setClass(DetailWatchActivity.this,SaveWatchActivity.class));
			this.finish();
			break;
		default:
			// 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		}
		// 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}
	

    private void closeActivity()
    {
    	this.finish();
    }
    
	 private class UpdateUiReceiver extends BroadcastReceiver
	    {
	        @Override
	        public void onReceive(Context context, Intent intent)
	        {
	            String action = intent.getAction();
	            // mute
	            if (action.equals("DetailWatchActivityUpdate"))
	            {
	                Log.i(TAG,
	                    "--------------recieved----1---------");
	                updateUi();
	            }
	        }
	    }

}
