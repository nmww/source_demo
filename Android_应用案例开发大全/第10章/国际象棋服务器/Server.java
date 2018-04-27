package com.bn.wlxq;
import java.io.*;
import java.net.*;

public class Server
{
	
	static int count=0;//�������
	static ServerAgent player1;//��һ�����
	static ServerAgent player2;//�ڶ������
	static ServerAgent currPlayer;//��ǰ���
	
	
	public static void main(String args[]) throws Exception
	{
		ServerSocket ss=new ServerSocket(9999);//�Զ˿ڽ��м���
		System.out.println("Listening on 9999...");
		while(true)
		{
			Socket sc=ss.accept();
			DataInputStream din=new DataInputStream(sc.getInputStream());//��ȡ���������
			DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
			
			if(count==0)
			{//������Ϊ�ڷ�
				dout.writeUTF("<#ACCEPT#>1");//����ǵ�һ���˽���,��ô���Է��ǵ�һ������Ϣ,
				System.out.println("<#ACCEPT#>1");
				player1=new ServerAgent(sc,din,dout);//һ���ͷ��˵Ĵ����߳�,
				player1.start();
				count++;
			}
			else if(count==1)//����ǵڶ����˽���,��ô�����ǵڶ����˲���Ϊ����
			{//������Ϊ�׷�
				dout.writeUTF("<#ACCEPT#>2");
				player2=new ServerAgent(sc,din,dout);
				player2.start();
				count++;
				
				//�������Ѿ����������,��ʼ�����ؽ�����Ϣ
				player1.dout.writeUTF("<#START#>");
				player2.dout.writeUTF("<#START#>");
				
				//��ڷ���������Ȩ
				player1.dout.writeUTF("<#PERMISIION#>");
				currPlayer=player1;
				
			}
			else if(count==2)//����ǵ������˽�����
			{
				dout.writeUTF("<#FULL#>");
				System.out.println("<#FULL#>");
				dout.close();
				din.close();
				sc.close();
			}
		}
	}
}