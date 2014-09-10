package com.kushal.chaton.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.kushal.chatton.R;
import com.kushal.chatton.Adapters.ContactsAdapter;
import com.kushal.chatton.Beans.ContactBean;

public class UserContacts extends Activity implements OnItemClickListener {

	private ListView listView;
	private List<ContactBean> listContacts = new ArrayList<ContactBean>();
	private PhoneNumberUtil phoneUtil;
	private String countryCode;
	@SuppressLint("DefaultLocale")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.action_bar);
		TextView tv = (TextView) findViewById(R.id.Title);
		tv.setText(R.string.app_name);
		tv.setPadding(160, 15, 0, 15);
		setContentView(R.layout.activity_user_contacts);
		listView = (ListView) findViewById(R.id.contact_listview);
		listView.setOnItemClickListener(this);
		TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		countryCode = manager.getNetworkCountryIso();
		phoneUtil = PhoneNumberUtil.getInstance();
		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {

				String Contactname = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	
				String ContactNo = phones
						.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				
			
				try {

					ContactNo = phoneUtil.format(phoneUtil.parse(ContactNo, countryCode.toUpperCase()), PhoneNumberFormat.E164).replaceAll("[-+^]*", "");
				} catch (NumberParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ContactBean objContact = new ContactBean();
				objContact.setName(Contactname);		
				objContact.setPhone(ContactNo);
				listContacts.add(objContact);
		}
		phones.close();

		ContactsAdapter objAdapter = new ContactsAdapter(
				UserContacts.this, R.layout.list_item, listContacts);
		listView.setAdapter(objAdapter);		
		if (listContacts != null && listContacts.size() != 0) {
			Collections.sort(listContacts, new Comparator<ContactBean>() {
				@Override
				public int compare(ContactBean lhs, ContactBean rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}
			});

		} else {
			Toast.makeText(this, "No Contact Found!!!", Toast.LENGTH_SHORT).show();
		}
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
		String myNo = getIntent().getExtras().getString("myNo");
		try {
			myNo = phoneUtil.format(phoneUtil.parse(myNo, countryCode.toUpperCase()), PhoneNumberFormat.E164).replaceAll("[-+^]*", "");
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
		ContactBean cb = (ContactBean) listView.getItemAtPosition(position);	
		String str = cb.getName();
		String no = cb.getPhone();
		Intent user = new Intent(UserContacts.this,UserActivity.class);
		user.putExtra("user",str);
		user.putExtra("num", no);
		user.putExtra("myNo", myNo);
		Bundle bndlanimation = 
				ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation1,R.anim.animation2).toBundle();
		  startActivity(user, bndlanimation);
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
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {		    
		case R.id.refresh :
				return true;
		case R.id.contacts :
				return true;

		case R.id.action_settings:
				return true;

		case R.id.logout:
				Intent logout = new Intent(UserContacts.this,LoginActivity.class);
				startActivity(logout);
				finish();
		   
		}
		
		return super.onOptionsItemSelected(item);
	}


}
