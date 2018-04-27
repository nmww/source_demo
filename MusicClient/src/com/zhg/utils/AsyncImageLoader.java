package com.zhg.utils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {
	private HashMap<String, SoftReference<Bitmap>> cacheBitmap;
	public AsyncImageLoader(){
		this.cacheBitmap = new HashMap<String, SoftReference<Bitmap>>();
	}
	
	public Bitmap loadImage(final String path, final CallBack callback){
		if(cacheBitmap.containsKey(path)){
			SoftReference<Bitmap> ref = cacheBitmap.get(path);
			if(ref.get() != null){
			return ref.get();
			}
			cacheBitmap.remove(path);
		}
		
		final Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch(msg.what){
				case 0:
					Bitmap bitmap = (Bitmap)msg.obj;
					callback.loaded(path, bitmap);
					break;
				}
			}
		};
		new Thread(){

			@Override
			public void run() {
				try {
					// TODO Auto-generated method stub
					byte[] bytes = HttpTool.getBytes(HttpTool.URL + "/" + path, null, HttpTool.GET);
					Bitmap bitmap = BitmapTool.getScaleBitmap(bytes);
					cacheBitmap.put(path, new SoftReference<Bitmap>(bitmap));
					
					Message msg = handler.obtainMessage(0, bitmap);
					handler.sendMessage(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}.start();
		
		
		return null;
	}
	
	//回调接口
	public interface CallBack{
		void loaded(String path, Bitmap bitmap);
	}
	
	
	
	
}
