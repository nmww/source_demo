package com.dee.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.text.ClipboardManager;

import com.dee.utility.R;



public class SaveUtilityActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        //获得管理剪切板的对象(ClipboardManager)
        ClipboardManager clipboard = 
        	(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //向剪切板保存字符串
        clipboard.setText("通过clipboard传递的数据");
        //从剪切板获得字符串
        String text = clipboard.getText().toString(); 
        
    }
}


