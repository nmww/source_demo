package com.kl.test;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.provider.ContactsContract.QuickContact;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;


public class QuickContactAdapter extends BaseAdapter {
	public static final int RES_TEXT_ID = 1;
	public static final int RES_BADGE_ID = 0;
    private LayoutInflater mInflater;	
    private List<? extends Map<String, ?>> mData;
    private String[] mFrom;
    private int[] mTo;
    private int mResource;
   
    public final class ViewHolder{
        public QuickContactBadge badge;
        public TextView text;
    }
     
    public QuickContactAdapter(Context context, List<? extends Map<String, ?>> list, int resource,
    		String[] from, int[] to) {
    	mInflater = LayoutInflater.from(context);
    	mFrom = from;
    	mData = list;
    	mTo = to;
    	mResource = resource;
    }
    
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder(); 
            convertView = mInflater.inflate(mResource, null);
            holder.text = (TextView)convertView.findViewById(mTo[RES_TEXT_ID]);
            holder.badge = (QuickContactBadge)convertView.findViewById(mTo[RES_BADGE_ID]);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
         
        holder.text.setText((String)mData.get(position).get(mFrom[RES_TEXT_ID]));
        holder.badge.setMode(QuickContact.MODE_SMALL);
        holder.badge.assignContactFromPhone((String)mData.get(position).get(mFrom[RES_BADGE_ID]), true);  
         
        return convertView;
    }
}
