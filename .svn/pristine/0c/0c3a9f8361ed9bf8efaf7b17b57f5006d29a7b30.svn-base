package com.wifi.data;

import com.wifi.database.DBUtil;

import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 *
 */
public class BaseData {
	
	
	private String  devNumber;


	private String deviceName;
	
	private String IpAdd;
	
	private String  Info;
	
	private String Update; 
	
	public String id;
	
	private String status;
	
	
	private String RunningStatus;
	
	private String TargetNum;
	
	private String CompletedNum;
	
	private String RemainNum;
	
	private String ConnectionStatus;
	
	private String MissCnt;
	
	public BaseData()
	{
		super();
		
	}
	
	public String getDevNumber() {
		return devNumber;
	}


	public void setDevNumber(String devNumber) {
		this.devNumber = devNumber;
	}


	public String getDeviceName() {
		return deviceName;
	}


	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}


	public String getIpAdd() {
		return IpAdd;
	}


	public void setIpAdd(String ipAdd) {
		IpAdd = ipAdd;
	}


	public String getInfo() {
		return Info;
	}


	public void setInfo(String info) {
		Info = info;
	}


	public String getUpdate() {
		return Update;
	}


	public void setUpdate(String update) {
		Update = update;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRunningStatus() {
		return RunningStatus;
	}


	public void setRunningStatus(String runningStatus) {
		RunningStatus = runningStatus;
	}


	public String getTargetNum() {
		if(TargetNum==null)
			return (""+0);
		else
			return TargetNum;
	}


	public void setTargetNum(String targetNum) {
		TargetNum = targetNum;
	}


	public String getCompletedNum() {
		if(CompletedNum==null)
			return (""+0);
		else
			return CompletedNum;
	}


	public void setCompletedNum(String completedNum) {
		CompletedNum = completedNum;
	}

	
	public String getRemainNum() {
		return RemainNum;
	}


	public void setRemainNum(String numer) {
		RemainNum = numer;
	}


	public String getConnectionStatus() {
		return ConnectionStatus;
	}


	public void setConnectionStatus(String status) {
		ConnectionStatus = status;
	}

	public String getMissCnt() {
		if(MissCnt==null)
			return (""+0);
		else
			return MissCnt;
	}

	public void cleanMissCnt() {
		MissCnt = ""+0;
	}
	public void setMissCnt(String num) {
		MissCnt = num;
	}
	
	public BaseData(String id,String dev,String name,String ip,String info,String status)
	{
		super();
		this.id = id;
		this.devNumber = dev;
		this.deviceName =name;
		this.IpAdd = ip;
		this.Info = info;
		this.status = status;
	}
	
	
	
	
}
