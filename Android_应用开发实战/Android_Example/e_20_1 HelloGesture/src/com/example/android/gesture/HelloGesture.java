package com.example.android.gesture;

import java.util.ArrayList;
import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.GestureOverlayView.OnGesturingListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class HelloGesture extends Activity {
	//HelloGesture������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView textView = (TextView)findViewById(R.id.TextView01);
		textView.setText(R.string.app_description);
		//GestureLibrary-��¼Gesture�Ŀ�
		final GestureLibrary gl = GestureLibraries.fromPrivateFile(this, "gestures");
		//GestureOverlayView-Gesture�����͸�����ص���
		GestureOverlayView gov = (GestureOverlayView)findViewById(R.id.GestureOverlayView01);		
		//OnGestureListener��������
		gov.addOnGestureListener(new OnGestureListener() {		
			public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
				Log.v("Gesture", "onGestureStarted");
			}		
			public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
				Log.v("Gesture", "onGestureEnded");
			}	
			public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
				Log.v("Gesture", "onGestureCancelled");
			}	
			public void onGesture(GestureOverlayView overlay, MotionEvent event) {
				Log.v("Gesture", "onGesture");
			}
		});
		//OnGesturePerformedListener��������
		gov.addOnGesturePerformedListener(new OnGesturePerformedListener() {
			public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
				Log.v("Gesture", "onGesturePerformed");
				Log.v("Gesture", "id:" + gesture.getID());
				Log.v("Gesture", "length:" + gesture.getLength());
				Log.v("Gesture", "describeContents:" + gesture.describeContents());			
				if (gl.getGestureEntries().size() == 0) {
					gl.addGesture("First", gesture);
					gl.save();
				} else {
					//����GestureLibrary.recognize(gesture)ȡ��predictions
					ArrayList<Prediction> predictions = gl.recognize(gesture);
					Log.v("Gesture", "predictions.size:" + predictions.size());
					for (Prediction p : predictions) {
						Log.v("Gesture", "Prediction name:" + p.name + " score:" + p.score);
						Toast.makeText(HelloGesture.this, "score:" + p.score, Toast.LENGTH_SHORT).show();
					}
				}
			}		
		});
		//OnGesturingListener��������
		gov.addOnGesturingListener(new OnGesturingListener() {
			public void onGesturingEnded(GestureOverlayView overlay) {
				Log.v("Gesture", "onGesturingEnded");
			}
			public void onGesturingStarted(GestureOverlayView overlay) {
				Log.v("Gesture", "onGesturingStarted");
			}		
		});
	}
}