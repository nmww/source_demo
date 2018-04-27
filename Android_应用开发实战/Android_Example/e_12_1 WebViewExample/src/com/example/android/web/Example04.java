package com.example.android.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Example04 extends Activity implements OnClickListener, OnKeyListener {	
	Button zoomin;
	Button zoomout;
	Button info_title;
	Button info_url;
	EditText url;
	WebView webView;
	//Example04程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example04);
		//建立显示画面上Button,EditText,WebView类别的实例变量
		zoomin = (Button)findViewById(R.id.Button01);
		zoomout = (Button)findViewById(R.id.Button02);
		info_title = (Button)findViewById(R.id.Button03);
		info_url = (Button)findViewById(R.id.Button04);
		url = (EditText)findViewById(R.id.EditText01);
		webView = (WebView)findViewById(R.id.WebView01);
		webView.setWebViewClient(new WebViewClient());
		//设定Button和EditText的监听功能
		zoomin.setOnClickListener(this);
		zoomout.setOnClickListener(this);
		info_title.setOnClickListener(this);
		info_url.setOnClickListener(this);
		url.setOnKeyListener(this);      
	}
	//按下Button时的处理程序
	public void onClick(View v) {
		if (v == zoomin) {
			boolean ret = webView.zoomIn();
			Toast.makeText(this, "zoom in is "+ret, Toast.LENGTH_SHORT).show();
		} else if (v == zoomout) {
			boolean ret = webView.zoomOut();
			Toast.makeText(this, "zoom Out is "+ret, Toast.LENGTH_SHORT).show();
		} else if (v == info_title) {
			String title = webView.getTitle();
			new AlertDialog.Builder(this)
			.setTitle("Title")
			.setMessage(title)
			.setPositiveButton("Ok", null)
			.show();
		} else if (v == info_url) {
			String url = webView.getUrl();
			new AlertDialog.Builder(this)
			.setTitle("URL")
			.setMessage(url)
			.setPositiveButton("Ok", null)
			.show();
		}
	}
	//於url(EditText)编辑框按下ENTER时的处理程序，下载載新的网页
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			webView.loadUrl(url.getText().toString());
			return true;
		}
		return false;
	}
}
