package com.kushal.chaton.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kushal.chatton.R;
import com.kushal.chatton.misc.ConnectionDetector;

public class LoginActivity extends Activity {

	private static final String url = "https://kushal.firebaseio.com/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.action_bar);
		TextView tv = (TextView) findViewById(R.id.Title);
		tv.setText(R.string.login);
		tv.setPadding(265, 15, 0, 15);
		setContentView(R.layout.activity_login);
		getIntent();
		final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		final Boolean isInternetPresent = cd.isConnectingToInternet(); 
		
		if (!isInternetPresent) {
			cd.showAlertDialog(LoginActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
			
        } 
		final EditText num = (EditText) findViewById(R.id.mobno);
		final EditText pass = (EditText) findViewById(R.id.pass);
		
		Button login = (Button) findViewById(R.id.login_button);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isInternetPresent) {
					cd.showAlertDialog(LoginActivity.this, "No Internet Connection",
		                    "You don't have internet connection.", false);
					
		        } 
				else {
						
					if(num.length()==0 || pass.length()==0)
					{
						Toast.makeText(getApplicationContext(), "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show();
					}
					else
					{
						final ProgressDialog pd = ProgressDialog.show(LoginActivity.this, "Please Wait", "Loading....");
						pd.setCancelable(false);
						new Thread(new Runnable(){
							public void run(){
								try{
									Thread.sleep(4000);
								}catch(Exception e){}
								pd.dismiss();
							}
						}).start();
						final String nu = num.getText().toString();
						final String pa = pass.getText().toString();
						Firebase fb = new Firebase(url).child("users");
						fb.child(nu).addValueEventListener(new ValueEventListener() {
							
							@Override
							public void onDataChange(DataSnapshot snapshot) {	
								
								if(nu.equals(snapshot.child("mobno").getValue()) && pa.equals(snapshot.child("password").getValue()))
								{
									Intent user_con = new Intent(LoginActivity.this,UserContacts.class);
									user_con.putExtra("myNo", nu);
									Bundle bndlanimation = 
											ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
									  startActivity(user_con, bndlanimation);
						
								}
								else
								{
									Toast.makeText(getApplicationContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();
								}
								
							}
							
							@Override
							public void onCancelled(FirebaseError error) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(), "Failed. Try Again!", Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
			}
		});
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent back = new Intent(LoginActivity.this,MainActivity.class);
		 Bundle bndlanimation = 
					ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
			  startActivity(back, bndlanimation);
	}
	
}
