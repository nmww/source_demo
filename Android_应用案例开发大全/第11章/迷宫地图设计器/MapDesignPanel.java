package wyf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapDesignPanel extends JPanel 
implements MouseListener,MouseMotionListener
{
	int row;//��������
	int col;//��������
	int span=32;//��λ���
	MapDesigner md;//����MapDesigner������
	
	int[][] mapData;//������ͼ��ά����
	
	int[][] diamondMap;//����Բ�׶�ά����
		
	public MapDesignPanel(int row,int col,MapDesigner md)
	{
		this.row=row;//���и�ֵ
		this.col=col;//���и�ֵ
		this.md=md;//��ȡMapDesigner����
		
		this.setPreferredSize//�������Ĵ�С
		(
			new Dimension(span*col,span*row)
		);
		
		mapData=new int[row][col];//������ͼ����
		diamondMap=new int[row][col];//����Բ������
	
		
		this.addMouseListener(this);//���������
		this.addMouseMotionListener(this);//�������ƶ�����
	}
	
	public void paint(Graphics g)//���û��Ʒ���
	{
		g.setColor(Color.BLACK);//���û�����ɫΪ��ɫ
		g.fillRect(0,0,span*col,span*row);//����������
		
		for(int i=0;i<mapData.length;i++)
		{
			for(int j=0;j<mapData[0].length;j++)
			{
				if(mapData[i][j]==0)
				{//���ư�ɫ����
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
				{//����Բ��		
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
		{//���õ�ͼ��ͨ����
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
			   		"Բ�������²��ܽ���ǽ��",
			   		"�ڷŴ���",
			   		JOptionPane.ERROR_MESSAGE
			   	);
			   	return;
			}
			mapData[rowC][colC]=md.jrBlack.isSelected()?1:0;
		}
		else if(md.jrCrystal.isSelected())
		{//�ڷ�Բ��
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
			   		"����ͨ�������ܰڷ�Բ�ף�",
			   		"�ڷŴ���",
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
			   		"Բ�������²��ܽ���ǽ��",
			   		"�ڷŴ���",
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