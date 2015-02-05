package com.wifi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wifi.database.DBUtil;
import com.wifi.scan.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DeviceAdapter  extends BaseAdapter{ 

	
	private ArrayList<BaseData> deviceList;
    private Context ct ;
	private int currentPosition;
	private LayoutInflater mInflater;
	private DBUtil DB;
	private int mselectItem=-1;
	
	private ViewHolder holder;
	
	
//	public DeviceAdapter(Context context, ArrayList<HashMap<String, Object>> List) {
//
//		try {
//
//			mInflater = LayoutInflater.from(context);
//			this.deviceList = List;
//		
//
//		} catch (Exception e) {
//
//		}
//
//	}
	
public DeviceAdapter(Context context, ArrayList<BaseData> devicelist2) {

		try {
			ct = context;
			mInflater = LayoutInflater.from(context);
			this.deviceList = devicelist2;
		    DB = new DBUtil(context);

		} catch (Exception e) {

		}

	}
@Override
public int getCount() {
	// TODO Auto-generated method stub
	return deviceList.size();
}

@Override
public Object getItem(int position) {
	// TODO Auto-generated method stub
	return deviceList.get(position);
}

@Override
public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
}
private void setSelectItem(int selectItem) {
	mselectItem = selectItem;
}
public View getView(int position, View convertView, ViewGroup arg2) {

	if (convertView == null) {

		convertView = mInflater.inflate(com.wifi.scan.R.layout.listitem, null);

		holder = new ViewHolder();

		holder.DeviceNum = (TextView) convertView

		.findViewById(R.id.DeviceNumber);

		holder.DeviceName = (TextView) convertView
				.findViewById(R.id.DeviceName);
		
		holder.DeviceIp = (TextView) convertView
				.findViewById(R.id.DeviceIp);

		holder.DeviceInfo = (TextView) convertView

		.findViewById(R.id.DeviceInfo);

		holder.EditButton = (Button) convertView
				.findViewById(R.id.button1);
		holder.EditButton.setTag(position);
		convertView.setTag(holder);

	} else {

		holder = (ViewHolder)convertView.getTag(); 
	}

  
	holder.DeviceNum.setText(deviceList.get(position).getId());
	holder.DeviceName.setText( deviceList.get(position).getDeviceName());
	holder.DeviceIp.setText(deviceList.get(position).getIpAdd());
	holder.DeviceInfo.setText(deviceList.get(position).getInfo());
//	holder.DeviceName.setText( deviceList.get(position).get("devicename").toString());
//	holder.DeviceIp.setText(deviceList.get(position).get("deviceip").toString());
//	holder.DeviceInfo.setText(deviceList.get(position).get("deviceinfo").toString());
	currentPosition = position;
	Log.d("mpeng","deviceList.get(position).getSta(): "+deviceList.get(position).getStatus());
	Log.d("mpeng","deviceList.get(position).getSta()11111 : "+deviceList.get(position).getStatus().equals("true"));
	if(deviceList.get(position).getStatus().equals("是"))
		holder.EditButton.setText(R.string.deletedevice);
	else
		holder.EditButton.setText(R.string.adddevice);
	
	holder.EditButton.setOnClickListener(new lvButtonListener(position) );
	
	if (deviceList.get(position).getStatus().equals("是")) {
		// 如果当前的行就是ListView中选中的一行，就更改显示样式
		convertView.setBackgroundColor(Color.GREEN);
	} else if (deviceList.get(position).getStatus().equals("否")){
		convertView.setBackgroundColor(Color.LTGRAY);
	}
//	holder.EditButton.setOnClickListener(new lvButtonListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			int currectposition = Integer.valueOf(v.getTag().toString()).intValue();
//			Log.d("mpeng","the position is  3333  "+currectposition);
//			BaseData bd = new BaseData();
//			bd = deviceList.get(currectposition);
//
//			String  sta = bd.getSta();
//			if(sta.equals("false"))
//			{
//				sta ="true";
//				Log.d("mpeng"," if ");
////				holder.EditButton.setText(R.string.deletedevice);	
//				bd.setSta(sta);
//			}
//			else
//			{
//				sta= "false";
//				bd.setSta(sta);
////				holder.EditButton.setText(R.string.adddevice);	
//				Log.d("mpeng"," else");
//			}
//			
//			DB.Update(bd,currentPosition);
//		}
//	});
	
	return convertView;

}

static class ViewHolder {

	TextView DeviceNum;

	TextView DeviceName;
	
	TextView DeviceIp;
	
	TextView DeviceInfo;

	TextView settingOptionTextView;

	Button EditButton;

}

public void updateItem(int position){
	//deviceList.remove(position);
	setSelectItem(position);
    this.notifyDataSetChanged();
}
class lvButtonListener implements OnClickListener {
    private int position;

    lvButtonListener(int pos) {
        position = pos;
    }
    
    @Override
    public void onClick(View v) {
        int vid=v.getId();
        if (vid == holder.EditButton.getId())
        {	
        	//int currectposition = Integer.valueOf(v.getTag().toString()).intValue();
			Log.d("mpeng","the position is  3333  "+position);
			BaseData bd = new BaseData();
			bd = deviceList.get(position);

			String  sta = bd.getStatus();
			if(sta.equals("否"))
			{
				sta ="是";
				Log.d("mpeng"," if ");
				bd.setStatus(sta);
			}
			else
			{
				sta= "否";
				bd.setStatus(sta);
				Log.d("mpeng"," else");
			}
			
			DB.Update(bd,Integer.parseInt(deviceList.get(position).getId()));
		}
            updateItem(position);
        }    
    }
}


