package com.wapzq.test.gallery;

import com.wapzq.test.view.FlingGallery;
import com.wapzq.test.view.OnGalleryChangeListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class TestGallery extends Activity {
	private final int color_red = Color.argb(100, 200, 0, 0);
	private final int color_green = Color.argb(100, 0, 200, 0);
	private final int color_blue = Color.argb(100, 0, 0, 200);
	private final int color_yellow = Color.argb(100, 200, 200, 0);
	private final int color_purple = Color.argb(100, 200, 0, 200);

	private FlingGallery gallery;
	private final String[] mLabelArray = { "View1", "View2", "View3", "View4",
			"View5" };
	private final int[] mColorArray = { color_red, color_green, color_blue,
			color_yellow, color_purple };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gallery = new FlingGallery(this);
		gallery.setPaddingWidth(5);
		// gallery.setBackgroundColor(Color.WHITE);
		gallery.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, mLabelArray) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout view = new LinearLayout(getApplicationContext());
				view.setBackgroundColor(mColorArray[position]);
				view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
						LayoutParams.FILL_PARENT));
				TextView label = new TextView(getApplicationContext());
				label.setText(mLabelArray[position]);
				label.setTextSize(24);
				label.setTextColor(Color.WHITE);
				label.setGravity(Gravity.CENTER);
				view.addView(label, LayoutParams.FILL_PARENT,
						LayoutParams.WRAP_CONTENT);
				return view;
			}

		});
		
		gallery.addGalleryChangeListener(new OnGalleryChangeListener() {  
			  
            @Override  
            public void onGalleryChange(int currentItem) {  
                // 干些想干的事件  
            	Log.v("m","currentItem:"+currentItem);
            }  
  
        });  
		setContentView(gallery);
	}

	public boolean onTouchEvent(MotionEvent event) {
		return gallery.onGalleryTouchEvent(event);
	}

}