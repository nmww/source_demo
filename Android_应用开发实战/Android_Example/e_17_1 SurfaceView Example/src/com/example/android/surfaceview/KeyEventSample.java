package com.example.android.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;

public class KeyEventSample extends Activity {
	//继承SurfaceView类创建MySurfaceView
	class MySurfaceView extends SurfaceView {
		Paint paint = new Paint();
		String text = "";
		//实现MySurfaceView处理
		public MySurfaceView(Context context) {
			super(context);
			setFocusable(true); //使用Key Event，setFocusable设成true，可以聚焦
			requestFocus(); //要求聚焦，沒有聚焦的話，会收不到正确的Key Event???
			paint.setColor(Color.BLUE);
			paint.setAntiAlias(true);
			paint.setTextSize(24);
		}
		//按下键时的处理
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyDown");
			if (keyCode == KeyEvent.KEYCODE_BACK) { //Back键
				return false;
			}
			text = "onKeyDown: keycode[" + keyCode + "]";
			redraw();
			return true;
		}
		//提起键时的处理
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyUp");
			return true;
		}
		//长压键时的处理
		@Override
		public boolean onKeyLongPress(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyLongPress");
			return true;
		}
		//按多键时的处理
		@Override
		public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
			Log.d("TEST", "onKeyMultiple");
			return true;
		}
		//显示键的Code
		void redraw() {
			Canvas canvas = getHolder().lockCanvas();
			canvas.drawColor(Color.WHITE);
			canvas.drawText(text, 0, paint.getTextSize(), paint);
			getHolder().unlockCanvasAndPost(canvas);
		}
	}
	//KeyEventSample主程序
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	}
}
