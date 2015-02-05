package com.wifi.scan;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.isea.list2excel.ExpListObjExcelVS;
import com.wifi.data.BaseData;
import com.wifi.data.Config;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.NumberPicker.OnValueChangeListener;

public class SaveWatchActivity extends Activity implements View.OnTouchListener  {
	private Button savebut;
	private CheckBox checkBox1;
	private CheckBox checkBox2;
	private CheckBox checkBox3;
	private CheckBox checkBox4;
	private Spinner spinner1;
	private Spinner spinner2;
	private Spinner spinner3;
	private Spinner spinner4;
	private EditText dateWeek1;
	private EditText dateWeek2;
	private EditText dateWeek3;
	private EditText dateWeek4;
	private EditText time1;
	private EditText time2;
	private EditText time3;
	private EditText time4;
	private String weekstr="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savemain);        
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) findViewById(R.id.checkBox4);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        dateWeek1 = (EditText) findViewById(R.id.dateWeek1);
        dateWeek2 = (EditText) findViewById(R.id.dateWeek2);
        dateWeek3 = (EditText) findViewById(R.id.dateWeek3);
        dateWeek4 = (EditText) findViewById(R.id.dateWeek4);
        time1 = (EditText) findViewById(R.id.time1);
        time2 = (EditText) findViewById(R.id.time2);
        time3 = (EditText) findViewById(R.id.time3);
        time4 = (EditText) findViewById(R.id.time4);
        if(checkBox1==null){return;} if(checkBox2==null){return;} if(checkBox3==null){return;} if(checkBox4==null){return;}
        if(spinner1==null){return;} if(spinner2==null){return;} if(spinner3==null){return;} if(spinner4==null){return;}
        if(dateWeek1==null){return;} if(dateWeek2==null){return;} if(dateWeek3==null){return;} if(dateWeek4==null){return;}
        if(time1==null){return;} if(time2==null){return;} if(time3==null){return;} if(time4==null){return;}
        
        spinner1.setSelection(Config.spinnerpref1.getInt("spinnerp1", 0));
        spinner2.setSelection(Config.spinnerpref2.getInt("spinnerp2", 0));
        spinner3.setSelection(Config.spinnerpref3.getInt("spinnerp3", 0));
        spinner4.setSelection(Config.spinnerpref4.getInt("spinnerp4", 0));
      
        
        if(Config.spinnerpref1.getInt("spinnerp1", 0)==1){
        	dateWeek1.setText(""+Config.dateWeekpref1.getInt("dateWeekp1", 0));
        	dateWeek1.setEnabled(true);
        	time1.setEnabled(true);
        }else if(Config.spinnerpref1.getInt("spinnerp1", 0)==2){
        	 switch(Config.dateWeekpref1.getInt("dateWeekp1", 0)){
             case 2:dateWeek1.setText("周一");break;
             case 3:dateWeek1.setText("周二");break;
             case 4:dateWeek1.setText("周三");break;
             case 5:dateWeek1.setText("周四");break;
             case 6:dateWeek1.setText("周五");break;
             case 7:dateWeek1.setText("周六");break;
             case 1:dateWeek1.setText("周日");break;
             default :break ;
        	 }
        	dateWeek1.setEnabled(true);
         	time1.setEnabled(true);
        }else if(Config.spinnerpref1.getInt("spinnerp1", 0)==3){
        	dateWeek1.setEnabled(false);
        	time1.setEnabled(true);
        }
        
        if(Config.spinnerpref2.getInt("spinnerp2", 0)==1){
           	dateWeek2.setText(""+Config.dateWeekpref2.getInt("dateWeekp2", 0));
           	dateWeek2.setEnabled(true);
         	time2.setEnabled(true);
           }else if(Config.spinnerpref2.getInt("spinnerp2", 0)==2){
           	 switch(Config.dateWeekpref2.getInt("dateWeekp2", 0)){
                case 2:dateWeek2.setText("周一");break;
                case 3:dateWeek2.setText("周二");break;
                case 4:dateWeek2.setText("周三");break;
                case 5:dateWeek2.setText("周四");break;
                case 6:dateWeek2.setText("周五");break;
                case 7:dateWeek2.setText("周六");break;
                case 1:dateWeek2.setText("周日");break;
                default :break ;
           	 }
           	dateWeek2.setEnabled(true);
         	time2.setEnabled(true);
        }else if(Config.spinnerpref2.getInt("spinnerp2", 0)==3){
        	dateWeek2.setEnabled(false);
        	time2.setEnabled(true);
        }
           	 
           if(Config.spinnerpref3.getInt("spinnerp3", 0)==1){
           	dateWeek3.setText(""+Config.dateWeekpref3.getInt("dateWeekp3", 0));
           	dateWeek3.setEnabled(true);
         	time3.setEnabled(true);
           }else if(Config.spinnerpref3.getInt("spinnerp3", 0)==2){
           	 switch(Config.dateWeekpref3.getInt("dateWeekp3", 0)){
                case 2:dateWeek3.setText("周一");break;
                case 3:dateWeek3.setText("周二");break;
                case 4:dateWeek3.setText("周三");break;
                case 5:dateWeek3.setText("周四");break;
                case 6:dateWeek3.setText("周五");break;
                case 7:dateWeek3.setText("周六");break;
                case 1:dateWeek3.setText("周日");break;
                default :break ;
           	 }
           	dateWeek3.setEnabled(true);
         	time3.setEnabled(true);
        }else if(Config.spinnerpref3.getInt("spinnerp3", 0)==3){
        	dateWeek3.setEnabled(false);
        	time3.setEnabled(true);
        }
        if(Config.spinnerpref4.getInt("spinnerp4", 0)==1){
        	dateWeek4.setText(""+Config.dateWeekpref4.getInt("dateWeekp4", 0));
        	dateWeek4.setEnabled(true);
          	time4.setEnabled(true);
        }else if(Config.spinnerpref4.getInt("spinnerp4", 0)==2){
        	 switch(Config.dateWeekpref4.getInt("dateWeekp4", 0)){
             case 2:dateWeek4.setText("周一");break;
             case 3:dateWeek4.setText("周二");break;
             case 4:dateWeek4.setText("周三");break;
             case 5:dateWeek4.setText("周四");break;
             case 6:dateWeek4.setText("周五");break;
             case 7:dateWeek4.setText("周六");break;
             case 1:dateWeek4.setText("周日");break;
             default :break ;
             }
        	 dateWeek4.setEnabled(true);
          	time4.setEnabled(true);
         }else if(Config.spinnerpref4.getInt("spinnerp4", 0)==3){
         	dateWeek4.setEnabled(false);
         	time4.setEnabled(true);
         }
        time1.setText(Config.timepref1.getString("timep1", ""));
        time2.setText(Config.timepref2.getString("timep2", ""));
        time3.setText(Config.timepref3.getString("timep3", ""));
        time4.setText(Config.timepref4.getString("timep4", ""));
        if(Config.checkBoxpref1.getInt("checkBoxp1", 0)==1){
        	checkBox1.setChecked(true);
        	spinner1.setEnabled(true);
        }else{
        	checkBox1.setChecked(false);
        	spinner1.setEnabled(false);
        	dateWeek1.setEnabled(false);
        	time1.setEnabled(false);
        }
        
        if(Config.checkBoxpref2.getInt("checkBoxp2", 0)==1){
        	checkBox2.setChecked(true);
        	spinner2.setEnabled(true);
        }else{
        	checkBox2.setChecked(false);
        	spinner2.setEnabled(false);
        	dateWeek2.setEnabled(false);
        	time2.setEnabled(false);
        }
        
        if(Config.checkBoxpref3.getInt("checkBoxp3", 0)==1){
        	checkBox3.setChecked(true);
        	spinner3.setEnabled(true);
        }else{
        	checkBox3.setChecked(false);
        	spinner3.setEnabled(false);
        	dateWeek3.setEnabled(false);
        	time3.setEnabled(false);
        }
        
        if(Config.checkBoxpref4.getInt("checkBoxp4", 0)==1){
        	checkBox4.setChecked(true);
        	spinner4.setEnabled(true);
        }else{
        	checkBox4.setChecked(false);
        	spinner4.setEnabled(false);
        	dateWeek4.setEnabled(false);
        	time4.setEnabled(false);
        }
        checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
     				if(isChecked){
     					Editor editor = Config.checkBoxpref1.edit(); 
     					editor.putInt("checkBoxp1",1); 
     					editor.commit();
     					spinner1.setEnabled(true);dateWeek1.setEnabled(true);time1.setEnabled(true);
     					 spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
     						@Override
     						public void onItemSelected(AdapterView<?> parent,View arg1,int pos, long id) {
     							if (parent.getItemAtPosition(pos).toString().equals("每月")) {
     								Editor editor = Config.spinnerpref1.edit(); 
     		     					editor.putInt("spinnerp1",1); 
     		     					editor.commit();
     								dateWeek1.setEnabled(true);time1.setEnabled(true);
     							}else if (parent.getItemAtPosition(pos).toString().equals("每周")) {	
     								Editor editor = Config.spinnerpref1.edit(); 
     		     					editor.putInt("spinnerp1",2); 
     		     					editor.commit();
     								dateWeek1.setEnabled(true);time1.setEnabled(true);
     							}else if (parent.getItemAtPosition(pos).toString().equals("每天")) {	
     								Editor editor = Config.spinnerpref1.edit(); 
     		     					editor.putInt("spinnerp1",3); 
     		     					editor.commit();
     								dateWeek1.setEnabled(false);time1.setEnabled(true);
     							}
     						}
     						@Override
     						public void onNothingSelected(AdapterView<?> arg0) {
     						}
     					});
     				}else{
     					Editor editor = Config.checkBoxpref1.edit(); 
     					editor.putInt("checkBoxp1",0); 
     					editor.commit();
     					spinner1.setEnabled(false);dateWeek1.setEnabled(false);time1.setEnabled(false);
     				}
     			}
     		});
             checkBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
     				if(isChecked){
     					Editor editor = Config.checkBoxpref2.edit(); 
     					editor.putInt("checkBoxp2",1); 
     					editor.commit();
     					spinner2.setEnabled(true);dateWeek2.setEnabled(true);time2.setEnabled(true);
     					  spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
     							@Override
     							public void onItemSelected(AdapterView<?> parent,View arg1,int pos, long id) {
     								if (parent.getItemAtPosition(pos).toString().equals("每月")) {
     									Editor editor = Config.spinnerpref2.edit(); 
         		     					editor.putInt("spinnerp2",1); 
         		     					editor.commit();
     									dateWeek2.setEnabled(true);time2.setEnabled(true);
     								}else if (parent.getItemAtPosition(pos).toString().equals("每周")) {	
     									Editor editor = Config.spinnerpref2.edit(); 
         		     					editor.putInt("spinnerp2",2); 
         		     					editor.commit();
     									dateWeek2.setEnabled(true);time2.setEnabled(true);
     								}else if (parent.getItemAtPosition(pos).toString().equals("每天")) {
     									Editor editor = Config.spinnerpref2.edit(); 
         		     					editor.putInt("spinnerp2",3); 
         		     					editor.commit();
     									dateWeek2.setEnabled(false);time2.setEnabled(true);
     								}
     							}
     							@Override
     							public void onNothingSelected(AdapterView<?> arg0) {
     							}
     						});
     				}else{
     					Editor editor = Config.checkBoxpref2.edit(); 
     					editor.putInt("checkBoxp2",0); 
     					editor.commit();
     					spinner2.setEnabled(false);dateWeek2.setEnabled(false);time2.setEnabled(false);
     				}
     			}
     		});
             checkBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
     				if(isChecked){
     					Editor editor = Config.checkBoxpref3.edit(); 
     					editor.putInt("checkBoxp3",1); 
     					editor.commit();
     					spinner3.setEnabled(true);dateWeek3.setEnabled(true);time3.setEnabled(true);
     					spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
     						@Override
     						public void onItemSelected(AdapterView<?> parent,View arg1,int pos, long id) {
     							if (parent.getItemAtPosition(pos).toString().equals("每月")) {
     								Editor editor = Config.spinnerpref3.edit(); 
     		     					editor.putInt("spinnerp3",1); 
     		     					editor.commit();
     								dateWeek3.setEnabled(true);time3.setEnabled(true);
     							}else if (parent.getItemAtPosition(pos).toString().equals("每周")) {	
     								Editor editor = Config.spinnerpref3.edit(); 
     		     					editor.putInt("spinnerp3",2); 
     		     					editor.commit();
     								dateWeek3.setEnabled(true);time3.setEnabled(true);
     							}else if (parent.getItemAtPosition(pos).toString().equals("每天")) {	
     								Editor editor = Config.spinnerpref3.edit(); 
     		     					editor.putInt("spinnerp3",3); 
     		     					editor.commit();
     								dateWeek3.setEnabled(false);time3.setEnabled(true);
     							}
     						}
     						@Override
     						public void onNothingSelected(AdapterView<?> arg0) {
     						}
     					});
     				}else{
     					Editor editor = Config.checkBoxpref3.edit(); 
     					editor.putInt("checkBoxp3",0); 
     					editor.commit();
     					spinner3.setEnabled(false);dateWeek3.setEnabled(false);time3.setEnabled(false);
     				}
     			}
     		});
             checkBox4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
     			@Override
     			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
     				if(isChecked){
     					Editor editor = Config.checkBoxpref4.edit(); 
     					editor.putInt("checkBoxp4",1); 
     					editor.commit();
     					spinner4.setEnabled(true);dateWeek4.setEnabled(true);time4.setEnabled(true);
     					 spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
     						@Override
     						public void onItemSelected(AdapterView<?> parent,View arg1,int pos, long id) {
     							if (parent.getItemAtPosition(pos).toString().equals("每月")) {
     								Editor editor = Config.spinnerpref4.edit(); 
     		     					editor.putInt("spinnerp4",1); 
     		     					editor.commit();
     								dateWeek4.setEnabled(true);time4.setEnabled(true);
     							}else if (parent.getItemAtPosition(pos).toString().equals("每周")) {
     								Editor editor = Config.spinnerpref4.edit(); 
     		     					editor.putInt("spinnerp4",2); 
     		     					editor.commit();
     								dateWeek4.setEnabled(true);time4.setEnabled(true);
     							}else if (parent.getItemAtPosition(pos).toString().equals("每天")) {	
     								Editor editor = Config.spinnerpref4.edit(); 
     		     					editor.putInt("spinnerp4",3); 
     		     					editor.commit();
     								dateWeek4.setEnabled(false);time4.setEnabled(true);
     							}
     						}
     						@Override
     						public void onNothingSelected(AdapterView<?> arg0) {
     						}
     					});
     				}else{
     					Editor editor = Config.checkBoxpref4.edit(); 
     					editor.putInt("checkBoxp4",0); 
     					editor.commit();
     					spinner4.setEnabled(false);dateWeek4.setEnabled(false);time4.setEnabled(false);
     				}
     			}
     		});
        dateWeek1.setOnTouchListener(this); 
        dateWeek2.setOnTouchListener(this); 
        dateWeek3.setOnTouchListener(this); 
        dateWeek4.setOnTouchListener(this); 
        time1.setOnTouchListener(this);  
        time2.setOnTouchListener(this); 
        time3.setOnTouchListener(this);  
        time4.setOnTouchListener(this); 
        savebut=(Button) findViewById(R.id.savebutton);
        savebut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<BaseData> list = new ArrayList<BaseData>();
				list =Config.devicelist;
				String xmlPath = Config.trwatchPATH+"trwatchitem.xml";  
		        String filePath = Config.trwatchPATH;;  
		        String date = new SimpleDateFormat("yyyy-MM-dd  HH：mm").format(Calendar.getInstance().getTime());
		        try {
					ExpListObjExcelVS.expListObjFile(list, xmlPath, filePath, "生产情况"+date);
					Toast.makeText(SaveWatchActivity.this,"已成功保存监视信息到sd卡", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(SaveWatchActivity.this,"保存监视信息到sd卡失败", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
		        String udiskPATH = "/mnt/sda1/";
		        File file = new File(udiskPATH);
				if (file.exists()){
					try {
						ExpListObjExcelVS.expListObjFile(list, xmlPath, udiskPATH, "生产情况"+date);
						Toast.makeText(SaveWatchActivity.this,"已成功保存监视信息到U盘", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						Toast.makeText(SaveWatchActivity.this,"保存监视信息到U盘失败", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}
		        
			}
		});	
    }

    
    @Override 
    public boolean onTouch(View v, MotionEvent event) { 
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        	switch(v.getId())
        	{
        	case R.id.dateWeek1:
        		if(spinner1.getSelectedItem().toString().equals("每月")){
            		showDateDialog(v);
            	}else if(spinner1.getSelectedItem().toString().equals("每周")){
            		showWeekDialog(v);
            	}
        		break;
        	case R.id.dateWeek2:
        		if(spinner2.getSelectedItem().toString().equals("每月")){
            		showDateDialog(v);
            	}else if(spinner2.getSelectedItem().toString().equals("每周")){
            		showWeekDialog(v);
            	}
        		break;
        	case R.id.dateWeek3:
        		if(spinner3.getSelectedItem().toString().equals("每月")){
            		showDateDialog(v);
            	}else if(spinner3.getSelectedItem().toString().equals("每周")){
            		showWeekDialog(v);
            	}
        		break;
        	case R.id.dateWeek4:
        		if(spinner4.getSelectedItem().toString().equals("每月")){
            		showDateDialog(v);
            	}else if(spinner4.getSelectedItem().toString().equals("每周")){
            		showWeekDialog(v);
            	}
        		break;
        		
        	case R.id.time1:
        	case R.id.time2:
        	case R.id.time3:
        	case R.id.time4:
        		showTimeDialog(v);
        		break;
        		
        	}

        } 
   
        return true; 
    } 
    @SuppressLint("NewApi")
	private void showWeekDialog(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); 
        View view = View.inflate(this, R.layout.week_dialog, null); 
        NumberPicker weekPicker = (NumberPicker) view.findViewById(R.id.week_picker);         
        builder.setView(view); 
        weekPicker.setMinValue(1);
        weekPicker.setMaxValue(7);
        Calendar cal = Calendar.getInstance(); 
        cal.setTimeInMillis(System.currentTimeMillis()); 
        int week=cal.get(Calendar.DAY_OF_WEEK); 
        weekPicker.setValue(week-1==0?7:week-1);
        switch(week-1==0?7:week-1){
        case 1:weekstr="周一";break;
        case 2:weekstr="周二";break;
        case 3:weekstr="周三";break;
        case 4:weekstr="周四";break;
        case 5:weekstr="周五";break;
        case 6:weekstr="周六";break;
        case 7:weekstr="周日";break;
        default :break ;
		}
        weekPicker.setOnValueChangedListener(new OnValueChangeListener(){
            @Override
            public void onValueChange(NumberPicker picker, int oldVal,int newVal) {
                // TODO Auto-generated method stub
            	switch(newVal){
            	 case 1:weekstr="周一";break;
                 case 2:weekstr="周二";break;
                 case 3:weekstr="周三";break;
                 case 4:weekstr="周四";break;
                 case 5:weekstr="周五";break;
                 case 6:weekstr="周六";break;
                 case 7:weekstr="周日";break;
                default :break ;
        		}
            }
            
        });
        
            
        if (v.getId() == R.id.dateWeek1){ 
        	dateWeek1.requestFocus();  
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    dateWeek1.setText(weekstr);                             
                    dialog.cancel();
                    dateWeek1.setSelection(dateWeek1.getText().length());
                    
                    int weekint=0;
                    if(weekstr.equals("周一")){weekint=2;}
                    else if(weekstr.equals("周二")){weekint=3;}
                    else if(weekstr.equals("周三")){weekint=4;}
                    else if(weekstr.equals("周四")){weekint=5;}
                    else if(weekstr.equals("周五")){weekint=6;}
                    else if(weekstr.equals("周六")){weekint=7;}
                    else if(weekstr.equals("周日")){weekint=1;}
                    Editor editor = Config.dateWeekpref1.edit(); 
  					editor.putInt("dateWeekp1",weekint); 
  					editor.commit();
                } 
            });                
        }else if (v.getId() == R.id.dateWeek2){  
        	dateWeek2.requestFocus();
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    dateWeek2.setText(weekstr);                   
                    dialog.cancel(); 
                    dateWeek2.setSelection(dateWeek2.getText().length());
                    int weekint=0;
                    if(weekstr.equals("周一")){weekint=2;}
                    else if(weekstr.equals("周二")){weekint=3;}
                    else if(weekstr.equals("周三")){weekint=4;}
                    else if(weekstr.equals("周四")){weekint=5;}
                    else if(weekstr.equals("周五")){weekint=6;}
                    else if(weekstr.equals("周六")){weekint=7;}
                    else if(weekstr.equals("周日")){weekint=1;}
                    Editor editor = Config.dateWeekpref2.edit(); 
  					editor.putInt("dateWeekp2",weekint); 
  					editor.commit();
                } 
            });                
        }else if (v.getId() == R.id.dateWeek3){  
            dateWeek3.requestFocus();                    
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    dateWeek3.setText(weekstr);                             
                    dialog.cancel(); 
                    dateWeek3.setSelection(dateWeek3.getText().length());
                    int weekint=0;
                    if(weekstr.equals("周一")){weekint=2;}
                    else if(weekstr.equals("周二")){weekint=3;}
                    else if(weekstr.equals("周三")){weekint=4;}
                    else if(weekstr.equals("周四")){weekint=5;}
                    else if(weekstr.equals("周五")){weekint=6;}
                    else if(weekstr.equals("周六")){weekint=7;}
                    else if(weekstr.equals("周日")){weekint=1;}
                    Editor editor = Config.dateWeekpref3.edit(); 
  					editor.putInt("dateWeekp3",weekint); 
  					editor.commit();
                } 
            });                
        }else if (v.getId() == R.id.dateWeek4){   
            dateWeek4.requestFocus();                     
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    dateWeek4.setText(weekstr);                           
                    dialog.cancel(); 
                    dateWeek4.setSelection(dateWeek4.getText().length());
                    int weekint=0;
                    if(weekstr.equals("周一")){weekint=2;}
                    else if(weekstr.equals("周二")){weekint=3;}
                    else if(weekstr.equals("周三")){weekint=4;}
                    else if(weekstr.equals("周四")){weekint=5;}
                    else if(weekstr.equals("周五")){weekint=6;}
                    else if(weekstr.equals("周六")){weekint=7;}
                    else if(weekstr.equals("周日")){weekint=1;}
                    Editor editor = Config.dateWeekpref4.edit(); 
  					editor.putInt("dateWeekp4",weekint); 
  					editor.commit();
                } 
            });                
        }        
       
        Dialog dialog = builder.create(); 
        dialog.show(); 
    
    }
    private void showDateDialog(View v)
    {
    	
        AlertDialog.Builder builder = new AlertDialog.Builder(this); 
        View view = View.inflate(this, R.layout.date_dialog, null); 
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);         
        builder.setView(view); 

        Calendar cal = Calendar.getInstance(); 
        cal.setTimeInMillis(System.currentTimeMillis()); 
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);  
     
	    ((ViewGroup)((ViewGroup)datePicker.getChildAt(0)).getChildAt(0)).getChildAt(0).setVisibility(view.GONE);
	    ((ViewGroup)((ViewGroup)datePicker.getChildAt(0)).getChildAt(0)).getChildAt(1).setVisibility(view.GONE);
        if (v.getId() == R.id.dateWeek1){ 
        	dateWeek1.requestFocus();  
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer(); 
                    sb.append(String.format("%02d",datePicker.getDayOfMonth())); 
                    dateWeek1.setText(sb);                             
                    dialog.cancel();
                    dateWeek1.setSelection(dateWeek1.getText().length());

  					Editor editor = Config.dateWeekpref1.edit(); 
  					editor.putInt("dateWeekp1",datePicker.getDayOfMonth()); 
  					editor.commit();
                } 
            });                
        }else if (v.getId() == R.id.dateWeek2){  
        	dateWeek2.requestFocus();
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer(); 
                    sb.append(String.format("%02d",datePicker.getDayOfMonth())); 
                    dateWeek2.setText(sb);                   
                    dialog.cancel(); 
                    dateWeek2.setSelection(dateWeek2.getText().length());
                    Editor editor = Config.dateWeekpref2.edit(); 
  					editor.putInt("dateWeekp2",datePicker.getDayOfMonth()); 
  					editor.commit();
                } 
            });                
        }else if (v.getId() == R.id.dateWeek3){  
            dateWeek3.requestFocus();                    
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer(); 
                    sb.append(String.format("%02d",datePicker.getDayOfMonth())); 
                    dateWeek3.setText(sb);                             
                    dialog.cancel(); 
                    dateWeek3.setSelection(dateWeek3.getText().length());
                    Editor editor = Config.dateWeekpref3.edit(); 
  					editor.putInt("dateWeekp3",datePicker.getDayOfMonth()); 
  					editor.commit();
                } 
            });                
        }else if (v.getId() == R.id.dateWeek4){   
            dateWeek4.requestFocus();                     
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer(); 
                    sb.append(String.format("%02d",datePicker.getDayOfMonth())); 
                    dateWeek4.setText(sb);                           
                    dialog.cancel(); 
                    dateWeek4.setSelection(dateWeek4.getText().length());
                    Editor editor = Config.dateWeekpref4.edit(); 
  					editor.putInt("dateWeekp4",datePicker.getDayOfMonth()); 
  					editor.commit();
                } 
            });                
        }        
       
        Dialog dialog = builder.create(); 
        dialog.show(); 
    }
    private void showTimeDialog(View v)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
        View view = View.inflate(this, R.layout.time_dialog, null); 
       
        final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker); 
        builder.setView(view); 
        
        Calendar cal = Calendar.getInstance(); 
        cal.setTimeInMillis(System.currentTimeMillis()); 
            timePicker.setIs24HourView(true); 
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY)); 
            timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));        
        if (v.getId() == R.id.time1) {    
            time1.requestFocus();               
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer(); 
                    sb.append(String.format("%02d",timePicker.getCurrentHour())).append(":").append(String.format("%02d",timePicker.getCurrentMinute())); 
                    time1.setText(sb);                             
                    dialog.cancel(); 
                    time1.setSelection(time1.getText().length()); 
                    Editor editor = Config.timepref1.edit(); 
  					editor.putString("timep1",time1.getText().toString()); 
  					editor.commit();
                } 
            }); 
        }else if (v.getId() == R.id.time2) { 
            time2.requestFocus();                   
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer();                                      	
                    sb.append(String.format("%02d",timePicker.getCurrentHour())).append(":").append(String.format("%02d",timePicker.getCurrentMinute())); 
                    time2.setText(sb);                             
                    dialog.cancel(); 
                    time2.setSelection(time2.getText().length());
                    Editor editor = Config.timepref2.edit(); 
  					editor.putString("timep2",time2.getText().toString()); 
  					editor.commit();
                } 
            }); 
        }else if (v.getId() == R.id.time3) { 
            time3.requestFocus();                  
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer();                                      	
                    sb.append(String.format("%02d",timePicker.getCurrentHour())).append(":").append(String.format("%02d",timePicker.getCurrentMinute())); 
                    time3.setText(sb);                             
                    dialog.cancel(); 
                    time3.setSelection(time3.getText().length()); 
                    Editor editor = Config.timepref3.edit(); 
  					editor.putString("timep3",time3.getText().toString()); 
  					editor.commit();
                } 
            }); 
        }else if (v.getId() == R.id.time4) { 
            time4.requestFocus();                   
            builder.setTitle("选取起始时间"); 
            builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialog, int which) { 
                    StringBuffer sb = new StringBuffer();                                      	
                    sb.append(String.format("%02d",timePicker.getCurrentHour())).append(":").append(String.format("%02d",timePicker.getCurrentMinute()));  
                    time4.setText(sb);                            
                    dialog.cancel(); 
                    time4.setSelection(time4.getText().length()); 
                    Editor editor = Config.timepref4.edit(); 
  					editor.putString("timep4",time4.getText().toString()); 
  					editor.commit();
                } 
            }); 
        } 
        Dialog dialog = builder.create(); 
        dialog.show(); 
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_save, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_mian:
			startActivity(new Intent().setClass(SaveWatchActivity.this,UserActivity.class));
			this.finish();
			break;
		case R.id.menu_watch:
			startActivity(new Intent().setClass(SaveWatchActivity.this,WatchActivity.class));
			this.finish();
			break;
		case R.id.menu_save:
			startActivity(new Intent().setClass(SaveWatchActivity.this,DetailWatchActivity.class));
			this.finish();
			break;
		default:
			// 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		}
		// 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}
    
    
}