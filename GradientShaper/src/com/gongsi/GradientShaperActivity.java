package com.gongsi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class GradientShaperActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SampleView(this));
        
        
    }
    
        private static class SampleView extends View {
        	private float mRotate;
        	private float dRotate;
        	private float hRotate;
        	private float sRotate;
        	
        	
        	private Matrix mMatrix = new Matrix();
        	
        	public SampleView(Context context){
        		super(context);
        		setFocusable(true);//表示view可以接受事件
        		update();
        	}

			@Override
			protected void onDraw(Canvas canvas) {
				// TODO Auto-generated method stub
				Paint shaderPaint = new Paint();
				
				
				canvas.save();
				LinearGradient lg0 = new LinearGradient(
						0, 0, 0, 100, Color.BLUE, Color.RED, Shader.TileMode.CLAMP);
				shaderPaint.setShader(lg0);
				canvas.drawRect(0, 0, 100, 100, shaderPaint);
				canvas.restore();
				
				canvas.save();
				canvas.translate(150, 0);//移动圆点坐标
				LinearGradient lg1 = new LinearGradient(
						0, 0, 0, 100, Color.CYAN, Color.BLUE, Shader.TileMode.CLAMP);
				shaderPaint.setShader(lg1);
				canvas.drawRect(0, 0, 100, 100, shaderPaint);
				canvas.restore();
				
				/*canvas.save();
				canvas.translate(0, 120);
				LinearGradient lg2 = new LinearGradient(
						40, 30, 100, 100, Color.BLUE, Color.RED, Shader.TileMode.MIRROR);
				shaderPaint.setShader(lg2);
				canvas.drawCircle(80, 80, 50, shaderPaint);
				canvas.restore();*/
				/*
				 *  渐变效果
				 */
				canvas.save();
				canvas.translate(150, 150);
				float x = 40;
				float y = 40;
				SweepGradient mShader = new SweepGradient(x, y, new int[]{
						Color.GREEN,
						Color.RED,
						Color.BLUE,
						Color.GREEN},
						null);
				shaderPaint.setShader(mShader);
				
//				mMatrix.setRotate(mRotate, x, y);
				/*
				 * 让Matrix围绕x，y走mrotate
				 * jiaodu xuanzhuan 
				 */
//				mShader.setLocalMatrix(mMatrix);
				/*
				 * 把matrix设置到Shader上
				 */
				
				mRotate += 16;
				if(mRotate >= 360){
					mRotate = 0;
				}
//				canvas.rotate(mRotate);//整体旋转
				canvas.drawCircle(x, y, 60, shaderPaint);
				canvas.restore();
				
				canvas.save();
				canvas.translate(190, 190);
				shaderPaint.setColor(Color.BLACK);
				
				canvas.rotate(sRotate);
				sRotate += 6;
				if(sRotate >= 360){
					sRotate = 0;
					dRotate+=6;
					if(dRotate>=360){
						sRotate=0;
						dRotate=0;
						hRotate+=15;
					}if(hRotate>=360){
						sRotate=0;
						dRotate=0;
						hRotate=0;
					}
				}
				canvas.drawLine(0, -50, 0, 0, shaderPaint);
				
				canvas.restore();
				
				//分
				canvas.save();
				canvas.translate(190, 190);
				shaderPaint.setColor(Color.BLACK);
				
				canvas.rotate(dRotate);
				canvas.drawLine(0, -60, 0, 0, shaderPaint);
				
				canvas.restore();
				
				//时
				canvas.save();
				canvas.translate(190, 190);
				shaderPaint.setColor(Color.BLACK);
				
				canvas.rotate(hRotate);
				canvas.drawLine(0, -30, 0, 0, shaderPaint);
				
				canvas.restore();
				
				
//				invalidate();
				/*
				 * 通知view重画 会调用onDraw
				 */
				
				super.onDraw(canvas);
			}
			
				public void update(){
					
				mRedrawHandler.sleep(1000);
				}
				
        	
			 private RefreshHandler mRedrawHandler = new RefreshHandler();

		    	class RefreshHandler extends Handler {

		    		@Override
		    		public void handleMessage(Message msg) {
		    			/* 这个update是每隔600ms来调用 */
		    			SampleView.this.update();
		    			SampleView.this.invalidate();
		    			/*
		    			 * validate合法 invalidate失效，请求系统重画，
		    			 * 系统在适当的时候就会调用 onDraw
		    			 * 这里的 this就是表示SnakeView本身。所以这里是
		    			 * 通知SnakeView的内容失效，所有SnakeView会在适当的时候来重画界面
		    			 */
		    		}
		    		/*
		    		 * 扩展有一个sleep作为handler的方法 */
		    		public void sleep(long delayMillis) {
		    			/*
		    			 * 删除之前未处理的消息
		    			 */
		    			this.removeMessages(0);
		    			/* obtainMessage(0) 得到空内容的Message */
		    			sendMessageDelayed(obtainMessage(0), delayMillis);
		    			/*
		    			 * 等delayMillis之后在发送消息 delayMillis = 600ms;
		    			 */
		    		}
		    	}; 
        	
        }
        
       
        
        
        
        
        
        
        
        
        
        
}