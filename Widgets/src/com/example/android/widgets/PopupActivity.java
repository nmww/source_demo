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
	//PopupActivity������
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//5�����滥���Ի����¼���ѡ������
		CharSequence[] list = {
			"PopupWindow",
			"Dialog",
			"AlertDialog",
			"ProgressDialog",
			"Toast",
		};
		//��5��ѡ�����Ʒ�����ListView01
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, list);
		ListView listView = (ListView)findViewById(R.id.ListView01);
		listView.setAdapter(adapter);
		//����ѡ������(position)��������ص�case����
		listView.setOnItemClickListener(new OnItemClickListener() {		
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Button button = new Button(PopupActivity.this);
				switch (position) {
				//PopupWindow����Ի���
				case 0:
					button.setText("���¾Ϳ��Թر�PopupWindow");
					final PopupWindow popupWindow = new PopupWindow(PopupActivity.this);
					popupWindow.setContentView(button);
					popupWindow.setFocusable(true);
					popupWindow.setWidth(200);
					popupWindow.setHeight(100);
					popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
					//���¶Ի����ڹر�PopupWindow����
					button.setOnClickListener(new OnClickListener() {				
						public void onClick(View v) {
							popupWindow.dismiss();
						}	
					});
					break;
				//Dialog����Ի���	
				case 1:
					button.setText("�ر�Dialog");
					final Dialog dialog = new Dialog(PopupActivity.this);
					dialog.setTitle("�������������ʾDialog��Ϣ!");
					dialog.setContentView(button);
					dialog.show();
					//���¶Ի����ϵİ�ť���ر�Dialog
					button.setOnClickListener(new OnClickListener() {	
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					break;					
				//AlterDialog����Ի���	
				case 2:
					Builder builder = new Builder(PopupActivity.this);
					builder.setTitle("AlertDialog");
					builder.setMessage("�������������ʾAlert��Ϣ��Ҫ��[����ҳ]���Ż�ر�");
					builder.show();
					break;
				//ProgreeDialog����Ի���	
				case 3:
					final ProgressDialog progressDialog = ProgressDialog.show(PopupActivity.this, "������...", "���һ�ᣬ������ϻ��Զ�����...");
					final Handler handler = new Handler();
					//�����������callback
					final Runnable callback = new Runnable() {			
						public void run() {
							progressDialog.dismiss();
						}	
					};
					//����һ��Thread��Run��������������ʱ��ִ��callback��ʽ���ر�ProgreeDialog�Ի���
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
				//Toast����Ի���	
				case 4:
					Toast.makeText(PopupActivity.this, "�������������ʾToast��Ϣ����ʱ����ֺ��Զ��뿪", Toast.LENGTH_SHORT).show();
					break;
				}
			}			
		});		
	}
}
