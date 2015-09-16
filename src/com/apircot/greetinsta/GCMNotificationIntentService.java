package com.apircot.greetinsta;


import java.util.ArrayList;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.ArrayAdapter;

public class GCMNotificationIntentService extends IntentService {
	
	String sendersName,greetName,greetmsg;
	
	DBController2 db2 =  new DBController2(this);
	
	public static int notifyID = 9001;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
		
	//	sendersName = intent.getExtras().getString("senders_name");
		sendersName = extras.get(ApplicationConstants.SENDER_KEY).toString();
        greetmsg = extras.get(ApplicationConstants.GREETMSG_KEY).toString();
		System.out.println("HEEEEEEEEEEEEEEEEEEEELOLLLLLLLLLLLLLOOOOOOLLLLLLLLLOOOOOO "+greetmsg);		
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		
		 String messageType = gcm.getMessageType(intent);
                 
         System.out.println(messageType);
         System.out.println("HOOLLAA "+sendersName);
       if (!extras.isEmpty()) {
           if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                   .equals(messageType)) {
               sendNotification("Send error: " + extras.toString(),sendersName,greetmsg);
           } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                   .equals(messageType)) {
               sendNotification("Deleted messages on server: "
                       + extras.toString(),sendersName,greetmsg);
           } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                   .equals(messageType)) {
               sendNotification(extras.get(ApplicationConstants.MSG_KEY).toString(),sendersName,greetmsg);
           }
           
       }
         
       GcmBroadcastReceiver.completeWakefulIntent(intent);

	}
	
	   private void sendNotification(String msg,String sendersName,String mygreetmsg) {
           Intent resultIntent = new Intent(this, GreetingReceived.class);
           resultIntent.putExtra("msg", msg);
           resultIntent.putExtra("MYGREETMSG", mygreetmsg);
           System.out.println(msg+" from "+sendersName);
           int i=0;
           msg=msg.concat(" from ");
           msg=msg.concat(sendersName);
           msg=msg.toUpperCase();
           
           ArrayList<String> greetinbox = new ArrayList<String>();
           greetinbox.add(msg);           
           db2.insertInbox(greetinbox);    
           PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                   resultIntent, PendingIntent.FLAG_ONE_SHOT);

           NotificationCompat.Builder mNotifyBuilder;
           NotificationManager mNotificationManager;
           
           

           mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

           mNotifyBuilder = new NotificationCompat.Builder(this)
                   .setContentTitle("GREETINSTA")
                   .setContentText("You've received new message.")
                   .setSmallIcon(R.drawable.ic_launcher);
           // Set pending intent
           mNotifyBuilder.setContentIntent(resultPendingIntent);

           // Set Vibrate, Sound and Light            
           int defaults = 0;
           defaults = defaults | Notification.DEFAULT_LIGHTS;
           defaults = defaults | Notification.DEFAULT_VIBRATE;
           defaults = defaults | Notification.DEFAULT_SOUND;

           String sendersNameInCaps = sendersName.toUpperCase();
           mNotifyBuilder.setDefaults(defaults);
           PendingIntent.getActivity(this, notifyID, resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
           // Set the content for Notification 
           mNotifyBuilder.setContentText("GREETINGS FROM "+sendersNameInCaps);
           
           // Set autocancel
           mNotifyBuilder.setAutoCancel(true);
           // Post a notification
           mNotificationManager.notify(notifyID, mNotifyBuilder.build());
           notifyID++;
           
   }
	


}
