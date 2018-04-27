package com.zhg.demo;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SimplerAdapterTestActivity extends Activity {
	ArrayList<HashMap<String,String>> list ;
	private void createCollection(){
		 list = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> stu1= new HashMap<String, String>();
		stu1.put("id", String.valueOf(1));
		stu1.put("name", "zhangsan");
		stu1.put("sex", "Male");
		stu1.put("age", String.valueOf(19));
		list.add(stu1);
		
		stu1= new HashMap<String, String>();
		stu1.put("id", String.valueOf(2));
		stu1.put("name", "lisi");
		stu1.put("sex", "FeMale");
		stu1.put("age", String.valueOf(18));
		list.add(stu1);
		
		stu1= new HashMap<String, String>();
		stu1.put("id", String.valueOf(3));
		stu1.put("name", "zhangsan");
		stu1.put("sex", "Male");
		stu1.put("age", String.valueOf(19));
		list.add(stu1);
		
		stu1= new HashMap<String, String>();
		stu1.put("id", String.valueOf(4));
		stu1.put("name", "zhangsan");
		stu1.put("sex", "Male");
		stu1.put("age", String.valueOf(19));
		list.add(stu1);
		
		stu1= new HashMap<String, String>();
		stu1.put("id", String.valueOf(5));
		stu1.put("name", "zhangsan");
		stu1.put("sex", "Male");
		stu1.put("age", String.valueOf(19));
		list.add(stu1);
	}
	
	private void findViews(){
//		ListView lvStudents = (ListView)findViewById(R.id.lvStudent);
		GridView gv = (GridView)findViewById(R.id.gvStudent);
		createCollection();
		String[] from = {"id","name","sex","age"};
		int[] to = {R.id.tvId,R.id.tvName,R.id.tvSex,R.id.tvAge};
		SimpleAdapter adapter = 
				new SimpleAdapter(this,list,R.layout.griditem,from,to);
		gv.setAdapter(adapter);
	}
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid);
        findViews();
    }
}