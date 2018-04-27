package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery.LayoutParams;
import android.widget.ViewSwitcher.ViewFactory;

public class GalleryActivity extends Activity {
	//GalleryActivity������
	private ImageSwitcher imageSwitcher;
	Gallery gallery;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_activity);
		//��imageSwitcher�Ƚ���һ��imageView Widget
		imageSwitcher = (ImageSwitcher)findViewById(R.id.ImageSwitcher01);
		imageSwitcher.setFactory(new ViewFactory() {	
			public View makeView() {
				ImageView imageView = new ImageView(GalleryActivity.this);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				return imageView;
			}		
		});
		//����imageSwitcher��fade in & fade out��ʽ�ͳ�ʼ��ʾ����Ƭ
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		imageSwitcher.setImageResource(R.drawable.sakura01);
		//�趨�ಾgallery�ļ������ܣ�ȡ��ѡ�񵽵���Ƭ����ʾ��imageSwitcher��imageView Widget
		gallery = (Gallery) findViewById(R.id.Gallery01);
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {			
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				imageSwitcher.setImageResource((int)gallery.getItemIdAtPosition(position));
			}		
			public void onNothingSelected(AdapterView<?> parent) {
			}		
		});
		//�趨����gallery��������Ƭ�ļ�
		gallery.setAdapter(new BaseAdapter() {
			private Integer[] imageIds = {
					R.drawable.sakura01,
					R.drawable.sakura02,
					R.drawable.sakura03,
					R.drawable.sakura04,
					R.drawable.sakura05,
					R.drawable.sakura06,
					R.drawable.sakura07,
					R.drawable.sakura08,
			};		
			//ȡ��gallery�ȵ���Ƭ����
			public int getCount() {
				return imageIds.length;
			}		
			//
			public Object getItem(int position) {
				return null;
			}	
			//ȡ��gallery�ȵ�ĳһ����Ƭ���ļ�
			public long getItemId(int position) {
				return imageIds[position];
			}		
			//��ĳһ����Ƭ������imageView�����趨��ʾ��ʽ�����м䣬��С��80x60
			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView imageView = new ImageView(GalleryActivity.this);
				imageView.setImageResource(imageIds[position]);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new Gallery.LayoutParams(80, 60));
				return imageView;
			}		
		});
	}
}
