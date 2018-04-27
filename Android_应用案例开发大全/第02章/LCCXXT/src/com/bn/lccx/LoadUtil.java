package com.bn.lccx;


import java.util.Vector;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LoadUtil {
	
	public static SQLiteDatabase createOrOpenDatabase()//连接数据库
	{		
		SQLiteDatabase sld=null;
		try{
			sld=SQLiteDatabase.openDatabase//连接并创建数据库，如果不存在则创建
			(
					"/data/data/com.bn.lccx/mydb", 
					null, 
					SQLiteDatabase.OPEN_READWRITE|SQLiteDatabase.CREATE_IF_NECESSARY);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return sld;//返回该连接
	}
	
	
	
	public static void createTable(String sql){//创建表
		SQLiteDatabase sld=createOrOpenDatabase();//连接数据库
		try{
			sld.execSQL(sql);//执行SQL语句
			sld.close();//关闭连接
		}catch(Exception e){
			
			
		}		
	}
	
	public static boolean insert(String sql)//插入数据
	{
		SQLiteDatabase sld=createOrOpenDatabase();//连接数据库
		try{
			sld.execSQL(sql);
			
			sld.close();
			return true;
		}catch(Exception e){
			return false;			
		}
		
	}
	
	public  static Vector<Vector<String>>  query(String sql)//查询
	{
		Vector<Vector<String>> vector=new Vector<Vector<String>>();//新建存放查询结果的向量
		SQLiteDatabase sld=createOrOpenDatabase();//得到连接数据库的连接
		
		
		try{			
			Cursor cur=sld.rawQuery(sql, new String[]{});//得到结果集
			
			while(cur.moveToNext())//如果存在下一条
			{
				Vector<String> v=new Vector<String>();
				int col=cur.getColumnCount();		//将其放入向量		
				for( int i=0;i<col;i++)
				{
					v.add(cur.getString(i));					
				}				
				vector.add(v);         		
			}
			cur.close();//关闭结果集
			sld.close();//关闭连接
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
		return vector;
	}
	
		
	
	
	//查找某车的经过的所有车站
	public static Vector<Vector<String>> getInfo(String tname)
	{
		//查找某列车经过的车站
		String sql = "select Sname,Rarrivetime,Rstarttime "+
							"from station,"+
							"(select Sid,Rid,Rarrivetime,Rstarttime "+
							"from relation where Tid="+
							"(select Tid from train "+
							"where Tname='"+tname+"')) a "+
							"where a.Sid=station.Sid order by Rid";	
		//得到符合要求的站
		Vector<Vector<String>> vtemp = query(sql);	
		
		return vtemp;
		
	}
	//站站查询
	public static Vector<Vector<String>> getSameVector(String start,String end)
	{
		//查找车名,始发站,终点站和车类型
		String sql = "select Tname,Tstartstation,Tterminus,Ttype "+
						"from train where Tid in "+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+start+"') "+
						"and Tid in "+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+end+"')))";
		//得到有关火车信息的Vector
		Vector<Vector<String>> temp = query(sql);
		//查找出发站和火车开车的时间
		String sql1 = "select Sname,Rstarttime from station,relation"+
						" where Sname='"+start+"' and "+
						"station.Sid=relation.Sid and "+
						"relation.Tid in "+
						"(select Tid from relation where Sid in"+
						"(select Sid from station where Sname='"+start+"') "+
						"and Tid in"+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+end+"')))";
		//查找终点站和火车到站时间
		String sql2 = "select Sname,Rarrivetime from station,relation"+
						" where Sname='"+end+"' and "+
						"station.Sid=relation.Sid and "+
						"relation.Tid in "+
						"(select Tid from relation where Sid in"+
						"(select Sid from station where Sname='"+start+"') "+
						"and Tid in"+
						"(select Tid from relation where Sid in "+
						"(select Sid from station where Sname='"+end+"')))";
		//得到有关火车站的信息
		Vector<Vector<String>> temp1 = query(sql1);		
		Vector<Vector<String>> temp2 = query(sql2);			
		//将查询结果组合到一起	
		temp = combine(temp,temp1,temp2);		
		return temp;		
	}
	
	//组合向量
	public static  Vector<Vector<String>> combine(Vector<Vector<String>> temp,Vector<Vector<String>> temp1,Vector<Vector<String>> temp2)
	{//将这三个Vector组合成一个	
	for(int i=0;i<temp.size();i++)
	{
		Vector<String> v1 = temp.get(i);
		if(i<temp1.size())
		{
			Vector<String> v2 = temp1.get(i);
			//将V2里面的元素加到V1里面
			for(int j=0;j<v2.size();j++)
			{
				v1.add(v2.get(j));
			}				
		}
		else{
		//没有关系时添加空
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
				//将V2里面的元素加到V1里面
				for(int j=0;j<v2.size();j++)
				{
					v1.add(v2.get(j));
				}				
			}
			else
			{
				//没有关系时添加空
				v1.add("");
			}
		
		}
	return temp;
	}

	//某车次的情况，初始站和末尾站还有车型等时间
	public static Vector<Vector<String>> trainSearch(String tname)
	{//车次查询		
		
		
		String sql =//查找车名,始发站,终点站和车类型
			"select Tname,Tstartstation,Tterminus,Ttype "+
									"from train where Tname='"+tname+"'";
								
		String sql1 = //查找出发站和火车开车的时间	
			"select Tstartstation,Rstarttime from train,relation "+
								"where train.Tid=relation.Tid and "+
								"Tname='"+tname+"' and relation.Sid="+
								"(select Sid from station "+
								"where Sname=train.Tstartstation)";

		
		
		String sql2 = //查找终点站和火车到站时间Rarrivetime
			"select Tterminus,Rarrivetime from train,relation "+
								"where train.Tid=relation.Tid and "+
								"Tname='"+tname+"' and relation.Sid="+
								"(select Sid from station "+
								"where Sname=train.Tterminus)";
		
		Vector<Vector<String>> temp = query(sql);//得到车名,始发站,终点站和车类型的Vector
		
		Vector<Vector<String>> temp1 = query(sql1);//得到出发站和火车开车时间的vector
				
		Vector<Vector<String>> temp2 = query(sql2);//得到终点站和火车到站时间的vector
		temp = combine(temp,temp1,temp2);
		
		return temp;
	}
	
	public static Vector<Vector<String>> stationSearch(String station)////根据车站名字查询经过车站的所有车
	{//车站查询	，得到经过每一辆车的到站时间和出战时间
		
		
		//查询有关火车的信息
		String sql = "select Tname,Tstartstation,Tterminus,Ttype "+
								"from train where Tid in "+
								"(select Tid from relation where Sid in "+
								"(select Sid from station where "+
								"Sname='"+station+"'))";
//		//查询出发站及出发时间
		String sql1 = "select '"+station+"',Rstarttime from relation "
								+"where Sid = "+	
								"(select Sid from station where "+
								"Sname='"+station+"')";
		

		
		String sql2 = "select '"+station+"',Rarrivetime from relation "
		+"where Sid = "+	
		"(select Sid from station where "+
		"Sname='"+station+"')";
		
		//得到有关信息的向量
		Vector<Vector<String>> temp = query(sql);
		
		//得到出发站和火车开车时间的vector
		Vector<Vector<String>> temp1 = query(sql1);
		
		//得到终点站和火车到站时间的vector
		Vector<Vector<String>> temp2 = query(sql2);
		
		
		//将三个Vector组合在一起		
		temp = combine(temp,temp1,temp2);
		return temp;
	}
	public  static  Vector<Vector<String>>  Zjzquery(String start,String zjz,String end)//查询，中转站查询的
	{
		Vector<Vector<String>> vector=getSameVector(start,zjz);//分两步，先查出起点站到中转站，然后再查出中转站到终点站车次即可
		Vector<Vector<String>> vector2=getSameVector(zjz,end);
		if(vector.size()==0||vector2.size()==0)//如果某一个无结果，则说明无该中转站的车次
		{
			
			vector.clear();
			vector2.clear();//清空向量中数据
		}
		
		for(int i=0;i<vector2.size();i++)
		{
			vector.add(vector2.get(i));
		}//否则将第二个向量中的数据添加到第一个向量中
		
		return vector;//返回
	}

	//查询其插入的表项ID的最大值
	public static int getInsertId(String name,String tid)
	{
		int id = 0;
		String sql = "select Max("+tid+") from "+name;		
			
			SQLiteDatabase sld=createOrOpenDatabase();
			
			
			try{			
				Cursor cur=sld.rawQuery(sql, new String[]{});
			
			//查看结果集			
			if(cur.moveToNext())
			{
				id = cur.getInt(0);
			}
			//关闭结果集,语句及连接
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
