package com.bn.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.widget.Toast;

public class TextLoadUtil 
{
	//��ָ���Ŀ�ʼλ�ü���ָ�����ȵ������ַ���
	public static String readFragment(int begin,int len,String path)
	{
		String result=null;
		
		try
		{ 
			FileReader fr=new FileReader(path);	
			BufferedReader br=new BufferedReader(fr);
			br.skip(begin);
			char[] ca=new char[len];//��ȡ������ca[]��
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
	 public static String loadFromSDFile(ReaderView readerView,int begin,int len,String fname)//��ȡAPK���ļ��Ĺ�����!!!!!!!!!!!!!!!!!!!!
	    {
	    	String result=null;    	
	    	try
	    	{
	    		InputStream in=readerView.getResources().getAssets().open(fname);
	    		InputStreamReader isr=new InputStreamReader(in); //���ֽ���ת��Ϊ�ַ���
	    		BufferedReader br=new BufferedReader(isr);//��һ������������һЩ����
	    		br.skip(begin);
				char[] ca=new char[len];
				br.read(ca);
				result=new String(ca);
				result=result.replaceAll("\\r\\n","\n");
	    	}
	    	catch(Exception e)
	    	{
	    		Toast.makeText(readerView.getContext(), "�Բ���û���ҵ�ָ���ļ���", Toast.LENGTH_SHORT).show();
	    	}    	
	    	return result;
	    }
	 public static int getCharacterCountApk(ReaderView readerView,String fname)
		{
			int count = 0;
			try
			{
				InputStream in=readerView.getResources().getAssets().open(fname);
		    		InputStreamReader isr=new InputStreamReader(in); //���ֽ���ת��Ϊ�ַ���
		    		BufferedReader br=new BufferedReader(isr);//��һ������������һЩ����
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
