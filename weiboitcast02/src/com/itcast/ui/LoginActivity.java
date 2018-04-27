package com.itcast.ui;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itcast.db.TokenUtil;
import com.itcast.logic.MainService;
import com.itcast.logic.Task;
import com.itcast.logic.TaskType;
import com.itcast.logic.WeiboActivity;
import com.itcast.net.http.AccessToken;
import com.itcast.util.NetUtil;
import com.itcast.weibo.sina.User;

public class LoginActivity extends WeiboActivity {
	public ProgressDialog pd;
	public EditText etUser;
	public EditText etPass;
	public Button btLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login);
		View title = this.findViewById(R.id.title);
		Button btleft = (Button) title.findViewById(R.id.title_bt_left);
		btleft.setVisibility(View.GONE);
		Button btright = (Button) title.findViewById(R.id.title_bt_right);
		btright.setVisibility(View.GONE);
		TextView tv = (TextView) title.findViewById(R.id.textView);
		tv.setText("itCast΢���û���¼");
		etUser = (EditText) this.findViewById(R.id.user);
		etPass = (EditText) this.findViewById(R.id.password);
		btLogin = (Button) this.findViewById(R.id.loginButton);
		btLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HashMap hm = new HashMap();
				hm.put("us", etUser.getText().toString());
				hm.put("ps", etPass.getText().toString());

				Task ts = new Task(TaskType.TS_USER_LOGIN, hm);
				MainService.newTask(ts);
				if (pd == null) {
					pd = new ProgressDialog(LoginActivity.this);
					pd.setTitle("�û���¼");
				}
				pd.setMessage("���ڵ�¼���Ժ�....");
				pd.show();
			}
		});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void init() {
		if (!NetUtil.checkNet(this))// û������
		{
			MainService.alertNetError(this);

		} else {// ����ϵͳ����
			if (!MainService.isrun) {
				Intent it = new Intent(this, MainService.class);
				this.startService(it);
			}
			// �Ƿ��¼��
			AccessToken at = TokenUtil.readAccessToken(this);
			if (at != null) {
				MainService.weibo.setToken(at.getToken(), at.getTokenSecret());
				// ��ӵ�¼����
				Task ts = new Task(TaskType.TS_USER_LOGIN, null);
				MainService.newTask(ts);
				// ��ʾ�Զ���¼������
				if (pd == null) {
					pd = new ProgressDialog(this);
					pd.setTitle("��¼");
				}
				pd.setMessage("�����Զ���¼,���Ժ�...");
				pd.show();
			} else {

			}
		}
	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

		User u = (User) param[1];
		if (u != null) {
			pd.cancel();
			Toast.makeText(this, "��¼�ɹ���ӭ" + u.getName(), 1000).show();
			Intent it = new Intent(this, MainActivity.class);
			this.startActivity(it);
			finish();
		}
	}

}
