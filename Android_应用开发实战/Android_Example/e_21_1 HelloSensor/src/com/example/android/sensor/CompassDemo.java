package com.example.android.sensor;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CompassDemo extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private MySurfaceView view;
	private Object[] orientation = {
			"Rotate Z-axis Orientation", "Rotate X-axis Orientation","Rotate Y-axis Orientation",
	};
	//CompassDemoÖ÷³ÌÐò
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
		List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			sensorManager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
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
		private Bitmap bitmap,curBitmap;
		private float x, y, z, delta=-8;
		private int curWidth, curHeight;
		public MySurfaceView(Context context) {
			super(context);
			getHolder().addCallback(this);
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			x = getWidth()/2;
			y = getHeight()/2;
			onValueChanged(new float[3]);
		}
		public void surfaceCreated(SurfaceHolder holder) {
		}
		public void surfaceDestroyed(SurfaceHolder holder) {
		}
		@SuppressWarnings("static-access")
		void onValueChanged(float[] values) {
			Canvas canvas = getHolder().lockCanvas();
			if (canvas != null) {
				Paint paint = new Paint();
				paint.setAntiAlias(true);
				paint.setColor(Color.BLUE);
				paint.setTextSize(24);
				canvas.drawColor(Color.WHITE);
				canvas.save();
				Matrix matrix = new Matrix();
				curWidth = (int) (bitmap.getWidth()* 1);
				curHeight =  (int) (bitmap.getHeight()* 1);
				curBitmap = bitmap.createScaledBitmap(bitmap, curWidth, curHeight, false);
				matrix.setRotate(-values[0]+delta, x , y );
				canvas.setMatrix(matrix);
				canvas.drawBitmap(curBitmap, x-curWidth/2, y-curHeight/2, null);
				canvas.restore();
				for (int i = 0; i < values.length; i++) {
					canvas.drawText(orientation[i] + ": "  + values[i], 0, paint.getTextSize() * (i + 1), paint);
				}
				getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
}

