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
	AndroidGame_SurfaceView1 sportActivity;// 调用该SurfaceView的上下文引用
	private Ball ball;// 小球
	SurfaceHolder holder;
	boolean running = false;
	Thread refreshThread;

	public BallSurfaceView(Context context) {
		super(context);
		this.sportActivity = (AndroidGame_SurfaceView1) context;
		/*
		 * ball的启示位置，屏幕左上角开始0，0.
		 */
		ball = new Ball(120, 50, this);
		holder = this.getHolder();
		/* this.这里就是一个SurfaceView对象
		 * getHolder得到SurfaceView对象的拥有者
		 * 也就是说我们有了holder我们就有了SurfaceView一切
		 * SurfaceHolder是SurfaceView的对外的操作对象
		 * 每一个SurfaceView有一个SurfaceHolder
		 *  */
		holder.addCallback(this);
		Log.v("BallSurfaceView", "BallSurfaceView 构造函数");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

//		if (canvas == null)
//			canvas = holder.lockCanvas();// 锁定画布
		/** canvas有可能为null，因为如果普通线程直接调用onDraw
		 * canvas就是null，如果不是普通线程调用canvas是有实际值的 
		 * 
		 * SurfaceView只有一个画布 通过SurfaceHolder
		 * 取得画布Canvas然后就可以和之前一样在Canvas上画
		 * */
		Paint p = new Paint();
		int c = p.getColor();
		p.setColor(Color.WHITE);// 设置背景白色
		if (canvas != null)
			canvas.drawRect(0, 0, sportActivity.screenWidth,
					sportActivity.screenHeight, p);
		p.setColor(c);
		ball.onDraw(canvas);
//		holder.unlockCanvasAndPost(canvas);// 释放锁
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		/*
		 * 每次宽高变化，传入一个变化后的宽高。
		 */
		Log.v("BallSurfaceView", "BallSurfaceView surfaceChanged");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		running = true;
		refreshThread = new RefreshThread();
		refreshThread.start();
		/*
		 * start();同一线程中只能调用一次。
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
			 * join 等待线程结束
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
					// 锁定画布
					onDraw(canvas);
					holder.unlockCanvasAndPost(canvas);
					// 释放锁
					/* 该线程循环每隔一段事件就会调用
					 * SurfaceView中的onDraw函数，并且传入的
					 * canvas为null
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
