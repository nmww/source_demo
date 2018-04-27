package com.example.android.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class TouchAndTrackballEvent extends Activity {
	//继承SurfaceView类创建MySurfaceView
	class MySurfaceView extends SurfaceView {
		float left;
		float top;
		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.droid);
		//实现MySurfaceView处理
		public MySurfaceView(Context context) {
			super(context);
			setFocusable(true); //使用Key Event，setFocusable设成true，可以聚焦
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
		//实现TrackBall事件处理
		@Override
		public boolean onTrackballEvent(MotionEvent event) {
			Log.d("TEST", "onTrackballEvent");
			left = event.getX();
			top = event.getY();
			doDraw();
			return true;
		}
		//描绘图形到Canvas
		void doDraw() {
			Canvas canvas = getHolder().lockCanvas();
			canvas.drawBitmap(bitmap, left, top, null);
			getHolder().unlockCanvasAndPost(canvas);
		}
	}
	//TouchAndTrackball主程序
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	}
}
