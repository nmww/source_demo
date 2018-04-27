package exe.matrix.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
	private ImageView iv1;
	private Button b1;
	private Button b2;
	private int displayWidth;
	private int displayHeight;
	private Bitmap bmp;
	private LinearLayout ll1;
	private LinearLayout ll2;
	float scaleWidth = 1.0f;
	float scaleHeight = 1.0f;
	int id = 0;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        iv1 = (ImageView) findViewById(R.id.imageView1);
        ll1 = (LinearLayout) findViewById(R.id.layout1);
        ll2 = (LinearLayout) findViewById(R.id.layout2);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        displayWidth = dm.widthPixels;
        displayHeight = dm.heightPixels-80;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		System.out.println(bmpWidth);
		System.out.println(bmpHeight);
        b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				big();
			}
		});
        b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				small();
			}
		});
    }

	protected void big() {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		double scale = 1.25;
		scaleWidth = (float) (scaleWidth*scale);
		scaleHeight = (float) (scaleHeight*scale);
		Bitmap resizeBmp = null;
		if(bmpWidth>0 && bmpHeight>0){
			
			Matrix matrix = new Matrix();
			matrix.postScale(scaleWidth, scaleHeight);
			Log.i(TAG, "bmpWidth = " + bmpWidth + ", bmpHeight = " + bmpHeight);   
			resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight,matrix,true);
		}else{
			Log.i(TAG, "WRONG");
		}
		
		if(id == 0){
			ll2.removeView(iv1);
		}else{
			ll2.removeView((ImageView)findViewById(id));
		}
		id++;
		ImageView imageView = new ImageView(MainActivity.this);
		imageView.setId(id);
		imageView.setImageBitmap(resizeBmp);
		ll2.addView(imageView);
		if(scaleWidth*scale*bmpWidth>displayWidth || scaleHeight*scale*bmpWidth>displayHeight){
			b2.setEnabled(false);
		}
	}

	protected void small() {
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		double scale = 0.8;
		scaleWidth = (float) (scaleWidth*scale);
		scaleHeight = (float) (scaleHeight*scale);
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBmp = Bitmap.createBitmap(bmp,0, 0, bmpWidth, bmpHeight,matrix,true);
		if(id == 0){
			ll2.removeView(iv1);
		}else{
			ll2.removeView((ImageView)findViewById(id));
		}
		id++;
		ImageView imageView = new ImageView(MainActivity.this);
		imageView.setId(id);
		imageView.setImageBitmap(resizeBmp);
		ll2.addView(imageView);
		Log.i(TAG, "imageView.getWidth() = " + imageView.getWidth()   
                + ", imageView.getHeight() = " + imageView.getHeight());   

		b2.setEnabled(true);
	}
}