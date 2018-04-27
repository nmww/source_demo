package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

public class EditActivity extends Activity {
	//EditActivity������
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_activity);
		//�趨�ַ���������-adapter
		ArrayAdapter<CharSequence> adapter = 
			ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_dropdown_item_1line);
		//���Զ���ʾ�ַ����е�ѡ��(����)-AutoCompleteTextView
		AutoCompleteTextView autoCompleteTextView = 
			(AutoCompleteTextView)findViewById(R.id.AutoCompleteTextView01);
		autoCompleteTextView.setAdapter(adapter);
		//���Զ���ʾ�ַ����е�ѡ��(����)-MultiAutoCompleteTextView
		MultiAutoCompleteTextView multiAutoCompleteTextView = 
			(MultiAutoCompleteTextView)findViewById(R.id.MultiAutoCompleteTextView01);
		multiAutoCompleteTextView.setAdapter(adapter);
		multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
		//�趨�ַ���������-adapter_spinner
		ArrayAdapter<CharSequence> adapter_spinner = 
			ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_dropdown_item_1line);
		//�Զ��������˵�-Spinner
		adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
		spinner.setAdapter(adapter_spinner);
	}
}
