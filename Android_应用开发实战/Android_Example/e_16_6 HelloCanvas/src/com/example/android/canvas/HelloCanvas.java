package com.example.android.canvas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class HelloCanvas extends Activity {
	private SampleView mView;
    private Bitmap mBitmap;
    //HelloCanvas主程序
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        mBitmap = BitmapFactory.decodeResource(getResources(), 
                R.drawable.icon);        
        mView = new SampleView(this);
        setContentView(mView);
    }
    //View.onDraw()方法
    private class SampleView extends View {
        private Paint mPaint;
        private float imageX = 0f;
        private float imageY = 0f;
        //
        public SampleView(Context context) {
            super(context);
            mPaint = new Paint();
        }
        //onDraw() callback方法
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLUE);
            canvas.drawText(getString(R.string.hello), 100, 20, mPaint);
            //绘图表示
            canvas.drawBitmap(mBitmap, 
                    imageX - mBitmap.getWidth() / 2, 
                    imageY - mBitmap.getHeight() / 2, 
                    mPaint);
        }
        //手势触控的监听功能
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                imageX = event.getX();
                imageY = event.getY();
            }
            else if(event.getAction() == MotionEvent.ACTION_MOVE){
                imageX = event.getX();
                imageY = event.getY();
            }
            else if(event.getAction() == MotionEvent.ACTION_UP){
                imageX = event.getX();
                imageY = event.getY();
            }
            //再描绘的命令
            invalidate();        
            return true;
        }
    }
}