package com.bn.gjxq;
/**
 * 该类是国际象棋的规则类，其他类通过调用canMove方法给出起始位置与结束位置
 */
enum Finish {NO_FINISH,BLACK_WIN,WHITE_WIN}
public class GuiZe
{	
	public static boolean canMove(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int toZ,int toX)//行,列
	{
		int row=chessforcontrol.row;//当前对象的行
		int col=chessforcontrol.col;//当前对象的列
		if(//如果该位置是自己方的，那么直接不可下
			    currBoard[toZ][toX]!=null
				&&currBoard[toZ][toX].chessType>=(chessforcontrol.chessType/6)*6
				&&currBoard[toZ][toX].chessType<((chessforcontrol.chessType/6)+1)*6
		   )
			{
				return false;
			}
		switch(chessforcontrol.chessType)//根据对象的类型进行分支判断
		{
		case 0://黑车
		case 6://白车
			if((chessforcontrol.row==toZ&&!containHor(chessforcontrol,currBoard,toZ,toX))||
					(chessforcontrol.col==toX&&!containVer(chessforcontrol,currBoard,toZ,toX))
			  )
			{
				return true;
			}
			break;
		case 1:
		case 7://当前为马
		
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
		case 8://当前为象
			if(!containSlant(chessforcontrol,currBoard,toZ,toX))
			{
				return true;
			}
			break;
		case 3:
		case 9://当前为后
			if((chessforcontrol.row==toZ&&!containHor(chessforcontrol,currBoard,toZ,toX))||
					(chessforcontrol.col==toX&&!containVer(chessforcontrol,currBoard,toZ,toX))||
					(!containSlant(chessforcontrol,currBoard,toZ,toX))
			  )
			{
				return true;
			}
			break;
		case 4:
		case 10://当前为王
			if(Math.abs(toZ-row)<2&&Math.abs(toX-col)<2)
			{
				return true;
			}
           break;	
		case 5://黑兵		
			if(row+1<8&&currBoard[row+1][col]==null)//移动一格，
	  		{
	  			if(toZ==row+1&&toX==col)//目标格子就是这个格子
	  			{
	  				chessforcontrol.isMoved=true;
	  				return true;
	  			}
	  			else if(row+2<8&&!chessforcontrol.isMoved//兵没有移动过，才能走两格
	  					&&currBoard[row+2][col]==null//移动两格的那个格子必须为空
	  					&&toZ==row+2&&toX==col)//目标位置为此位置
	  		  			{
	  				        chessforcontrol.isMoved=true;
	  		  				return true;
	  		  			}
	  		}
			if(row+1<8&&col+1<8&&currBoard[row+1][col+1]!=null//这个是为了斜走吃对方的，所以此格肯定不能为空
					&&currBoard[row+1][col+1].chessType>=((chessforcontrol.chessType/6+1)%2)*6
					&&currBoard[row+1][col+1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//此格要是对方棋子，
			{
				if(toZ==row+1&&toX==col+1)//目标格子就是这个格子
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}
			if(row+1<8&&col-1>=0&&currBoard[row+1][col-1]!=null//这个是为了斜走吃对方的，所以此格肯定不能为空
					&&currBoard[row+1][col-1].chessType>=((chessforcontrol.chessType/6)+1)%2*6
					&&currBoard[row+1][col-1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//此格要是对方棋子，
			{
				if(toZ==row+1&&toX==col-1)//目标格子就是这个格子
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}
			break;
		case 11://当前为白兵		
			if(row-1>0&&currBoard[row-1][col]==null)//移动一格，
	  		{
	  			if(toZ==row-1&&toX==col)//目标格子就是这个格子
	  			{
	  				chessforcontrol.isMoved=true;
	  				return true;
	  			}
	  			else if(row-2>0&&!chessforcontrol.isMoved//兵没有移动过，才能走两格
	  					&&currBoard[row-2][col]==null//移动两格的那个格子必须为空
	  					&&toZ==row-2&&toX==col)//目标位置为此位置
	  		  			{
	  						chessforcontrol.isMoved=true;
	  		  				return true;
	  		  			}
	  		}
			if(row-1>0&&col+1<8&&currBoard[row-1][col+1]!=null//这个是为了右斜走吃对方的，所以此格肯定不能为空
					&&currBoard[row-1][col+1].chessType>=((chessforcontrol.chessType/6)+1)%2*6
					&&currBoard[row-1][col+1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//此格要是对方棋子，
			{
				if(toZ==row-1&&toX==col+1)//目标格子就是这个格子
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}
			if(row-1>0&&col-1>=0&&currBoard[row+1][col-1]!=null//这个是为了左斜走吃对方的，所以此格肯定不能为空
					&&currBoard[row-1][col-1].chessType>=(((chessforcontrol.chessType/6)+1)%2)*6
					&&currBoard[row-1][col-1].chessType<(((chessforcontrol.chessType/6)+1)%2+1)*6)//此格要是对方棋子，
			{
				if(toZ==row-1&&toX==col-1)//目标格子就是这个格子
	  			{
					chessforcontrol.isMoved=true;
	  				return true;
	  			}
			}			
			break;
		}
		return false;
    }
	public static Finish isFinish(ChessForControl[][] currBoard)//判断某家是否赢了
	{
		boolean black=false;
		boolean white=false;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(currBoard[i][j]!=null)
				{
					if(currBoard[i][j].chessType==4)//如果当前是黑王
					{
						black=true;
						
					}
					else if(currBoard[i][j].chessType==10)//如果当前是白王
					{
						white=true;
					}
				}
			}
		}		
		if(!black)//如果是黑方输了
		{
			return Finish.WHITE_WIN;//返回白方赢
		}
		else if(!white)//如果是白方输了
		{
			return Finish.BLACK_WIN;//返回黑方赢
		}
		return Finish.NO_FINISH;//否则返回没有输赢
	}

//水平两点之间含有棋子
public static boolean containHor(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int row,int col)
{
	if(col>chessforcontrol.col)//如果目标点在当前对象右边
	{
		for(int i=chessforcontrol.col+1;i<col;i++)
		{
			if(currBoard[row][i]!=null)//当前点不为空
			{
				return true;//含有子
			}
		}
	}
	else//如果目标点在当前对象左边
	{
		for(int i=col+1;i<chessforcontrol.col;i++)
		{
			if(currBoard[row][i]!=null)//当前点不为空
			{
				return true;//含有子
			}
		}
	}
	return false;
 }
//垂直方向上还有棋子
public static boolean containVer(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int row,int col)
{
	if(chessforcontrol.row<row)//目标点在当前子下方
	{
		for(int i=chessforcontrol.row+1;i<row;i++)
		{
			if(currBoard[i][col]!=null)
			{
				return true;
			}
		}
	}
	else//目标点在当前子上方
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
//判断斜方向上是否含有棋子
public static boolean containSlant(ChessForControl chessforcontrol,ChessForControl[][] currBoard,int row,int col)
{
	if(chessforcontrol.row-row+chessforcontrol.col-col==0)//撇
	{
		if(chessforcontrol.col>col)//如果目标点位置在当前位置左边
		{
			for(int i=col+1;i<chessforcontrol.col;i++)
			{
				if(currBoard[row+col-i][i]!=null)
				{
					return true;
				}
			}
		}
		if(chessforcontrol.col<col)//如果目标点在当前位置右边
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
	else if(chessforcontrol.col-chessforcontrol.row==col-row)//如果在斜线上-----捺
		{
			if(col<chessforcontrol.col)//如果目标点在当前位置左边
			{
				for(int i=col+1;i<chessforcontrol.col;i++)
				{
					if(currBoard[row-col+i][i]!=null)
					{
						return true;
					}
				}
			}
			if(col>chessforcontrol.col)//如果目标点在当前点右边
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	