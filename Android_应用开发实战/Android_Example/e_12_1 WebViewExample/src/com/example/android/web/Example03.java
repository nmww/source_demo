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
	//�趨WebViewClient�Ĵ������
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
	//Example03����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example03);
		//������ʾ������Button,EditText,WebView���ʵ������
		first = (Button)findViewById(R.id.Button01);
		back = (Button)findViewById(R.id.Button02);
		forward = (Button)findViewById(R.id.Button03);
		last = (Button)findViewById(R.id.Button04);
		clear = (Button)findViewById(R.id.Button05);
		url = (EditText)findViewById(R.id.EditText01);
		webView = (WebView)findViewById(R.id.WebView01);
		webView.setWebViewClient(new MyWebViewClient());
		//�趨Button��EditText�ļ�������
		back.setOnClickListener(this);
		forward.setOnClickListener(this);
		first.setOnClickListener(this);
		last.setOnClickListener(this);
		clear.setOnClickListener(this);
		url.setOnKeyListener(this);
	}
	//����Buttonʱ�Ĵ������
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
	//�url(EditText)�༭����ENTER�r�Ĵ�����������µ���ҳ
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			webView.loadUrl(url.getText().toString());
			return true;
		}
		return false;
	}
}
