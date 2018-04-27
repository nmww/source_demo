package com.bn.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.widget.Toast;

public class TextLoadUtil 
{
	//从指定的开始位置加载指定长度的内容字符串
	public static String readFragment(int begin,int len,String path)
	{
		String result=null;
		
		try
		{ 
			FileReader fr=new FileReader(path);	
			BufferedReader br=new BufferedReader(fr);
			br.skip(begin);
			char[] ca=new char[len];//读取并放入ca[]中
			br.read(ca);
			result=new String(ca);
			result=result.replaceAll("\\r\\n","\n");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static int getCharacterCount(String path)
	{
		int count = 0;
		try
		{
			FileReader fl = new FileReader(path);
			BufferedReader bf = new BufferedReader(fl);
			String content = null;
			while((content = bf.readLine()) != null)
			{
				count += content.length();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}
	 public static String loadFromSDFile(ReaderView readerView,int begin,int len,String fname)//读取APK中文件的工具类!!!!!!!!!!!!!!!!!!!!
	    {
	    	String result=null;    	
	    	try
	    	{
	    		InputStream in=readerView.getResources().getAssets().open(fname);
	    		InputStreamReader isr=new InputStreamReader(in); //将字节流转化为字符流
	    		BufferedReader br=new BufferedReader(isr);//用一个处理流增加一些功能
	    		br.skip(begin);
				char[] ca=new char[len];
				br.read(ca);
				result=new String(ca);
				result=result.replaceAll("\\r\\n","\n");
	    	}
	    	catch(Exception e)
	    	{
	    		Toast.makeText(readerView.getContext(), "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
	    	}    	
	    	return result;
	    }
	 public static int getCharacterCountApk(ReaderView readerView,String fname)
		{
			int count = 0;
			try
			{
				InputStream in=readerView.getResources().getAssets().open(fname);
		    		InputStreamReader isr=new InputStreamReader(in); //将字节流转化为字符流
		    		BufferedReader br=new BufferedReader(isr);//用一个处理流增加一些功能
					String content = null;
					while((content = br.readLine()) != null)
					{
						count += content.length();
					}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
				return count;
		}
}
