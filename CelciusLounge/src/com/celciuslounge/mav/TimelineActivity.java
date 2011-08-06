package com.celciuslounge.mav;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter.ViewBinder;

public class TimelineActivity extends ListActivity {
	private Cursor cursor;
	private SimpleCursorAdapter adapter;
	private TimelineReceiver receiver;
	private IntentFilter filter;
	
	private static final String[] FROM = {
		//DB VERSION
		//TimelineHelper.KEY_USER, 
		//TimelineHelper.KEY_MSG, 
		//TimelineHelper.KEY_CREATED_AT
		
		//CP VERSION
		StatusProvider.KEY_USER, 
		StatusProvider.KEY_MESSAGE, 
		StatusProvider.KEY_CREATED_AT
		};
	private static final int[] TO = {
		R.id.status_name_text,
		R.id.status_message_text,
		R.id.status_date_text
	};
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		
		//Content provider Version
		cursor = getContentResolver().query(StatusProvider.CONTENT_URI,null,null,null,null);
		adapter = new SimpleCursorAdapter(this, R.layout.timeline_row,
															  cursor, FROM, TO);//tells listview that 
		//cursor has info
		adapter.setViewBinder(VIEW_BINDER);
		setListAdapter(adapter);
		startManagingCursor(cursor);//manage database in conjunction with activity lifecycle
		
		receiver = new TimelineReceiver();
		filter = new IntentFilter(CelciusLoungeApplication.NEW_STATUS);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(receiver, filter, CelciusLoungeApplication.PERM_NEW_STATUS, null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	private static final ViewBinder VIEW_BINDER = new ViewBinder(){

		@Override
		public boolean setViewValue(View view, Cursor c, int columnIndex) {
			if(view.getId() != R.id.status_date_text){
			return false;
			}
			long timestamp = c.getLong(columnIndex);
			CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
			((TextView) view).setText(relTime); 
			return true;
		}
	};
	
	private class TimelineReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//possible want to execute instance of async task
			cursor.requery();
			adapter.notifyDataSetChanged();
		}
		
	}
	

}
