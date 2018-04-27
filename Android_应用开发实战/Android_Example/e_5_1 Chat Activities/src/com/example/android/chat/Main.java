package com.example.android.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class Main extends Activity {
	//Main主程序
	private static final int SHOW_EDITOR = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//设定Button01按钮的操作
		Button button = (Button)findViewById(R.id.Button01);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Main.this,Chatter.class);
				EditText editText = (EditText)findViewById(R.id.EditText01);
				CharSequence text = editText.getText();
				//将传送的数据放置在共同存储区，变量:TEXT
				intent.putExtra("TEXT", text );
				//切换到Chatter，同时等待回应，设定requestCode=SHOW_EDITOR
				startActivityForResult(intent, SHOW_EDITOR);
			}
		});
	}
	//Main等待回应信息
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SHOW_EDITOR) {
			if (resultCode == RESULT_OK) {
				TextView textView = (TextView)findViewById(R.id.TextView02);
			   	textView.setText( "RESULT_OK: " + data.getCharSequenceExtra("TEXT"));
			}
		}
	}
}