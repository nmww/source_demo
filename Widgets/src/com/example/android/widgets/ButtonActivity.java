package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ButtonActivity extends Activity {
	//ButtonActivity������
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.button_activity);
		
		final TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		
		//����һ�㰴�o��Button��-������ʾ�ڰ�ť��
		Button mButton01 = (Button)findViewById(R.id.Button01);
		mButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mTextView01.setText("����Button-������!����ť");
			}
		});
		//����ON/OFF�ГQ��ť��ToggleButton��
		final ToggleButton mToggleButton01 = (ToggleButton)findViewById(R.id.ToggleButton01);
		mToggleButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mTextView01.setText("���¡�ToggleButton����ť: " + mToggleButton01.getText());
			}
		});
		//����һ�㰴ť��ImageButton��-Ӱ��ӡ�ڰ�ť��
		ImageButton mImageButton01 = (ImageButton)findViewById(R.id.ImageButton01);
		mImageButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mTextView01.setText("���¡�ImageButton�����o");
			}
		});
		//���¶���x���CheckBox��
		Button mButton02 = (Button)findViewById(R.id.Button02);
		mButton02.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				CheckBox mCheckBox01 = (CheckBox)findViewById(R.id.CheckBox01);
				CheckBox mCheckBox02 = (CheckBox)findViewById(R.id.CheckBox02);
				String msg01 = "���¡�CheckBox�����o: ";
				if (mCheckBox01.isChecked()) {
					msg01 = msg01 + mCheckBox01.getText() +",";	
				}
				if (mCheckBox02.isChecked()) {
					msg01 = msg01 + mCheckBox02.getText() +",";	
				}
				mTextView01.setText(msg01);
			}
		});
		//����Ȧ�x���o��RadioButton��
		Button mButton03 = (Button)findViewById(R.id.Button03);
		mButton03.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				RadioButton mRadioButton01 = (RadioButton)findViewById(R.id.RadioButton01);
				RadioButton mRadioButton02 = (RadioButton)findViewById(R.id.RadioButton02);
				String msg02 = "���¡�RadioButton����ť: ";
				if (mRadioButton01.isChecked()) {
					msg02 = msg02 + mRadioButton01.getText();	
				}
				if (mRadioButton02.isChecked()) {
					msg02 = msg02 + mRadioButton02.getText();	
				}
				mTextView01.setText(msg02);
			}
		});
	}
}
