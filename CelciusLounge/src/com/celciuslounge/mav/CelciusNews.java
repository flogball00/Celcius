package com.celciuslounge.mav;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONArray;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.celciuslounge.mav.R;


public class CelciusNews extends ListActivity {
	private JSONObject jObject;
	private String jString;
	static final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	String url = "http://empty-water-46.heroku.com/api/news?password=xyzxyz";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_list_view);
		try { 
			//read the JSON from the url
			URL mir = new URL(url);
			URLConnection mc = mir.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                mc.getInputStream()));
	        String inputLine;
	        String line = null;
	        while ((inputLine = in.readLine()) != null) 
	            line = inputLine;
	        in.close();
		    
			if(line != null) {
				//if there is a JSON string, parse info
				parse(line);
				
				SimpleAdapter adapter = new SimpleAdapter(
		        		this,
		        		list,
		        		R.layout.custom_row_view,
		        		new String[] {"title","date","description"},
		        		new int[] {R.id.status_headline_text,R.id.status_date_text, R.id.status_message_text}
		        		);
				
		        setListAdapter(adapter);
			}   
			else{
				//displays if no content in input
				TextView textview = new TextView(this);
				jString = "No Content Available";
				textview.setText(jString);
		        setContentView(textview);
		        }		
		    } catch(Exception e) {
		    	 e.printStackTrace();
		}
	}
	
	private void parse(String newsfeed) throws Exception {
		jObject = new JSONObject(newsfeed);

		//getting the JSON array for news
		JSONArray menuObject = jObject.getJSONArray("news");
		int size = menuObject.length();
		ArrayList<JSONObject> arrays = new ArrayList<JSONObject>();
		//parsing individual news stories from JSON array
		for (int i = 0; i < size; i++) {
	        JSONObject another_json_object = menuObject.getJSONObject(i);
	            arrays.add(another_json_object);
	    }
		//pulling title, date and description from JSON objects
		for (int i = 0; i < arrays.size(); i++) {
			populateList(menuObject.getJSONObject(i).getString("title").toString(),
						 menuObject.getJSONObject(i).getString("date").toString(),
						 menuObject.getJSONObject(i).getString("description").toString());
			}
	}
	
	private void populateList(String title, String date, String description) {
		//removes prior info from list
		list.clear();
		//enter info into list 
		HashMap<String,String> temp = new HashMap<String,String>();
		temp.put("title",title);
		temp.put("date", date);
		temp.put("description", description);
		list.add(temp);
	}
	
}
		
