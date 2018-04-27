package com.qianfeng.animation.viewflipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Animation7_ViewFlipper extends Activity {

	private ViewFlipper flipper;// ViewFlipper实例
	private GestureDetector detector;// 触摸监听实例

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		detector = new GestureDetector(this, l);// 初始化触摸探测
		flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper01);// 获得ViewFlipper实例

		flipper.addView(addTextView("Page 1"));// 将View添加到flipper队列中
		flipper.addView(addTextView("Page 2"));
		flipper.addView(addTextView("Page 3"));
		flipper.addView(addTextView("Page 4"));
		flipper.addView(addTextView("Page 5"));
	}

	private View addTextView(String text) {
		TextView tv = new TextView(this);
		tv.setText(text);
		tv.setGravity(1);
		tv.setTextSize(30);
		return tv;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.detector.onTouchEvent(event);
	}

	private GestureDetector.OnGestureListener l = new GestureDetector.OnGestureListener() {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			if (e1.getX() - e2.getX() > 120) {// 如果是从右向左滑动
				// 注册flipper的进出效果
				flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.left_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.left_out));
				flipper.showNext();
				return true;
			} else if (e1.getX() - e2.getX() < -120) {// 如果是从左向右滑动
				flipper.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.right_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(),
						R.anim.right_out));
				flipper.showPrevious();
				return true;
			}
			return true;
		}

		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
				float arg3) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onShowPress(MotionEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onSingleTapUp(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	};

}