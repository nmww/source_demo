package com.example.android.web;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class Example02 extends Activity {
	//Example02����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example02);
		//������ʾ������EditText,WebView���ʵ������
		final WebView webView = (WebView)findViewById(R.id.WebView01);
		webView.setWebViewClient(new WebViewClient());
		final EditText editText = (EditText)findViewById(R.id.EditText01);
		//�趨EditText�ļ������ܣ��editText�༭����ENTERʱ�Ĵ�����������µ���ҳ
		editText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					webView.loadUrl(editText.getEditableText().toString());
					return true;
				}
				return false;
			}		
		});	
	}
}