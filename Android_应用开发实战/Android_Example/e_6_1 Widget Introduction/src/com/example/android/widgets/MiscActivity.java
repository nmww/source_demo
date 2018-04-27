package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MiscActivity extends Activity implements RatingBar.OnRatingBarChangeListener  {
//RatingBar.OnRatingBarChangeListener  SeekBar.OnSeekBarChangeListener 
	//MiscActivity主程序
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.misc_activity);
		
		TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		TextView mTextView02 = (TextView)findViewById(R.id.TextView02);
		RatingBar mRatingBar01 = (RatingBar)findViewById(R.id.RatingBar01);
		SeekBar mSeekBar01 = (SeekBar)findViewById(R.id.SeekBar01);
		//用Rating Bar表示亮度的初始值
		mTextView01.setText("用Rating Bar表示:\n亮度是=" + mRatingBar01.getProgress());
		//用Seek Bar表示音量的初始值
		mTextView02.setText("用Seek Bar表示:\n音量是=" + mSeekBar01.getProgress());
		//RatingBar.OnRatingBarChangeListener在Activity己定義 implements
		mRatingBar01.setOnRatingBarChangeListener(this);
		//SeekBar.OnSeekBarChangeListener沒有在Activity定义，这里完整规划mSeekBar01的简体监听功能
		mSeekBar01.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			//Seek Bar有变化时，执行这里显示音量的大小
			public void onProgressChanged(SeekBar mSeekBar01, int progress, boolean fromTouch) {
				final TextView mTextView02 = (TextView)findViewById(R.id.TextView02);
				mTextView02.setText("用Seek Bar表示:\n音量是=" + mSeekBar01.getProgress());
			}
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});		
	}
	//Rating Bar有变化时，执行这里显示亮度的大小
	public void onRatingChanged(RatingBar mRatingBar01, float rating, boolean fromTouch) {
		final TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		mTextView01.setText("用Rating Bar表示:\n亮度是=" + mRatingBar01.getProgress());
	}
}

