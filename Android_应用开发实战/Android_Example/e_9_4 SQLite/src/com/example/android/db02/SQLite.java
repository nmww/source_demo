package com.example.android.db02;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SQLite extends Activity {
//SQLite������	
	private DatabaseHelper helper;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//�������ݿ�StudentDB��Table:students,Table:courses
		helper = new DatabaseHelper(this);
		final SQLiteDatabase db = helper.getReadableDatabase();
		//��students��ȡ���������ݵ�student_no��������list[]��
		Cursor c = db.query("students", new String[] {"student_no"}, null, null, null, null, null);
		c.moveToFirst();
		CharSequence[] list = new CharSequence[c.getCount()];
		for (int i = 0; i < list.length; i++) {
			list[i] = c.getString(0);
			c.moveToNext();
		}
		c.close();
		//��ʾstudent_no��Spinner�����б�-spinner��
		Spinner spinner = (Spinner)findViewById(R.id.Spinner01);
		spinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, list));
		//��Spinner�����б�-spinner��ѡ����ѯ����-student_no
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String student_no = ((Spinner)parent).getSelectedItem().toString();
				//��students����student_no�ҵ�student_name
				Cursor c;
				c = db.query("students", new String[] {"student_name"}, "student_no='" + student_no + "'", null, null, null, null);
				c.moveToFirst();
				String student_this = c.getString(0);
				c.close();
				TextView textView = (TextView)findViewById(R.id.TextView01);
				textView.setText(student_this);
				//��courses����student_no�ҵ�����course_name
				c = db.query("courses", new String[] {"course_name"}, "student_no='" + student_no + "'", null, null, null, null);
				c.moveToFirst();
				CharSequence[] list = new CharSequence[c.getCount()];
				for (int i = 0; i < list.length; i++) {
					list[i] = c.getString(0);
					c.moveToNext();
				}
				c.close();
				ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(SQLite.this, android.R.layout.simple_list_item_1, list);
				ListView listView = (ListView)findViewById(R.id.ListView01);
				listView.setAdapter(adapter);
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}		
		});	
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		helper.close();
	}
	//SQLiteOpenHelper-�������ݿ�StudentDB��Table:students,Table:courses
	class DatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "StudentDB";
		private static final int DATABASE_VERSION = 1;
		//Table:students������
		private String[][] STUDENTS = {
			{"A-123","Macoto Lin"},
			{"B-456","Hatusko Ueno"},
			{"C-789","Wilson Lin"},
		};
		//Table:courses������
		private String[][] COURSES = {
			{"A-123","Japanese"},
			{"A-123","Computer"},
			{"A-123","English"},
			{"B-456","English"},
			{"B-456","Computer"},
			{"C-789","Computer"},
		};
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.beginTransaction();
			try {
				SQLiteStatement stmt;
				//SQL���for�������ݿ�ı�-table:students
				String sql01 = "create table students (student_no text primary key, student_name text not null);";
				//ִ��table:students SQL���
				db.execSQL(sql01);
				//����table:students����
				stmt = db.compileStatement("insert into students values (?, ?);");
				for (String[] studentname : STUDENTS) {
					stmt.bindString(1, studentname[0]);
					stmt.bindString(2, studentname[1]);
					stmt.executeInsert();
				}
				//SQL�Z��for�������ݿ�ı�-table:courses
				String sql02 = "create table courses (student_no text not null, course_name text not null);";
				//ִ��courses SQL���
				db.execSQL(sql02);
				//����table:courses����
				stmt = db.compileStatement("insert into courses values (?, ?);");
				for (String[] coursename : COURSES) {
					stmt.bindString(1, coursename[0]);
					stmt.bindString(2, coursename[1]);
					stmt.executeInsert();
				}
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}	
	}
}