package com.kl.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class QuickContactList extends Activity {
	List<Map<String, String>> mData = new ArrayList<Map<String, String>>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fillListView();
    }
    
    private void fillListView() {
    	ListView view = (ListView) findViewById(R.id.list);
    	
    	List<Map<String, String>> data = mData;
    	for( int i = 0; i < 10; ++i ) {
    		Map<String, String> d1 = new HashMap<String, String>();
    		d1.put("text", String.format("Item %d", i));
    		d1.put("phone", String.format( "12%d", i));
    		data.add(d1);
    	}
    	
    	QuickContactAdapter adapter = new QuickContactAdapter(this, data, R.layout.list_item,
    			new String[] { "phone", "text" },
    			new int[] { R.id.avatar, R.id.subject } );
    	
    	view.setAdapter(adapter);
    	//view.setAdapter(new MyAdapter(this));
    	view.setOnItemClickListener(new OnItemClickListener() {  
    		@Override  
    		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {  
    			setTitle("点击第"+arg2+"个项目");  
    		}  
    	});  
    }
}