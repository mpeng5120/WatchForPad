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
 * ��λ״̬��ѯ
 * @author ������
 *
 */
public class DetailMsgQueryRunnable implements Runnable {
	
	protected static final String TAG = "DetailMsgQueryRunnable";
	//���Ϻõ�plc��ַ����ƫ�Ƶĵ�ַ����������
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x1000B220,20);//(0x10007000, 32);
	private static boolean selfDestroy_flag;
	public static boolean existFlag = false;
	private ReceiveMsg RM;
	
	
	
	/**
	 * ���캯��
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
	
	
	
	
	
	
	
	/** ��ȡ�ŷ�����ʱ��ͨ���߳��յ���Ϣʱ�Ĵ�����. */
	private  ChatListenerByte ReadDataFeedback = new ChatListenerByte() {

		@Override
		public void onChat(byte[] message) {
			// TODO �Զ����ɵķ������
			Log.e("mpeng"," recive message :"+message.length);
			Config.SendBlank = true;
			
		}
		
	};
	
	//���ٸ��߳�
	public void destroy() {
			System.out.println("�������̹߳ر�");		
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
