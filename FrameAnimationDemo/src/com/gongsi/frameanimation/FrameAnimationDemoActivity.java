package com.gongsi.frameanimation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
/* 
 * 新建xml文件，类型frame animation 帧动画
 * <animation-list xmlns:android="http://schemas.android.com/apk/res/android">
 * 		<item android:drawable="@drawable/p01" android:duration="500"/>
 * </animation-list>
 * duration设置图片之间见个时间
 * 
 * */
public class FrameAnimationDemoActivity extends Activity {
	ImageView imgTarget;
	AnimationDrawable anim;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        imgTarget = (ImageView)findViewById(R.id.imgTarget);
        anim = (AnimationDrawable)imgTarget.getBackground();
    }
    
    public void onStart(View view){
    	anim.start();
    }
    public void onStop(View view){
    	anim.stop();
    }
    /*start和run的区别是什么？*/
    public void onRepeat(View view){
    	anim.run();
    }
}