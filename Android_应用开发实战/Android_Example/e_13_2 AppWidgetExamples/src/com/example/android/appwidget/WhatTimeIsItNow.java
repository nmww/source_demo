package com.example.android.appwidget;

import java.util.Date;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WhatTimeIsItNow extends AppWidgetProvider {
	//onUpdate
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Toast.makeText(context, "onUpdate", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(context, MyService.class);
		context.startService(intent);
	}
	//MyService�������
	public static class MyService extends Service {
		@Override
		public void onStart(Intent intent, int startId) {
			RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.what_time_is_it_now);
			remoteViews.setTextViewText(R.id.TextView01, new Date().toLocaleString());			
			ComponentName thisWidget = new ComponentName(this, WhatTimeIsItNow.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, remoteViews);
		}
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
	}
}
