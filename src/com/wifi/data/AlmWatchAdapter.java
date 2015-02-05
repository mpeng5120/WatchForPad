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

public class AlmWatchAdapter  extends BaseAdapter{ 

	
	private ArrayList<BaseData> deviceList;
    private Context ct ;
	private int currentPosition;
	private LayoutInflater mInflater;
	private DBUtil DB;
	private int selectItem = -1;
	
	private ViewHolder holder;
	
public AlmWatchAdapter(Context context, ArrayList<BaseData> devicelist2) {

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

//选中红色表示
		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
		}
public View getView(int position, View convertView, ViewGroup arg2) {

	if (convertView == null) {

		convertView = mInflater.inflate(com.wifi.scan.R.layout.traalmwatch_items, null);

		holder = new ViewHolder();

		holder.DeviceNum = (TextView) convertView.findViewById(R.id.show_num);
		holder.DeviceName = (TextView) convertView.findViewById(R.id.machine_num);
        holder.work_state= (TextView) convertView.findViewById(R.id.work_state);
        holder.former_state= (TextView) convertView.findViewById(R.id.former_state);
        holder.dryer_state= (TextView) convertView.findViewById(R.id.dryer_state);
        
		convertView.setTag(holder);

	} else {
		holder = (ViewHolder)convertView.getTag(); 
	}
	holder.DeviceNum.setText(deviceList.get(position).getDevNumber());
	holder.DeviceName.setText(deviceList.get(position).getDeviceName());
	holder.work_state.setText(deviceList.get(position).getRunningStatus());
	holder.former_state.setText(deviceList.get(position).getRemainNum());
	holder.dryer_state.setText(deviceList.get(position).getConnectionStatus());

	currentPosition = position;
	

	// 选中红色显示
				if (position == selectItem) {
					// 如果当前的行就是ListView中选中的一行，就更改显示样式
					convertView.setBackgroundColor(Color.RED);
				} else {
					convertView.setBackgroundColor(Color.BLACK);
				}
	return convertView;

}

static class ViewHolder {

	TextView DeviceNum;

	TextView DeviceName;
	
	TextView work_state;

	TextView former_state;

	TextView dryer_state;

}
}


