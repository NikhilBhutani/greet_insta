package com.apircot.greetinsta;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import com.google.android.gms.plus.model.people.Person;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Friends extends Activity implements OnClickListener, OnItemClickListener{
     View v,view;
     TextView text;
     String name,nameIncaps,useri;
     String a= "1";
     ListView lv;
     ArrayAdapter<String> adapter;
     EditText userInput;
     ArrayList<String> userList;
     ImageButton imagebutton;
     DBController controller = new DBController(this);
     RequestParams params = new RequestParams();
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
  		getActionBar().setDisplayShowHomeEnabled(false);
    	getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00933B")));
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setCustomView(R.layout.mycustomactionbar);	
		
		lv = (ListView)findViewById(R.id.list_temp);
		//Get User records from SQLite DB
         userList =  controller.getAllUsers();
        adapter = new MyGreetCAdapter(getBaseContext(),android.R.layout.simple_list_item_1 , userList);
       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,userList);
		 lv.setAdapter(adapter); 
		    
    
     //   LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = getActionBar().getCustomView();

        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        name = prefs.getString("greet_id", "");
        nameIncaps = name.toUpperCase();
      
        
		text = (TextView)v.findViewById(R.id.textView1);
 		text.setText("FRIENDS" );
		
		  
        getActionBar().setCustomView(v);
        imagebutton = (ImageButton)findViewById(R.id.imageButton1);
        imagebutton.setOnClickListener(this);
        lv = (ListView)findViewById(R.id.list_temp);
        lv.setOnItemClickListener(this);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,temp);
       // listview.setAdapter(adapter);
        
        
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		 case  R.id.imageButton1:
		 {
			 inputDialog();
		 }	  
		
		}
	}
	private void inputDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(Friends.this);
		View ourview = inflater.inflate(R.layout.users, null);
		AlertDialog.Builder alertdialog =  new AlertDialog.Builder(Friends.this);
		
		alertdialog.setView(ourview);
		
		userInput = (EditText)ourview.findViewById(R.id.editTextDialogUserInput);
		
		
		alertdialog.setCancelable(false)
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		}).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String user = userInput.getText().toString();
			    useri = user.toUpperCase();
			    if(userList.contains(useri))
			    {
			    	Toast.makeText(Friends.this, "User Already added", Toast.LENGTH_SHORT).show();
			    }
			    else if(nameIncaps.equals(useri))
			    {
			    	Toast.makeText(Friends.this, "You cannot add yourself", Toast.LENGTH_SHORT).show();
			    }
			    else
			    {
				storeinServer(useri);
			//	Toast.makeText(Friends.this, "Ok clicked", Toast.LENGTH_SHORT).show();
				Toast.makeText(Friends.this, "Adding Friend..", Toast.LENGTH_SHORT).show();
			    }
			 }
		});
		
		AlertDialog alertDialog = alertdialog.create();
		 
		// show it
		alertDialog.show();

	}
	
	private void newfunc()
	{
		// TODO Auto-generated method stub
			ArrayList<String> queryValues = new ArrayList<String>();
		queryValues.add(useri);
		
		System.out.println("..!!...ADDING FRIEND NAME "+useri);
		  if (useri!= null
	                && useri.trim().length() != 0) 
		  {
		  controller.insertUser(queryValues);

			  this.callHome(view);
		  }else {
	            Toast.makeText(getApplicationContext(), "Please enter GreetId",
	                    Toast.LENGTH_LONG).show();
	        }
		
	}
	    private void callHome(View view) {
		  // TODO Auto-generated method stub
		   Intent objIntent = new Intent(getApplicationContext(),
	                Friends.class);
	        startActivity(objIntent);
	        
	  
  }


	    
	    private void storeinServer(String greetID) {
	       
	        params.put("greetid", greetID);
	        
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	        AsyncHttpClient client = new AsyncHttpClient();
	        client.post(ApplicationConstants.CHECK_ID, params,
	                new AsyncHttpResponseHandler() {
	                    // When the response returned by REST has Http
	                    // response code '200'
	                    @Override
	                    public void onSuccess(String response) {
	                        // Hide Progress Dialog
	                 //   	System.out.println("Niks"+response);
	                  
	                    	response = response.replace("\n","");
	                    	
	                 /*   	System.out.println("look below");
	                    	
	                        String myresponse = (response.concat("Niks"));
	                   */   
	                      if(response.equals("1"))
	                      {	  
	                    	 
	                       newfunc();
	                      }
	                      else
	                      {
	                    	  
	                    	  Toast.makeText(Friends.this,
		                                "USER NOT FOUND",
		                                Toast.LENGTH_LONG).show();
		                        
	                      }
	                       // finish();
	                    }
	 
	                    // When the response returned by REST has Http
	                    // response code other than '200' such as '404',
	                    // '500' or '403' etc
	                    @Override
	                    public void onFailure(int statusCode, Throwable error,
	                            String content) {
	                        // Hide Progress Dialog
	                   
	                        // When Http response code is '404'
	                        if (statusCode == 404) {
	                            Toast.makeText(Friends.this,
	                                    "Requested resource not found",
	                                    Toast.LENGTH_LONG).show();
	                        }
	                        // When Http response code is '500'
	                        else if (statusCode == 500) 
	                          {
	                            Toast.makeText(Friends.this,
	                                    "Something went wrong at server end",
	                                    Toast.LENGTH_LONG).show();
	                          }
	                        // When Http response code other than 404, 500
	                        else {
	                            Toast.makeText(
	                                    Friends.this,
	                                    "Unexpected Error occcured! [Most common Error: Device might "
	                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
	                                    Toast.LENGTH_LONG).show();
	                             }
	                    }
	                    });
	        
	        }

	    
	    
	    
	    
	    
		/* (non-Javadoc)
		 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
           String friendName = (String)parent.getItemAtPosition(position);
		//	Toast.makeText(getApplicationContext(), friendName, Toast.LENGTH_SHORT).show();
		
			Intent intent = new Intent(getApplicationContext(),SendGreet.class);
			intent.putExtra("friendName", friendName);
			startActivity(intent);
		}
		/* (non-Javadoc)
		 * @see android.app.Activity#finish()
		
	    @Override
	    protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}
		 */
		
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
			Intent i = new Intent(Friends.this,GreetSettings.class);
				
				startActivity(i);
			}
				return true;
			}
			return onOptionsItemSelected(item);
			
		}

	

}
