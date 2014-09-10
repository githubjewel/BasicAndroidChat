package com.kushal.chatton.Adapters;

import java.util.List;

import com.kushal.chatton.R;
import com.kushal.chatton.Beans.ContactBean;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContactsAdapter extends ArrayAdapter<ContactBean> {

	private Activity activity;
	private int row;
	private List<ContactBean> items;
	private ContactBean conBean;

	public ContactsAdapter(Activity act, int row,
			List<ContactBean> items) {
		super(act, row, items);
		this.activity = act;
		this.row = row;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder;
		if(view == null)
		{
			LayoutInflater inflater =  (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);
			holder = new ViewHolder();
			view.setTag(holder);	
		}
		else
		{
			holder = (ViewHolder) view.getTag();
		}
		
		if ((items == null) || ((position + 1) > items.size()))
			return view;
		
		conBean = items.get(position);
		holder.contact_name = (TextView) view.findViewById(R.id.tv1);
		String[] colors = {"#B3A263D9","#B356B336","#B3E36326","#B353A4A6","#B3764345","#B343602A",
				"#B3D8507D","#B3C99438",
				"#B3435770",
				"#B3BC84A4",
				"#B3D88069",
				"#B37299CD",
				"#B34EAF6F",
				"#B367458E",
				"#B37A5323",
				"#B3E0444E",
				"#B39AA238",
				"#B36575D6",
				"#B3803965",
				"#B3CB3F99",
				"#B3C887D0",
				"#B3D54AD5",
				"#B3A33B1D",
				"#B3953339",
				"#B376A168"};
		//String[] colors = {"#002FD9FB","#EF663C","#B5F743","#F99DE8","#6F9867","#FBD15B","#FE5586","#7FFFA0","#978593","#CAE2E4","#8789D3","#838725","#0BC299","#E89A5A","#E7FB82","#4A91A3","#59B94F","#72FBE9","#E9BFC6","#9DBE15","#3BB6F1","#8D8457","#C8B8F0","#C2FA9E","#22A467","#88F6B6","#BE96EF","#ECF25A","#948775","#9D8511","#7BEDFA","#E55448","#D96F35","#C0D918","#21B067"};
		
		if (holder.contact_name != null && conBean.getName() != null
				&& conBean.getName().trim().length() > 0) {
			holder.contact_name.setText(Html.fromHtml(conBean.getName()));
			holder.contact_name.setBackgroundColor(Color.parseColor(colors[position%colors.length]));
			}
		return view;
		
	}
	
	public class ViewHolder{
		private TextView contact_name;
	}

}
