package com.example.android.anim;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class HelloFrameAnim extends Activity {
	AnimationDrawable fireAnimation;
	//HelloFrameAnim主程序
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //定义Frame Animation动画的xml文件
        ImageView mImageView = (ImageView) findViewById(R.id.ImageView01);  
        mImageView.setBackgroundResource(R.anim.fireanim);  
        fireAnimation = (AnimationDrawable) mImageView.getBackground();
    }
    //Start Frame Animation动画
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		fireAnimation.start();
    		return true;  
    	}
    	return super.onTouchEvent(event);
    }
}