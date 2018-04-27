package com.example.android.surfaceview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Hello extends Activity {
	//继承SurfaceView类创建MySurfaceView且实现SurfaceHolder.Callback
	class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
		public MySurfaceView(Context context) {
			super(context);
			getHolder().addCallback(this);
		}
		//surfaceHolder.Callback方法-surfaceChanged()
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Log.d("TEST", "surfaceChanged");
		}
		//surfaceHolder.Callback方法-surfaceCreated()
		public void surfaceCreated(SurfaceHolder holder) {
			Log.d("TEST", "surfaceCreated");
			Canvas canvas = holder.lockCanvas();
			Paint paint = new Paint();
			canvas.drawColor(Color.WHITE);
			paint.setColor(Color.BLUE);
			paint.setAntiAlias(true);
			paint.setTextSize(24);
			canvas.drawText("Hello, Android SurfaceView!", 0, paint.getTextSize(), paint);
			holder.unlockCanvasAndPost(canvas);
		}
		//surfaceHolder.Callback方法-surfaceDestroyed()
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d("TEST", "surfaceDestroyed");
		}	
	}
	//Hello主程序
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	}
}
