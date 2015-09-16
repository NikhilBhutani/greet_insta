package com.apircot.greetinsta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.android.gms.internal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper{
	
	public DBController(Context applicationcontext)
	 {
        super(applicationcontext, "androidsqlite.db", null, 2);
	 }
	
	//Create Table
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query;
		query = "CREATE TABLE users (greetId TEXT)";
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		  String query;
	        query = "DROP TABLE IF EXISTS users";
	        db.execSQL(query);
	        onCreate(db);
		
	}
	
	/**
	 * Insert into sqlite DB
	 */
	 
	public void insertUser(ArrayList<String> queryValues)
	{
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int size  = queryValues.size();
		for(int i=0;i<size;i++)
		{
			System.out.println("SIZE is.. "+size+" and value of i="+i);
			System.out.println("...FIRST FRIEND NAME "+queryValues.get(i));
			values.put("greetId",queryValues.get(i));
			db.insert("users",null, values);
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

	public ArrayList<String> getAllUsers()
	{
		
		String getGreetId;
		ArrayList<String> wordList = new ArrayList<String>();
	String selectquery = "Select * FROM users";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectquery, null);
	
		if(cursor.moveToFirst())
		{

			
			do{
			   
				getGreetId = cursor.getString(cursor.getColumnIndex("greetId"));
				wordList.add(getGreetId);
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
