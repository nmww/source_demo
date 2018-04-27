package com.example.android.media;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MediaPlayerAudio extends Activity {
    private static final String TAG = "MediaPlayerDemo";
    private MediaPlayer mMediaPlayer;
    private static final String MEDIA = "media";
    private static final int LOCAL_AUDIO = 1;
    private static final int STREAM_AUDIO = 2;
    private static final int RESOURCES_AUDIO = 3;
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    private static final int RESOURCES_VIDEO = 6;
    private String path;
    private TextView mTextView;
    //MediaPlayerAudio
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mediaplayer02);
        mTextView = (TextView) findViewById(R.id.TextView01);
        Bundle extras = getIntent().getExtras();
        playAudio(extras.getInt(MEDIA));
    }
    //��������
    private void playAudio(Integer media) {
        try {
            switch (media) {
                case LOCAL_AUDIO:
                	//�趨��Ƶ�ļ�·��
                    path = "/sdcard/song01.mp3";//�������߲����õ���Ƶ�ļ�
                	//path = "";
                    if (path == "") {
                        //����User��δ������Ƶ�ļ� 
                        Toast.makeText(MediaPlayerAudio.this,"��δ������Ƶ�ļ�",Toast.LENGTH_LONG).show();
                    }
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mTextView.setText("LOCAL_AUDIO-����������...");
                    break;
                case STREAM_AUDIO:
                	//�趨��Ƶ�ļ�·����URL
                	path = "http://www.uenocity.com/media-android/song11.mp3";
                    //path = "";
                    if (path == "") {
                        //����User��δ������Ƶ�ļ���URL.
                        Toast.makeText(MediaPlayerAudio.this,"Please edit MediaPlayerDemo_Video Activity and set the path variable to your media file URL.",Toast.LENGTH_LONG).show();
                    }
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mTextView.setText("STREAM_AUDIO-����������...");
                    break;
                case RESOURCES_AUDIO:
                    //������Ƶ�ļ�Ҫ����/res/rawĿ¼�����ṩresid��MediaPlayer.create()
                	mMediaPlayer = MediaPlayer.create(this, R.raw.song21);
                    mMediaPlayer.start();
                    mTextView.setText("RESOURCES_AUDIO-����������...");
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }
    //�ر�MediaPlayerAudio�������ͷ�MediaPlayer
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
