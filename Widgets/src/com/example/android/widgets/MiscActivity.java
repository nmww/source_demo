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
	//MiscActivity������
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.misc_activity);
		
		TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		TextView mTextView02 = (TextView)findViewById(R.id.TextView02);
		RatingBar mRatingBar01 = (RatingBar)findViewById(R.id.RatingBar01);
		SeekBar mSeekBar01 = (SeekBar)findViewById(R.id.SeekBar01);
		//��Rating Bar��ʾ���ȵĳ�ʼֵ
		mTextView01.setText("��Rating Bar��ʾ:\n������=" + mRatingBar01.getProgress());
		//��Seek Bar��ʾ�����ĳ�ʼֵ
		mTextView02.setText("��Seek Bar��ʾ:\n������=" + mSeekBar01.getProgress());
		//RatingBar.OnRatingBarChangeListener��Activity�����x implements
		mRatingBar01.setOnRatingBarChangeListener(this);
		//SeekBar.OnSeekBarChangeListener�]����Activity���壬���������滮mSeekBar01�ļ����������
		mSeekBar01.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			//Seek Bar�б仯ʱ��ִ��������ʾ�����Ĵ�С
			public void onProgressChanged(SeekBar mSeekBar01, int progress, boolean fromTouch) {
				final TextView mTextView02 = (TextView)findViewById(R.id.TextView02);
				mTextView02.setText("��Seek Bar��ʾ:\n������=" + mSeekBar01.getProgress());
			}
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});		
	}
	//Rating Bar�б仯ʱ��ִ��������ʾ���ȵĴ�С
	public void onRatingChanged(RatingBar mRatingBar01, float rating, boolean fromTouch) {
		final TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
		mTextView01.setText("��Rating Bar��ʾ:\n������=" + mRatingBar01.getProgress());
	}
}

