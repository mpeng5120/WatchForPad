package com.wifi.data;

import java.util.ArrayList;
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
import android.widget.TextView;

public class WatchAdapter  extends BaseAdapter{ 

	
	private ArrayList<BaseData> deviceList;
    private Context ct ;
	private int currentPosition;
	private LayoutInflater mInflater;
	private DBUtil DB;
	private int selectItem = -1;
	private int OffLineItem = -1;
	private int AlarmItem = -1;
	private int ComplteItem = -1;

	private ViewHolder holder;
public WatchAdapter(Context context, ArrayList<BaseData> devicelist2) {

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
		convertView = mInflater.inflate(com.wifi.scan.R.layout.trawatch_items, null);
		holder = new ViewHolder();
		holder.DeviceNum = (TextView) convertView.findViewById(R.id.show_num);
		holder.DeviceName = (TextView) convertView.findViewById(R.id.machine_num);
		holder.DeviceIp = (TextView) convertView.findViewById(R.id.wifi_name);
        holder.DeviceInfo = (TextView) convertView.findViewById(R.id.work_msg);
    	
        holder.work_state= (TextView) convertView.findViewById(R.id.work_state);
        
        holder.all_number= (TextView) convertView.findViewById(R.id.all_number);
      
        holder.now_number= (TextView) convertView.findViewById(R.id.now_number);

        holder.remain_number= (TextView) convertView.findViewById(R.id.former_state);

        holder.connect_state= (TextView) convertView.findViewById(R.id.dryer_state);
        
        holder.EditButton = (Button) convertView.findViewById(R.id.button1);
		holder.EditButton.setTag(position);
		convertView.setTag(holder);

	} else {
		holder = (ViewHolder)convertView.getTag(); 
	}
	BaseData bd = deviceList.get(position);
	holder.DeviceNum.setText(bd.getDevNumber());
	holder.DeviceName.setText(bd.getDeviceName());
	holder.DeviceIp.setText(bd.getIpAdd());
	holder.DeviceIp.setTextSize(15);
	holder.DeviceInfo.setText(bd.getInfo());
	String Str1 =bd.getRunningStatus();	
	String Str2 =bd.getTargetNum();
	String Str3 =bd.getCompletedNum();
	String Str4 =bd.getRemainNum();
	String Str5 =bd.getConnectionStatus();
	if(Str1==null)
		  holder.work_state.setText("****");
	else
		  holder.work_state.setText(Str1);
	
	if(Str2 == null)
		 holder.all_number.setText("****");
	else
		  holder.all_number.setText(Str2);
	if(Str3 == null)
		 holder.now_number.setText("0");
	else
		  holder.now_number.setText(Str3);
	if(Str4 == null)
		 holder.remain_number.setText("****");
	else
		  holder.remain_number.setText(Str4);
	if(Str5 == null)
		 holder.connect_state.setText("掉线");
	else
		  holder.connect_state.setText(Str5);

		currentPosition = position;	
		holder.EditButton.setOnClickListener(new lvButtonListener(position) );
		if(holder.connect_state.getText().equals("掉线")){
			holder.DeviceNum.setBackgroundColor(Color.GRAY);
	 	    holder.DeviceName.setBackgroundColor(Color.GRAY);
	 	    holder.DeviceIp.setBackgroundColor(Color.GRAY);
	 	    holder.DeviceInfo.setBackgroundColor(Color.GRAY);
	 	    holder.work_state.setBackgroundColor(Color.GRAY);
	 	    holder.all_number.setBackgroundColor(Color.GRAY);
	 	    holder.now_number.setBackgroundColor(Color.GRAY);
	 	    holder.remain_number.setBackgroundColor(Color.GRAY);
	 	    holder.connect_state.setBackgroundColor(Color.GRAY);
   	 	
		}else if(holder.work_state.getText().equals("警报")){
	//			Config.alarmdevicelist.add(bd);
			holder.DeviceNum.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.DeviceName.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.DeviceIp.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.DeviceInfo.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.work_state.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.all_number.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.now_number.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.remain_number.setBackgroundColor(Color.rgb(227,51,51));
	 	    holder.connect_state.setBackgroundColor(Color.rgb(227,51,51));
		}else{//如果未被选中，设置为白色
				holder.DeviceNum.setBackgroundColor(Color.WHITE);
	    	    holder.DeviceName.setBackgroundColor(Color.WHITE);
	    	    holder.DeviceIp.setBackgroundColor(Color.WHITE);
	    	    holder.DeviceInfo.setBackgroundColor(Color.WHITE);
	    	    holder.work_state.setBackgroundColor(Color.WHITE);
	    	    holder.all_number.setBackgroundColor(Color.WHITE);
	    	    holder.now_number.setBackgroundColor(Color.WHITE);
	    	    holder.remain_number.setBackgroundColor(Color.WHITE);
	    	    holder.connect_state.setBackgroundColor(Color.WHITE);
	       }
	return convertView;

}

static class ViewHolder {

	TextView DeviceNum;

	TextView DeviceName;
	
	TextView DeviceIp;
	
	TextView DeviceInfo;
	
	TextView work_state;
    
	TextView all_number;
  
	TextView now_number;

	TextView remain_number;

	TextView connect_state;

	Button EditButton;

}

public void updateItem(int position){
	deviceList.remove(position);
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
			if(sta.equals("是"))
			{
				sta ="否";
				Log.d("mpeng"," if ");
				bd.setStatus(sta);
			}
			
			DB.Update(bd,Integer.parseInt(deviceList.get(position).getId()));
		}
            updateItem(position);
        }    
    }
}


