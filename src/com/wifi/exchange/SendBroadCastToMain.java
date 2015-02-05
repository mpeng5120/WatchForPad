package com.wifi.exchange;

import java.net.DatagramPacket;
import java.util.ArrayList;

import com.wifi.data.AlmWatchAdapter;
import com.wifi.data.BaseData;
import com.wifi.data.Config;
import com.wifi.data.HexDecoding;
import com.wifi.data.WatchAdapter;
import com.wifi.scan.R;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * ��λ״̬��ѯ
 * @author ������
 *
 */
public class SendBroadCastToMain implements Runnable {
	
	protected static final String TAG = "SendBroadCastToMain";
	//���Ϻõ�plc��ַ����ƫ�Ƶĵ�ַ����������
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x1000B200, 140);//(0x10007000, 32);
	private static boolean selfDestroy_flag;
	public static boolean existFlag = false;
	private byte[] getData;
	private WatchAdapter ListAdapter ;
	private AlmWatchAdapter almListAdapter ;
	
	private int aaa =0;
	private Activity targetActivity;
	
	
	
	/**
	 * ���캯��
	 * @param targetActivity
	 * @param viewbeingChangedArrayList
	 */
	public SendBroadCastToMain(Activity targetActivity, WatchAdapter adapter,AlmWatchAdapter almAdapter) {
		selfDestroy_flag = true;
		Config.SendBlank = true;
		this.ListAdapter= adapter ;
		this.almListAdapter = almAdapter;
		this.targetActivity = targetActivity;
	}
	
	
	
	
	
	
	
	/** ��ȡ�ŷ�����ʱ��ͨ���߳��յ���Ϣʱ�Ĵ�������. */
	private  ChatListener Feedback = new ChatListener() {

		@Override
		public void onChat(ArrayList<DatagramPacket> messageList) {
			// TODO �Զ����ɵķ������
			    Log.e("mpeng","ReadDataFeedback ChatListener");	
				int size = Config.devicelist.size();	
				Config.alarmdevicelist.clear();
				for(int i = 0;i<size;i++)
				{
//					Log.d("mpeng","the i is "+i+"size is "+size);
					int messageSize =  Config.MessageList.size();					
					if(messageSize==0)
					{
//						δ�����ͻ��˷�������Ϣ����������
						BaseData bd =Config.devicelist.get(i);
						int count =Integer.valueOf(bd.getMissCnt()).intValue();
						if(count>=30)
						{
							String connStatus =  targetActivity.getResources().getString(R.string.offline_state); 
							bd.setConnectionStatus(connStatus);								
							
						}else
						{
							count++;
							bd.setMissCnt(""+count);
						}
						if(bd.getRunningStatus()!=null&&bd.getRunningStatus().equals("����"))
						{								
							Config.alarmdevicelist.add(bd);
						}
//						ListAdapter.notifyDataSetChanged();
						continue;
					}
					

					for(int j = 0 ;j<messageSize;j++)
					{
						byte[] data = Config.MessageList.get(j).getData();
						BaseData bd =Config.devicelist.get(i);
						Log.e("mpeng","the ip is :"+((BaseData)Config.devicelist.get(i)).getIpAdd().toString());
						String ip = ((BaseData)Config.devicelist.get(i)).getIpAdd().toString();
						String ip1 = Config.MessageList.get(j).getAddress().getHostAddress().toString();			
						if(ip.equals(ip1))
						{							
							formatReadMessage.backMessageCheck(data);
							if(formatReadMessage.finishStatus()&&(formatReadMessage.getErrFlag())){
///
								//������ȷ�����   �������ص�����	
								//0-7pos|count(bit8-9)|���ڣ�bit10-12��|״̬��bit13��|
								//
								//
								getData=new byte[formatReadMessage.getLength()];
								//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
								System.arraycopy(formatReadMessage.getFinalData(),0, getData, 0, formatReadMessage.getLength());
															
								byte[] ProductData =  new byte[4];
								System.arraycopy(getData, 32, ProductData, 0, 4);
								int tarNumber = HexDecoding.Array4Toint(ProductData);
								bd.setTargetNum(""+tarNumber);
								
								byte[] completeData =  new byte[4];
								System.arraycopy(getData, 36, completeData, 0, 4);
								int CompleteNumber = HexDecoding.Array4Toint(completeData);
								bd.setCompletedNum(""+CompleteNumber);
								
								int remNumber = tarNumber -CompleteNumber;
								bd.setRemainNum(""+remNumber);
								String connStatus =  targetActivity.getResources().getString(R.string.watch_state); 
		
								bd.setConnectionStatus(connStatus);
								bd.cleanMissCnt(); //���յ���IP�Ŀͻ��˷�������Ϣ  ��Ѵ����������
								
								
								String SysStatus = "";
								if(((int)((getData[52]>>4)&0x01))==1){//����
									SysStatus = targetActivity.getResources().getString(R.string.alarm_state); 
									Config.alarmdevicelist.add(bd);
								}else if(((int)((getData[52]>>2)&0x01))==1) //�Զ�
							    {
							    	SysStatus = targetActivity.getResources().getString(R.string.product_state); 
							    }else //if(((int)((getData[52]>>3)&0x01))==1) //�ֶ� ֹͣ
							    {
									SysStatus = targetActivity.getResources().getString(R.string.stop_state); 
									
							    }
//								if(ip.equals("192.168.2.222"))
//								{
//									SysStatus = targetActivity.getResources().getString(R.string.alarm_state);
//									Config.alarmdevicelist.add(bd);
//								}
								bd.setRunningStatus(SysStatus);	
								Config.devicelist.set(i, bd);
								Config.MessageList.remove(j);
								Log.e("mpeng","update list !!");
								
//								ListAdapter.notifyDataSetChanged();
							}
							 //���ҳɹ���ֱ���������������²���
							break;
						}else if(j == (messageSize-1))  //���յ���MESSAGELIST����û���ҵ���ǰ��IP����������������Ϣ�� ��¼����
						{						
							Log.e(TAG,"δ�յ� ��"+bd.getIpAdd()+"����������Ϣ����");
							int count =Integer.valueOf(bd.getMissCnt()).intValue();
							
							if(count>=30)
							{
								String connStatus =  targetActivity.getResources().getString(R.string.offline_state); 
								bd.setConnectionStatus(connStatus);								
								
							}else
							{
								count++;								
								bd.setMissCnt(""+count);
							}
							if((bd.getRunningStatus()!= null)&&bd.getRunningStatus().equals("����"))
							{								
								Config.alarmdevicelist.add(bd);
							}
//							ListAdapter.notifyDataSetChanged();
						
						}
						
					}
						
				}
//				Intent it = new Intent();
//				it.setAction("updateListView");
//				targetActivity.sendBroadcast(it);
//				Log.e("mpeng"," updata list ");
				ListAdapter.notifyDataSetChanged();
				almListAdapter.notifyDataSetChanged();
		}
		
	};
	
	//���ٸ��߳�
	public void destroy() {
			System.out.println("SendBroadCastToMain �ر�");		
			selfDestroy_flag=false;
			existFlag = false;
			Config.SendBlank = true;			
			if(Config.Userver!=null)
			{
				Config.Userver.Destroy();
				Config.Userver = null;
			}
	}
	
	@Override
	public void run() {
		try {
		while (selfDestroy_flag) {	
			
			if (selfDestroy_flag&&Config.Userver!=null&&Config.SendBlank) {
				            Config.SendBlank = false;
							Thread.sleep(170);
							aaa++;
							Log.d("mpeng","run~~~~~~delay~~~~~~~~~ formatReadMessage ");
							if(	Config.Userver!=null)
								Config.Userver.sendMessage(formatReadMessage,Feedback);
						}	
		   }
		} catch (Exception e) {
		e.printStackTrace();
		destroy();
	}

	}

	
	
	
}