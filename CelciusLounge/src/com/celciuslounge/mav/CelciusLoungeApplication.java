package com.celciuslounge.mav;



import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

public class CelciusLoungeApplication extends Application implements OnSharedPreferenceChangeListener{
	public static final String NEW_STATUS = "com.celciuslounge.mav.NEW_STATUS";
	public static final String NEW_STATUS_EXTRA_COUNT = "com.celciuslounge.mav.NEW_STATUS_EXTRA_COUNT";
	public static final String PERM_NEW_STATUS = "com.celciuslounge.mav.PERM_NEW_STATUS";
	private Twitter twitter;
	private SharedPreferences prefs;
	private TimelineHelper dbHelper;
	
	@Override
	public void onCreate() {
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    prefs.registerOnSharedPreferenceChangeListener(this);
	    dbHelper = new TimelineHelper(this);
	    
	}
	
	public SQLiteDatabase getDb(){
		return dbHelper.getWritableDatabase();
	}
	public Twitter getTwitter(){
    	if(twitter==null){
    		String user = prefs.getString("PREF_USER", null);
    		String password = prefs.getString("PREF_PASSWORD", null);
    		String url = prefs.getString("PREF_SITE_URL", null);
    		
    		 twitter = new Twitter (user,password);
    	        twitter.setAPIRootUrl(url);
    	}
    	return twitter;
    }
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {		
    	twitter = null;
		
	}

}
