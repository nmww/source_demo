package com.example.android.preference02;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferencesEx02 extends Activity {
//SharedPreferencesEx02������     
	public static final String SETTING_FILE = "SETTING_File";//����SharedPreferences�����ļ���
    public static final String NAME_MSG = "Name_Msg";		 //�����ַ�������-1
    public static final String MOBILE_MSG = "Mobile_Msg";	 //�����ַ�������-2
    private EditText mEditText01;
    private EditText mEditText02;
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mEditText01 = (EditText)findViewById(R.id.EditText01);
        mEditText02 = (EditText)findViewById(R.id.EditText02);
        //����ʱ����SETTING_FILE�����ļ�ȡ��NAME_MSG��MOBILE_MSG������ʾ��EditText01��EditText02
        SharedPreferences settings = getSharedPreferences(SETTING_FILE, 0);
        String name_msg = settings.getString(NAME_MSG, "");
        String mobile_msg = settings.getString(MOBILE_MSG, "");
        mEditText01.setText(name_msg);
        mEditText02.setText(mobile_msg);
    }
	 
	@Override
	protected void onStop(){
		super.onStop();
		Toast.makeText(this, "onStop(): SharedPreferences�洢NAME_MSG��MOBILE_MSG��SETTING_FILE", Toast.LENGTH_SHORT).show();
		//�뿪ʱ����onStop()�����д��NAME_MSG��MOBILE_MSG��SETTING_FILE�����ļ�
		SharedPreferences settings = getSharedPreferences(SETTING_FILE, 0);
		settings.edit()
		.putString(NAME_MSG, mEditText01.getText().toString())
		.putString(MOBILE_MSG, mEditText02.getText().toString())
		.commit();
	}
}