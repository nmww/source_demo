package com.example.android.sensor;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AccelerometerDemo extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private MySurfaceView view;
	private Object[] accelerometer = {
			"X-axis Accelerometer", "Y-axis Accelerometer","Z-axis Accelerometer",
	};
	//AccelerometerDemoÖ÷³ÌÐò
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		view = new MySurfaceView(this);
		setContentView(view);
	}
	@Override
	protected void onResume() {
		super.onResume();
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_FASTEST);
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	public void onSensorChanged(SensorEvent event) {
		view.onValueChanged(event.values);
	}
	class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
		private Bitmap bitmap, curBitmap;
		private float x, y, z;
		private int curWidth, curHeight;
		public MySurfaceView(Context context) {
			super(context);
			getHolder().addCallback(this);
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android);
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			x = (getWidth() - bitmap.getWidth()) / 2;
			y = (getHeight() - bitmap.getHeight()) / 2;
			onValueChanged(new float[3]);
		}
		public void surfaceCreated(SurfaceHolder holder) {
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
		}
		@SuppressWarnings("static-access")
		void onValueChanged(float[] values) {
			z = 2 + values[2]/5;
			curWidth = (int) (bitmap.getWidth()* z);
			curHeight =  (int) (bitmap.getHeight()* z);
			curBitmap = bitmap.createScaledBitmap(bitmap, curWidth, curHeight, false);
			x = (getWidth() - curWidth) / 2;
			y = (getHeight() - curHeight) / 2;
			x -= values[0]*10;
			y += values[1]*10;
			Canvas canvas = getHolder().lockCanvas();
			if (canvas != null) {
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setColor(Color.BLUE);
				paint.setTextSize(24);
				canvas.drawColor(Color.WHITE);
				canvas.drawBitmap(curBitmap, x, y, null);
				for (int i = 0; i < values.length; i++) {
					canvas.drawText(accelerometer[i] + ": " + values[i], 0, paint.getTextSize() * (i + 1), paint);
				}
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
}