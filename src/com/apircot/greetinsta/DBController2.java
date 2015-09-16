package com.apircot.greetinsta;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController2 extends SQLiteOpenHelper{

	
	public DBController2(Context applicationcontext)
	 {
       super(applicationcontext, "androidsqlite1.db", null, 2);
	 }
	
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query;
		query = "CREATE TABLE inbox (greetdata TEXT)";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 String query;
	        query = "DROP TABLE IF EXISTS inbox";
	        db.execSQL(query);
	        onCreate(db);
	}

	
	public void insertInbox(ArrayList<String> queryValues)
	{
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int size  = queryValues.size();
		for(int i=0;i<size;i++)
		{
			System.out.println("SIZE is.. "+size+" and value of i="+i);
			System.out.println("...FIRST FRIEND NAME "+queryValues.get(i));
			values.put("greetdata",queryValues.get(i));
			db.insert("inbox",null, values);
		}
		
	
		System.out.println("!!........HERE IT SAVED.........!!!");
		
		Iterator<String> itr = queryValues.iterator();
		while(itr.hasNext())
		{
			Object obj = itr.next();
			System.out.println(obj);
		}
		
		db.close();
	}

	public ArrayList<String> getInbox()
	{
		
		String getGreetData;
		ArrayList<String> wordList = new ArrayList<String>();
	String selectquery = "Select * FROM inbox";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectquery, null);
	
		if(cursor.moveToFirst())
		{

			
			do{
			   
				getGreetData = cursor.getString(cursor.getColumnIndex("greetdata"));
				wordList.add(getGreetData);
			}while(cursor.moveToNext());
		}
		System.out.println("!!!......HERE YOU HAVE ENTERED TO RECEIVED......!!");
		Iterator<String> itr = wordList.iterator();
		while(itr.hasNext())
		{
			Object obj = itr.next();
			System.out.println(obj);
		}
	
		
		db.close();
		
		return wordList;
	}
	
	
}
