package com.example.android.widgets;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {
//VideoActivityÖ÷³ÌÐò	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        
    	VideoView videoView = (VideoView)findViewById(R.id.VideoView01);
        videoView.setVideoPath("/sdcard/navy.3gp");
        videoView.setMediaController(new MediaController(this));
    }
}
