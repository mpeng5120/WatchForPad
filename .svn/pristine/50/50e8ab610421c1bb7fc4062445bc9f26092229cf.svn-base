package com.wifi.exchange;

import java.net.DatagramPacket;
import java.util.ArrayList;

import com.wifi.data.BaseData;
import com.wifi.data.Config;
import com.wifi.data.HexDecoding;
import com.wifi.data.WatchAdapter;
import android.app.Activity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 复位状态查询
 * @author 李婷婷
 *
 */
public class DetailMsgQueryRunnable implements Runnable {
	
	protected static final String TAG = "DetailMsgQueryRunnable";
	//整合好的plc地址加上偏移的地址（计数器）
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x1000B220,20);//(0x10007000, 32);
	private static boolean selfDestroy_flag;
	public static boolean existFlag = false;
	private ReceiveMsg RM;
	
	
	
	/**
	 * 构造函数
	 * @param targetActivity
	 * @param viewbeingChangedArrayList
	 */
	public DetailMsgQueryRunnable(Activity targetActivity,String ip) {
		Log.d("mpeng","DetailMsgQueryRunnable");
		selfDestroy_flag = true;
		Config.SendBlank = true;		
		RM = new ReceiveMsg(targetActivity,ip);
		RM.start();
		
	}
	
	
	
	
	
	
	
	/** 读取伺服参数时的通信线程收到信息时的处理函数. */
	private  ChatListenerByte ReadDataFeedback = new ChatListenerByte() {

		@Override
		public void onChat(byte[] message) {
			// TODO 自动生成的方法存根
			Log.e("mpeng"," recive message :"+message.length);
			Config.SendBlank = true;
			
		}
		
	};
	
	//销毁该线程
	public void destroy() {
			System.out.println("计数器线程关闭");		
			selfDestroy_flag=false;
			existFlag = false;
			RM.Destroy();
	}
	
	@Override
	public void run() {
		try {
		while (selfDestroy_flag) {	
			if (selfDestroy_flag&&RM!=null&&Config.SendBlank) {
				Log.e("mpeng"," send message !!");
				            Config.SendBlank = false;
							Thread.sleep(170);					
							RM.SendMsg(formatReadMessage,ReadDataFeedback);
						}	
		   }
		} catch (Exception e) {
		e.printStackTrace();
		destroy();
	}

	}

	
	
	
}
