package com.apircot.greetinsta;


import java.io.IOException;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
 
	ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    GoogleCloudMessaging gcmObj;
    Context applicationContext;
    String regId = "";
 
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
 
    AsyncTask<Void, Void, String> createRegIdTask;
 
    public static final String REG_ID = "regId";
    public static final String GREET_ID = "greet_id";
    EditText greetIdET;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        
        
        applicationContext = getApplicationContext();
        greetIdET = (EditText) findViewById(R.id.greetid);
 
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
 
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
 
        if (!TextUtils.isEmpty(registrationId)) {
           // Intent i = new Intent(applicationContext, GreetingReceived.class);
        	 Intent i = new Intent(applicationContext, Friends.class);
            i.putExtra("regId", registrationId);
            startActivity(i);
            finish();
        }
 
    }
 
    // When Register button is clicked
    public void RegisterUser(View view) {
        String greetID = greetIdET.getText().toString();
 
        if (!TextUtils.isEmpty(greetID) ) {
            // Check if Google Play Service is installed in Device
            // Play services is needed to handle GCM stuffs
            if (checkPlayServices()) {
 
                // Register Device in GCM Server
                registerInBackground(greetID);
            }
        }
        
        else {
            Toast.makeText(applicationContext, "Please Enter UserId",
                    Toast.LENGTH_LONG).show();
        }
    }
 
    // AsyncTask to register Device in GCM Server
    private void registerInBackground(final String greetID) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(applicationContext);
                    }
                    regId = gcmObj
                            .register(ApplicationConstants.GOOGLE_PRJ_KEY);
                    msg = "Registration ID :" + regId;
 
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }
 
            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    storeRegIdinSharedPref(applicationContext, regId, greetID);
                  /*  Toast.makeText(
                            applicationContext,
                            "Registered with GCM Server successfully.\n\n"
                                    + msg, Toast.LENGTH_SHORT).show(); */
                    
                      Toast.makeText(applicationContext, "Welcome "+ greetID, Toast.LENGTH_LONG).show();
            
                    
                } else {
                    Toast.makeText(
                            applicationContext,
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }
 
    // Store RegId and Email entered by User in SharedPref
    private void storeRegIdinSharedPref(Context context, String regId,
            String greetID) {
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putString(GREET_ID, greetID);
        editor.commit();
        storeRegIdinServer(regId, greetID);
 
    }
 
    // Share RegID and Email ID with GCM Server Application (Php)
    private void storeRegIdinServer(String regId2, String greetID) {
        prgDialog.show();
        String greetIDcaps = greetID.toUpperCase();
        params.put("greetId", greetIDcaps);
        params.put("regId", regId);
      //  System.out.println("User id = " + greetIDcaps + " Reg Id = " + regId);
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(ApplicationConstants.APP_SERVER_URL, params,
                new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                  /*      Toast.makeText(applicationContext,
                                "Reg Id shared successfully with Web App ",
                                Toast.LENGTH_LONG).show();  */
                       // Intent i = new Intent(applicationContext,
                         //       GreetingReceived.class);
                        Intent i = new Intent(applicationContext,
                                Friends.class);
                        
                        i.putExtra("regId", regId);
                        startActivity(i);
                        finish();
                    }
 
                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                            String content) {
                        // Hide Progress Dialog
                        prgDialog.hide();
                        if (prgDialog != null) {
                            prgDialog.dismiss();
                        }
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(applicationContext,
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(applicationContext,
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    applicationContext,
                                    "Unexpected Error occcured! [Most common Error: Device might "
                                            + "not be connected to Internet or remote server is not up and running], check for other errors as well",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
 
    // Check if Google Playservices is installed in Device or not
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(
                        applicationContext,
                        "This device doesn't support Play services, App will not work normally",
                        Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
           /* Toast.makeText(
                    applicationContext,
                    "This device supports Play services, App will work normally",
                    Toast.LENGTH_LONG).show();  */
        }
        return true;
    }
 
    // When Application is resumed, check for Play services support to make sure
    // app will be running normally
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

	
}
