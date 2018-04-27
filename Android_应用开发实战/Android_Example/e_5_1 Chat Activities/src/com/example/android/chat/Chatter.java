package com.example.android.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Chatter extends Activity {
	//Chatter������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatter);
		
		Button button = (Button)findViewById(R.id.Button01);
		//�Թ�ͬ�洢��ȡ�ô������ݣ���������:TEXT
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			EditText editText = (EditText)findViewById(R.id.EditText01);
			editText.setText(extras.getCharSequence("TEXT"));
		}
		//�趨Button01��ť�Ĳ���
		button.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				Intent intent = new Intent();
				EditText editText = (EditText)findViewById(R.id.EditText01);
				CharSequence text = editText.getText();
				//���ش������ݷ����ڹ�ͬ�洢������������:TEXT
				intent.putExtra("TEXT", text);
				//��Ӧ��Ϣ���ص�Main��resultCode == RESULT_OK
				setResult(RESULT_OK, intent);
				//����Chatter
				finish();
			} 
		});
	}
}
