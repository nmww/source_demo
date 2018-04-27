package wyf;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapDesigner extends JFrame
implements ActionListener
{
	int row;//行数
	int col;//列数
	MapDesignPanel mdp;//声明MapDesignPanel的引用
	JScrollPane jsp;//声明JScrollPane的引用
	JButton jbGenerate=new JButton("生成地图");//生成地图按钮
	JButton jbGenerateD=new JButton("生成圆孔");//生成圆孔按钮
	JRadioButton jrBlack=new JRadioButton("墙",null,true);//墙单选按钮
	JRadioButton jrWhite=new JRadioButton("非墙",null,false);//白色单选按钮
	JRadioButton jrCrystal=new JRadioButton("圆孔",null,false);//圆孔单选按钮
	ButtonGroup bg=new ButtonGroup();//创建ButtonGroup
	Image icrystal;//圆孔图标
	JPanel jp=new JPanel();//创建JPanel面板
	
	public MapDesigner(int row,int col)
	{
		this.row=row;//设置行数
		this.col=col;//设置列数		
		this.setTitle("3D迷宫重力球地图设计器");//设置标题
		icrystal=new ImageIcon("img/Diamond.png").getImage();//圆孔的标志图	
		mdp=new MapDesignPanel(row,col,this);//创建地图设计器面板
		jsp=new JScrollPane(mdp);//创建JScrollPane面板
		
		this.add(jsp);//添加JScrollPane面板
		
		jp.add(jbGenerate);//添加生成地图按钮
		jp.add(jbGenerateD);//添加生成圆孔按钮
		jp.add(jrBlack);bg.add(jrBlack);//向jp中添加黑色单选按钮
		jp.add(jrWhite);bg.add(jrWhite);//向jp中添加白色单选按钮
		jp.add(jrCrystal);bg.add(jrCrystal);//向jp中添加圆孔单选按钮
		this.add(jp,BorderLayout.NORTH);//jp添加进窗体中
		jbGenerate.addActionListener(this);//生成地图按钮设置监听
		jbGenerateD.addActionListener(this);//生成圆孔按钮设置监听
		this.setBounds(10,10,800,600);//设置窗口大小
		this.setVisible(true);//设置可见
		this.mdp.requestFocus(true);//MapDesignPanel获取焦点	
	}
	
	public void actionPerformed(ActionEvent e)
	{		
	    if(e.getSource()==this.jbGenerate)//生成地图代码
	    {
	    	String s="public static final int[][] MAP=//0 可通过 1 不可通过\n{";
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.mapData[i][j]+",";
				}
				s=s.substring(0,s.length()-1)+"},";
			}
			s=s.substring(0,s.length()-1)+"\n};";
			
			new CodeFrame(s,"3D重力球游戏地图");			
	    }
	    else if(e.getSource()==this.jbGenerateD)
	    {//生成圆孔位置代码
	    	String s="public static final int[][] MAP_OBJECT=//表示可遇圆孔位置的矩阵\n{";
		
			
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.diamondMap[i][j]+",";
				}
				s=s.substring(0,s.length()-1)+"},";//去掉最后的逗号
			}
			s=s.substring(0,s.length()-1)+"\n};";
			new CodeFrame(s,"圆孔分布矩阵");
	    }
	}
	public static void main(String args[])
	{
		new MapColRowDialog();
	} 
}