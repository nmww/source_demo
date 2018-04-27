package com.example.android.preference02;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferencesEx02 extends Activity {
//SharedPreferencesEx02主程序     
	public static final String SETTING_FILE = "SETTING_File";//定义SharedPreferences內容文件名
    public static final String NAME_MSG = "Name_Msg";		 //定义字符串变量-1
    public static final String MOBILE_MSG = "Mobile_Msg";	 //定义字符串变量-2
    private EditText mEditText01;
    private EditText mEditText02;
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mEditText01 = (EditText)findViewById(R.id.EditText01);
        mEditText02 = (EditText)findViewById(R.id.EditText02);
        //开机时，自SETTING_FILE內容文件取得NAME_MSG、MOBILE_MSG，并显示在EditText01和EditText02
        SharedPreferences settings = getSharedPreferences(SETTING_FILE, 0);
        String name_msg = settings.getString(NAME_MSG, "");
        String mobile_msg = settings.getString(MOBILE_MSG, "");
        mEditText01.setText(name_msg);
        mEditText02.setText(mobile_msg);
    }
	 
	@Override
	protected void onStop(){
		super.onStop();
		Toast.makeText(this, "onStop(): SharedPreferences存储NAME_MSG、MOBILE_MSG到SETTING_FILE", Toast.LENGTH_SHORT).show();
		//离开时，在onStop()程序中存放NAME_MSG、MOBILE_MSG到SETTING_FILE內容文件
		SharedPreferences settings = getSharedPreferences(SETTING_FILE, 0);
		settings.edit()
		.putString(NAME_MSG, mEditText01.getText().toString())
		.putString(MOBILE_MSG, mEditText02.getText().toString())
		.commit();
	}
}