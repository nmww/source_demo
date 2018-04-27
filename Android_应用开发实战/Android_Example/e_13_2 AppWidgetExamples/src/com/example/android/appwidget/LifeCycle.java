package com.example.android.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class LifeCycle extends AppWidgetProvider {
	//onUpdate
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Toast.makeText(context, "onUpdate", Toast.LENGTH_LONG).show();
		if (appWidgetIds != null) {
			StringBuffer sb = new StringBuffer("onUpdate:");
			for (int id : appWidgetIds) {
				sb.append(id).append(" ");
			}
			Log.v("LifeCycle", new String(sb));
		}
	}
	//onDeleted
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Toast.makeText(context, "onDeleted", Toast.LENGTH_LONG).show();
		if (appWidgetIds != null) {
			StringBuffer sb = new StringBuffer("onDeleted:");
			for (int id : appWidgetIds) {
				sb.append(id).append(" ");
			}
			Log.v("LifeCycle", new String(sb));
		}
	}
	//onDisabled
	@Override
	public void onDisabled(Context context) {
		Toast.makeText(context, "onDisabled", Toast.LENGTH_LONG).show();
	}
	//onEnabled
	@Override
	public void onEnabled(Context context) {
		Toast.makeText(context, "onEnabled", Toast.LENGTH_LONG).show();
	}
	//onReceive
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_DELETED)) {
			onDeleted(context, intent.getIntArrayExtra("appWidgetId"));
		}
		Log.v("LifeCycle", intent.toString());
	}	
}
