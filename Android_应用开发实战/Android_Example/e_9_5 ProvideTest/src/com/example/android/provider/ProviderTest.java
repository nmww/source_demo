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
//ProvideTest主程序
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取得Content Provider的Uri
        getIntent().setData(Uri.parse("content://com.example.android.provider.testprovider"));
        Uri uri_test = getIntent().getData();
        //建立二行测试用的数据
        ContentValues values = new ContentValues();
        values.put("name", "Macoto");
        values.put("description", "0932-158983");
        getContentResolver().insert(uri_test, values);
        values.put("name", "Ming");
        values.put("description", "0932-123456");
        getContentResolver().insert(uri_test, values);
        //经Content Provider来检索
        Cursor cur = managedQuery(uri_test, null, null, null, null);
        cur.moveToFirst();
        //设定ArrayList显示下拉列表
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        //將自数据库读出的数据整理到ArrayList data容器內
        	for (int i = 0; i < cur.getCount(); i++) {
        		item = new HashMap<String, Object>();
        		item.put("column00", cur.getString(0));
        		item.put("column01", cur.getString(1));
        		item.put("column02", cur.getString(2));
        		data.add(item);
        		cur.moveToNext();
        	}
        	cur.close();
        	//ArrayList data容器內的数据放到mListView01
        	ListView mListView01 = new ListView(this);
        	SimpleAdapter adapter = new SimpleAdapter(this, data,
    			R.layout.main, new String[] {"column00", "column01", "column02" }, new int[] {
    				R.id.TextView01, R.id.TextView02, R.id.TextView03 });
        	mListView01.setAdapter(adapter);
        	setContentView(mListView01);      
    }
}