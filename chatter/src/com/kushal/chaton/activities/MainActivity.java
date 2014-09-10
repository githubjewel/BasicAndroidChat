package com.kushal.chaton.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kushal.chatton.R;
import com.kushal.chatton.misc.ConnectionDetector;


public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.action_bar);
		TextView tv = (TextView) findViewById(R.id.Title);
		tv.setText(R.string.app_name);
		tv.setPadding(265, 15, 0, 15);
		final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		final Boolean isInternetPresent = cd.isConnectingToInternet(); 
		
		if (!isInternetPresent) {
			cd.showAlertDialog(MainActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        } 
		setContentView(R.layout.activity_main);
		
		Button signup = (Button) findViewById(R.id.signup);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isInternetPresent){
					Intent signup = new Intent(MainActivity.this,SignupActivity.class);
					Bundle bndlanimation = 
							ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
					  startActivity(signup, bndlanimation);
				}
				if (!isInternetPresent) {
					cd.showAlertDialog(MainActivity.this, "No Internet Connection",
		                    "You don't have internet connection.", false);
		        } 
			}
		});
		
		Button login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isInternetPresent){		
					Intent login = new Intent(MainActivity.this,LoginActivity.class);
					Bundle bndlanimation = 
							ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
					  startActivity(login, bndlanimation);
				
				}
				if (!isInternetPresent) {
					cd.showAlertDialog(MainActivity.this, "No Internet Connection",
		                    "You don't have internet connection.", false);
		        } 
			}
		});
	
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  
    {
         if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
         {
            this.moveTaskToBack(true);
            return true;
         }
        return super.onKeyDown(keyCode, event);
    }
		
}
