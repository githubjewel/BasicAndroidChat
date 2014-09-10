package com.kushal.chaton.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kushal.chatton.R;
import com.kushal.chatton.Adapters.ChatListAdapter;
import com.kushal.chatton.Beans.Chat;

@SuppressLint("InflateParams")
public class UserActivity extends ListActivity {
	private Firebase ref;
	private String url = "https://kushal.firebaseio.com/";
    public String key;
    private ChatListAdapter chatListAdapter;
    public String num, myNo, user;
    private ValueEventListener connectedListener;

	@SuppressLint({ "InflateParams", "SimpleDateFormat" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		user = getIntent().getExtras().getString("user");
		num = getIntent().getExtras().getString("num");
		myNo = getIntent().getExtras().getString("myNo");
		key = numberSorting(myNo,num);
		ref = new Firebase(url).child("zChats");
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		LayoutInflater title_inflater = LayoutInflater.from(this);
		View customView = title_inflater.inflate(R.layout.action_bar, null);
		getActionBar().setCustomView(customView);
		getActionBar().setDisplayShowCustomEnabled(true);
		TextView tv = (TextView) customView.findViewById(R.id.Title);		
		tv.setText(user);
		tv.setTextSize(20);
		tv.setPadding(0, 20, 0, 0);
		
		 final ListView lv = getListView();
	     chatListAdapter = new ChatListAdapter(ref.child(key).endAt().limit(100), this, R.layout.list_chat, myNo);
	     lv.setAdapter(chatListAdapter);
	     lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		 lv.setStackFromBottom(true);
		    
		
	        connectedListener = ref.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
	            @Override
	            public void onDataChange(DataSnapshot dataSnapshot) {
	                	dataSnapshot.getValue();
	            }

				@Override
				public void onCancelled(FirebaseError arg0) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Failed. Try Again!", Toast.LENGTH_SHORT).show();
					
				}
	        });
	        
	        ref.getRoot().child(".info/connected").removeEventListener(connectedListener);

	        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	                sendMessage();
	            }
	        });
	    }
	
	
				@SuppressLint("SimpleDateFormat")
		private void sendMessage() {
	        EditText inputText = (EditText)findViewById(R.id.messageInput);
	        String input = inputText.getText().toString();
	        
	        Calendar c = Calendar.getInstance();
	        SimpleDateFormat tim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String tym = tim.format(c.getTime());

	        if (!input.equals("")) {
	            // Create our 'model', a Chat object
	            Chat chat = new Chat(input, myNo, tym);
	            // Create a new, auto-generated child of that chat location, and save our chat data there
	            ref.child(key).push().setValue(chat);
	            inputText.setText("");
	     
	        }
	    }
	    
	    public String numberSorting(String myNo , String num){
			String result = myNo + '_' + num;
			try{
				if(Long.parseLong(myNo) > Long.parseLong(num))
				{
					result = num + '_' + myNo;
				}
			}
			catch(Exception e){
				e.getStackTrace();
			}
			return result;
		}
	    
	}

