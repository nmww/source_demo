package com.example.android.tts;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public final class HelloTTS extends Activity implements OnInitListener, OnClickListener {
	private TextToSpeech tts;
	//HelloTTS������
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);		
		tts = new TextToSpeech(getApplicationContext(), this);	
		Button Button01 = (Button)findViewById(R.id.Button01);
		Button01.setOnClickListener(this);
	}
	//�ر�
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tts.shutdown();
	}
	//��ʼ
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			tts.speak("I'm ready!", TextToSpeech.QUEUE_FLUSH, null);
		} else {
			System.out.println("Oops!");
		}
	}
	//����TextToSpeech
	public void onClick(final View v) {
		TextView TextView01 = (TextView)findViewById(R.id.EditText01);
		tts.speak(TextView01.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
	}
}
