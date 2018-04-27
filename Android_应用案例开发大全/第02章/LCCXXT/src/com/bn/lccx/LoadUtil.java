package com.bn.lccx;


import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoadUtil {
	
	public static SQLiteDatabase createOrOpenDatabase()//�������ݿ�
	{		
		SQLiteDatabase sld=null;
		try{
			sld=SQLiteDatabase.openDatabase//���Ӳ��������ݿ⣬����������򴴽�
			(
					"/data/data/com.bn.lccx/mydb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return sld;//���ظ�����
	}
	
	
	
	public static void createTable(String sql){//������
		SQLiteDatabase sld=createOrOpenDatabase();//�������ݿ�
		try{
			sld.execSQL(sql);//ִ��SQL���
			sld.close();//�ر�����
		}catch(Exception e){
			
			
		}		
	}
	
	public static boolean insert(String sql)//��������
	{
		SQLiteDatabase sld=createOrOpenDatabase();//�������ݿ�
		try{
			sld.execSQL(sql);
			
			sld.close();
			return true;
		}catch(Exception e){
			return false;			
		}
		
	}
	
	public  static Vector<Vector<String>>  query(String sql)//��ѯ
	{
		Vector<Vector<String>> vector=new Vector<Vector<String>>();//�½���Ų�ѯ���������
		SQLiteDatabase sld=createOrOpenDatabase();//�õ��������ݿ������
		
		
		try{			
			Cursor cur=sld.rawQuery(sql, new String[]{});//�õ������
			
			while(cur.moveToNext())//���������һ��
			{
				Vector<String> v=new Vector<String>();
				int col=cur.getColumnCount();		//�����������		
				for( int i=0;i<col;i++)
				{
					v.add(cur.getString(i));					
				}				
				vector.add(v);         		
			}
			cur.close();//�رս����
			sld.close();//�ر�����
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		return vector;
	}
	
		
	
	
	//����ĳ���ľ��������г�վ
	public static Vector<Vector<String>> getInfo(String tname)
	{
		//����ĳ�г������ĳ�վ
		String sql = "select Sname,Rarrivetime,Rstarttime "+
							"from station,"+
							"(select Sid,Rid,Rarrivetime,Rstarttime "+
							"from relation where Tid="+
							"(select Tid from train "+
							"where Tname='"+tname+"')) a "+
							"where a.Sid=station.Sid order by Rid";	
		//�õ�����Ҫ���վ
		Vector<Vector<String>> vtemp = query(sql);	
		
		return vtemp;
		
	}
	//վվ��ѯ
	public static Vector<Vector<String>> getSameVector(String start,String end)
	{
		//���ҳ���,ʼ��վ,�յ�վ�ͳ�����
		String sql = "select Tname,Tstartstation,Tterminus,Ttype "+
						"from train where Tid in "+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+start+"') "+
						"and Tid in "+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+end+"')))";
		//�õ��йػ���Ϣ��Vector
		Vector<Vector<String>> temp = query(sql);
		//���ҳ���վ�ͻ𳵿�����ʱ��
		String sql1 = "select Sname,Rstarttime from station,relation"+
						" where Sname='"+start+"' and "+
						"station.Sid=relation.Sid and "+
						"relation.Tid in "+
						"(select Tid from relation where Sid in"+
						"(select Sid from station where Sname='"+start+"') "+
						"and Tid in"+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+end+"')))";
		//�����յ�վ�ͻ𳵵�վʱ��
		String sql2 = "select Sname,Rarrivetime from station,relation"+
						" where Sname='"+end+"' and "+
						"station.Sid=relation.Sid and "+
						"relation.Tid in "+
						"(select Tid from relation where Sid in"+
						"(select Sid from station where Sname='"+start+"') "+
						"and Tid in"+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+end+"')))";
		//�õ��йػ�վ����Ϣ
		Vector<Vector<String>> temp1 = query(sql1);		
		Vector<Vector<String>> temp2 = query(sql2);			
		//����ѯ�����ϵ�һ��	
		temp = combine(temp,temp1,temp2);		
		return temp;		
	}
	
	//�������
	public static  Vector<Vector<String>> combine(Vector<Vector<String>> temp,Vector<Vector<String>> temp1,Vector<Vector<String>> temp2)
	{//��������Vector��ϳ�һ��	
	for(int i=0;i<temp.size();i++)
	{
		Vector<String> v1 = temp.get(i);
		if(i<temp1.size())
		{
			Vector<String> v2 = temp1.get(i);
			//��V2�����Ԫ�ؼӵ�V1����
			for(int j=0;j<v2.size();j++)
			{
				v1.add(v2.get(j));
			}				
		}
		else{
		//û�й�ϵʱ��ӿ�
			v1.add("");
			v1.add("");
		}	
	}
		for(int i=0;i<temp.size();i++)
		{
			Vector<String> v1 = temp.get(i);
			if(i<temp2.size())
			{
				Vector<String> v2 = temp2.get(i);
				//��V2�����Ԫ�ؼӵ�V1����
				for(int j=0;j<v2.size();j++)
				{
					v1.add(v2.get(j));
				}				
			}
			else
			{
				//û�й�ϵʱ��ӿ�
				v1.add("");
			}
		
		}
	return temp;
	}

	//ĳ���ε��������ʼվ��ĩβվ���г��͵�ʱ��
	public static Vector<Vector<String>> trainSearch(String tname)
	{//���β�ѯ		
		
		
		String sql =//���ҳ���,ʼ��վ,�յ�վ�ͳ�����
			"select Tname,Tstartstation,Tterminus,Ttype "+
									"from train where Tname='"+tname+"'";
								
		String sql1 = //���ҳ���վ�ͻ𳵿�����ʱ��	
			"select Tstartstation,Rstarttime from train,relation "+
								"where train.Tid=relation.Tid and "+
								"Tname='"+tname+"' and relation.Sid="+
								"(select Sid from station "+
								"where Sname=train.Tstartstation)";

		
		
		String sql2 = //�����յ�վ�ͻ𳵵�վʱ��Rarrivetime
			"select Tterminus,Rarrivetime from train,relation "+
								"where train.Tid=relation.Tid and "+
								"Tname='"+tname+"' and relation.Sid="+
								"(select Sid from station "+
								"where Sname=train.Tterminus)";
		
		Vector<Vector<String>> temp = query(sql);//�õ�����,ʼ��վ,�յ�վ�ͳ����͵�Vector
		
		Vector<Vector<String>> temp1 = query(sql1);//�õ�����վ�ͻ𳵿���ʱ���vector
				
		Vector<Vector<String>> temp2 = query(sql2);//�õ��յ�վ�ͻ𳵵�վʱ���vector
		temp = combine(temp,temp1,temp2);
		
		return temp;
	}
	
	public static Vector<Vector<String>> stationSearch(String station)////���ݳ�վ���ֲ�ѯ������վ�����г�
	{//��վ��ѯ	���õ�����ÿһ�����ĵ�վʱ��ͳ�սʱ��
		
		
		//��ѯ�йػ𳵵���Ϣ
		String sql = "select Tname,Tstartstation,Tterminus,Ttype "+
								"from train where Tid in "+
								"(select Tid from relation where Sid in "+
								"(select Sid from station where "+
								"Sname='"+station+"'))";
//		//��ѯ����վ������ʱ��
		String sql1 = "select '"+station+"',Rstarttime from relation "
								+"where Sid = "+	
								"(select Sid from station where "+
								"Sname='"+station+"')";
		

		
		String sql2 = "select '"+station+"',Rarrivetime from relation "
		+"where Sid = "+	
		"(select Sid from station where "+
		"Sname='"+station+"')";
		
		//�õ��й���Ϣ������
		Vector<Vector<String>> temp = query(sql);
		
		//�õ�����վ�ͻ𳵿���ʱ���vector
		Vector<Vector<String>> temp1 = query(sql1);
		
		//�õ��յ�վ�ͻ𳵵�վʱ���vector
		Vector<Vector<String>> temp2 = query(sql2);
		
		
		//������Vector�����һ��		
		temp = combine(temp,temp1,temp2);
		return temp;
	}
	public  static  Vector<Vector<String>>  Zjzquery(String start,String zjz,String end)//��ѯ����תվ��ѯ��
	{
		Vector<Vector<String>> vector=getSameVector(start,zjz);//���������Ȳ�����վ����תվ��Ȼ���ٲ����תվ���յ�վ���μ���
		Vector<Vector<String>> vector2=getSameVector(zjz,end);
		if(vector.size()==0||vector2.size()==0)//���ĳһ���޽������˵���޸���תվ�ĳ���
		{
			
			vector.clear();
			vector2.clear();//�������������
		}
		
		for(int i=0;i<vector2.size();i++)
		{
			vector.add(vector2.get(i));
		}//���򽫵ڶ��������е�������ӵ���һ��������
		
		return vector;//����
	}

	//��ѯ�����ı���ID�����ֵ
	public static int getInsertId(String name,String tid)
	{
		int id = 0;
		String sql = "select Max("+tid+") from "+name;		
			
			SQLiteDatabase sld=createOrOpenDatabase();
			
			
			try{			
				Cursor cur=sld.rawQuery(sql, new String[]{});
			
			//�鿴�����			
			if(cur.moveToNext())
			{
				id = cur.getInt(0);
			}
			//�رս����,��估����
			cur.close();
			sld.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		id++;
		return id;
	}

}
