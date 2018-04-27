package com.example.android.widgets;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PopupActivity extends Activity {
	//PopupActivity主程序
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//5个警告互动对话框事件的选单名称
		CharSequence[] list = {
			"PopupWindow",
			"Dialog",
			"AlertDialog",
			"ProgressDialog",
			"Toast",
		};
		//将5个选单名称放置在ListView01
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//按下选单名称(position)，跳到相关的case处理
		listView.setOnItemClickListener(new OnItemClickListener() {		
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Button button = new Button(PopupActivity.this);
				switch (position) {
				//PopupWindow警告对话框
				case 0:
					button.setText("按下就可以关闭PopupWindow");
					final PopupWindow popupWindow = new PopupWindow(PopupActivity.this);
					popupWindow.setContentView(button);
					popupWindow.setFocusable(true);
					popupWindow.setWidth(200);
					popupWindow.setHeight(100);
					popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
					//按下对话窗口关闭PopupWindow窗口
					button.setOnClickListener(new OnClickListener() {				
						public void onClick(View v) {
							popupWindow.dismiss();
						}	
					});
					break;
				//Dialog警告对话框	
				case 1:
					button.setText("关闭Dialog");
					final Dialog dialog = new Dialog(PopupActivity.this);
					dialog.setTitle("这里可以用来显示Dialog信息!");
					dialog.setContentView(button);
					dialog.show();
					//按下对话框上的按钮来关闭Dialog
					button.setOnClickListener(new OnClickListener() {	
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					break;					
				//AlterDialog警告对话框	
				case 2:
					Builder builder = new Builder(PopupActivity.this);
					builder.setTitle("AlertDialog");
					builder.setMessage("这里可以用来显示Alert信息，要按[回上页]键才会关闭");
					builder.show();
					break;
				//ProgreeDialog警告对话框	
				case 3:
					final ProgressDialog progressDialog = ProgressDialog.show(PopupActivity.this, "处理中...", "请等一会，处理完毕会自动结束...");
					final Handler handler = new Handler();
					//建立处理程序callback
					final Runnable callback = new Runnable() {			
						public void run() {
							progressDialog.dismiss();
						}	
					};
					//建立一个Thread来Run，当处理进度完毕时，执行callback程式来关闭ProgreeDialog对话框
					Thread thread = new Thread() {				
						@Override
						public void run() {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							handler.post(callback);
						}		
					};
					thread.start();
					break;
				//Toast警告对话框	
				case 4:
					Toast.makeText(PopupActivity.this, "这里可以用来显示Toast信息，短时间出现后，自动离开", Toast.LENGTH_SHORT).show();
					break;
				}
			}			
		});		
	}
}
