package com.example.android.video;

import android.app.Activity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class HelloVidioView extends Activity {
    //HelloVidioViewÖ÷³ÌÐò
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        VideoView videoView = (VideoView)findViewById(R.id.VideoView01);
        videoView.setVideoPath("/sdcard/navy.3gp");
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.requestFocus();
        videoView.start();
    }
}