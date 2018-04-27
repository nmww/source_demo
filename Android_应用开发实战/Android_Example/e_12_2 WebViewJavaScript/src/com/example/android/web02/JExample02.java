package com.example.android.web02;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class JExample02 extends Activity {
	//ʵ����WebChromeClient�࣬����MyWebChromeClient
	class MyWebChromeClient extends WebChromeClient {
		@Override
		//onJsAlert()�������յ�����HTML��ҳ��alert()������Ϣ
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			if (message.length() != 0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(JExample02.this);
				builder.setTitle("From JavaScript").setMessage(message).show();
				result.cancel();
				return true;
			}
			return false;
		}
	}
	//JExample02����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ʵ����WebView�����webView�͵���javascript02.HTML
		WebView webView = new WebView(this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
		setContentView(webView, new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
	}
}