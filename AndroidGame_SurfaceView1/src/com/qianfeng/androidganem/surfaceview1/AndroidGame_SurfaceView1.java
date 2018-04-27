package com.qianfeng.androidganem.surfaceview1;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class AndroidGame_SurfaceView1 extends Activity {

	public int screenWidth;
	public int screenHeight;
	BallSurfaceView bsv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ������Ϊ����ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		bsv = new BallSurfaceView(this);

		setContentView(bsv);
		// �����Ļ�ߴ�
		DisplayMetrics dm;
		dm = this.getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		Log.v("SurfaceView", "Width "+screenWidth+"  Height "+screenHeight);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
}