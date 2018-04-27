package com.example.android.web;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Example05 extends Activity {
	//~{6(~}??~{J>H]Fw5D~}?~{8_LXPT~}	
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT; 
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	float[] history;
	//Example05~{3LJ=~}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		final WebView webView = new WebView(this);	
		//?~{?X~}??~{OrG0;rOraa~}
		webView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				//ACTION_MOVE???~{RF~}?~{5DB7~}?
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					history = new float[event.getHistorySize()];
					for (int i = 0; i < history.length; i++) {
						history[i] = event.getHistoricalX(i);
					}
				} else {
					//ACTION_UP~{;r~}ACTION_OUTSIDE?~{#,EP6(~}??~{OrG0;rOraa~}
					if (event.getAction() == MotionEvent.ACTION_UP ||
							event.getAction() == MotionEvent.ACTION_OUTSIDE) {
						if (history != null && history.length >= 3) {
							boolean back = webView.canGoBack();
							boolean forward = webView.canGoForward();
							for (int i = 0; i < history.length; i++) {
								if (back && (i != 0 && history[i] >= history[i - 1])) {
									back = false;
								}
								if (forward && (i != 0 && history[i] <= history[i - 1])) {
									forward = false;
								}
							}
							if (back) {
								webView.goBack();
								return true;
							} else if (forward) {
								webView.goForward();
								return true;
							}
						}
					}
					history = null;
				}
				return false;
			}		
		});	
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
		setContentView(webView, new LayoutParams(FP, FP));
	}
}