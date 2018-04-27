package com.gongsi.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
/*
 * adapter增加list条目的demo
 */
public class ToDoListActivity extends Activity {
	Button btn_add;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView myListView = (ListView)findViewById(R.id.myListView);
        final EditText myEditText = (EditText)findViewById(R.id.myEditText);
        
        final ArrayList<String> todoItems = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1,
        		todoItems);
        myListView.setAdapter(adapter);
        
        myEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(event.getAction() == KeyEvent.ACTION_DOWN){
					/*手机上下左右键的中间确定键 KEYCODE_DPAD_CENTER*/
					if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
						todoItems.add(0, myEditText.getText().toString());
						adapter.notifyDataSetChanged();//通知adapter更新
						myEditText.setText("");
						return true;
					}
				}
				return false;
			}
		});
        
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				todoItems.add(0, myEditText.getText().toString());
				adapter.notifyDataSetChanged();//通知adapter更新
				myEditText.setText("");
			}
		});
    }
}