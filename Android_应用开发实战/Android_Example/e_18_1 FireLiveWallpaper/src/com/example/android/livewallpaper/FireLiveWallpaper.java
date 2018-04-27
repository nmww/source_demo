package com.example.android.livewallpaper;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class FireLiveWallpaper extends WallpaperService {
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
    public class WallpaperEngine extends Engine {
        private final Handler handler=new Handler();        
        private Bitmap image; //Image
        private Bitmap image01; //Image01 for fire01.PNG
        private Bitmap image02; //Image02 for fire02.PNG
        private int    px=0;  //Flag for switch
        private boolean visible; //显示状态
        private int     width;   //长
        private int     height;  //高
        //描绘程序
        private final Runnable drawThread=new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        //实现WallpaperEngine()
        public WallpaperEngine(Resources r) {
            image01=BitmapFactory.decodeResource(r,R.drawable.fire01);
            image02=BitmapFactory.decodeResource(r,R.drawable.fire02);
            px=1;
        }
        //创建引擎通过SurfaceHolder来描描绘壁纸在Canvas上
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
        //变更壁纸位置
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
            if (px == 1) {
            	image=image01;
            	px=2;
            } else {
            	image=image02;
            	px=1 ;
            }
            c.drawBitmap(image, (width-image.getWidth())/2, (height-image.getHeight())/2, null);
            //解锁画布
            holder.unlockCanvasAndPost(c);
            //再描
            handler.removeCallbacks(drawThread);
            if (visible) handler.postDelayed(drawThread,100);
        }
    }
}