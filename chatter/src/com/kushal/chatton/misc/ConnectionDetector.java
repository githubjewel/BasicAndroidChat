package com.kushal.chatton.misc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    
    private Context context;
     
    public ConnectionDetector(Context context){
        this.context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null) 
                  for (int i = 0; i < info.length; i++) 
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
	   @SuppressWarnings("deprecation")
	public void showAlertDialog(final Context context, String title, String message, Boolean status) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	 
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(message);
	         
	 
	        // Setting OK Button
	        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	Intent settings = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
	            	settings.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    			context.startActivity(settings);
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	    }
}