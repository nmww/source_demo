package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends Activity {
	//ProgressActivity主程序
	private ProgressBar progressBar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.progress_activity);
		//设定ProgressBar是可视的
		setProgressBarVisibility(true);
		
		progressBar = (ProgressBar)findViewById(R.id.ProgressBar01);
		//设定Handle类别
		final Handler handler = new Handler();
		//次要进度显示处理程序-callback1
		final Runnable callback1 = new Runnable() {		
			public void run() {
				progressBar.incrementSecondaryProgressBy(1);
				setSecondaryProgress(100 * progressBar.getSecondaryProgress());
			}		
		};
		//主要进度处理程序-callback2
		final Runnable callback2 = new Runnable() {		
			public void run() {
				progressBar.incrementProgressBy(10);
				setProgress(100 * progressBar.getProgress());
				progressBar.incrementSecondaryProgressBy(-100);
				setSecondaryProgress(100 * progressBar.getSecondaryProgress());
			}		
		};
		//显示图形处理程序-callback3
		final Runnable callback3 = new Runnable() {		
			public void run() {
				TextView textView = (TextView)findViewById(R.id.TextView01);
				textView.setVisibility(View.GONE);
				progressBar.setVisibility(View.GONE);
				ImageView imageView = (ImageView)findViewById(R.id.ImageView01);
				imageView.setImageResource(R.drawable.sakura01);
				imageView.setVisibility(View.VISIBLE);
			}	
		};
		//建立一个Thread来Run
		Thread thread = new Thread() {	
			@Override
			public void run() {
				try {
					for (int i = 0; i < progressBar.getMax() / 10; i++) {
						for (int j = 0; j < progressBar.getMax(); j++) {
							Thread.sleep(20);
							handler.post(callback1);
						}
						handler.post(callback2);
					}
					handler.post(callback3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}		
		};
		thread.start();
	}
}
