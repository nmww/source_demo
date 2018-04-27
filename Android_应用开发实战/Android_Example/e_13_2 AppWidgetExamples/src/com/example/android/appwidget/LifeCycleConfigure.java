package com.example.android.appwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class LifeCycleConfigure extends Activity {
	//LifeCycleConfigure主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.life_cycle_configure);
		//创作自己发动Activity的Intent
		Intent intent = getIntent();
		final Bundle extras = intent.getExtras();
		if (extras != null) {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(10000);//設定10sec
					} catch (InterruptedException e) {
					}
					//取得App Widget ID
					int mAppWidgetId = extras.getInt(
				            AppWidgetManager.EXTRA_APPWIDGET_ID, 
				            AppWidgetManager.INVALID_APPWIDGET_ID);
					//取得AppWidgetManager
					AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(LifeCycleConfigure.this);
					//更新具备RemoteViews画面布局的life_cycle.xml
					RemoteViews views = new RemoteViews(getPackageName(), R.layout.life_cycle);
					appWidgetManager.updateAppWidget(mAppWidgetId, views);
					//创建一个返回的Intent-resultValue，设定成Activity的结果并结束Activity
					Intent resultValue = new Intent();
					resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
					setResult(RESULT_OK, resultValue);
					finish();
				}
			}.start();
		}
	}
}
