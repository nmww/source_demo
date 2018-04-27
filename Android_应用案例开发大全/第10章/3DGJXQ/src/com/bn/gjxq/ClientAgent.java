package com.bn.gjxq;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class ClientAgent extends Thread
{
	GJXQActivity father;//Activity����
	Socket sc;
	DataInputStream din;//������
	DataOutputStream dout;//�����
	boolean flag=true;//�Ƿ�����ͷ����߳�
	int num;//��ǰ���
	boolean perFlag=false;//�Ƿ�Ϊ�Լ������־λ,trueΪ�Լ���,falseΪ�Է���
	
	public ClientAgent(GJXQActivity father,Socket sc,DataInputStream din,DataOutputStream dout)
	{
		this.father=father;//activity������
		this.sc=sc;//Socket����
		this.din=din;//������
		this.dout=dout;//�����
	}
	public void run()
	{
		while(flag)
		{
			try
			{
				String msg=din.readUTF();//�ȴ���Ϣ
				if(msg.startsWith("<#ACCEPT#>"))//����ǳɹ��ļ�������Ϸ,��ô����ȴ�����
				{
					String numStr=msg.substring(10);
					num=Integer.parseInt(numStr);//�õ���ǰ���Լ��Ľ�ɫ
					father.hd.sendEmptyMessage(0);//��ת���ȴ�������Ϣ	
				}
				else if(msg.startsWith("<#START#>"))//����ǵڶ�����ҽ���,��ô����3D����
				{
					father.hd.sendEmptyMessage(1);//������3d������Ϣ
				}
				else if(msg.startsWith("<#PERMISIION#>"))//ÿ���������Ӻ�鿴����Ƿ�����Ӯ�����
				{
					perFlag=true;
					if(father.msv!=null)
					{
						switch(GuiZe.isFinish(father.msv.currBoard))
						{
						  case BLACK_WIN:
							 dout.writeUTF("<#FINISH#>0");//����Ǻڷ�Ӯ��
						  break;
						  case WHITE_WIN:
							 dout.writeUTF("<#FINISH#>1");///����ǰ׷�Ӯ��
						  break;
						}
					}
				}
				else if(msg.startsWith("<#MOVE#>"))//�ƶ���־,
				{
					String temps=msg.substring(8);
					String[] sa=temps.split("\\,");
					int srcRow=Integer.parseInt(sa[0]);
	      			int srcCol=Integer.parseInt(sa[1]);
	      			int dstRow=Integer.parseInt(sa[2]);
	      			int dstCol=Integer.parseInt(sa[3]);
	      			
	      			ChessForControl[][] currBoard=father.msv.currBoard;//�õ����̵����ӵ�����
	      			currBoard[srcRow][srcCol].y=0;//�����ӵĸ߶���Ϊ��
	    			currBoard[srcRow][srcCol].row=dstRow;//����λ����Ϊ��λ��
	    			currBoard[srcRow][srcCol].col=dstCol;
	    			currBoard[dstRow][dstCol]=currBoard[srcRow][srcCol];//��������ָ��ԭ����λ�õĶ���
	    			currBoard[srcRow][srcCol]=null;//ԭ��λ�õ�����������Ϊ��
	    			father.playSound(1, 0);//�����ƶ�����
				}
				else if(msg.startsWith("<#FINISH#>"))//����ǽ��ܵ���Ӯ��Ϣ��.
				{
					father.msv=null;
					int pTemp=Integer.parseInt(msg.substring(10));
					if(pTemp==0&&this.num==1||pTemp==1&&this.num==2)//�����Ӯ��
					{
						father.hd.sendEmptyMessage(2);
					}
					else//�䷽
					{
						father.hd.sendEmptyMessage(3);
					}
					this.flag=false;
					this.din.close();//���������
					this.dout.close();//�ص������
					this.sc.close();//�ص���ص�sc					
				}
				else if(msg.startsWith("<#FULL#>"))
				{
					this.flag=false;
					this.din.close();//���������
					this.dout.close();//�ص������
					this.sc.close();//�ص���ص�sc	
					father.hd.sendEmptyMessage(7);
				}
				else if(msg.startsWith("<#EXIT#>"))
				{
					father.hd.sendEmptyMessage(4);
					this.flag=false;
					this.din.close();//���������
					this.dout.close();//�ص������
					this.sc.close();//�ص���ص�sc	
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	//������Ϣ
	public void sendMessage(String msg)
	{
		try 
		{
			dout.writeUTF(msg);
		} catch (IOException e) 
		{			
			e.printStackTrace();
		}
	}
}
