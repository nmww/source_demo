package com.example.android.transition;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class HelloTransition extends Activity {
	//HelloTransition主程序
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //设定转换图像来自navy_photos.xml
		Resources res = getResources();
        TransitionDrawable transition = (TransitionDrawable) res.getDrawable(R.drawable.navy_photos);
        ImageView mImageView = (ImageView) findViewById(R.id.ImageView01);
        mImageView.setImageDrawable(transition);
        //开始转换到第2章图像
        transition.startTransition(5000);
    }
}