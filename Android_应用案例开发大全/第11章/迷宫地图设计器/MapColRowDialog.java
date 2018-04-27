package wyf;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapColRowDialog extends JFrame//生成行数和列数对话框
implements ActionListener
{
	JLabel jlRow=new JLabel("地图行数");//地图行数
	JLabel jlCol=new JLabel("地图列数");//地图列数
	JTextField jtfRow=new JTextField("20");//创建行数JTextField
	JTextField jtfCol=new JTextField("20");//创建行数JTextField
	
	JButton jbOk=new JButton("确定");//创建确定按钮
	
	public MapColRowDialog()//构造器
	{
		this.setTitle("3D重力球地图设计器");
		
		this.setLayout(null);//设置布局为空
		jlRow.setBounds(10,5,60,20);//设置位置
		this.add(jlRow);//添加jlRow
		jtfRow.setBounds(70,5,100,20);//设置位置
		this.add(jtfRow);//添加jtfRow
		
		jlCol.setBounds(10,30,60,20);//设置位置
		this.add(jlCol);//添加jlCol
		jtfCol.setBounds(70,30,100,20);//设置位置
		this.add(jtfCol);//添加jtfCol
		
		jbOk.setBounds(180,5,60,20);//设置位置
		this.add(jbOk);//添加jbOk
		jbOk.addActionListener(this);//添加监听器
		
		this.setBounds(440,320,300,100);//设置位置
		this.setVisible(true);//设置可见
		
	}
	
	public void actionPerformed(ActionEvent e)//监听处理代码
	{
		int row=Integer.parseInt(jtfRow.getText().trim());//获取行数
		int col=Integer.parseInt(jtfCol.getText().trim());//获取列数
		
		new MapDesigner(row,col);//创建MapDesigner对象
		this.dispose();//本窗口消失
	}
	
	public static void main(String args[])
	{
		new MapColRowDialog();//创建对象
	}   
}