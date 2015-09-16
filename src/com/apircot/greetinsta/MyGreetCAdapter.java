package   com.apircot.greetinsta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MyGreetCAdapter extends ArrayAdapter<String>{
	
		public MyGreetCAdapter(Context context, int simpleListItem1, ArrayList<String> userList) {
		// TODO Auto-generated constructor stub
		super(context, simpleListItem1,userList);
		
	}


	public MyGreetCAdapter(Context baseContext, int simpleListItem1,
			String[] settingsarray) {
		super(baseContext,simpleListItem1,settingsarray);
		// TODO Auto-generated constructor stub
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub 
		View view = super.getView(position, convertView, parent);
		
	TextView textview = (TextView)view.findViewById(android.R.id.text1);
		
		 
		if(position % 2 == 1 )
		{
			view.setBackgroundColor(Color.rgb(245, 250, 237));
			view.setBackgroundColor(Color.rgb(245, 250, 237));
		    textview.setTextColor(Color.rgb(49, 180, 4));
	        textview.setGravity(Gravity.CENTER);
            
		}else
		{
	        view.setBackgroundColor(Color.rgb(49, 180, 4));
            textview.setTextColor(Color.rgb(245, 250, 237));
            textview.setGravity(Gravity.CENTER);
		}
		return view;
	}
}
   