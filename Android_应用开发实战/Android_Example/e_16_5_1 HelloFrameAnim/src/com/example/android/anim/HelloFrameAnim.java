package com.example.android.anim;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

public class HelloFrameAnim extends Activity {
	AnimationDrawable fireAnimation;
	//HelloFrameAnim������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //����Frame Animation������xml�ļ�
        ImageView mImageView = (ImageView) findViewById(R.id.ImageView01);  
        mImageView.setBackgroundResource(R.anim.fireanim);  
        fireAnimation = (AnimationDrawable) mImageView.getBackground();
    }
    //Start Frame Animation����
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		fireAnimation.start();
    		return true;  
    	}
    	return super.onTouchEvent(event);
    }
}