/**
 * 
 */
package com.wifi.exchange;

/**
 * @author Administrator
 *
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.wifi.data.Config;
import com.wifi.data.DetailData;
import com.wifi.data.HexDecoding;
import com.wifi.scan.WatchActivity;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;


public class ReceiveMsg {
	
	private Activity targetActivity;

	private String WatchIp=null;
	
	private StartReceive ReceThread ;
	private boolean status = false;
	private DatagramSocket	eSocket = null ;
	private  static ChatListenerByte chatListener;
	
	private static WifiReadDataFormat frm;
	private static DatagramPacket eRecPacket;
	private DatagramSocket esendSocket;
	
	private final static String TAG = "ReceiveMsg";
	private static boolean IsSendStatus ;
	private static Handler myhandler = new Handler(){
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == LittleDownTimer.TIME_OUT_MSG)
			{
				Log.e(TAG,"time out !!!");
				LittleDownTimer.stopMenu();	
				if(eRecPacket!=null&&chatListener!=null)
					chatListener.onChat(eRecPacket.getData());	
				//在这里处理消息，通知UI更新，
				Config.SendBlank =true;
				IsSendStatus = false;
			
			}
		}
};

	public  ReceiveMsg(Activity tarActivity)
	{
		this.targetActivity  =tarActivity;	
		
	}
	private static void setOnChatListener(ChatListenerByte listener) {
		chatListener = listener;
	}
	public  ReceiveMsg(Activity tarActivity,String ip)
	{
		this.targetActivity  =tarActivity;	
		this.WatchIp = ip;
		IsSendStatus = false;

		LittleDownTimer.getInstance().start();		
		LittleDownTimer.setHandler(myhandler);
		LittleDownTimer.stopMenu();
	}
	public void start(){
		if(ReceThread == null)
			{
				Log.d(TAG,"start!!!");
				status = true;
				ReceThread = new StartReceive();
				ReceThread.start();
			}
		
	}

	private void handleData(byte[] data)
	{
		getdata(data);
		Intent intent = new Intent();
		intent.setAction("DetailWatchActivityUpdate");
		this.targetActivity.sendBroadcast(intent);//传递过去
	}

	
	public void ReceiveMsg() throws IOException
	{

	    eSocket = new DatagramSocket(8903);	
		byte[] data = new byte[1024];
		eRecPacket = new DatagramPacket(data, data.length);
		eRecPacket.setData(data);
		eSocket.receive(eRecPacket);		
		Log.i("mpeng","the name is:"+eRecPacket.getAddress().getHostName() +"the ip is:"+eRecPacket.getAddress().getHostAddress());
		if(eRecPacket.getLength()>0)
		{
			LittleDownTimer.stopMenu();
			Config.SendBlank =true;
			IsSendStatus = false;
		}
		eSocket.close();	
	}
	
	
	public void SendMsg(WifiReadDataFormat formatmessage ,ChatListenerByte lsitener) {
		try {
			if(!IsSendStatus)
			{
				Log.e("mpeng"," send msg re !!");
				LittleDownTimer.resetMenu();				
				frm = formatmessage;
				IsSendStatus = true;
				byte[] message = frm.sendDataFormat();
				esendSocket = new DatagramSocket();			
				DatagramPacket ePacket = new DatagramPacket(message, message.length,
						InetAddress.getByName(WatchIp), 8904);				
		        setOnChatListener(lsitener);
				ePacket.setData(message);
				esendSocket.send(ePacket);
				esendSocket.close();
			}
		} catch (Exception e) {
			
		}
	}
	private class StartReceive extends Thread{
			public void run() {
				while(status)
				{
					try {
						if(IsSendStatus)
						{
							ReceiveMsg();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				
			}
	}
   
	


	public void Destroy(){
		Log.d("mpeng"," the ReceThread.destroy ");
		status = false;
		ReceThread = null;
		WatchIp = null;
		if(!eSocket.isClosed())
			eSocket.close();
		if(!esendSocket.isClosed())
			esendSocket.close();
		
	
	}
	public void getdata(byte[] data)
	{
		
//		
		String x=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(data,0+1))))/100));
		String y=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(data,4+1))))/100));
		String z=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(data,2*4+1))))/100));
		//位置信息
		WatchActivity.Data.setX_Pos(x);
		WatchActivity.Data.setY_Pos(y);
		WatchActivity.Data.setZ_Pos(z);
//		Data.setYs_Pos((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,3*4))))/100);
//		Data.setZs_Pos((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,4*4))))/100);
		//周期信息
		String mouldcyc=String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(data,32+8+1)))/100);
		String removecyc=String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(data,32+12+1)))/100);
		String allcyc=String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(data,32+16+1)))/100);
		WatchActivity.Data.setMouldeCycle(mouldcyc);
		WatchActivity.Data.setRemoveCycle(removecyc);
		WatchActivity.Data.setAllCycle(allcyc);
        //警报信息
		System.out.println("位置信息 x="+x+"    y="+y+"     z="+z);
		System.out.println("周期信息 mouldcyc="+mouldcyc+"    removecyc="+removecyc+"     allcyc="+allcyc);
	}
	
}

