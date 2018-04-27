package com.company.imagephoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class ImageThumbnail {

	/**
	 * 计算缩放比例
	 * @param oldWidth
	 * @param oldHeight
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
    public static int reckonThumbnail(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        if ((oldHeight > newHeight && oldWidth > newWidth)
                || (oldHeight <= newHeight && oldWidth > newWidth)) {
            int be = (int) (oldWidth / (float) newWidth);
            if (be <= 1)
                be = 1;
            return be;
        } else if (oldHeight > newHeight && oldWidth <= newWidth) {
            int be = (int) (oldHeight / (float) newHeight);
            if (be <= 1)
                be = 1;
            return be;
        }

        return 1;
    }

    /**
     * @param photoPath --图片路径
     * @param aFile     --File流
     * @param newWidth  --新宽高
     * @param newHeight --
     */
    public static boolean bitmapToFile(String photoPath, File aFile, int newWidth, int newHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 解析图片路径为Bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        options.inJustDecodeBounds = false;
 
        //根据计算的缩放比例调整图片
        options.inSampleSize = reckonThumbnail(options.outWidth, options.outHeight, newWidth, newHeight);

        bitmap = BitmapFactory.decodeFile(photoPath, options);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] photoBytes = baos.toByteArray();

            if (aFile.exists()) {
                aFile.delete();
            }
            aFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(aFile);
            fos.write(photoBytes);
            fos.flush();
            fos.close();

            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            if (aFile.exists()) {
                aFile.delete();
            }
            Log.e("Bitmap To File Fail", e1.toString());
            return false;
        }
    }

    /**
     * 缩放图片
     * @param bmp
     * @param width
     * @param height
     * @return
     */
    public static Bitmap PicZoom(Bitmap bmp, int width, int height) {
        int bmpWidth = bmp.getWidth();
        int bmpHeght = bmp.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) width / bmpWidth, (float) height / bmpHeght);

        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeght, matrix, true);
    }
    /**
    * 设置图片为圆角
    * @param bitmap 闇€瑕佷慨鏀圭殑鍥剧墖   
    * @param pixels 鍦嗚鐨勫姬搴�   
    * @return 鍦嗚鍥剧墖   
    */    
   public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {    
     
       Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);    
       Canvas canvas = new Canvas(output);    
     
       final int color = 0xff424242;    
       final Paint paint = new Paint();    
       final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());    
       final RectF rectF = new RectF(rect);    
       final float roundPx = pixels;    
     
       paint.setAntiAlias(true);    
       canvas.drawARGB(0, 0, 0, 0);    
       paint.setColor(color);    
       canvas.drawRoundRect(rectF, roundPx, roundPx, paint);    
     
       paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));    
       canvas.drawBitmap(bitmap, rect, rect, paint);    
     
       return output;    
   }  
   
   /**
    * 鑾峰彇鍥剧墖鐨勫€掑奖 
    * @param bitmap
    * @return
    */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
				width, height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);
		return bitmapWithReflection;
	}
	
	/**
	 * 灏咲rawable杞寲涓築itmap 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
				.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}
	
	
}