package com.example.android.anim03;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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
        mButton = (Button)findViewById(R.id.Button01);
        mButton.setOnClickListener(new OnClickListener() {
        	//�趨��ť����ʼ����ֹFrame Animation����
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
    @Override 
    public void onWindowFocusChanged(boolean hasFocus) { 
    	super.onWindowFocusChanged(hasFocus);
    	if (hasFocus) { 
    		fireAnimation.start();
    		mButton.setText(R.string.click_me_to_stop);
    	} else { 
    		fireAnimation.stop();
    		mButton.setText(R.string.click_me_to_start);
    	} 
    } 
}