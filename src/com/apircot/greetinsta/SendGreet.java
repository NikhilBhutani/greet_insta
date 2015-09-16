package com.apircot.greetinsta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SendGreet extends Activity implements OnItemSelectedListener, OnClickListener{

	private Integer images[] = {R.drawable.birthday_1,R.drawable.birthday_2,R.drawable.birthday_3,R.drawable.birthday_4, 
			R.drawable.birthday_5,R.drawable.birthday_6,R.drawable.birthday_7,R.drawable.birthday_8,R.drawable.birthday_9,
			R.drawable.birthday_10,R.drawable.anniversary_1, R.drawable.anniversary_2,
			R.drawable.anniversary_3,R.drawable.anniversary_4,R.drawable.anniversary_5,R.drawable.anniversary_6,
			R.drawable.anniversary_7,R.drawable.anniversary_8,R.drawable.anniversary_9,R.drawable.anniversary_10,R.drawable.thankyou_1,
			R.drawable.thankyou_2,R.drawable.thankyou_3,R.drawable.thankyou_4,R.drawable.thankyou_5,R.drawable.thankyou_6,R.drawable.thankyou_7,R.drawable.thankyou_8,R.drawable.thankyou_9,
			R.drawable.thankyou_10,R.drawable.goodmorning_1,R.drawable.goodmorning_2,R.drawable.goodmorning_3,R.drawable.goodmorning_4,R.drawable.goodmorning_5,R.drawable.goodmorning_6,
			R.drawable.goodmorning_7,R.drawable.goodmorning_8,R.drawable.goodmorning_9,R.drawable.goodmorning_10,R.drawable.goodnight_1,
			R.drawable.goodnight_2,R.drawable.goodnight_3,R.drawable.goodnight_4,R.drawable.goodnight_5,R.drawable.goodnight_6,R.drawable.goodnight_7,
			R.drawable.goodnight_8,R.drawable.goodnight_9,R.drawable.goodnight_10,R.drawable.getwell_1,R.drawable.getwell_2,R.drawable.getwell_3,R.drawable.getwell_4,
			R.drawable.getwell_5,R.drawable.getwell_6,R.drawable.getwell_7,R.drawable.getwell_8,R.drawable.getwell_9,R.drawable.getwell_10,R.drawable.sorry_1,
			R.drawable.sorry_2,R.drawable.sorry_3,R.drawable.sorry_4,R.drawable.sorry_5,R.drawable.sorry_6,R.drawable.sorry_7,R.drawable.sorry_8,R.drawable.sorry_9
			,R.drawable.sorry_10,R.drawable.christmas_1,R.drawable.christmas_2,R.drawable.christmas_3,R.drawable.christmas_4,R.drawable.christmas_5,R.drawable.christmas_6,
			R.drawable.christmas_7,R.drawable.christmas_8,R.drawable.christmas_9,R.drawable.christmas_10,R.drawable.happynewyear_1,R.drawable.happynewyear_2,R.drawable.happynewyear_3,
			R.drawable.happynewyear_4,R.drawable.happynewyear_5,R.drawable.happynewyear_6,R.drawable.happynewyear_7,R.drawable.happynewyear_8,R.drawable.happynewyear_9,
			R.drawable.happynewyear_10};
	
	String ourimagename,msgwithgreet,friend_name,senderName;
	EditText etmsg;
	TextView Fname;
	Button rotatebutton,button;
    private int currImage = 0;
    int i = 0,varyPosition,j;
    Spinner spinner;
    String GREET_ID = "greet_id";
    private String occasionNames[] = {"Birthday","Anniversary","Thank you","Good Morning", "Good Night", "Get Well Soon"
    		,"Sorry","Merry Christmas", "Happy New Year","All The Best "};
    RequestParams greetparams = new RequestParams();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        

		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);		
    	getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00933B")));
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.mycustomactionbar);
	    
        
        SharedPreferences prefs = this.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
		senderName = prefs.getString(GREET_ID, "novalue");
	     
        
    	     
		spinner = (Spinner)findViewById(R.id.spinner);
		
		
		ArrayAdapter<String> adapter = new MyAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,occasionNames);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	    rotatebutton = (Button) findViewById(R.id.btnRotateImage);
         rotatebutton.setOnClickListener(this);
         etmsg = (EditText)findViewById(R.id.addmsg);
         button = (Button)findViewById(R.id.greet_to_a_user);
        
         Bundle bundle = getIntent().getExtras(); 
 	    friend_name = bundle.getString("friendName");
 	    
 	   View v = getActionBar().getCustomView();
 	  Fname = (TextView)v.findViewById(R.id.textView1);
		 Fname.setText(friend_name);
		  
 	  getActionBar().setCustomView(v);
 	    
     //   setInitialImage();
        
        button.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }
 
    /* public void setImageRotateListener(int p) {
    	final int y = p;
    	int i = y;
         {
            @Override
            public void onClick(View arg0) {
             if(i==0) 
              {
            	  
            	  y++;
            	  
              }
                   
            }
        });
    }*/
 /*
    private void setInitialImage() {
    //   setCurrentImage();
    }
 */
   private void setCurrentImage(int pos) {
        System.out.println("!!!!.........Your current position - "+pos);
    	currImage = pos;
        final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
       
        System.out.println("!!...Here is your printed ID "+images[currImage]);
        Field[] ID_Fields = R.drawable.class.getFields();
        int[] resArray = new int[ID_Fields.length];
        for(int i = 0; i < ID_Fields.length; i++) {
            try { 
                try {
					resArray[i]   = ID_Fields[i].getInt(null);
					
				   
					if(resArray[i]==images[currImage])
					{
						Field f = ID_Fields[i];
						ourimagename = f.getName();
						System.out.println("!!!!!!...... YOUR CARD NAME....... "+ourimagename);
						break;
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block 
                e.printStackTrace();
            } 
        }  
        imageView.setImageResource(images[currImage]); 
         
    }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		varyPosition = position;
		System.out.println("Your Spinner position is .. "+position);
		i = 10 * position;
		setCurrentImage(i);
	//	i = 10 * position;
		j = i + 9;
		// TODO Auto-generated method stub
		/* position++;
		int j= 10*position;
		  
		System.out.println("Helllooo value of ---- "+j);
		
			for(int m=j-10;m<j;m++)
			{
		     setImageRotateListener(m);	
			}
			
		*/
		
	}

	@Override 
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
    

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
     switch(v.getId())
     
     {		
    	 case  R.id.btnRotateImage :
    	   { if(i>=0 && i<j)
		      {
			    i++;
			    setCurrentImage(i);
		      }
		      else
		     {
			 
			  i= varyPosition*10;
			  setCurrentImage(i); 
			 
		     }
    	   break;
    	   }
    	   
    	 case R.id.greet_to_a_user:
    		 
    	 {
    		 	msgwithgreet = etmsg.getText().toString();
    			greetparams.put("GreetName", ourimagename);
    			greetparams.put("Greet_to",friend_name);
    			greetparams.put("Sender_name",senderName);
    			greetparams.put("GreetMSG", msgwithgreet);
    			AsyncHttpClient client = new AsyncHttpClient();
    			client.post(ApplicationConstants.APP_SERVER_URL_TO_RECEIVE, greetparams,
    					new AsyncHttpResponseHandler(){

    						/* (non-Javadoc)
    						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onSuccess(java.lang.String)
    						 */
    						@Override
    						public void onSuccess(String response) {
    							// TODO Auto-generated method stub
    					
    							Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();
    							super.onSuccess(response);
    						}

    						/* (non-Javadoc)
    						 * @see com.loopj.android.http.AsyncHttpResponseHandler#onFailure(int, java.lang.Throwable, java.lang.String)
    						 */
    						@Override
    					
    						public void onFailure(int statusCode, Throwable error,
    								String content) {
    							// TODO Auto-generated method stub
    							super.onFailure(statusCode, error, content);
    						}
    				
    				
    				
    				
    			
    		});

    		 
    		 
    		 
    		break; 
    	 }
     } 
	}


    
}