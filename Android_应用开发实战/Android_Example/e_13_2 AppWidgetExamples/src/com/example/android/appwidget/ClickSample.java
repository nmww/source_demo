package com.example.android.appwidget;

import java.util.Date;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class ClickSample extends AppWidgetProvider {
	private static final String ACTION_MY_CLICK = "com.example.android.appwidget.ClickSample.ACTION_MY_CLICK";
	//onUpdate
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent serviceIntent = new Intent(context, MyService.class);
        context.startService(serviceIntent);
	}
	//MyService�������
    public static class MyService extends Service {
        @Override
        public void onStart(Intent intent, int startId) {
            ComponentName thisWidget = new ComponentName(this, ClickSample.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(this);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.click_sample);
            if (ACTION_MY_CLICK.equals(intent.getAction())) {
                remoteViews.setTextViewText(R.id.TextView01, new Date().toLocaleString());
            }
            Intent clickIntent = new Intent();
            clickIntent.setAction(ACTION_MY_CLICK);
            PendingIntent pendingIntent = PendingIntent.getService(this, 0, clickIntent, 0);
            remoteViews.setOnClickPendingIntent(R.id.TextView01, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.Button01, pendingIntent);
            manager.updateAppWidget(thisWidget, remoteViews);
        }
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
