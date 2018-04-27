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
	//����
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //����
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //ʵ��������
    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine(getResources());
    }
    //WallpaperEngine����
    public class WallpaperEngine extends Engine {
        private final Handler handler=new Handler();        
        private Bitmap image; //Image
        private Bitmap image01; //Image01 for fire01.PNG
        private Bitmap image02; //Image02 for fire02.PNG
        private int    px=0;  //Flag for switch
        private boolean visible; //��ʾ״̬
        private int     width;   //��
        private int     height;  //��
        //������
        private final Runnable drawThread=new Runnable() {
            public void run() {
                drawFrame();
            }
        };
        //ʵ��WallpaperEngine()
        public WallpaperEngine(Resources r) {
            image01=BitmapFactory.decodeResource(r,R.drawable.fire01);
            image02=BitmapFactory.decodeResource(r,R.drawable.fire02);
            px=1;
        }
        //��������ͨ��SurfaceHolder��������ֽ��Canvas��
        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
        }
        //��������
        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(drawThread);
        }
        //���Surface
        @Override
        public void onSurfaceChanged(SurfaceHolder holder,
            int format,int width,int height) {
            super.onSurfaceChanged(holder,format,width,height);
            this.width =width;
            this.height=height;
            drawFrame();
        }
        //����Surface
        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }
        //����Surface
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            visible=false;
            handler.removeCallbacks(drawThread);
        }
        //����ɼ�/���ɼ�״̬
        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible=visible;
            if (visible) {
                drawFrame();
            } else {
                handler.removeCallbacks(drawThread);
            }
        }
        //�����ֽλ��
        @Override
        public void onOffsetsChanged(float xOffset,float yOffset,
            float xStep,float yStep,int xPixels,int yPixels) {
            drawFrame();
        }
        //Frame���
        private void drawFrame() {
        	//��������
            SurfaceHolder holder=getSurfaceHolder();
            Canvas c=holder.lockCanvas();
            //���
            c.drawColor(Color.BLUE);
            if (px == 1) {
            	image=image01;
            	px=2;
            } else {
            	image=image02;
            	px=1 ;
            }
            c.drawBitmap(image, (width-image.getWidth())/2, (height-image.getHeight())/2, null);
            //��������
            holder.unlockCanvasAndPost(c);
            //����
            handler.removeCallbacks(drawThread);
            if (visible) handler.postDelayed(drawThread,100);
        }
    }
}