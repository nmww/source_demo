package com.example.android.appwidget;

import java.util.Date;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class AlarmManagerSample extends AppWidgetProvider { 
	private static final String ACTION_START_MY_ALARM = "com.example.android.appwidget.AlarmManagerSample.ACTION_START_MY_ALARM";
	private final long interval = 60 * 60 * 1000;
	//onUpdate
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		setAlarm(context);
	}
	//onReceive
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals(ACTION_START_MY_ALARM)) {
	        if (ACTION_START_MY_ALARM.equals(intent.getAction())) {
		        Intent serviceIntent = new Intent(context, MyService.class);
		        context.startService(serviceIntent);
			}
	        setAlarm(context);
		}
	}
	//setAlarm
	private void setAlarm(Context context) {
		Intent alarmIntent = new Intent(context, AlarmManagerSample.class);
		alarmIntent.setAction(ACTION_START_MY_ALARM);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		long now = System.currentTimeMillis() + 1; //保險起見+1，確認一定會是下一個時間
		long oneHourAfter = now + interval - now % (interval);
		am.set(AlarmManager.RTC, oneHourAfter, operation);
	}
	//MyService
    public static class MyService extends Service {
        @Override
        public void onStart(Intent intent, int startId) {
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.alarm_manager_sample);
            remoteViews.setTextViewText(R.id.TextView01, new Date().toLocaleString());      
            ComponentName thisWidget = new ComponentName(this, AlarmManagerSample.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(thisWidget, remoteViews);
        }
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
