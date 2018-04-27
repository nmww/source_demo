package com.company.popwindows;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopWindowsActivity extends Activity {// PopupWindow属于不阻塞的对话框，AlertDialog则是阻塞的。
	private Button bt_popupWindow1;
	private Button bt_popupWindow2;
	private Button bt_popupWindow3;
	private Button bt_popupWindow4;
	private TextView tv_showText;
	private PopupWindow popupWindow;
	private int screenWidth;
	private int screenHeight;
	private int dialgoWidth;
	private int dialgoheight;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initView();
	}

	/**
	 * 初始化控件和响应事件
	 */
	private void initView() {
		bt_popupWindow1 = (Button) findViewById(R.id.bt_PopupWindow1);
		bt_popupWindow2 = (Button) findViewById(R.id.bt_PopupWindow2);
		bt_popupWindow3 = (Button) findViewById(R.id.bt_PopupWindow3);
		bt_popupWindow4 = (Button) findViewById(R.id.bt_PopupWindow4);
		tv_showText = (TextView) findViewById(R.id.tv_showText);

		bt_popupWindow1.setOnClickListener(new ClickEvent());
		bt_popupWindow2.setOnClickListener(new ClickEvent());
		bt_popupWindow3.setOnClickListener(new ClickEvent());
		bt_popupWindow4.setOnClickListener(new ClickEvent());

	}

	/**
	 * 按钮点击事件处理
	 * 
	 * @author Kobi
	 */
	private class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			case R.id.bt_PopupWindow1: // 以自己为Anchor，不偏移
				getPopupWindow();
				popupWindow.showAsDropDown(v);//当前id视图的左下位置
				break;

			case R.id.bt_PopupWindow2: // 以自己为Anchor，偏移(screenWidth-dialgoWidth)/2,
										// 0)--按钮正下方
				getPopupWindow();
				popupWindow.showAsDropDown(v, (screenWidth - dialgoWidth) / 2,
						0);
				break;

			case R.id.bt_PopupWindow3: // 以屏幕中心为参照，不偏移
				getPopupWindow();
				popupWindow.showAtLocation(findViewById(R.id.layout),
						Gravity.CENTER, 0, 0);
				break;

			case R.id.bt_PopupWindow4: // 以屏幕左下角为参照，偏移(screenWidth-dialgoWidth)/2,
										// 0) --屏幕下方中央
				getPopupWindow();
				popupWindow.showAtLocation(findViewById(R.id.layout),
						Gravity.BOTTOM, 0, 0);
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 创建PopupWindow
	 */
	protected void initPopuptWindow() {
		// TODO Auto-generated method stub
		/**
		 * Inflate a new view hierarchy from the specified xml resource.
		 */
		View popupWindow_view = getLayoutInflater().inflate( // 获取自定义布局文件dialog.xml的视图
				R.layout.dialog, null, false);
		// false视图没有焦点,无法输入信息,点击其他按钮可以变换当前PopupWindow || true有焦点
		popupWindow = new PopupWindow(popupWindow_view, 200, 150, false);// 创建PopupWindow实例

		Button bt_ok = (Button) popupWindow_view.findViewById(R.id.bt_ok); // dialog.xml视图里面的控件
		Button bt_cancle = (Button) popupWindow_view
				.findViewById(R.id.bt_cancle);
		final EditText et_text = (EditText) popupWindow_view
				.findViewById(R.id.et_text);

		bt_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tv_showText.setText(et_text.getText()); // 在标签里显示内容
				popupWindow.dismiss(); // 对话框消失
			}
		});

		bt_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});

		/**
		 * Use this method to get the default Display object.
		 */
		// 获取屏幕和对话框各自高宽
		screenWidth = PopWindowsActivity.this.getWindowManager()
				.getDefaultDisplay().getWidth();
		screenHeight = PopWindowsActivity.this.getWindowManager()
				.getDefaultDisplay().getHeight();
		/**
		 * <p>Return this popup's width MeasureSpec</p>
		 */
		dialgoWidth = popupWindow.getWidth();
		dialgoheight = popupWindow.getHeight();

	}

	/*
	 * 获取PopupWindow实例
	 */
	private void getPopupWindow() {

		if (null != popupWindow) {
			popupWindow.dismiss();
			return;
		} else {
			initPopuptWindow();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("ActivityState", "onPause");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.e("ActivityState", "onResume");
	}

}
