package com.bn.wlqp;
import java.io.*;
import java.net.*;
import static com.bn.wlqp.Server.*;

public class ServerAgent extends Thread
{
	Socket sc; //�����׽���
	DataInputStream din;
	DataOutputStream dout;
	boolean flag=true;
	int count=17;
	
	public ServerAgent(Socket sc,DataInputStream din,DataOutputStream dout)
	{		
		this.sc=sc;
		this.din=din;
		this.dout=dout;		
	}
	
	public void run()
	{
		while(flag)
		{
			try
			{
				String msg=din.readUTF();
				System.out.println(msg);
				if(msg.startsWith("<#PLAY#>"))
				{  //�ͻ��˷��Ʋ��Ҹ�֪������ ���ҷ������ж���һ�����Ƶ����
					String cards=msg.substring(8);
					ServerAgent next=null;
					String mTemp="<#CURR#>";
					if(currPlayer==player1)
					{
						mTemp=mTemp+"1";
						next=player2;
					}
					else if(currPlayer==player2)
					{
						mTemp=mTemp+"2";
						next=player3;
					}
					else if(currPlayer==player3)
					{
						mTemp=mTemp+"3";
						next=player1;
					}
					
					player1.dout.writeUTF(mTemp);
					player2.dout.writeUTF(mTemp);
					player3.dout.writeUTF(mTemp);
					
					mTemp="<#CARDS#>"+cards;//��һ����ҷ����Ƶ���Ϣ
					
					player1.dout.writeUTF(mTemp);
					player2.dout.writeUTF(mTemp);
					player3.dout.writeUTF(mTemp);
					
					next.dout.writeUTF("<#YOU#>");//��һ�������Ȩ
					currPlayer=next;
				}
				else if(msg.startsWith("<#COUNT#>"))
				{//ת��<#COUNT#>  ����Ҫ�����Ƶ��ƺź͵�ǰ��ҵı�־λ
					player1.dout.writeUTF(msg);
					player2.dout.writeUTF(msg);
					player3.dout.writeUTF(msg);
				}
				else if(msg.startsWith("<#I_WIN#>"))
				{//�յ�<#I_WIN#>(Ӯ��)��Ϣ  ������<#FINISH#>��Ϣ
					int currNumTemp=-1;
					if(this==player1)
					{
						currNumTemp=1;
					}
					else if(this==player2)
					{
						currNumTemp=2;
					}
					else if(this==player3)
					{
						currNumTemp=3;
					}
					
					Server.count=0;//��������¼���ӵ���������
					
					player1.dout.writeUTF("<#FINISH#>"+currNumTemp);
					player2.dout.writeUTF("<#FINISH#>"+currNumTemp);
					player3.dout.writeUTF("<#FINISH#>"+currNumTemp);
					
					//һ����Ϸ���� �رճ���������������߳� ���ҹر�����������������׽���
					player1.flag=false;
					player1.dout.close();
					player1.din.close();
					player1.sc.close();
					
					player2.flag=false;
					player2.dout.close();
					player2.din.close();
					player2.sc.close();
					
					player3.flag=false;
					player3.dout.close();
					player3.din.close();
					player3.sc.close();
				}
				else if(msg.startsWith("<#NO_PLAY#>"))
				{//�ͻ��˵��������ť֮�� ���������ж���һ�������˭
					ServerAgent next=null;
					if(currPlayer==player1)
					{
						next=player2;
					}
					else if(currPlayer==player2)
					{
						next=player3;
					}
					else if(currPlayer==player3)
					{
						next=player1;
					}
					
					next.dout.writeUTF("<#YOU#>");
					currPlayer=next;
				}
				else if(msg.startsWith("<#EXIT#>"))
				{//�յ��ͻ����˳���Ϣ ��������ص�����
					Server.count=0;
						
					player1.dout.writeUTF("<#EXIT#>");
					player2.dout.writeUTF("<#EXIT#>");
					player3.dout.writeUTF("<#EXIT#>");
					
					//�пͻ����˳� �رճ���������������߳� ���ҹر�����������������׽���
					player1.flag=false;
					player1.dout.close();
					player1.din.close();
					player1.sc.close();
					
					player2.flag=false;
					player2.dout.close();
					player2.din.close();
					player2.sc.close();
					
					player3.flag=false;
					player3.dout.close();
					player3.din.close();
					player3.sc.close();
				}
				else if(msg.startsWith("<#SCORE#>"))
				{
					scoresMsg[scoresIndex++]=msg;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}