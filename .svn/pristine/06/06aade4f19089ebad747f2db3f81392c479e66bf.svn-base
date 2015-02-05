/**
 * 
 */
package com.wifi.exchange;

/**
 * @author Administrator
 *
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.app.Activity;

/**
 * @author Administrator
 * 
 *
 */
public class SendMsg {
	
	private Activity targetActivity;
	
	public SendMsg( Activity targetActivity)
	{
		this.targetActivity = targetActivity;
	}
	
	
	public void startSend(byte[] msg){// 启动线程发送
		Send send = new Send(msg);
		send.start();
	}

	class Send extends Thread {
		byte[] msg = null;
		Send(byte[] msg) {
			this.msg = msg;
		}

		public void run() {
//			SendMsg(msg);
			return;
		}
	}
	
	// 发消息
	private void SendMsg(byte[] msg,String ip) {
		try {
			DatagramSocket ds = new DatagramSocket(8903);
			DatagramPacket ePacket = new DatagramPacket(msg, msg.length,
					InetAddress.getByName(ip), 8904);
			ePacket.setData(msg);
			ds.send(ePacket);
			ds.close();
		} catch (Exception e) {
			
		}
	}
	

}
