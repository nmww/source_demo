package com.example.android.preference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SharedPreferencesEx01 extends Activity {
 //SharedPreferencesEx01主程序	
    public String SETTING_PREF = "SETTING_Pref";//定义SharedPreferences內容文件名
    public String SHARED_MSG1 = "Shared_Msg1";	//定义字符串变量-1
    public String SHARED_MSG2 = "Shared_Msg2";	//定义字符串变量-2
    public String SHARED_MSG3 = "Shared_Msg3";	//定义字符串变量-3
    public String SHARED_MSG4 = "Shared_Msg4";	//定义字符串变量-4
    public String SHARED_MSG5 = "Shared_Msg5";	//定义字符串变量-5
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText mEditText01 = (EditText)findViewById(R.id.EditText01);
        final EditText mEditText02 = (EditText)findViewById(R.id.EditText02);
        final EditText mEditText03 = (EditText)findViewById(R.id.EditText03);
        final EditText mEditText04 = (EditText)findViewById(R.id.EditText04);
        final RadioButton mRadioButton01  = (RadioButton )findViewById(R.id.RadioButton01);
        final RadioButton mRadioButton02  = (RadioButton )findViewById(R.id.RadioButton02);
        final RadioButton mRadioButton03  = (RadioButton )findViewById(R.id.RadioButton03);
        Button mButton01 = (Button)findViewById(R.id.Button01);
        Button mButton02 = (Button)findViewById(R.id.Button02);
        Button mButton03 = (Button)findViewById(R.id.Button03);
        //按下Buttton01时，存放SHARED_MSGi,i=1,2,3,4,5到SETTING_PREF內容文件      
        mButton01.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v){
        		SharedPreferences settings = getSharedPreferences(SETTING_PREF, 0);
        		int int2 = Integer.parseInt(mEditText02.getText().toString());
        		long long3 = Long.parseLong(mEditText03.getText().toString());
        		float float4 = Float.parseFloat(mEditText04.getText().toString());
        		boolean boolean5 = true;
        		if (mRadioButton01.isChecked()){
        			boolean5 = true;
        		} else if (mRadioButton02.isChecked()) {
        			boolean5 = false;
        		}
        		settings.edit()
        		.putString(SHARED_MSG1, mEditText01.getText().toString())
        		.putInt(SHARED_MSG2, int2)
        		.putLong(SHARED_MSG3, long3)
        		.putFloat(SHARED_MSG4, float4)
        		.putBoolean(SHARED_MSG5, boolean5)
        		.commit();
        	}
        });
        //按下Buttton03时，自SETTING_PREF內容文件取得SHARED_MSGi,i=1,2,3,4,5
        mButton03.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v){
        		SharedPreferences settings = getSharedPreferences(SETTING_PREF, 0);
        		String msg1 = settings.getString(SHARED_MSG1, "");
        		mEditText01.setText(msg1);
        		int defint2 = 0;
        		int msg2_int = settings.getInt(SHARED_MSG2, defint2);
        		String msg2 = String.valueOf(msg2_int);
        		mEditText02.setText(msg2);
        		long deflong3 = 0;
        		long msg3_long = settings.getLong(SHARED_MSG3, deflong3);
        		String msg3 = String.valueOf(msg3_long);
        		mEditText03.setText(msg3);
        		float deffloat4 = 0;
        		float msg4_float = settings.getFloat(SHARED_MSG4, deffloat4);
        		String msg4 = String.valueOf(msg4_float);
        		mEditText04.setText(msg4);
        		boolean defboolean5 = true;
        		boolean msg5_boolean = settings.getBoolean(SHARED_MSG5, defboolean5);
        		if (msg5_boolean) {
        			mRadioButton01.setChecked(true);
            		mRadioButton02.setChecked(false);
            		mRadioButton03.setChecked(false);
        		} else {
        			mRadioButton01.setChecked(false);
        			mRadioButton02.setChecked(true);
        			mRadioButton03.setChecked(false);
        		}
        	}
        });
        //按下Buttton02时，清除EditText01显示內容
        mButton02.setOnClickListener(new Button.OnClickListener() {
        	public void onClick(View v){
        		mEditText01.setText("");
        		mEditText02.setText("");
        		mEditText03.setText("");
        		mEditText04.setText("");
        		mRadioButton01.setChecked(false);
        		mRadioButton02.setChecked(false);
        		mRadioButton03.setChecked(true);
        	}
        });
    }
}