package com.celciuslounge.mav;

import winterwell.jtwitter.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusUpdateActivity extends Activity implements OnClickListener {
    private static final String TAG = "StatusUpdateActivity";
    
    private EditText statusText;
    
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status_update_activity);
        
        Log.d(TAG, "OnCreate() invoked");
        
        statusText = (EditText) findViewById(R.id.edit_status_text);
        Button updateButton = (Button) findViewById(R.id.button_update_status);
        updateButton.setOnClickListener(this);//"this" is used to make the activity object the listener
        
       
        
       
    }

    
    
    
	//Handle the button click and post to Twitter
	@Override
	public void onClick(View v) {
		 if(v.getId()== R.id.button_update_status){
			 Log.d(TAG, "Button clicked");
			 
			 String msg = statusText.getText().toString();
			 Log.d(TAG, "Message: " +msg);
			 statusText.setText("");
			 if(msg.length()>0){
				 new PostToTwitter().execute(msg);
			 }
		 }
	}
	
	private class PostToTwitter extends AsyncTask<String, Void, Integer>{

		@Override
		protected Integer doInBackground(String... params) {
			 int result;
			 try {
				CelciusLoungeApplication app = (CelciusLoungeApplication) getApplication(); 
				app.getTwitter().setStatus(params[0]);
				result = R.string.status_post_success;
			} catch (TwitterException e) {
				result = R.string.status_post_fail;
				Log.e(TAG,getResources().getString(result), e);
			}

			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			//need access to outer classes this so use StatusUpdateActivity.this
			Toast.makeText(StatusUpdateActivity.this, result, Toast.LENGTH_LONG).show();//need access to outer classes this
		}
		
	}

	/*
	//Implement options menu support
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.status_activity_options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()){
		case R.id.menu_prefs:
			//display the preference activity
			intent = new Intent(this, PrefsActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_timeline_refresh: 
			//R.id.menu_start_service:
			//Start the UpdateService
			intent = new Intent(this, UpdaterService.class);
			startService(intent);
			return true;
		//case R.id.menu_stop_service:
			//Stop the UpdateService
			//intent = new Intent(this, UpdaterService.class);
			//stopService(intent);
			//return true;
		case R.id.menu_timeline:
			//display the timeline activity
			intent = new Intent(this, TimelineActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
*/
	
	
	
	//We really didnt need to implement the following
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() invoked");
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		Log.d(TAG, "onResume() invoked");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() invoked");
	}

	@Override
	protected void onStop() {	
		super.onStop();
		Log.d(TAG, "onStop() invoked");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart() invoked");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() invoked");
	}
}