package com.qianfeng.animation.animation1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
public class Animation1 extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//定义UI组件
		final ImageButton ib1 = (ImageButton) findViewById(R.id.imageButton1);
		final ImageButton ib2 = (ImageButton) findViewById(R.id.imageButton2);
		final ImageButton ib3 = (ImageButton) findViewById(R.id.imageButton3);
		final ImageButton ib4 = (ImageButton) findViewById(R.id.imageButton4);
		final ImageButton ib5 = (ImageButton) findViewById(R.id.imageButton5);
		final ImageButton ib6 = (ImageButton) findViewById(R.id.imageButton6);
		final ImageButton start = (ImageButton) findViewById(R.id.start);
		

		//定义监听器
		OnClickListener ocl = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.imageButton1:
					//创建Animation对象
					Animation ani1 = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.alpha_animation);
					//组件播放动画
					ib1.startAnimation(ani1);
					break;
				case R.id.imageButton2:
					//创建Animation对象
					Animation ani2 = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.translate_animation);
					//组件播放动画
					ib2.startAnimation(ani2);
					break;
				case R.id.imageButton3:
					//创建Animation对象
					Animation ani3 = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.scale_animation);
					//组件播放动画
					ib3.startAnimation(ani3);
					break;
				case R.id.imageButton4:
					//创建Animation对象
					Animation ani4 = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.rotate_animation);
					//组件播放动画
					ib4.startAnimation(ani4);
					break;
				case R.id.start:
					Animation ani6 = AnimationUtils.loadAnimation(getApplicationContext(), 
							R.anim.rotate5_animation);
					ib6.startAnimation(ani6);
					//ib6.startAnimation(ani5);
					break;
				}

			}

		};

		//绑定监听器
		ib1.setOnClickListener(ocl);
		ib2.setOnClickListener(ocl);
		ib3.setOnClickListener(ocl);
		ib4.setOnClickListener(ocl);
		ib5.setOnClickListener(ocl);
		start.setOnClickListener(ocl);
	}
}