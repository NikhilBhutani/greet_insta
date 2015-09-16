package com.apircot.greetinsta;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter<String> extends ArrayAdapter<String>{

	

	public MyAdapter(Context context, int textViewResourceId, String[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
		
		TextView textview = (TextView)view.findViewById(android.R.id.text1);
		
		
	//	if(position % 2 == 1 )
		{
			view.setBackgroundColor(Color.rgb(255, 64, 64));
            textview.setTextColor(Color.WHITE);
            textview.setGravity(Gravity.CENTER);
           
            
		}//else
	//	{
	//		view.setBackgroundColor(Color.WHITE);
		//	textview.setTextColor(Color.rgb(0, 153, 204));
	  //      textview.setGravity(Gravity.CENTER);	
	//	}
		return view;
	}
	
	
}
