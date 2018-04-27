package com.example.android.appwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class LifeCycleConfigure extends Activity {
	//LifeCycleConfigure������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.life_cycle_configure);
		//�����Լ�����Activity��Intent
		Intent intent = getIntent();
		final Bundle extras = intent.getExtras();
		if (extras != null) {
			new Thread() {
				public void run() {
					try {
						Thread.sleep(10000);//�O��10sec
					} catch (InterruptedException e) {
					}
					//ȡ��App Widget ID
					int mAppWidgetId = extras.getInt(
				            AppWidgetManager.EXTRA_APPWIDGET_ID, 
				            AppWidgetManager.INVALID_APPWIDGET_ID);
					//ȡ��AppWidgetManager
					AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(LifeCycleConfigure.this);
					//���¾߱�RemoteViews���沼�ֵ�life_cycle.xml
					RemoteViews views = new RemoteViews(getPackageName(), R.layout.life_cycle);
					appWidgetManager.updateAppWidget(mAppWidgetId, views);
					//����һ�����ص�Intent-resultValue���趨��Activity�Ľ��������Activity
					Intent resultValue = new Intent();
					resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
					setResult(RESULT_OK, resultValue);
					finish();
				}
			}.start();
		}
	}
}
