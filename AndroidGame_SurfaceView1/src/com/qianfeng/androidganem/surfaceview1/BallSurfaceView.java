package com.qianfeng.androidganem.surfaceview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BallSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {
	AndroidGame_SurfaceView1 sportActivity;// ���ø�SurfaceView������������
	private Ball ball;// С��
	SurfaceHolder holder;
	boolean running = false;
	Thread refreshThread;

	public BallSurfaceView(Context context) {
		super(context);
		this.sportActivity = (AndroidGame_SurfaceView1) context;
		/*
		 * ball����ʾλ�ã���Ļ���Ͻǿ�ʼ0��0.
		 */
		ball = new Ball(120, 50, this);
		holder = this.getHolder();
		/* this.�������һ��SurfaceView����
		 * getHolder�õ�SurfaceView�����ӵ����
		 * Ҳ����˵��������holder���Ǿ�����SurfaceViewһ��
		 * SurfaceHolder��SurfaceView�Ķ���Ĳ�������
		 * ÿһ��SurfaceView��һ��SurfaceHolder
		 *  */
		holder.addCallback(this);
		Log.v("BallSurfaceView", "BallSurfaceView ���캯��");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

//		if (canvas == null)
//			canvas = holder.lockCanvas();// ��������
		/** canvas�п���Ϊnull����Ϊ�����ͨ�߳�ֱ�ӵ���onDraw
		 * canvas����null�����������ͨ�̵߳���canvas����ʵ��ֵ�� 
		 * 
		 * SurfaceViewֻ��һ������ ͨ��SurfaceHolder
		 * ȡ�û���CanvasȻ��Ϳ��Ժ�֮ǰһ����Canvas�ϻ�
		 * */
		Paint p = new Paint();
		int c = p.getColor();
		p.setColor(Color.WHITE);// ���ñ�����ɫ
		if (canvas != null)
			canvas.drawRect(0, 0, sportActivity.screenWidth,
					sportActivity.screenHeight, p);
		p.setColor(c);
		ball.onDraw(canvas);
//		holder.unlockCanvasAndPost(canvas);// �ͷ���
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		/*
		 * ÿ�ο�߱仯������һ���仯��Ŀ�ߡ�
		 */
		Log.v("BallSurfaceView", "BallSurfaceView surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		running = true;
		refreshThread = new RefreshThread();
		refreshThread.start();
		/*
		 * start();ͬһ�߳���ֻ�ܵ���һ�Ρ�
		 * 
		 */
		Log.v("BallSurfaceView", "BallSurfaceView surfaceCreated");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		running = false;
		try {
			refreshThread.join();
			/*
			 * join �ȴ��߳̽���
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.v("BallSurfaceView", "BallSurfaceView surfaceDestroyed");
	}

	void update() {
		ball.move();
	}
	private class RefreshThread extends Thread {

		@Override
		public void run() {

			while (running) {
				Canvas canvas = null;
				try {
					update();
					if (canvas == null)
						canvas = holder.lockCanvas();
					// ��������
					onDraw(canvas);
					holder.unlockCanvasAndPost(canvas);
					// �ͷ���
					/* ���߳�ѭ��ÿ��һ���¼��ͻ����
					 * SurfaceView�е�onDraw���������Ҵ����
					 * canvasΪnull
					 *  */
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
