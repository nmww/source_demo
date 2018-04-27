package com.example.android.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;

public class GameSampleWithBitmapDrawable extends Activity {
	MySurfaceView view;
	Thread mainLoop;
	//继承SurfaceView类创建MySurfaceView且实现Runnable
	class MySurfaceView extends SurfaceView implements Runnable {
		BitmapDrawable bd;
		boolean u, d, l, r;
		int left, top;
		Paint paint = new Paint();
		long time;
		int tx, ty;
		int imageWidth, imageHeight;
		//实现MySurfaceView处理
		public MySurfaceView(Context context) {
			super(context);
			setFocusable(true); //
			requestFocus(); //
			Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.droid);
			bd = new BitmapDrawable(context.getResources(), bitmap);
			paint.setColor(Color.BLUE);
			paint.setTextSize(12);
			tx = bitmap.getWidth();
			ty = bitmap.getHeight() / 2;
			imageWidth = bitmap.getWidth();
			imageHeight = bitmap.getHeight();
			mainLoop = new Thread(this);
			mainLoop.start();
		}
		//按下键时的处理
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyDown");
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_UP:
				u = true;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				d = true;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				l = true;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				r = true;
				break;
			default:
				return false;
			}
			return true;
		}
		//提起键时的处理
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyDown");
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_UP:
				u = false;
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				d = false;
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				l = false;
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				r = false;
				break;
			default:
				return false;
			}
			return true;
		}
		//不断执行中，调用doDraw()
		void doDraw() {
			Canvas canvas = getHolder().lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				if (u) top--;
				if (d) top++;
				if (l) left--;
				if (r) left++;
				bd.draw(canvas);
				bd.setBounds(left*10, top*10, left*10 + imageWidth, top*10 + imageHeight);
				long now = System.currentTimeMillis();
				Log.d("TEST", "" + (now - time));
				canvas.drawText("" + 1000f / (now - time) + "fps", tx, ty, paint);
				time = now;
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
		public void run() {
			while (true) {
				doDraw();
			}
		}
	}
	//GameSampleWithBitmapDrawable主程序
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new MySurfaceView(this);
		setContentView(view);
	}
	//onDestroy()狀態處理
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Thread.interrupted();
	}
}
