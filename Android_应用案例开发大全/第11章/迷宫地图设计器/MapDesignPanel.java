package wyf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapDesignPanel extends JPanel 
implements MouseListener,MouseMotionListener
{
	int row;//声明行数
	int col;//声明列数
	int span=32;//单位间隔
	MapDesigner md;//声明MapDesigner的引用
	
	int[][] mapData;//声明地图二维数组
	
	int[][] diamondMap;//声明圆孔二维数组
		
	public MapDesignPanel(int row,int col,MapDesigner md)
	{
		this.row=row;//给行赋值
		this.col=col;//给列赋值
		this.md=md;//获取MapDesigner引用
		
		this.setPreferredSize//设置面板的大小
		(
			new Dimension(span*col,span*row)
		);
		
		mapData=new int[row][col];//创建地图数组
		diamondMap=new int[row][col];//创建圆孔数组
	
		
		this.addMouseListener(this);//添加鼠标监听
		this.addMouseMotionListener(this);//添加鼠标移动监听
	}
	
	public void paint(Graphics g)//设置绘制方法
	{
		g.setColor(Color.BLACK);//设置画笔颜色为黑色
		g.fillRect(0,0,span*col,span*row);//绘制填充矩形
		
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[0].length;j++)
			{
				if(mapData[i][j]==0)
				{//绘制白色格子
					g.setColor(Color.white);
					g.fillRect(j*span,i*span,span,span);
				}
			}
		}
		
		for(int i=0;i<diamondMap.length;i++)
		{
			for(int j=0;j<diamondMap[0].length;j++)
			{
				if(diamondMap[i][j]==1)
				{//绘制圆孔		
					g.drawImage(md.icrystal,j*span+1,i*span+3,this);
				}
			}
		}
		g.setColor(Color.green);		
		for(int i=0;i<row+1;i++)
		{
			g.drawLine(0,span*i,span*col,span*i);
		}
		
		for(int j=0;j<col+1;j++)
		{
			g.drawLine(span*j,0,span*j,span*row);
		}
	}	

	public void mouseClicked(MouseEvent e)
	{
		if(md.jrBlack.isSelected()||md.jrWhite.isSelected())
		{//设置地图可通过性
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			if(diamondMap[rowC][colC]==1&&md.jrBlack.isSelected())			
			{
				JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"圆孔区域下不能建造墙！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			mapData[rowC][colC]=md.jrBlack.isSelected()?1:0;
		}
		else if(md.jrCrystal.isSelected())
		{//摆放圆孔
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			if(mapData[rowC][colC]==1)
			{
			   	JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"不可通过处不能摆放圆孔！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			diamondMap[rowC][colC]=(diamondMap[rowC][colC]+1)%2;
		}
		this.repaint();
	}
	
	public void mousePressed(MouseEvent e)
	{
		
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public void mouseDragged(MouseEvent e)
	{
		if(md.jrBlack.isSelected()||md.jrWhite.isSelected())
		{
			int x=e.getX();
			int y=e.getY();
			
			int rowC=y/span;
			int colC=x/span;
			
			if(rowC>=row||colC>=col)
			{
				return;
			}
			if(diamondMap[rowC][colC]==1&&md.jrBlack.isSelected())			
			{
				JOptionPane.showMessageDialog
			   	(
			   		this,
			   		"圆孔区域下不能建造墙！",
			   		"摆放错误",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}	
			mapData[rowC][colC]=md.jrBlack.isSelected()?1:0;
		}		
		this.repaint();
	}
	
	public void mouseMoved(MouseEvent e){}
	public static void main(String args[])
	{
		new MapColRowDialog();
	}
}