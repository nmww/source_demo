package com.example.android.web02;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class JExample03 extends Activity {
	//实例化WebChromeClient类，创建MyWebChromeClient
	class MyWebChromeClient extends WebChromeClient {
		@Override
		//onJsAlert()函数接收到來自HTML网页的alert()警告信息
		public boolean onJsAlert(WebView view, String url, final String message, JsResult result) {
			TextView textView = (TextView)findViewById(R.id.TextView01);
			textView.setText(message);
			result.confirm();
			return true;
		}
	}
	//JExample03程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example03);
		//实例化WebView类变量webView和调用javascript03.HTML
		WebView webView = (WebView)findViewById(R.id.WebView01);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
	}
}