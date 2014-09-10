package com.kushal.chatton.Adapters;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.kushal.chatton.R;
import com.kushal.chatton.Beans.Chat;

public class ChatListAdapter extends FirebaseListAdapter<Chat> {

    private String number;

    public ChatListAdapter(Query ref, Activity activity, int layout, String number) {
        super(ref, Chat.class, layout, activity);
        this.number = number;
    }


	/**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param chat An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Chat chat) {
        // Map a Chat object to an entry in our listview
        	String num = chat.getSender();
        	TextView ch = (TextView) view.findViewById(R.id.tv_chat);
        	TextView sent = (TextView) view.findViewById(R.id.tv_time);

        if (num.equals(number)) {
        	ch.setText(chat.getMessage());
        	ch.setBackgroundResource(R.drawable.bubble_a);
        	ch.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        	ch.setPadding(250, 5, 40, 15);
        	sent.setText(chat.getSent_time());
        	sent.setTextSize(11);
        	sent.setPadding(10, 10, 5, 0);	
        }
         else {
        	ch.setText(chat.getMessage());
        	ch.setBackgroundResource(R.drawable.bubble_b);
        	ch.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        	ch.setPadding(40, 20, 250, 15);
        	sent.setText(chat.getSent_time());
        	sent.setTextSize(11);
        	sent.setPadding(500, 20, 5, 0);
        }
    }
}
