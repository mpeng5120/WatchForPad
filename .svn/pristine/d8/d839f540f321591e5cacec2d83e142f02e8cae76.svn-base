/**
 * 
 */
package com.wifi.data;

import java.io.File;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import com.wifi.exchange.UdpServerHelper;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * @author Administrator
 *
 */
public class Config {
	
	public static ArrayList<BaseData>  devicelist = new ArrayList<BaseData>();
    /** The Constant PORT. */
    public static final int PORT = 8080;
    public static final String ServerIp = "192.168.2.119";
    public static final String UdpClientPort = "8903";
    public static final String UdpServerPort = "8904";
	public static UdpServerHelper Userver;
	public static boolean SendBlank = false;
	public static ArrayList<DatagramPacket> MessageList = new ArrayList<DatagramPacket>(); //Send的时候清楚List
	public static ArrayList<BaseData>  alarmdevicelist = new ArrayList<BaseData>();
	public final static String sdPATH=Environment.getExternalStorageDirectory()+File.separator;//sd卡路径名
 	public final static String trwatchPATH=sdPATH+"TRAwatch"+File.separator;//一级路径
 	
    public static  SharedPreferences checkBoxpref1;
	public static  SharedPreferences checkBoxpref2;
	public static  SharedPreferences checkBoxpref3;
	public static  SharedPreferences checkBoxpref4;
	public static  SharedPreferences spinnerpref1;
	public static  SharedPreferences spinnerpref2;
	public static  SharedPreferences spinnerpref3;
	public static  SharedPreferences spinnerpref4;
	public static  SharedPreferences dateWeekpref1;
	public static  SharedPreferences dateWeekpref2;
	public static  SharedPreferences dateWeekpref3;
	public static  SharedPreferences dateWeekpref4;
	public static  SharedPreferences timepref1;
	public static  SharedPreferences timepref2;
	public static  SharedPreferences timepref3;
	public static  SharedPreferences timepref4;


}
