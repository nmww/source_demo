package com.company.imagephoto;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ImagePhotoActivity extends Activity {
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 1001;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 1002;
	private Bitmap bitMap; // 用来保存图片
	private boolean hasImage; // 是否已经选择了图片

	ImageView imageView;
	Button btn_image;
	Button btn_photo;
	EditText et_text;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		InitView();
	}

	public void InitView() {
		imageView = (ImageView) findViewById(R.id.imageView);
		et_text = (EditText) findViewById(R.id.et_text);
		btn_image = (Button) findViewById(R.id.btn_image);
		btn_image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SpannableString ss = new SpannableString("abc");
				Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				// 需要处理的文本，[smile]是需要被替代的文本
				SpannableString spannable = new SpannableString(et_text
						.getText().toString() + "[smile]");
				// 要让图片替代指定的文字就要用ImageSpan
				ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
				// 开始替换，注意第2和第3个参数表示从哪里开始替换到哪里替换结束（start和end）
				// 最后一个参数类似数学中的集合,[5,12)表示从5到12，包括5但不包括12
				spannable.setSpan(span, et_text.getText().length(), et_text
						.getText().length() + "[smile]".length(),
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				et_text.setText(spannable);
				// 浏览本地图片方法
				doSelectImageFromLoacal();
			}
		});

		btn_photo = (Button) findViewById(R.id.btn_photo);
		btn_photo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 调用系统照相机功能
				doTakePhoto();

				et_text.setText("");
			}
		});
	}

	/**
	 * 拍照获取图片
	 */

	protected void doTakePhoto() {
		try {
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 从本地手机中选择图片
	 */

	private void doSelectImageFromLoacal() {
		
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		Uri mUri = Uri.parse("file://" + "image/");
//		intent.setDataAndType(mUri, "image/*");
//		startActivity(intent);
		
		Intent localIntent = new Intent();
		localIntent.setType("image/*");
		localIntent.setAction("android.intent.action.GET_CONTENT");
		Intent localIntent2 = Intent.createChooser(localIntent, "选择图片");
		startActivityForResult(localIntent2, PHOTO_PICKED_WITH_DATA);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: // 从本地选择图片
			if (bitMap != null && !bitMap.isRecycled()) {
				bitMap.recycle();
			}
			Uri selectedImageUri = data.getData();

			if (selectedImageUri != null) {
				try {
					bitMap = BitmapFactory.decodeStream(getContentResolver()
							.openInputStream(selectedImageUri));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				// 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。有关图片的处理将重新写文章来介绍。
				int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(),
						bitMap.getHeight(), 500, 600);
				bitMap = ImageThumbnail.PicZoom(bitMap,
						(int) (bitMap.getWidth() / scale),
						(int) (bitMap.getHeight() / scale));
				 imageView.setImageBitmap(bitMap);
				 imageView.setVisibility(View.VISIBLE);

				hasImage = true;
			}
			break;
		case CAMERA_WITH_DATA: // 拍照
			Bundle bundle = data.getExtras();
			bitMap = (Bitmap) bundle.get("data");
			if (bitMap != null)
				bitMap.recycle();
			bitMap = (Bitmap) data.getExtras().get("data");
			int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(),
					bitMap.getHeight(), 500, 600);
			bitMap = ImageThumbnail.PicZoom(bitMap,
					(int) (bitMap.getWidth() / scale),
					(int) (bitMap.getHeight() / scale));
			
			 imageView.setImageBitmap(bitMap);
			 imageView.setVisibility(View.VISIBLE);
			hasImage = true;
			break;
		}
	}
}