package com.example.android.web02;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;

public final class JExample04 extends Activity {
	private WebView webView;
    private Handler handler;
    public int flag=0;
    //JExample04程序
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
      //实例化WebView类变量webView和调用javascript04.HTML
        handler=new Handler();    
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSInterface(),"demo");
        webView.loadUrl(getIntent().getCharSequenceExtra("url").toString());
        setContentView(webView, new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT)); 
    }
    //JavaScript Interface
    public final class JSInterface {
        //JavaScript脚本代码可以调用的函数onClick()处理
        public void onClick() {
            handler.post(new Runnable() {
                public void run() {
                    if (flag == 0) {
                    	flag = 1;
                    	webView.loadUrl("javascript:changeImage02()");
                    } else {
                    	flag = 0;
                    	webView.loadUrl("javascript:changeImage01()");
                    }
                }
            });
        }
    }
}
