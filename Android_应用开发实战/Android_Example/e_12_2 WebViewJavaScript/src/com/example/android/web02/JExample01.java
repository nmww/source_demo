package com.example.android.web02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class JExample01 extends Activity {
	//JExample01程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//实例化WebView类变量webView和调用javascript01.HTML
		WebView webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
		//设定JavaScript脚本代码的界面名称是”android”
		webView.addJavascriptInterface(this, "android");
		setContentView(webView, new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	}
	//JavaScript脚本代码可以调用的函数js()处理
	public void js(String action, String uri) {
		Intent intent = new Intent(action, Uri.parse(uri));
		startActivity(intent);
	}
}
