package com.celciuslounge.mav;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){//checks to see if its from our program
			//could get spoofed from other program doing explicit intent
			Log.d("BootReceiver", "onReceive invoked");
			PendingIntent pendingIntent = PendingIntent.getService(context, 1, 
					new Intent(context, UpdaterService.class), PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10, 15000, pendingIntent);
		}

	}

}
