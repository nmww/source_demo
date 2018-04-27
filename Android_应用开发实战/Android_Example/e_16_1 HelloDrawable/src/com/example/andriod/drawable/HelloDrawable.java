package com.example.andriod.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HelloDrawable extends Activity {
    //HelloDrawable������
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final ImageView mImageView = (ImageView)findViewById(R.id.ImageView01);
        ImageButton mImageButton01 = (ImageButton)findViewById(R.id.ImageButton01);
        ImageButton mImageButton02 = (ImageButton)findViewById(R.id.ImageButton02);
        ImageButton mImageButton03 = (ImageButton)findViewById(R.id.ImageButton03);
        //����ImageButton01����ͼ����ͼ����ʾnavy_101��Ƭ
        mImageButton01.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View arg0) {
				mImageView.setImageResource(R.drawable.navy_101);				
				}				
    		});
        //����ImageButton02����ͼ����ͼ����ʾnavy_102��Ƭ
        mImageButton02.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View arg0) {
				mImageView.setImageResource(R.drawable.navy_102);				
			}				
		});
        //����ImageButton03����ͼ����ͼ����ʾnavy_103��Ƭ
        mImageButton03.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View arg0) {
				mImageView.setImageResource(R.drawable.navy_103);				
			}				
		});
    }
}