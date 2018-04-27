package com.example.android.media;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerVideo extends Activity implements
        OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {
    private static final String TAG = "MediaPlayerDemo";
    private int mVideoWidth;
    private int mVideoHeight;
    private MediaPlayer mMediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private String path;
    private TextView mTextView;
    private Bundle extras;
    private static final String MEDIA = "media";
    private static final int LOCAL_AUDIO = 1;
    private static final int STREAM_AUDIO = 2;
    private static final int RESOURCES_AUDIO = 3;
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    private static final int RESOURCES_VIDEO = 6;
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;
    //MediaPlayerVideo程序
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer01);
        mTextView = (TextView) findViewById(R.id.TextView01);
        mPreview = (SurfaceView) findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        extras = getIntent().getExtras();
    }
    //播放视频
    private void playVideo(Integer Media) {
        doCleanUp();
        try {
            switch (Media) {
                case LOCAL_VIDEO:
                	//设定视频文件路径
                	path = "/sdcard/navy.3gp";//这是作者测试用的视频文件
                    //path = "";
                    if (path == "") {
                        //警告User尚未建立视频文件
                        Toast.makeText(MediaPlayerVideo.this, "尚未建立视频文件",Toast.LENGTH_LONG).show();
                    }
                    mTextView.setText("LOCAL_VIDEO-播放视频中...");
                    break;
                case STREAM_VIDEO:
                	//警告User尚未建立视频文件的URL.
                	path = "http://www.uenocity.com/media-android/navy.3gp";//這是作者測試用的視訊檔案的RUL
                    //path = "";
                    if (path == "") {
                        //警告User尚未建立视频文件的URL.
                        Toast.makeText(MediaPlayerVideo.this,"Please edit MediaPlayerDemo_Video Activity and set the path variable to your media file URL.",Toast.LENGTH_LONG).show();
                    }
                    mTextView.setText("STREAM_VIDEO-播放视频中...");
                    break;
                case RESOURCES_VIDEO:
                    //本地视频文件要放在/res/raw目录內且提供resid给MediaPlayer.create()
                	//mMediaPlayer = new MediaPlayer();
                	//mMediaPlayer.setDataSource("/sdcard/navy.3gp");
                	mMediaPlayer = MediaPlayer.create(this, R.raw.navy);//执行create()时，会调用prepare()  	
                	mMediaPlayer.setDisplay(holder);
                	//mMediaPlayer.prepare();
                	mMediaPlayer.setOnBufferingUpdateListener(this);
                	mMediaPlayer.setOnCompletionListener(this);
                	mMediaPlayer.setOnPreparedListener(this);
                	mMediaPlayer.setOnVideoSizeChangedListener(this);
                	mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.start();
                	mTextView.setText("RESOURCES_VIDEO-播放视频中...");
                	break;
            }
            //实例化MedialPlayer且设定监听功能
            if (Media == LOCAL_VIDEO || Media == STREAM_VIDEO) {
            	mMediaPlayer = new MediaPlayer();
            	mMediaPlayer.setDataSource(path);
            	mMediaPlayer.setDisplay(holder);
            	mMediaPlayer.prepare();
            	mMediaPlayer.setOnBufferingUpdateListener(this);
            	mMediaPlayer.setOnCompletionListener(this);
            	mMediaPlayer.setOnPreparedListener(this);
            	mMediaPlayer.setOnVideoSizeChangedListener(this);
            	mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }
    //onBufferingUpdate
    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate percent:" + percent);
    }
    //onCompletion
    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }
    //onVideoSizeChanged
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        Toast.makeText(MediaPlayerVideo.this, "onVideoSizeChanged called",Toast.LENGTH_LONG).show();
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;    
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }
    //onPrepared
    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        Toast.makeText(MediaPlayerVideo.this, "onPrepared called",Toast.LENGTH_LONG).show();
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }     
    }
    //surfaceChanged
    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");
    }
    //surfaceDestroyed
    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }
    //调用playVideo()，播放视频
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
        playVideo(extras.getInt(MEDIA));
    }
    //onPause
    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }
    //关闭MediaPlayerVideo程序且释放MediaPlayer
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }
    //releaseMediaPlayer
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
    //doCleanUp
    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }
    //startVideoPlayback
    private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        Toast.makeText(MediaPlayerVideo.this, "startVideoPlayback()",Toast.LENGTH_LONG).show();
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }
}
