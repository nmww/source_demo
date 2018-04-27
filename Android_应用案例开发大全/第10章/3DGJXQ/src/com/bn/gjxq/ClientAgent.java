package com.bn.gjxq;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class ClientAgent extends Thread
{
	GJXQActivity father;//Activity引用
	Socket sc;
	DataInputStream din;//输入流
	DataOutputStream dout;//输出流
	boolean flag=true;//是否结束客服端线程
	int num;//当前编号
	boolean perFlag=false;//是否为自己走棋标志位,true为自己走,false为对方走
	
	public ClientAgent(GJXQActivity father,Socket sc,DataInputStream din,DataOutputStream dout)
	{
		this.father=father;//activity的引用
		this.sc=sc;//Socket引用
		this.din=din;//输入流
		this.dout=dout;//输出流
	}
	public void run()
	{
		while(flag)
		{
			try
			{
				String msg=din.readUTF();//等待消息
				if(msg.startsWith("<#ACCEPT#>"))//如果是成功的加入了游戏,那么进入等待界面
				{
					String numStr=msg.substring(10);
					num=Integer.parseInt(numStr);//得到当前的自己的角色
					father.hd.sendEmptyMessage(0);//发转换等待界面消息	
				}
				else if(msg.startsWith("<#START#>"))//如果是第二个玩家进入,那么进入3D界面
				{
					father.hd.sendEmptyMessage(1);//发进入3d界面消息
				}
				else if(msg.startsWith("<#PERMISIION#>"))//每次下完棋子后查看玩家是否有输赢的情况
				{
					perFlag=true;
					if(father.msv!=null)
					{
						switch(GuiZe.isFinish(father.msv.currBoard))
						{
						  case BLACK_WIN:
							 dout.writeUTF("<#FINISH#>0");//如果是黑方赢了
						  break;
						  case WHITE_WIN:
							 dout.writeUTF("<#FINISH#>1");///如果是白方赢了
						  break;
						}
					}
				}
				else if(msg.startsWith("<#MOVE#>"))//移动标志,
				{
					String temps=msg.substring(8);
					String[] sa=temps.split("\\,");
					int srcRow=Integer.parseInt(sa[0]);
	      			int srcCol=Integer.parseInt(sa[1]);
	      			int dstRow=Integer.parseInt(sa[2]);
	      			int dstCol=Integer.parseInt(sa[3]);
	      			
	      			ChessForControl[][] currBoard=father.msv.currBoard;//拿到棋盘的棋子的引用
	      			currBoard[srcRow][srcCol].y=0;//将棋子的高度设为零
	    			currBoard[srcRow][srcCol].row=dstRow;//把其位置设为新位置
	    			currBoard[srcRow][srcCol].col=dstCol;
	    			currBoard[dstRow][dstCol]=currBoard[srcRow][srcCol];//把其引用指向原来的位置的对象
	    			currBoard[srcRow][srcCol]=null;//原来位置的数组引用舍为空
	    			father.playSound(1, 0);//播放移动声音
				}
				else if(msg.startsWith("<#FINISH#>"))//如果是接受到输赢信息了.
				{
					father.msv=null;
					int pTemp=Integer.parseInt(msg.substring(10));
					if(pTemp==0&&this.num==1||pTemp==1&&this.num==2)//如果是赢方
					{
						father.hd.sendEmptyMessage(2);
					}
					else//输方
					{
						father.hd.sendEmptyMessage(3);
					}
					this.flag=false;
					this.din.close();//光掉输入流
					this.dout.close();//关掉输出流
					this.sc.close();//关掉相关的sc					
				}
				else if(msg.startsWith("<#FULL#>"))
				{
					this.flag=false;
					this.din.close();//光掉输入流
					this.dout.close();//关掉输出流
					this.sc.close();//关掉相关的sc	
					father.hd.sendEmptyMessage(7);
				}
				else if(msg.startsWith("<#EXIT#>"))
				{
					father.hd.sendEmptyMessage(4);
					this.flag=false;
					this.din.close();//光掉输入流
					this.dout.close();//关掉输出流
					this.sc.close();//关掉相关的sc	
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	//发送消息
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
