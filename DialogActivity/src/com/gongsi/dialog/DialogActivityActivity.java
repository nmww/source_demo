package com.gongsi.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class DialogActivityActivity extends Activity {
    /** Called when the activity is first created. */
	Dialog dialog = null;
	private Button btnNormal = null;
	private static final int DIALOG_NORMAL = 1;
	private static final int DIALOG_INPUT= 2;
	private static final int DIALOG_SINGLE= 3;/*单选*/
	private static final int DIALOG_MULT = 4;/*多项*/
	private static final int DIALOG_SELF = 5;/*自定义*/
	private static final int DIALOG_DATE = 6;
	private static final int DIALOG_TIME = 7;
	private static final int DIALOG_PROGRESS = 8; /*进度条*/
	private static final int DIALOG_CHAR = 9;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnNormal = (Button)findViewById(R.id.btn_normal);
        btnNormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				showDialog(DIALOG_SINGLE);
//				showDialog(DIALOG_DATE);
//				showDialog(DIALOG_TIME);
//				showDialog(DIALOG_PROGRESS);
//				showDialog(DIALOG_CHAR);
//				showDialog(DIALOG_SELF);
				showDialog(DIALOG_INPUT);
			}
		});
    }

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		switch(id){
		case DIALOG_NORMAL:
			dialog = builder.setTitle("标准对话框")
			.setMessage("标准对话框的文本内容")
			.setIcon(R.drawable.icon)
			.setPositiveButton("确定", null)
			.setNeutralButton("忽略", null)
			.setNegativeButton("取消", null)
			.create();
			break;
			
		case DIALOG_INPUT:
			dialog = builder.setTitle("输入框")
			.setIcon(R.drawable.icon)
			.setView(new EditText(this))
			.setPositiveButton("确定", null)
			.setNegativeButton("取消", null)
			.create();
			break;
			
		case DIALOG_SINGLE:
			dialog = builder.setTitle("单选对话框")
			.setIcon(R.drawable.icon)
			.setSingleChoiceItems(new String[]{"cho1","cho2","cho3"}, -1, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(DialogActivityActivity.this,
									"选中了第"+(which+1)+"项", Toast.LENGTH_SHORT).show();
//							dialog.dismiss();/*直接关闭了dialog*/
//							dismissDialog(DIALOG_SINGLE);
							
						}
					})
			  //添加一个确定按钮
            .setPositiveButton(" 确 定 ", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    
                }
            })
			.create();
			break;
		case DIALOG_MULT:
			dialog = builder.setTitle("多选框")
//			.setIcon(R.drawable.icon)
			.setIcon(null)
			.setMultiChoiceItems(new String[] {"item1","item2","item3"}, new boolean[]{true,false,false}, 
					new OnMultiChoiceClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which, boolean isChecked) {
							// TODO Auto-generated method stub
							Toast.makeText(DialogActivityActivity.this,
									"第"+(which+1)+"项"+isChecked, Toast.LENGTH_SHORT).show();
//							dialog.dismiss();
						}
					})
					.setPositiveButton("确定", null)
					.setNegativeButton("取消", null)
					.create();
			break;
			
		case DIALOG_SELF:
			View view = LayoutInflater.from(this).inflate(R.layout.dialogview, null);
			dialog = builder.setTitle("自定义对话框")
					.setIcon(R.drawable.icon)
					.setView(view)
					.setPositiveButton("确定", null)
					.setNegativeButton("取消", null)
					.create();
			break;
			
		case DIALOG_DATE:
			dialog = new DatePickerDialog(this,new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					String day = year + "年" + monthOfYear + "月" + dayOfMonth + "日";
					Toast.makeText(DialogActivityActivity.this, day, Toast.LENGTH_SHORT).show();
				}
			},2011,10,27);
			break;
			
		case DIALOG_TIME:
			dialog = new TimePickerDialog(this, new OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
					String time = hourOfDay + ":" + minute;
					Toast.makeText(DialogActivityActivity.this, time, Toast.LENGTH_SHORT).show();
				}
			}, 20, 33, true);
			break;
			
		case DIALOG_PROGRESS:
			dialog = new ProgressDialog(this);
			((ProgressDialog)dialog).setIndeterminate(false);/*false显示进度数字*/
			((ProgressDialog)dialog).setMax(100);
			((ProgressDialog)dialog).setIcon(R.drawable.icon);
			((ProgressDialog)dialog).setTitle("进度条");
			((ProgressDialog)dialog).setMessage("loadding......");
			((ProgressDialog)dialog).setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			new Thread(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i=1; i<100; i++){
						((ProgressDialog)dialog).setProgress(i);
						try {
							sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					dialog.dismiss();
				}
				
			}.start();
			break;
			
		case DIALOG_CHAR:
			dialog = new CharacterPickerDialog(this, new View(this), null, "123abc", false){

				@Override
				public void onItemClick(AdapterView parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Log.i("info", "onitemclick : " + position);
					dismiss();
				}

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.i("info", "onclick : " + ((Button)v).getText());
//					dismiss();
				}
				
			};
			break;
			
			
		}
		
		return dialog; 
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);
	}
    
    
    
    
    
    
    
    
    
}