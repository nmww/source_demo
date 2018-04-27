package com.example.android.livewallpaperhello;

import android.app.Activity;
import android.content.SharedPreferences;
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
	//创建
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //实例化引擎
    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine(getResources());
    }
    //WallpaperEngine引擎
    public class WallpaperEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener {
        SharedPreferences prefs;
    	private final Handler handler=new Handler();        
        private Bitmap image; //Image
        private int    px=0;  //X坐标
        private int    py=0;  //Y坐标
        private int    vx=10; //X速度
        private int    vy=10; //Y速度
        private boolean visible; //显示状态
        private int     width;   //长
        private int     height;  //高
        private int		speedLevel=100; //Initial Speed = 0.1 sec
        //描绘程序
        private final Runnable drawThread=new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        //实现WallpaperEngine()
        public WallpaperEngine(Resources r) {
            prefs = HelloLiveWallpaper.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
            prefs.registerOnSharedPreferenceChangeListener(this);
            onSharedPreferenceChanged(prefs, null);
        	image=BitmapFactory.decodeResource(r,R.drawable.droid);
            px=image.getWidth()/2;
            py=image.getHeight()/2;
        }
        //创建引擎
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }
        //销毁引擎
        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawThread);
        }  
        //变更Surface
        @Override
        public void onSurfaceChanged(SurfaceHolder holder,
            int format,int width,int height) {
            super.onSurfaceChanged(holder,format,width,height);
            this.width =width;
            this.height=height;
            drawFrame();
        }
        //创建Surface
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
        //销毁Surface
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible=false;
            handler.removeCallbacks(drawThread);
        }
        //变更可见/不可见状态
        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible=visible;
            if (visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawThread);
            }
        }
        //变更壁紙位置
        @Override
        public void onOffsetsChanged(float xOffset,float yOffset,
            float xStep,float yStep,int xPixels,int yPixels) {
            drawFrame();
        }
        //Frame描绘
        private void drawFrame() {
        	//加锁画布
            SurfaceHolder holder=getSurfaceHolder();
            Canvas c=holder.lockCanvas();
            //描绘
            c.drawColor(Color.BLUE);
            c.drawBitmap(image,px-image.getWidth()/2,py-image.getHeight()/2,null);
            //解锁画布
            holder.unlockCanvasAndPost(c);
            //移动
            if (px<image.getWidth()/2 || width-image.getWidth()/2<px) vx=-vx;
            if (py<image.getHeight()/2 || height-image.getHeight()/2<py) vy=-vy;
            px+=vx;
            py+=vy;
            //再描绘
            handler.removeCallbacks(drawThread);
            if (visible) handler.postDelayed(drawThread,speedLevel);
        }
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d("TEST", "onSharedPreferenceChanged 1");
            speedLevel = Integer.parseInt(prefs.getString(getResources().getString(R.string.preference_key), "100"));
            Log.d("TEST", "onSharedPreferenceChanged 2");
        }
    }
}