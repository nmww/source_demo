package com.example.android.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Translucent extends Activity {
	//继承SurfaceView类创建MySurfaceView且实现SurfaceHolder.Callback
	class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
		float left;
		float top;
		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.cupcake);
		//实现MySurfaceView处理
		public MySurfaceView(Context context) {
			super(context);
			getHolder().setFormat(PixelFormat.TRANSLUCENT);
			getHolder().addCallback(this);
			setFocusable(true);
		}
		//surfaceHolder.Callback方法-surfaceChanged()
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Log.d("TEST", "surfaceChanged");
		}
		//surfaceHolder.Callback方法-surfaceCreated()
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d("TEST", "surfaceCreated");
			doDraw();
		}
		//surfaceHolder.Callback方法-surfaceDestroyed()
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d("TEST", "surfaceDestroyed");
		}
		//实现TouchEvent事件处理
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			Log.d("TEST", "onTouchEvent");
			left = event.getX();
			top = event.getY();
			doDraw();
			return true;
		}
		//描绘图形到Canvas
		void doDraw() {
			Canvas canvas = getHolder().lockCanvas();
			//canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(bitmap, left, top, null);
			getHolder().unlockCanvasAndPost(canvas);
		}
	}
	//Transparent主程序
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	}
}
