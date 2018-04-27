package com.example.android.cast;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Main extends Activity {
	//Web Intent Caster������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//�趨Spinner�������˵�
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.actions, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
		spinner.setAdapter(adapter);
		//����[Submit]�����Ĵ���
		Button button = (Button)findViewById(R.id.Button01);
		button.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				try {
					EditText editText = (EditText)findViewById(R.id.EditText01);
					Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
					//�趨Intent�Ķ���(Action)��Uri
					Intent intent = new Intent(spinner.getSelectedItem().toString(), Uri.parse(editText.getText().toString()));
					//Start Activity��ִ��Browser 
					startActivity(intent);
				} catch (Exception e) {
					TextView textView = new TextView(Main.this);
					textView.setText(e.getMessage());
					Dialog dialog = new Dialog(Main.this);
					dialog.setTitle(e.getClass().getName());
					dialog.setContentView(textView);
					dialog.show();
				}
			}
		});
	}
}