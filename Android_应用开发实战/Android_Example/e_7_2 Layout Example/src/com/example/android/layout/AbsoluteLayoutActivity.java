package com.example.android.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AbsoluteLayoutActivity extends Activity {
//AbsoluteLayoutActivity主程序	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.absolute_layout);
		//按下[Submit]按钮，显示输入结果
		Button mButton = (Button)findViewById(R.id.Button01);
		mButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
				EditText mEditText01 = (EditText)findViewById(R.id.EditText01);
				TextView mTextView02 = (TextView)findViewById(R.id.TextView02);
				EditText mEditText02 = (EditText)findViewById(R.id.EditText02);
				TextView mTextView03 = (TextView)findViewById(R.id.TextView03);
				EditText mEditText03 = (EditText)findViewById(R.id.EditText03);
				TextView mTextView04 = (TextView)findViewById(R.id.TextView04);
				EditText mEditText04 = (EditText)findViewById(R.id.EditText04);
				TextView mTextView05 = (TextView)findViewById(R.id.TextView05);
				mTextView05.setText("输入结果: \n"+mTextView01.getText()+": "+mEditText01.getText()+"\n"
												  +mTextView02.getText()+": "+mEditText02.getText()+"\n"
												  +mTextView03.getText()+": "+mEditText03.getText()+"\n"
												  +mTextView04.getText()+": "+mEditText04.getText()
													);
			}
		});
	}
}
