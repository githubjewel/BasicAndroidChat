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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kushal.chatton.R;
import com.kushal.chatton.misc.ConnectionDetector;

public class SignupActivity extends Activity {
	private static final String url = "https://kushal.firebaseio.com/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.action_bar);
		TextView tv = (TextView) findViewById(R.id.Title);
		tv.setText(R.string.signup);
		tv.setPadding(265, 15, 0, 15);
		setContentView(R.layout.activity_signup);
		getIntent();
		final ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		final Boolean isInternetPresent = cd.isConnectingToInternet(); 
		
		if (!isInternetPresent) {
			cd.showAlertDialog(SignupActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
			
        } 
		
		
		final EditText num = (EditText) findViewById(R.id.mobno);
		final EditText pass = (EditText) findViewById(R.id.pass);
		final EditText repass = (EditText) findViewById(R.id.repass);
		Button signup = (Button) findViewById(R.id.signup_button);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isInternetPresent) {
					cd.showAlertDialog(SignupActivity.this, "No Internet Connection",
		                    "You don't have internet connection.", false);
		        } 
				if(num.length()==0 || pass.length()==0 || repass.length() == 0)
				{
					Toast.makeText(getApplicationContext(), "Fields Cannot Be Empty", Toast.LENGTH_LONG).show();
				}
				else if(!pass.getText().toString().equals(repass.getText().toString()))
				{
					Toast.makeText(getApplicationContext(), "Password does not Match", Toast.LENGTH_LONG).show();
				}
				
				else
				{
					String nu = num.getText().toString();
					final String pa = pass.getText().toString();
					Firebase fb = new Firebase(url);
						fb.child("users").child(nu).child("mobno").setValue(nu,new Firebase.CompletionListener() {
							
							@Override
							public void onComplete(FirebaseError error, Firebase firebase) {
									if(error!=null)
									{
										Toast.makeText(getApplicationContext(), "Retry Again", Toast.LENGTH_LONG).show();
									}	
							}
						});
							
						fb.child("users").child(nu).child("password").setValue(pa, new Firebase.CompletionListener() {
							@Override
							public void onComplete(FirebaseError error, Firebase firebase) {
								if(error!=null)
								{
									Toast.makeText(getApplicationContext(), "Retry Again", Toast.LENGTH_LONG).show();
								}	
								
							}
						});
						final ProgressDialog pd = ProgressDialog.show(SignupActivity.this, "Please Wait", "Loading....");
						pd.setCancelable(false);
						new Thread(new Runnable(){
							public void run(){
								try{
									Thread.sleep(4000);
								}catch(Exception e){}
								pd.dismiss();
							}
						}).start();
						Intent user_con = new Intent(SignupActivity.this,UserContacts.class);
						user_con.putExtra("myNo", nu);
						Bundle bndlanimation = 
								ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
						  startActivity(user_con, bndlanimation);
					}
				}
				
		});		
	}
	@Override
	public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	Intent back = new Intent(SignupActivity.this,MainActivity.class);
	 Bundle bndlanimation = 
				ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation3,R.anim.animation4).toBundle();
		  startActivity(back, bndlanimation);
}
}
