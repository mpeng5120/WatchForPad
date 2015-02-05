/**
 * 
 */
package com.tr.ui;

import com.wifi.scan.UserActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Administrator
 *
 */
public class TrBroadcastReceiver extends BroadcastReceiver{

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
        Log.d("BroadcastReceiver", "onReceive" + intent.getAction());
        String action = intent.getAction();
		 if (action.equals("updateList")) {
//			 UserActivity.update();
	            
	        }
		
	}

}
