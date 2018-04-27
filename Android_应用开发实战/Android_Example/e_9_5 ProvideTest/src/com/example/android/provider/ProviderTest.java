package com.example.android.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ProviderTest extends Activity {
//ProvideTest������
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ȡ��Content Provider��Uri
        getIntent().setData(Uri.parse("content://com.example.android.provider.testprovider"));
        Uri uri_test = getIntent().getData();
        //�������в����õ�����
        ContentValues values = new ContentValues();
        values.put("name", "Macoto");
        values.put("description", "0932-158983");
        getContentResolver().insert(uri_test, values);
        values.put("name", "Ming");
        values.put("description", "0932-123456");
        getContentResolver().insert(uri_test, values);
        //��Content Provider������
        Cursor cur = managedQuery(uri_test, null, null, null, null);
        cur.moveToFirst();
        //�趨ArrayList��ʾ�����б�
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        //�������ݿ��������������ArrayList data������
        	for (int i = 0; i < cur.getCount(); i++) {
        		item = new HashMap<String, Object>();
        		item.put("column00", cur.getString(0));
        		item.put("column01", cur.getString(1));
        		item.put("column02", cur.getString(2));
        		data.add(item);
        		cur.moveToNext();
        	}
        	cur.close();
        	//ArrayList data�����ȵ����ݷŵ�mListView01
        	ListView mListView01 = new ListView(this);
        	SimpleAdapter adapter = new SimpleAdapter(this, data,
    			R.layout.main, new String[] {"column00", "column01", "column02" }, new int[] {
    				R.id.TextView01, R.id.TextView02, R.id.TextView03 });
        	mListView01.setAdapter(adapter);
        	setContentView(mListView01);      
    }
}