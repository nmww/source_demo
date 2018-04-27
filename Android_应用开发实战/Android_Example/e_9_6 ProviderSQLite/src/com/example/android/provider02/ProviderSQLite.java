package com.example.android.provider02;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class ProviderSQLite extends Activity {
	OnClickListener listener_add = null;
	OnClickListener listener_update = null;
	OnClickListener listener_delete = null;
	OnClickListener listener_clear = null;
	Button button_add;
	Button button_update;
	Button button_delete;
	Button button_clear;
	//# DBConnection helper;
	public int id_this;
	public interface UserSchema {
			String TABLE_NAME = "Users";          //Table Name
			String ID = "_id";                    //ID
			String USER_NAME = "user_name";       //User Name
			String ADDRESS = "address";           //Address
			String TELEPHONE = "telephone";       //Phone Number
			String MAIL_ADDRESS = "mail_address"; //Mail Address
		}
	/** Called when the activity is first created. */
    //SQLiteTest������
	@Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ȡ��Content Provider Uri
        getIntent().setData(Uri.parse("content://com.example.android.provider02.testprovider02"));
        final Uri uri_test = getIntent().getData();  
        setContentView(R.layout.main);
        final EditText mEditText01 = (EditText)findViewById(R.id.EditText01);
		final EditText mEditText02 = (EditText)findViewById(R.id.EditText02);
		final EditText mEditText03 = (EditText)findViewById(R.id.EditText03);
		final EditText mEditText04 = (EditText)findViewById(R.id.EditText04);
		//�������ݿ�PhoneBookDB�ͱ�Table:Users
		//# helper = new DBConnection(this);
        //# final SQLiteDatabase db = helper.getWritableDatabase();	
        final String[] FROM = 
		{   
        	UserSchema.ID,
        	UserSchema.USER_NAME,
        	UserSchema.TELEPHONE,
        	UserSchema.ADDRESS, 
        	UserSchema.MAIL_ADDRESS
		};
        //ȡ���������ݵ�USER_NAME��������list[]��
        //# Cursor c = db.query(UserSchema.TABLE_NAME, new String[] {UserSchema.USER_NAME}, null, null, null, null, null);
        Cursor c = managedQuery(uri_test, new String[] {UserSchema.USER_NAME}, null, null, null);
        c.moveToFirst();
		CharSequence[] list = new CharSequence[c.getCount()];
		for (int i = 0; i < list.length; i++) {
				list[i] = c.getString(0);
				c.moveToNext();
		}
		c.close();
		//��ʾUSER_NAME��Spinner�����б�-spinner��
		Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
		spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list));
		//��Spinner�����б�-spinner��ѡ����ѯ���ݣ���ʾ���������ڻ�����
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String user_name = ((Spinner)parent).getSelectedItem().toString();
				//# Cursor c = db.query("Users", FROM , "user_name='" + user_name + "'", null, null, null, null);
				Cursor c = managedQuery(uri_test, FROM , "user_name='" + user_name + "'", null, null);
				c.moveToFirst();
				id_this = Integer.parseInt(c.getString(0));
				String user_name_this = c.getString(1);
				String telephone_this = c.getString(2);
				String address_this = c.getString(3);
				String mail_address_this = c.getString(4);
				c.close();
				mEditText01.setText(user_name_this);
				mEditText02.setText(telephone_this);
				mEditText03.setText(address_this);
				mEditText04.setText(mail_address_this);
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});    
        //����[Add]��ťʱ������һ������
        listener_add = new OnClickListener() {	
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(UserSchema.USER_NAME, mEditText01.getText().toString());
				values.put(UserSchema.TELEPHONE, mEditText02.getText().toString());
				values.put(UserSchema.ADDRESS, mEditText03.getText().toString());
				values.put(UserSchema.MAIL_ADDRESS, mEditText04.getText().toString());
				//# SQLiteDatabase db = helper.getWritableDatabase();
				//# db.insert(UserSchema.TABLE_NAME, null, values);
				//# db.close();
				getContentResolver().insert(uri_test, values);
				onCreate(savedInstanceState);
			}
		};
		//����[Update]��ťʱ������һ������
		listener_update = new OnClickListener() {
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put(UserSchema.USER_NAME, mEditText01.getText().toString());
				values.put(UserSchema.TELEPHONE, mEditText02.getText().toString());
				values.put(UserSchema.ADDRESS, mEditText03.getText().toString());
				values.put(UserSchema.MAIL_ADDRESS, mEditText04.getText().toString());
				String where = UserSchema.ID + " = " + id_this;
				//# SQLiteDatabase db = helper.getWritableDatabase();
				//# db.update(UserSchema.TABLE_NAME, values, where ,null);
				//# db.close();
				getContentResolver().update(uri_test, values, where, null);
				onCreate(savedInstanceState);
			}
		};
		//����[Delete]��ťʱ���h��һ������
		listener_delete = new OnClickListener() {
			public void onClick(View v) {
				String where = UserSchema.ID + " = " + id_this;
				//# SQLiteDatabase db = helper.getWritableDatabase();
				//# db.delete(UserSchema.TABLE_NAME, where ,null);
				//# db.close();
				getContentResolver().delete(uri_test, where, null);
				onCreate(savedInstanceState);
			}
		};
		//����[Clear]��ťʱ����ձ༭��
		listener_clear = new OnClickListener() {
			public void onClick(View v) {
				mEditText01.setText("");
				mEditText02.setText("");
				mEditText03.setText("");
				mEditText04.setText("");
			}
		};
		//�趨BUTTON0i,i=1,2,3,4��OnClickListener
		button_add = (Button)findViewById(R.id.Button01);
		button_add.setOnClickListener(listener_add);
		button_update = (Button)findViewById(R.id.Button02);
		button_update.setOnClickListener(listener_update);
		button_delete = (Button)findViewById(R.id.Button03);
		button_delete.setOnClickListener(listener_delete);	
		button_clear = (Button)findViewById(R.id.Button04);
		button_clear.setOnClickListener(listener_clear);	
    }
}