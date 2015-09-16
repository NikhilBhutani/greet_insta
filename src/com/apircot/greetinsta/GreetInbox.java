package com.apircot.greetinsta;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;     
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GreetInbox extends Activity{

	ListView lv;
	View v;
    TextView text;
	
	ArrayList<String> greetList;
	DBController2 db2 = new DBController2(this);
	ArrayAdapter<String> adapter;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inbox);
		
		
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00933B")));
		getActionBar().setDisplayShowCustomEnabled(true);
		 getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		   getActionBar().setCustomView(R.layout.mycustomactionbar);
		    
		
				v = getActionBar().getCustomView();
				
				
				text = (TextView)v.findViewById(R.id.textView1);
				 text.setText("INBOX");
				 
		         getActionBar().setCustomView(v);
		lv = (ListView)findViewById(R.id.greetInboxList);
		//Get User records from SQLite DB
         greetList =  db2.getInbox();
        adapter = new MyGreetCAdapter(getBaseContext(),android.R.layout.simple_list_item_1 , greetList);
       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,userList);
		 lv.setAdapter(adapter); 
		 
		 
	
	}
 
	
	
	
	
}
