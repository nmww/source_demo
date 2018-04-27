package com.zhg.music;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
/* mainfest 中需要调整该Activity为第一启动
 * 1.建立 splash.xml视图文件
 * 2.建立res/anim/fadein.xml和fadeout.xml文件
 * 3.建立splashActivity文件
 * 4.闪动图片之后切换到主程序，MusicClientActivity， onCreate中添加淡出效果
 * */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		ImageView imgSplash = (ImageView)findViewById(R.id.imgSplash);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
		imgSplash.startAnimation(anim);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (SplashActivity.this, MusicClientActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
