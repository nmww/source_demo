package com.example.android.web02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public class JExample01 extends Activity {
	//JExample01����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ʵ����WebView�����webView�͵���javascript01.HTML
		WebView webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
		//�趨JavaScript�ű�����Ľ��������ǡ�android��
		webView.addJavascriptInterface(this, "android");
		setContentView(webView, new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	}
	//JavaScript�ű�������Ե��õĺ���js()����
	public void js(String action, String uri) {
		Intent intent = new Intent(action, Uri.parse(uri));
		startActivity(intent);
	}
}
