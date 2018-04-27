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
	//HelloFrameAnim������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //����Frame Animation������xml�ļ�
        ImageView mImageView = (ImageView) findViewById(R.id.ImageView01);  
        mImageView.setBackgroundResource(R.anim.fireanim);  
        fireAnimation = (AnimationDrawable) mImageView.getBackground();
        //�趨��ť����ʼ����ֹFrame Animation����
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
    //Start Frame Animation����
    public boolean onTouchEvent(MotionEvent event) {  
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		fireAnimation.start();
    		mButton.setText(R.string.click_me_to_stop);
    		return true;  
    	}  
    	return super.onTouchEvent(event);
    }
}