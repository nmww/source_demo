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
        		setFocusable(true);//��ʾview���Խ����¼�
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
				canvas.translate(150, 0);//�ƶ�Բ������
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
				 *  ����Ч��
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
				 * ��MatrixΧ��x��y��mrotate
				 * jiaodu xuanzhuan 
				 */
//				mShader.setLocalMatrix(mMatrix);
				/*
				 * ��matrix���õ�Shader��
				 */
				
				mRotate += 16;
				if(mRotate >= 360){
					mRotate = 0;
				}
//				canvas.rotate(mRotate);//������ת
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
				
				//��
				canvas.save();
				canvas.translate(190, 190);
				shaderPaint.setColor(Color.BLACK);
				
				canvas.rotate(dRotate);
				canvas.drawLine(0, -60, 0, 0, shaderPaint);
				
				canvas.restore();
				
				//ʱ
				canvas.save();
				canvas.translate(190, 190);
				shaderPaint.setColor(Color.BLACK);
				
				canvas.rotate(hRotate);
				canvas.drawLine(0, -30, 0, 0, shaderPaint);
				
				canvas.restore();
				
				
//				invalidate();
				/*
				 * ֪ͨview�ػ� �����onDraw
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
		    			/* ���update��ÿ��600ms������ */
		    			SampleView.this.update();
		    			SampleView.this.invalidate();
		    			/*
		    			 * validate�Ϸ� invalidateʧЧ������ϵͳ�ػ���
		    			 * ϵͳ���ʵ���ʱ��ͻ���� onDraw
		    			 * ����� this���Ǳ�ʾSnakeView��������������
		    			 * ֪ͨSnakeView������ʧЧ������SnakeView�����ʵ���ʱ�����ػ�����
		    			 */
		    		}
		    		/*
		    		 * ��չ��һ��sleep��Ϊhandler�ķ��� */
		    		public void sleep(long delayMillis) {
		    			/*
		    			 * ɾ��֮ǰδ�������Ϣ
		    			 */
		    			this.removeMessages(0);
		    			/* obtainMessage(0) �õ������ݵ�Message */
		    			sendMessageDelayed(obtainMessage(0), delayMillis);
		    			/*
		    			 * ��delayMillis֮���ڷ�����Ϣ delayMillis = 600ms;
		    			 */
		    		}
		    	}; 
        	
        }
        
       
        
        
        
        
        
        
        
        
        
        
}