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

public class SaveAndRestore extends Activity {
	//继承SurfaceView类创建MySurfaceView且执行SurfaceHolder.Callback
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
			doDraw();
		}
		//surfaceHolder.Callback方法-surfaceDestroyed()
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d("TEST", "surfaceDestroyed");
		}
		//自surfaceCreated()调用doDraw()
		void doDraw() {
			Canvas canvas = getHolder().lockCanvas();
			Paint paint = new Paint();
			canvas.drawColor(Color.WHITE);
			paint.setColor(Color.RED);
			paint.setTextSize(12);
			canvas.drawText("Before Canvas save()", paint.getTextSize(), 200, paint);
			canvas.save(); //保存状态
			canvas.rotate(45.0f);
			paint.setColor(Color.BLUE);
			paint.setAntiAlias(true);
			paint.setTextSize(24);
			canvas.drawText("Hello,Android SurfaceView!", paint.getTextSize(), 0, paint);
			canvas.restore(); //回复状态
			canvas.drawText("After Canvas restore()", paint.getTextSize(), 250, paint);
			getHolder().unlockCanvasAndPost(canvas);
		}	
	}
	//SaveAndRestore主程序
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	}
}
