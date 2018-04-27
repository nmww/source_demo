package com.qianfeng.animation.gesturedetector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Animation_GestureDetector extends Activity {
	ViewFlipper vf;
	GestureDetector detector;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
        vf = (ViewFlipper)this.findViewById(R.id.viewFlipper1);
        vf.addView(addTextView("第一页"));
        vf.addView(addTextView("第二页"));
        vf.addView(addTextView("第三页"));
        vf.addView(addTextView("第亖页"));
        vf.addView(addTextView("第五页"));
        
        detector = new GestureDetector(this, l);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	return detector.onTouchEvent(event);
    }
    
    private View addTextView(String text) {
    	TextView tv = new TextView(this);
    	tv.setText(text);
    	tv.setGravity(1);
    	tv.setTextSize(20);
    	return tv;
    }
    
    private GestureDetector.OnGestureListener l = 
    		new GestureDetector.OnGestureListener() {

				@Override
				public boolean onDown(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					if(e1.getX() - e2.getX() > 120) {
						Log.v("FLING", "从右到左");
						Animation a1 = AnimationUtils.loadAnimation(
								Animation_GestureDetector.this, R.anim.left_in);
						vf.setInAnimation(a1);
						Animation a2 = AnimationUtils.loadAnimation(
								Animation_GestureDetector.this, R.anim.left_out);
						vf.setOutAnimation(a2);
						vf.showNext();
						return true;
					} else if(e1.getX() - e2.getX() < -120 ) {
						Log.v("FLING", "从左到右");
						Animation a1 = AnimationUtils.loadAnimation(
								Animation_GestureDetector.this, R.anim.right_in);
						vf.setInAnimation(a1);
						Animation a2 = AnimationUtils.loadAnimation(
								Animation_GestureDetector.this, R.anim.right_out);
						vf.setOutAnimation(a2);
						vf.showPrevious();
						return true;
					}
					return false;
				}

				@Override
				public void onLongPress(MotionEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean onScroll(MotionEvent e1, MotionEvent e2,
						float distanceX, float distanceY) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void onShowPress(MotionEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public boolean onSingleTapUp(MotionEvent e) {
					// TODO Auto-generated method stub
					return false;
				}
    	
    };
    
}