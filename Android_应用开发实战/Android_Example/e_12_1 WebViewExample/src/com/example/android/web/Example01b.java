package com.example.android.web;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class Example01b extends Activity {
	//定义显示容器的长高特性
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT; 
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	//Example01程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		WebView webView = new WebView(this);
		//下载网页
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
		setContentView(webView, new LayoutParams(FP, FP));
	}
}