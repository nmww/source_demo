package com.example.android.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Chatter extends Activity {
	//Chatter主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatter);
		
		Button button = (Button)findViewById(R.id.Button01);
		//自共同存储区取得传递数据，变量名称:TEXT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			EditText editText = (EditText)findViewById(R.id.EditText01);
			editText.setText(extras.getCharSequence("TEXT"));
		}
		//设定Button01按钮的操作
		button.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent();
				EditText editText = (EditText)findViewById(R.id.EditText01);
				CharSequence text = editText.getText();
				//将回传的数据放置在共同存储区，变量名称:TEXT
				intent.putExtra("TEXT", text);
				//回应信息，回到Main，resultCode == RESULT_OK
				setResult(RESULT_OK, intent);
				//结束Chatter
				finish();
			} 
		});
	}
}
