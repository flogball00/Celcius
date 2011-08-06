package com.celciuslounge.mav;


import java.util.Date;
import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends IntentService {
	
	public UpdaterService(){
			super("UpdaterService");
	}
	private static final String TAG = "UpdaterService";
		
	@Override
	protected void onHandleIntent(Intent intent) {
				
	CelciusLoungeApplication app = (CelciusLoungeApplication) getApplication();
	int count = 0;
	try {
		List<Twitter.Status> timeline = app.getTwitter().getHomeTimeline();
		ContentValues values = new ContentValues();
		for(Twitter.Status status: timeline){
			values.clear();
			long id = status.id;
			String msg = status.text;
			Date createdAt = status.createdAt;
			String name = status.user.name;
			Log.d(TAG, name+" posted at "+ createdAt+": "+msg);
			
			
			
			//content provider access VERSION
			values.put(StatusProvider.KEY_ID, id);
			values.put(StatusProvider.KEY_USER, name);
			values.put(StatusProvider.KEY_MESSAGE, msg);
			values.put(StatusProvider.KEY_CREATED_AT, createdAt.getTime());
			try {
				getContentResolver().insert(StatusProvider.CONTENT_URI, values);
				count++;
			} catch (Exception e) {
				//Assume its a duplicate. Ignore the exception.
			}
			
		}
	} catch (TwitterException e) {
		Log.e(TAG, "Error fetching timeline", e);
	}
	if(count>0){
		Intent broadcast = new Intent(CelciusLoungeApplication.NEW_STATUS);
		broadcast.putExtra(CelciusLoungeApplication.NEW_STATUS_EXTRA_COUNT, count);
		sendBroadcast(broadcast, CelciusLoungeApplication.PERM_NEW_STATUS);
	}
}

}


	
