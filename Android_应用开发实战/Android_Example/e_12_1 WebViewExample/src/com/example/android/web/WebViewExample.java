package com.example.android.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class WebViewExample extends Activity {
	//6�������Ĳ˵����ƺ�Ӧ�ó���Class
	private Object[] activities = {
			"Yahoo!",				Example01a.class,
			"Google",				Example01b.class,
			"Load URL", 			Example02.class,
			"Back and Forward", 	Example03.class,
			"Zoomin and Zoomout",	Example04.class,
			"Gesture",				Example05.class,
		};
	//WebViewExample����ʽ
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//����6���˵����Ƶ�����list
		CharSequence[] list = new CharSequence[activities.length / 2];
		for (int i = 0; i < list.length; i++) {
			list[i] = (String)activities[i * 2];
		}
		//��6�������˵����Ʒ�����listView
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//���²˵�����ָ����ص�Ӧ�ó���Class
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(WebViewExample.this, (Class<?>)activities[position * 2 + 1]);
				//���ֲ˵�������Ҫ����url�ă���
				if (position == 0) intent.putExtra("url", "http://www.yahoo.com");
				if (position == 1) intent.putExtra("url", "http://www.google.com");
				if (position == 5) intent.putExtra("url", "http://www.msn.com");
				startActivity(intent);
			}		
		});	
	}
}