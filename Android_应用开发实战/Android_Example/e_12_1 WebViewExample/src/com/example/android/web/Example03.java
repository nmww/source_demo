package com.example.android.web;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class Example03 extends Activity implements OnClickListener, OnKeyListener {
	//设定WebViewClient的处理程序
	class MyWebViewClient extends WebViewClient {
		@Override
		public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			back.setEnabled(webView.canGoBack());
			forward.setEnabled(webView.canGoForward());
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			if (webView.getTitle() != null) {
				Example03.this.setTitle(webView.getTitle());
			}
		}
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Example03.this.setTitle("Loading...");
			back.setEnabled(webView.canGoBack());
			forward.setEnabled(webView.canGoForward());
		}
	}
	Button back;
	Button forward;
	Button first;
	Button last;
	Button clear;
	EditText url;
	WebView webView;
	//Example03程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example03);
		//建立显示画面上Button,EditText,WebView类的实例变量
		first = (Button)findViewById(R.id.Button01);
		back = (Button)findViewById(R.id.Button02);
		forward = (Button)findViewById(R.id.Button03);
		last = (Button)findViewById(R.id.Button04);
		clear = (Button)findViewById(R.id.Button05);
		url = (EditText)findViewById(R.id.EditText01);
		webView = (WebView)findViewById(R.id.WebView01);
		webView.setWebViewClient(new MyWebViewClient());
		//设定Button和EditText的监听功能
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		first.setOnClickListener(this);
		last.setOnClickListener(this);
		clear.setOnClickListener(this);
		url.setOnKeyListener(this);
	}
	//按下Button时的处理程序
	public void onClick(View v) {
		if (v == back) {
			if (webView.canGoBack()){
				webView.goBack();
			}
		} else if (v == forward) {
			if (webView.canGoForward()){
				webView.goForward();
			}
		} else if (v == first) {
			if (webView.canGoBackOrForward(-2)){
				webView.goBackOrForward(-2);
			}
		} else if (v == last) {
			if (webView.canGoBackOrForward(+2)){
				webView.goBackOrForward(+2);
			}
		} else if (v == clear) {
				webView.clearHistory();
		}
	}
	//於url(EditText)编辑框按下ENTER時的处理程序，下载新的网页
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			webView.loadUrl(url.getText().toString());
			return true;
		}
		return false;
	}
}
