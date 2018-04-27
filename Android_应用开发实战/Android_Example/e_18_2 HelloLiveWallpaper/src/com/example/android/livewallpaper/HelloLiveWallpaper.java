package com.example.android.livewallpaper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

public class HelloLiveWallpaper extends WallpaperService {
	public static final String SHARED_PREFS_NAME = "com.example.android.livewallpaper";
	//����
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //��ʧ
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //����Engine
    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine(getResources());
    }
    //WallpaperEngine
    public class WallpaperEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {
        SharedPreferences prefs;
    	private final Handler handler=new Handler();        
        private Bitmap image; //Image
        private int    px=0;  //X����
        private int    py=0;  //Y����
        private int    vx=10; //X�ٶ�
        private int    vy=10; //Y�ٶ�
        private boolean visible; //��ʾ״̬
        private int     width;   //��
        private int     height;  //��
        private int		speedLevel=100; //Initial Speed = 0.1 sec
        //������
        private final Runnable drawThread=new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        //Control
        public WallpaperEngine(Resources r) {
            prefs = HelloLiveWallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
            prefs.registerOnSharedPreferenceChangeListener(this);
            onSharedPreferenceChanged(prefs, null);
        	image=BitmapFactory.decodeResource(r,R.drawable.droid);
            px=image.getWidth()/2;
            py=image.getHeight()/2;
        }
        //����
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }
        //��ʧ
        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawThread);
        }
        //��ʾ״̬���
        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible=visible;
            if (visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawThread);
            }
        }
        //Surface���
        @Override
        public void onSurfaceChanged(SurfaceHolder holder,
            int format,int width,int height) {
            super.onSurfaceChanged(holder,format,width,height);
            this.width =width;
            this.height=height;
            drawFrame();
        }
        //Surface����
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
        //Surface��ʧ
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible=false;
            handler.removeCallbacks(drawThread);
        }
        //Offset���
        @Override
        public void onOffsetsChanged(float xOffset,float yOffset,
            float xStep,float yStep,int xPixels,int yPixels) {
            drawFrame();
        }
        //Frame���
        private void drawFrame() {
        	//Lock
            SurfaceHolder holder=getSurfaceHolder();
            Canvas c=holder.lockCanvas();
            //���
            c.drawColor(Color.BLUE);
            c.drawBitmap(image,px-image.getWidth()/2,py-image.getHeight()/2,null);
            //Unlock
            holder.unlockCanvasAndPost(c);
            //�ƶ�
            if (px<image.getWidth()/2 || width-image.getWidth()/2<px) vx=-vx;
            if (py<image.getHeight()/2 || height-image.getHeight()/2<py) vy=-vy;
            px+=vx;
            py+=vy;
            //�����
            handler.removeCallbacks(drawThread);
            if (visible) handler.postDelayed(drawThread,speedLevel);
        }
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d("TEST", "onSharedPreferenceChanged 1");
            speedLevel = Integer.parseInt(prefs.getString(getResources().getString(R.string.preference_key), "100"));
            //speedEffect.init(getWallpaperDesiredMinimumWidth(), getWallpaperDesiredMinimumHeight(), Integer.parseInt(prefs.getString(getResources().getString(R.string.preference_key), "120")));
            Log.d("TEST", "onSharedPreferenceChanged 2");
        }
    }
}