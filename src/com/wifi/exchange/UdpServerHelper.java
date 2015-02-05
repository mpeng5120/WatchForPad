/**
 * 
 */
package com.wifi.exchange;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import com.wifi.data.BaseData;
import com.wifi.data.Config;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * @author 
 * 
 */
public class UdpServerHelper  implements Runnable {
    public    Boolean IsThreadDisable = false;//指示监听线程是否终止
    private static WifiManager.MulticastLock lock;
    private static boolean isSendMessage = false;
    private final static String TAG = "UdpServerHelper";
    private  static ChatListener chatListener;
    private static WifiReadDataFormat frm;
    private static Activity targetActivity;
    private DatagramSocket dataReciveSocket;
    private DatagramSocket dataSendSocket;
    
    private static AlertDialog  netWorkDialog ;
	private static Handler myhandler = new Handler(){
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == LittleDownTimer.TIME_OUT_MSG)
			{
				Log.e(TAG,"time out !");
				LittleDownTimer.stopMenu();				
//				putDataToList();
				chatListener.onChat(Config.MessageList);	
				//在这里处理消息，通知UI更新，
				Config.SendBlank =true;
				isSendMessage = true;
			}
			else if(msg.what == 11123)
			{
				if(netWorkDialog == null||!(netWorkDialog.isShowing()))
					netWorkDialog = new AlertDialog.Builder(targetActivity).setMessage("网络异常，请检查网络连接")
		        	.setNegativeButton("退出", null).show();
			}
		}
};


/**
 * @param manager
 * @param lock2
 */
public UdpServerHelper(Activity targetActivity ,WifiManager manager, MulticastLock lock) {
	// TODO 自动生成的构造函数存根
	UdpServerHelper.lock= lock; 
	LittleDownTimer.getInstance().start();
	LittleDownTimer.setHandler(myhandler);		
	LittleDownTimer.stopMenu();
	isSendMessage = false;
	this.targetActivity = targetActivity;
}


private static void setOnChatListener(ChatListener listener) {
	chatListener = listener;
}


public void StartListen()  {
	
        // UDP服务器监听的端口
        Integer port = 8903;
        // 接收的字节大小，客户端发送的数据不能超过这个大小
       
        try {
            // 建立Socket连接
        	
            dataReciveSocket = new DatagramSocket(port);
            dataReciveSocket.setBroadcast(true);
           
            try {
                while (!IsThreadDisable) {
                    // 准备接收数据
                    if(isSendMessage)
                    {
//                    	Arrays.fill(message, (byte) 0);
                    	 byte[] message = new byte[1000];
                         DatagramPacket datagramPacket = new DatagramPacket(message,
                                 message.length);
	                    UdpServerHelper.lock.acquire();
	                    dataReciveSocket.receive(datagramPacket);
//	                    new String(datagramPacket.getData()).trim();
	                    
	                    Log.d("UDP Demo", datagramPacket.getAddress()
	                            .getHostAddress().toString()
	                            + ":" +datagramPacket.getData().toString() );
//	                    for(int i=0;i<10;i++)
//	                    {
//	                    	Log.d("mpeng","ip:"+datagramPacket.getAddress().getHostAddress()+"the message ["+i+" ]is :"+(datagramPacket.getData()[i]&0xff));
//	                    }
	                    Config.MessageList.add(datagramPacket);
	                    
//	                    for(int i = 0;i<10;i++)
//	                    {
//	                    	Log.d("mpeng","aaaaaaa ip:"+Config.MessageList.get(0).getAddress().getHostAddress()+"the message ["+i+" ]is :"+(datagramPacket.getData()[i]&0xff));
//	                    }
	                    UdpServerHelper.lock.release();
                    }
                }
            } catch (IOException e) {//IOException
                e.printStackTrace();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(WifiReadDataFormat formatmessage ,ChatListener lsitener) {
//        message = (message == null ? "Hello IdeasAndroid!" : message);
    	frm = formatmessage;
    	byte[] message = frm.sendDataFormat();
        int server_port = 8904;
        Config.MessageList.clear();
        LittleDownTimer.resetMenu();
        setOnChatListener(lsitener);
        isSendMessage = true;
        Log.d("UDP Demo", "UDP发送数据:"+message.length);

        try {
        	dataSendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress local = null;
        try {
            local = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DatagramPacket p = new DatagramPacket(message, message.length, local,
                server_port);
        try {

        	dataSendSocket.send(p);
        	dataSendSocket.close();
            
        } catch (IOException e) {
        	Message msg = new Message();
        	msg.what = 11123;
        	myhandler.sendMessage(msg);
//        	Toast.makeText(targetActivity, "请连接WIFI并正确配置路由器", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
 
    @Override
    public void run() {
            StartListen();
    }
    
    public void Destroy()  {
    	Log.e("mpeng","udp destroy");
    	IsThreadDisable = false;
    	isSendMessage = false;
    	if(!dataSendSocket.isClosed())
    		dataSendSocket.close();
    	if(!dataReciveSocket.isClosed())
    		dataReciveSocket.close();
    }
  
}