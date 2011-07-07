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

public class UpdaterService extends IntentService {/*
		@Override
		public IBinder onBind(Intent arg0) {
			// We don't implement bound service behavior
			return null;
		}
	*/
	public UpdaterService(){
			super("UpdaterService");
	}
	private static final String TAG = "UpdaterService";
		
	@Override
	protected void onHandleIntent(Intent intent) {
					
//private void pullFromTwitter(){
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
			
			
			//direct db access VERSION
			//values.put(TimelineHelper.KEY_ID, id);
			//values.put(TimelineHelper.KEY_USER, name);
			//values.put(TimelineHelper.KEY_MSG, msg);
			//values.put(TimelineHelper.KEY_CREATED_AT, createdAt.getTime());
			//if (app.getDb().insert(TimelineHelper.TABLE, null, values) != -1){
				//count++;
			//}
			
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

//Service {

	
	/*private Thread worker;
	private boolean running = false;
	@Override
	public void onCreate() {
		
		Log.d(TAG, "onCreate() invoked");
		worker = new Thread(new UpdaterLoop());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand() invoked");
		if (!running){
			running = true;
			worker.start();
		}
		
		return START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy() invoked");
		worker.interrupt();
		
	}

	private class UpdaterLoop implements Runnable{

		@Override
		public void run() {
			while (!Thread.interrupted()){
				Log.d(TAG, "UpdaterLoop running");
				pullFromTwitter();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					return;
				}
			}
			
		}
		
	}
	*/
	
