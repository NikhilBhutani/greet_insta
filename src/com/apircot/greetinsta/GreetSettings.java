package com.apircot.greetinsta;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GreetSettings extends Activity implements OnItemClickListener{

	String value,valueincap;
	TextView tv,t_v;
	String GREET_ID = "greet_id";
	View v;
	ArrayAdapter<String> adapter;
	String Settingsarray[] = {"ABOUT","INBOX"};
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.settings);
		

		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
    	getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00933B")));
		getActionBar().setDisplayShowCustomEnabled(true);
	    getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	    getActionBar().setCustomView(R.layout.mycustomactionbar);
	    
	//	LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = getActionBar().getCustomView();
		
		
		tv = (TextView)v.findViewById(R.id.textView1);
		 tv.setText("SETTINGS");
		 
         getActionBar().setCustomView(v);
         
         t_v = (TextView)findViewById(R.id.settingTextView);
         ListView lv = (ListView)findViewById(R.id.SettingsList);
         SharedPreferences prefs = this.getSharedPreferences("UserDetails",
                 Context.MODE_PRIVATE);
          value= prefs.getString(GREET_ID, "novalue");
 		 valueincap = value.toUpperCase();
         t_v.setText(valueincap);
         t_v.setGravity(Gravity.CENTER);
         t_v.setTextColor(Color.rgb(49, 180, 4));
         
        adapter = new MyGreetCAdapter(getBaseContext(), android.R.layout.simple_list_item_1,Settingsarray);
        lv.setAdapter(adapter);
 		lv.setOnItemClickListener(this); 
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String a="INBOX",b="ABOUT";
		
		String friendName = (String)parent.getItemAtPosition(position);
//		Toast.makeText(getApplicationContext(), friendName, Toast.LENGTH_LONG).show();
		if(a.equals(friendName))
		{
			
			Intent inten = new Intent(getApplicationContext(),GreetInbox.class);
			startActivity(inten);
		}
		else if(b.equals(friendName))
		{
			
			Intent in = new Intent(getApplicationContext(),About.class);
			startActivity(in);
			
		}
		
	
		
	}
	
   	

}
