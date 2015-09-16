package com.apircot.greetinsta;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends Activity{
 View v;
 TextView tv,greetinsta_info;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.aboutgreetinsta);
	    
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
		 tv.setText("GREETINSTA");
		 
         getActionBar().setCustomView(v);
         
         greetinsta_info = (TextView)findViewById(R.id.about_greetinsta);
         greetinsta_info.setText("Send 100+ free greeting cards with quotes and a message to your friends and" +
         		" family on special occasions, Spread love and happiness.\n" +
         		" DON'T FORGET TO GREET WITH GREETINSTA. " +
         		"\n \n" +
         		"1. Add your friends with their respective GREET ID. \n" +
         		"2. Choose your card.\n" +
         		"3. Add a message." +
         		"\n \n \n \n " +
         		"VERSION : 1.0"); 
         
	    greetinsta_info.setTextSize(15);
	    
	}
	

}
