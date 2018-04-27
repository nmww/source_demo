package com.example.android.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

public class SlideShow extends AppWidgetProvider {
    //index要定义成static
    static int index = 0;
    //照片文件的数组安排
    int[] images = {
        R.drawable.g0,
        R.drawable.g1,
        R.drawable.g2,
        R.drawable.g3,
        R.drawable.g4,
        R.drawable.g5,
        R.drawable.g6,
        R.drawable.g7,
    };
    //onUpdate阶段
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    	Toast.makeText(context, "onUpdate", Toast.LENGTH_LONG).show();
    	RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.slide_show);
        remoteViews.setImageViewResource(R.id.ImageView01, images[index]);
        index = ++index % images.length;
        ComponentName thisWidget = new ComponentName(context, SlideShow.class);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    } 
}
