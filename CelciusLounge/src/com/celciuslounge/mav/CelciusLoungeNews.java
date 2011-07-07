package com.celciuslounge.mav;



import com.celciuslounge.mav.PrefsActivity;
import com.celciuslounge.mav.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class CelciusLoungeNews extends TabActivity {
    /** Called when the activity is first created. */
	final static String TAG = "CLN";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final TabHost tabHost = getTabHost();
        Resources res = getResources();
        TabHost.TabSpec spec;
        Intent intent;
        
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, CelciusNews.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("News").setIndicator("News",
                          res.getDrawable(R.drawable.ic_tab_artist))
                      .setContent(intent);
        tabHost.addTab(spec);
        
       
        intent = new Intent().setClass(this, StatusUpdateActivity.class);
        spec = tabHost.newTabSpec("PostTweet").setIndicator("Post Tweet",
                          res.getDrawable(R.drawable.ic_tab_songs))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, TimelineActivity.class);
        spec = tabHost.newTabSpec("GetTweet").setIndicator("Get Tweets",
                          res.getDrawable(R.drawable.ic_tab_songs))
                      .setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, CelciusVideos.class);
        spec = tabHost.newTabSpec("Video").setIndicator("Video",
                res.getDrawable(R.drawable.ic_tab_artist))
            .setContent(intent);
        tabHost.addTab(spec);
        
        
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);
        
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
        	public void onTabChanged(String tabId) {
        	// TODO Auto-generated method stub

        	// this will show you which tab you clicked...
        	//Toast.makeText(helloTab.this,"CURRENT TAB IS = " + tabHost.getCurrentTab(), Toast.LENGTH_LONG).show(); 
        	tabHost.setCurrentTabByTag(getIntent().getStringExtra("tab_name"));
        	setTabColors(tabHost);
        	}});
    }
        
    private void setTabColors(TabHost tabHost) {
    		// TODO Auto-generated method stub
        	
        	for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { 
        		//tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 40;
        		tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.BLACK); 
        		} 

        		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.BLUE);	
    }

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
		
		default:
			return super.onOptionsItemSelected(item);
		}
    }
	
	
    
	
}
    
