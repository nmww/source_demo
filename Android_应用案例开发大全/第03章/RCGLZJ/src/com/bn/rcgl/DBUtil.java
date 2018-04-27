package com.bn.rcgl;

import java.util.ArrayList;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import static com.bn.rcgl.Constant.*;
import static com.bn.rcgl.RcActivity.*;

public class DBUtil 
{
	static SQLiteDatabase sld;
	
	//============================���д����������ݿ�ķ���start==============================
	public static void loadType(RcActivity father)//���������ݿ��ж�ȡ����
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY
				);
			String sql="create table if not exists type(tno integer primary key,tname varchar2(20));";
			sld.execSQL(sql);
			Cursor cursor=sld.query("type", null, null, null, null, null, "tno");
			int count=cursor.getCount();
			if(count==0)//����ǵ�һ�����г����Զ�����3��ȱʡ����
			{
				for(int i=0;i<father.defultType.length;i++)
				{
					sql="insert into type values("+i+",'"+father.defultType[i]+"')";
					sld.execSQL(sql);
				}
				
				cursor=sld.query("type", null, null, null, null, null, "tno");
				count=cursor.getCount();
			}
			alType.clear();
			while(cursor.moveToNext())
			{
				alType.add(cursor.getString(1));
			}
			sld.close();
			cursor.close();
		}catch(Exception e)
		{
			Toast.makeText(father, "�������ݿ�򿪴�������"+e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	public static boolean insertType(RcActivity father,String newType)//�����������ݿ�
	{
		Cursor cursor=null;
		boolean duplicate=false;//false����û�����������ظ���true�������ظ�
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE				
				);
			cursor=sld.query("type", null, null, null, null, null, "tno");
			alType.clear();
			while(cursor.moveToNext())//�������ճ�ʱ�������ݿ������е��ճ̽��бȽϣ�����ظ������־λ��Ϊtrue
			{
				if(newType.equals(cursor.getString(1)))
				{
					duplicate=true;
				}
				alType.add(cursor.getString(1));
			}
			if(!duplicate)
			{ 
				alType.add(newType);
				String sql="delete from type";
				sld.execSQL(sql);
				for(int i=0;i<alType.size();i++)
				{
					sql="insert into type values("+i+",'"+alType.get(i)+"')";
					sld.execSQL(sql);
				}
				Toast.makeText(father, "�ɹ�������͡�"+newType+"����", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(father, "���������ظ���", Toast.LENGTH_SHORT).show();
			}
		}
		catch(Exception e)
		{
			Toast.makeText(father, "�������ݿ���´���"+e.toString(), Toast.LENGTH_LONG).show();
			return false;
		}
		finally
		{
			cursor.close();
			sld.close();
		}
		return true;
	}
	
	public static void deleteType(RcActivity father,String s)
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE				
				);
			String sql="delete from type where tname='"+s+"'";
			sld.execSQL(sql);
			Toast.makeText(father, "�ɹ�ɾ������", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "����ɾ������"+e.toString(), Toast.LENGTH_LONG).show();
		}
		finally
		{
			sld.close();
		}
	}
	
    public static ArrayList<String> getAllType(RcActivity father)//��������ճ̵����ͣ�����������û��Խ��ģ���������Ѿ���ɾ���������ݿ��д洢���ճ�ȴ�����õ�����
	{
		ArrayList<String> type=new ArrayList<String>();
		type=alType;//������д��ڵ����ͷ���type
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READONLY				
				);
			String sql="select distinct type from schedule;";//��Ϊ�洢�����ݿ��е��ճ̵����Ϳ��ܱ��û�ɾ��������Ҫ����һ��
			Cursor cursor=sld.rawQuery(sql,new String[]{});
			while(cursor.moveToNext())
			{
				if(!type.contains(cursor.getString(0)))
				{
					type.add(cursor.getString(0));
				}
			}
			sld.close();
			cursor.close();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "��ȡ���ʹ���"+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("exception!!",e.toString());
		}
		return type;
	}
	//============================���д����������ݿ�ķ���end==============================

	//============================���д����ճ����ݿ�ķ���start==============================
	public static void loadSchedule(RcActivity father)//���ճ����ݿ��ȡ�ճ�����
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY				
				);
			String sql="create table if not exists schedule(" +
															"sn integer primary key," +
															"date1 char(10)," +
															"time1 char(5)," +
															"date2 char(10)," +
															"time2 char(5)," +
															"title varchar2(40)," +
															"note varchar2(120)," +
															"type varchar2(20)," +
															"timeset boolean," +
															"alarmset boolean" +
															")";
			sld.execSQL(sql);
			Cursor cursor=sld.query("schedule", null, null, null, null, null, "date1 desc,time1 desc");//��datetime1������

			while(cursor.moveToNext())
			{
				int sn=cursor.getInt(0);
				String date1=cursor.getString(1);
				String time1=cursor.getString(2);
				String date2=cursor.getString(3);
				String time2=cursor.getString(4);
				String title=cursor.getString(5);
				String note=cursor.getString(6);
				String type=cursor.getString(7);
				String timeSet=cursor.getString(8);
				String alarmSet=cursor.getString(9);
				Schedule schTemp=new Schedule(sn,date1,time1,date2,time2,title,note,type,timeSet,alarmSet);
				alSch.add(schTemp);
				Log.d("schdata",""+cursor.getPosition()+":sn="+sn+":"+date1+","+time1+","+date2+","+timeSet);
			}
			sld.close();
			cursor.close();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "�ճ����ݿ�򿪴�������"+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("exception",e.toString());
		}
	}
	
	public static void insertSchedule(RcActivity father)//�����ճ�
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE				
				);
			String sql=father.schTemp.toInsertSql(father);
			sld.execSQL(sql);

			sld.close();

		}
		catch(Exception e)
		{
			Toast.makeText(father, "�ճ����ݿ���´���"+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("exception!!",e.toString());
		}
	}
	
	public static void updateSchedule(RcActivity father)//�����ճ�
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE				
				);
			String sql=father.schTemp.toUpdateSql(father);
			sld.execSQL(sql);
			sld.close();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "�ճ����ݿ���´���"+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("exception!!",e.toString());
		}
	}
	
	public static void deleteSchedule(RcActivity father)//ɾ���ճ�
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE				
				);
			int sn=father.schTemp.getSn();			
			String sql="delete from schedule where sn="+sn;
			sld.execSQL(sql);
			sld.close();
			Toast.makeText(father, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "�ճ�ɾ������"+e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	public static void deletePassedSchedule(RcActivity father)//ɾ�����й����ճ�
	{
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE				
				);
			String nowDate=getNowDateString();
			String nowTime=getNowTimeString();
			String sql="date1<'"+nowDate+"' or date1='"+nowDate+"' and time1<'"+nowTime+"'";
			sql="delete from schedule where date1<'"+nowDate+"' or date1='"+nowDate+"' and time1<'"+nowTime+"'";
			sld.execSQL(sql);
			sld.close();
			Toast.makeText(father, "�ɹ�ɾ�������ճ�", Toast.LENGTH_SHORT).show();
		}
		catch(Exception e)
		{
			Toast.makeText(father, "�ճ�ɾ������"+e.toString(), Toast.LENGTH_LONG).show();
			Log.d("error", e.toString());
		}
	}

	public static void searchSchedule(RcActivity father,ArrayList<String> allKindsType)//�����ճ�
	{
		ArrayList<Boolean> alSelectedType=father.alSelectedType;
		try
		{
			sld=SQLiteDatabase.openDatabase
				(
					"/data/data/com.bn.rcgl/myDb", 
					null, 
					SQLiteDatabase.OPEN_READONLY				
				);
			String[] args=new String[2];
			args[0]=father.rangeFrom;
			args[1]=father.rangeTo;
			String sql="select * from schedule where date1 between ? and ?";
			StringBuffer sbtmp=new StringBuffer();
			sbtmp.append(" and (type=");
			for(int i=0;i<alSelectedType.size();i++)
			{
				if(alSelectedType.get(i))
				{
					sbtmp.append("'");
					sbtmp.append(allKindsType.get(i));
					sbtmp.append("' or type=");
				}
			}
			String strSelectedType=sbtmp.toString();
			strSelectedType=strSelectedType.substring(0, strSelectedType.length()-9);//���ȥ�������" or type="
			sql+=strSelectedType+")";
			
			Log.d("search sql:", sql);
			
			Cursor cursor=sld.rawQuery(sql,args);
			Toast.makeText(father, "������"+cursor.getCount()+"���ճ�", Toast.LENGTH_SHORT).show();
			alSch.clear();
			while(cursor.moveToNext())
			{
				int sn=cursor.getInt(0);
				String date1=cursor.getString(1);
				String time1=cursor.getString(2);
				String date2=cursor.getString(3);
				String time2=cursor.getString(4);
				String title=cursor.getString(5);
				String note=cursor.getString(6);
				String type=cursor.getString(7);
				String timeSet=cursor.getString(8);
				String alarmSet=cursor.getString(9);
				Schedule schTemp=new Schedule(sn,date1,time1,date2,time2,title,note,type,timeSet,alarmSet);
				alSch.add(schTemp);
			}
			sld.close();
			cursor.close();
		}
		catch(Exception e)
		{
			Toast.makeText(father, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	//============================���д����ճ����ݿ�ķ���end==============================

	public static int getSNFromPrefs(RcActivity father)//��ȡpreferences������ճ�sn
	{
		SharedPreferences sp=father.getSharedPreferences("SN", MODE_PRIVATE);
		int sn=sp.getInt("sn",0);
		Editor editor=sp.edit();
		editor.putInt("sn", sn+1);
		editor.commit();
		return sn;
	}
}