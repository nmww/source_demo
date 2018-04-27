package com.example.android.anim02;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class HelloFrameAnim extends Activity {
	AnimationDrawable fireAnimation;
	Button mButton;
	//HelloFrameAnim主程序
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //定义Frame Animation动画的xml文件
        ImageView mImageView = (ImageView) findViewById(R.id.ImageView01);  
        mImageView.setBackgroundResource(R.anim.fireanim);  
        fireAnimation = (AnimationDrawable) mImageView.getBackground();
        //设定按钮来开始或终止Frame Animation动画
        mButton = (Button)findViewById(R.id.Button01);
        mButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ( ! fireAnimation.isRunning() ) {
					fireAnimation.start();
					mButton.setText(R.string.click_me_to_stop);
				}
				else {
					fireAnimation.stop();
					mButton.setText(R.string.click_me_to_start);
				}
			}	
        });
    }
    //Start Frame Animation动画
    public boolean onTouchEvent(MotionEvent event) {  
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		fireAnimation.start();
    		mButton.setText(R.string.click_me_to_stop);
    		return true;  
    	}  
    	return super.onTouchEvent(event);
    }
}