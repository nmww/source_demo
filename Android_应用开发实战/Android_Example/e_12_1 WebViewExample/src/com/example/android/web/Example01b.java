package com.example.android.web;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class Example01b extends Activity {
	//������ʾ�����ĳ�������
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT; 
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	//Example01����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		WebView webView = new WebView(this);
		//������ҳ
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
		setContentView(webView, new LayoutParams(FP, FP));
	}
}