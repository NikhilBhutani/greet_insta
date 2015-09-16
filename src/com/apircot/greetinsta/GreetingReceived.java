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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GreetingReceived extends Activity implements OnClickListener{

	String name;
	String str;
	View v;
    TextView text,greetingsfrom,MSG_GREET;
    TextView addedmsg;
    ImageButton sendGreetings;
    Button send;
    ImageView iv;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.greetingsreceived);

		str = getIntent().getStringExtra("msg");
		String mygreet_msg = getIntent().getStringExtra("MYGREETMSG");
		String mygreet_msgcaps = mygreet_msg.toUpperCase();
		if(str!=null)
		{
			System.out.println("!!!!!!........ Value of STR = "+str);
			
		iv = (ImageView)findViewById(R.id.greetingImg);
		int res = getResources().getIdentifier(str, "drawable", getPackageName());
		iv.setImageDrawable(getResources().getDrawable(res));
	
		}
		
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00933B")));
		getActionBar().setDisplayShowCustomEnabled(true);
	    getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	    
	    getActionBar().setCustomView(R.layout.mycustomactionbar);
	    
	    
		MSG_GREET = (TextView)findViewById(R.id.greet_text);
	//	MSG_GREET.setText(mygreet_msgcaps);
		
		addedmsg = (TextView)findViewById(R.id.addmsg);
		addedmsg.setText(mygreet_msgcaps);
		
	//	LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		v = inflater.inflate(R.layout.mycustomactionbar, null);
        v = getActionBar().getCustomView();
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        name = prefs.getString("greet_id", "");
        String nameincaps = name.toUpperCase();
       
		text = (TextView)v.findViewById(R.id.textView1);
		text.setText("GREETINSTA");
		///text.setGravity(Gravity.CENTER);
	//	text.setTextColor(Color.rgb(210, 247, 253));
	       	 
		send = (Button)findViewById(R.id.bsendgreet);
		send.setOnClickListener(this);
		
     //   getActionBar().setCustomView(text);
     
       
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
 
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		
		
		case R.id.action_settings:
		{
			Intent i = new Intent(GreetingReceived.this,GreetSettings.class);
			startActivity(i);
		}
			return true;
		}
		return onOptionsItemSelected(item);
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.bsendgreet: 
		
		Intent i = new Intent(GreetingReceived.this,Friends.class);
		startActivity(i);
		 
		}
		
	}


	
		
	
	
}


