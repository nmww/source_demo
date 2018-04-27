package com.bn.gjxq;
/**
 * �����ǹ�������Ĺ����࣬������ͨ������canMove����������ʼλ�������λ��
 */
enum Finish {NO_FINISH,BLACK_WIN,WHITE_WIN}
public class GuiZe
{	
	public static boolean canMove(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int toZ,int toX)//��,��
	{
		int row=chessforcontrol.row;//��ǰ�������
		int col=chessforcontrol.col;//��ǰ�������
		if(//�����λ�����Լ����ģ���ôֱ�Ӳ�����
			    currBoard[toZ][toX]!=null
				&&currBoard[toZ][toX].chessType>=(chessforcontrol.chessType/6)*6
				&&currBoard[toZ][toX].chessType<((chessforcontrol.chessType/6)+1)*6
		   )
			{
				return false;
			}
		switch(chessforcontrol.chessType)//���ݶ�������ͽ��з�֧�ж�
		{
		case 0://�ڳ�
		case 6://�׳�
			if((chessforcontrol.row==toZ&&!containHor(chessforcontrol,currBoard,toZ,toX))||
					(chessforcontrol.col==toX&&!containVer(chessforcontrol,currBoard,toZ,toX))
			  )
			{
				return true;
			}
			break;
		case 1:
		case 7://��ǰΪ��
		
			if(Math.abs(chessforcontrol.row-toZ)+Math.abs(chessforcontrol.col-toX)==3)
			{
				if(chessforcontrol.row==toZ||chessforcontrol.col==toX)
				{
					return false;
				}
				return true;
			}
			break;
		
		case 2:
		case 8://��ǰΪ��
			if(!containSlant(chessforcontrol,currBoard,toZ,toX))
			{
				return true;
			}
			break;
		case 3:
		case 9://��ǰΪ��
			if((chessforcontrol.row==toZ&&!containHor(chessforcontrol,currBoard,toZ,toX))||
					(chessforcontrol.col==toX&&!containVer(chessforcontrol,currBoard,toZ,toX))||
					(!containSlant(chessforcontrol,currBoard,toZ,toX))
			  )
			{
				return true;
			}
			break;
		case 4:
		case 10://��ǰΪ��
			if(Math.abs(toZ-row)<2&&Math.abs(toX-col)<2)
			{
				return true;
			}
           break;	
		case 5://�ڱ�		
			if(row+1<8&&currBoard[row+1][col]==null)//�ƶ�һ��
	  		{
	  			if(toZ==row+1&&toX==col)//Ŀ����Ӿ����������
	  			{
	  				chessforcontrol.isMoved=true;
	  				return true;
	  			}
	  			else if(row+2<8&&!chessforcontrol.isMoved//��û���ƶ���������������
	  					&&currBoard[row+2][col]==null//�ƶ�������Ǹ����ӱ���Ϊ��
	  					&&toZ==row+2&&toX==col)//Ŀ��λ��Ϊ��λ��
	  		  			{
	  				        chessforcontrol.isMoved=true;
	  		  				return true;
	  		  			}
	  		}
			if(row+1<8&&col+1<8&&currBoard[row+1][col+1]!=null//�����Ϊ��б�߳ԶԷ��ģ����Դ˸�϶�����Ϊ��
					&&currBoard[row+1][col+1].chessType>=((chessforcontrol.chessType/6+1)%2)*6
					&&currBoard[row+1][col+1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//�˸�Ҫ�ǶԷ����ӣ�
			{
				if(toZ==row+1&&toX==col+1)//Ŀ����Ӿ����������
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}
			if(row+1<8&&col-1>=0&&currBoard[row+1][col-1]!=null//�����Ϊ��б�߳ԶԷ��ģ����Դ˸�϶�����Ϊ��
					&&currBoard[row+1][col-1].chessType>=((chessforcontrol.chessType/6)+1)%2*6
					&&currBoard[row+1][col-1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//�˸�Ҫ�ǶԷ����ӣ�
			{
				if(toZ==row+1&&toX==col-1)//Ŀ����Ӿ����������
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}
			break;
		case 11://��ǰΪ�ױ�		
			if(row-1>0&&currBoard[row-1][col]==null)//�ƶ�һ��
	  		{
	  			if(toZ==row-1&&toX==col)//Ŀ����Ӿ����������
	  			{
	  				chessforcontrol.isMoved=true;
	  				return true;
	  			}
	  			else if(row-2>0&&!chessforcontrol.isMoved//��û���ƶ���������������
	  					&&currBoard[row-2][col]==null//�ƶ�������Ǹ����ӱ���Ϊ��
	  					&&toZ==row-2&&toX==col)//Ŀ��λ��Ϊ��λ��
	  		  			{
	  						chessforcontrol.isMoved=true;
	  		  				return true;
	  		  			}
	  		}
			if(row-1>0&&col+1<8&&currBoard[row-1][col+1]!=null//�����Ϊ����б�߳ԶԷ��ģ����Դ˸�϶�����Ϊ��
					&&currBoard[row-1][col+1].chessType>=((chessforcontrol.chessType/6)+1)%2*6
					&&currBoard[row-1][col+1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//�˸�Ҫ�ǶԷ����ӣ�
			{
				if(toZ==row-1&&toX==col+1)//Ŀ����Ӿ����������
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}
			if(row-1>0&&col-1>=0&&currBoard[row+1][col-1]!=null//�����Ϊ����б�߳ԶԷ��ģ����Դ˸�϶�����Ϊ��
					&&currBoard[row-1][col-1].chessType>=(((chessforcontrol.chessType/6)+1)%2)*6
					&&currBoard[row-1][col-1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//�˸�Ҫ�ǶԷ����ӣ�
			{
				if(toZ==row-1&&toX==col-1)//Ŀ����Ӿ����������
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}			
			break;
		}
		return false;
    }
	public static Finish isFinish(ChessForControl[][] currBoard)//�ж�ĳ���Ƿ�Ӯ��
	{
		boolean black=false;
		boolean white=false;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(currBoard[i][j]!=null)
				{
					if(currBoard[i][j].chessType==4)//�����ǰ�Ǻ���
					{
						black=true;
						
					}
					else if(currBoard[i][j].chessType==10)//�����ǰ�ǰ���
					{
						white=true;
					}
				}
			}
		}		
		if(!black)//����Ǻڷ�����
		{
			return Finish.WHITE_WIN;//���ذ׷�Ӯ
		}
		else if(!white)//����ǰ׷�����
		{
			return Finish.BLACK_WIN;//���غڷ�Ӯ
		}
		return Finish.NO_FINISH;//���򷵻�û����Ӯ
	}

//ˮƽ����֮�京������
public static boolean containHor(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int row,int col)
{
	if(col>chessforcontrol.col)//���Ŀ����ڵ�ǰ�����ұ�
	{
		for(int i=chessforcontrol.col+1;i<col;i++)
		{
			if(currBoard[row][i]!=null)//��ǰ�㲻Ϊ��
			{
				return true;//������
			}
		}
	}
	else//���Ŀ����ڵ�ǰ�������
	{
		for(int i=col+1;i<chessforcontrol.col;i++)
		{
			if(currBoard[row][i]!=null)//��ǰ�㲻Ϊ��
			{
				return true;//������
			}
		}
	}
	return false;
 }
//��ֱ�����ϻ�������
public static boolean containVer(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int row,int col)
{
	if(chessforcontrol.row<row)//Ŀ����ڵ�ǰ���·�
	{
		for(int i=chessforcontrol.row+1;i<row;i++)
		{
			if(currBoard[i][col]!=null)
			{
				return true;
			}
		}
	}
	else//Ŀ����ڵ�ǰ���Ϸ�
	{
		for(int i=row+1;i<chessforcontrol.row;i++)
		{
			if(currBoard[i][col]!=null)
			{
				return true;
			}
		}
	}
	
	return false;
}
//�ж�б�������Ƿ�������
public static boolean containSlant(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int row,int col)
{
	if(chessforcontrol.row-row+chessforcontrol.col-col==0)//Ʋ
	{
		if(chessforcontrol.col>col)//���Ŀ���λ���ڵ�ǰλ�����
		{
			for(int i=col+1;i<chessforcontrol.col;i++)
			{
				if(currBoard[row+col-i][i]!=null)
				{
					return true;
				}
			}
		}
		if(chessforcontrol.col<col)//���Ŀ����ڵ�ǰλ���ұ�
		{
			for(int i=chessforcontrol.col+1;i<col;i++)
			{
				if(currBoard[col+row-i][i]!=null)
				{
					return true;
				}
			}
		}
	}
	else if(chessforcontrol.col-chessforcontrol.row==col-row)//�����б����-----��
		{
			if(col<chessforcontrol.col)//���Ŀ����ڵ�ǰλ�����
			{
				for(int i=col+1;i<chessforcontrol.col;i++)
				{
					if(currBoard[row-col+i][i]!=null)
					{
						return true;
					}
				}
			}
			if(col>chessforcontrol.col)//���Ŀ����ڵ�ǰ���ұ�
			{
				for(int i=chessforcontrol.col+1;i<col;i++)
				{
					if(currBoard[row-col+i][i]!=null)//
					{
						return true;
					}
				}
			}
			
		}
	return false;
}
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	