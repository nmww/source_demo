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
	//�̳�SurfaceView�ഴ��MySurfaceView
	class MySurfaceView extends SurfaceView {
		Paint paint = new Paint();
		String text = "";
		//ʵ��MySurfaceView����
		public MySurfaceView(Context context) {
			super(context);
			setFocusable(true); //ʹ��Key Event��setFocusable���true�����Ծ۽�
			requestFocus(); //Ҫ��۽����]�о۽���Ԓ�����ղ�����ȷ��Key Event???
			paint.setColor(Color.BLUE);
			paint.setAntiAlias(true);
			paint.setTextSize(24);
		}
		//���¼�ʱ�Ĵ���
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyDown");
			if (keyCode == KeyEvent.KEYCODE_BACK) { //Back��
				return false;
			}
			text = "onKeyDown: keycode[" + keyCode + "]";
			redraw();
			return true;
		}
		//�����ʱ�Ĵ���
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyUp");
			return true;
		}
		//��ѹ��ʱ�Ĵ���
		@Override
		public boolean onKeyLongPress(int keyCode, KeyEvent event) {
			Log.d("TEST", "onKeyLongPress");
			return true;
		}
		//�����ʱ�Ĵ���
		@Override
		public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
			Log.d("TEST", "onKeyMultiple");
			return true;
		}
		//��ʾ����Code
		void redraw() {
			Canvas canvas = getHolder().lockCanvas();
			canvas.drawColor(Color.WHITE);
			canvas.drawText(text, 0, paint.getTextSize(), paint);
			getHolder().unlockCanvasAndPost(canvas);
		}
	}
	//KeyEventSample������
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MySurfaceView(this));
	}
}
