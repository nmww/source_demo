package com.example.android.service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class KitchenTimer extends Activity {
//KitchenTimer主程序	
	public long alarmtimer;
	//定义BroadcastReceiver类别KitchenTimeeReceiver广播接收程序
	private class KitchenTimerReceiver extends BroadcastReceiver {	
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast toast = Toast.makeText(getApplicationContext(), "Time over!", Toast.LENGTH_LONG);
			toast.show();
	    	MediaPlayer mp = MediaPlayer.create(KitchenTimer.this, R.raw.alarm);
			try {
				mp.start();
			} catch (Exception e) {
				//例外发生时处理
			}
		kitchenTimerService.schedule(alarmtimer);
		}
	}
	//声明Service程序kitchenTimerService
	private KitchenTimerService kitchenTimerService;
	//声明BroadcastReceiver程序receiver
	private final KitchenTimerReceiver receiver = new KitchenTimerReceiver();
	//声明ServiceConnection程序serviceConnection
	private ServiceConnection serviceConnection = new ServiceConnection() {	
		public void onServiceConnected(ComponentName className, IBinder service) {
			kitchenTimerService = ((KitchenTimerService.KitchenTimerBinder)service).getService();
		}	
		public void onServiceDisconnected(ComponentName className) {
			kitchenTimerService = null;
		}	
	};
	//建立和执行KitchenTimer主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //用户设定alarm time时间
		final TimePicker timePicker = (TimePicker)findViewById(R.id.TimePicker01);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(0);
		timePicker.setCurrentMinute(1);	
		Button button = (Button)findViewById(R.id.Button01);
		button.setOnClickListener(new View.OnClickListener() {	
			public void onClick(View view) {
				long hour = timePicker.getCurrentHour();
				long min = timePicker.getCurrentMinute();
				alarmtimer = (hour * 60 + min) * 60 * 1000;
				//將alarmtimer告知Service程序kitchenTimerService的schedule
				kitchenTimerService.schedule(alarmtimer);
				moveTaskToBack(true);
			}		
		});	
		//启动服务程序KitchenTimerService
		Intent intent = new Intent(this, KitchenTimerService.class);
		startService(intent);
		//注册广播接收receiver
		IntentFilter filter = new IntentFilter(KitchenTimerService.ACTION);
		registerReceiver(receiver, filter);	
		//绑定(Bind)服务程序KitchenTimerService
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		//松绑(Unbind)服务程序KitchenTimerService
		unbindService(serviceConnection);
		//绑定(Bind)服务程序KitchenTimerService
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}
	//结束时要交换数据，Unbind Service, Unregister Receiver, Stop Service
	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection); 	//Unbind service
		unregisterReceiver(receiver); 		//Unregister Receiver
		kitchenTimerService.stopSelf(); 	//Stop Service
	}
}