package com.example.android.service;

import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class KitchenTimerService extends Service {
//Service程式KitchenTimerService	
	//Bind for Service Connection
	class KitchenTimerBinder extends Binder {	
		KitchenTimerService getService() {
			return KitchenTimerService.this;
		}	
	}
	//定義ACTION
	public static final String ACTION = "Kitchen Timer Service";
	private Timer timer;
	//Service程式-onCreate()
	@Override
	public void onCreate() {
		super.onCreate();
		Toast toast = Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT);
		toast.show();
System.out.println("####### service onCreate() process:"+ android.os.Process.myPid() + " task:" + android.os.Process.myTid());
	}
	//Service程式-onStart()
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast toast = Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT);
		toast.show();
	}
	//Service程式-onDestroy()
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast toast = Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT);
		toast.show();
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	//Service程式-onBind()
	@Override
	public IBinder onBind(Intent intent) {
		Toast toast = Toast.makeText(getApplicationContext(), "onBind()", Toast.LENGTH_SHORT);
		toast.show();
		return new KitchenTimerBinder();
	}
	//Service程式-onRebind()
	@Override
	public void onRebind(Intent intent) {
		Toast toast = Toast.makeText(getApplicationContext(), "onRebind()", Toast.LENGTH_SHORT);
		toast.show();
	}
	//Service程式-onUnbind()
	@Override
	public boolean onUnbind(Intent intent) {
		Toast toast = Toast.makeText(getApplicationContext(), "onUnbind()", Toast.LENGTH_SHORT);
		toast.show();
		return true; //當再度自Client接口時，呼叫 onRebind的場合會回覆 true
	}
	//Service程式-schedule，提 供給Client可呼叫的方法
	public void schedule(long delay) {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer();
		TimerTask timerTask = new TimerTask() {	
			public void run() {
				//送出信息給廣播接收程式
				sendBroadcast(new Intent(ACTION));
			}	
		};
		//設定Alarm time，且Time out時會執行timerTask送出信息
		timer.schedule(timerTask, delay);
	}
}