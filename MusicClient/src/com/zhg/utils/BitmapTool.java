package com.zhg.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public class BitmapTool {
	public static Bitmap getScaleBitmap(byte[] bytes){
		/**
		 * 将图片的uri地址转换成字符串byte[] bytes类型传入
		 * 该方法自动返回一个缩放比例之后的Bitmap类型对象.
		 * 2011年12月26日16:54:27
		 */
		//实现图像的缩略
		Bitmap bitmap = null;
		BitmapFactory.Options ops = new Options();
		ops.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, ops);
		int scaleX = ops.outWidth / 64;
		int scaleY = ops.outHeight / 64;
		int scale = scaleX>scaleY?scaleX:scaleY;
		ops.inSampleSize = scale;
		ops.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, ops);
		return bitmap ;
	}
}
