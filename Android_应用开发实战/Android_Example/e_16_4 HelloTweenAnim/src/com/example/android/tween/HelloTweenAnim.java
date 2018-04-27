package com.example.android.tween;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class HelloTweenAnim extends Activity {
    //HelloTweenAnim������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ImageView mImageView01 = (ImageView)findViewById(R.id.ImageView01);
        //����Tween Animation������xml�ļ�
        final Animation scaleAnimation =AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        final Animation rotateAnimation =AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        final Animation alphaAnimation =AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
        final Animation translateAnimation =AnimationUtils.loadAnimation(this, R.anim.translate_animation);
        final Animation setAnimation =AnimationUtils.loadAnimation(this, R.anim.set_animation);
		mImageView01.startAnimation(setAnimation);
		//����Scale��ť
        Button mButton01 = (Button)findViewById(R.id.Button01);
		mButton01.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mImageView01.setAnimation(scaleAnimation);
				mImageView01.startAnimation(scaleAnimation);
			}
		});
		//����Rotate��ť
		Button mButton02 = (Button)findViewById(R.id.Button02);
		mButton02.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mImageView01.setAnimation(rotateAnimation);
				mImageView01.startAnimation(rotateAnimation);
			}
		});
		//����Alpha��ť
		Button mButton03 = (Button)findViewById(R.id.Button03);
		mButton03.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mImageView01.setAnimation(alphaAnimation);
				mImageView01.startAnimation(alphaAnimation);
			}
		});
		//����Translate��ť
		Button mButton04 = (Button)findViewById(R.id.Button04);
		mButton04.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mImageView01.setAnimation(translateAnimation);
				mImageView01.startAnimation(translateAnimation);
			}
		});
		//����Set��ť
		Button mButton05 = (Button)findViewById(R.id.Button05);
		mButton05.setOnClickListener(new Button.OnClickListener() {		
			public void onClick(View v) {
				mImageView01.setAnimation(setAnimation);
				mImageView01.startAnimation(setAnimation);
			}
		});
    }
}