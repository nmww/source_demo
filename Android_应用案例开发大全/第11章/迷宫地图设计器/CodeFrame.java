package wyf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class CodeFrame extends JFrame//生成二维数组窗体
{
	JTextArea jta=new JTextArea();//创建JTextArea
	JScrollPane jsp=new JScrollPane(jta);//创建JScrollPane
	
	public CodeFrame(String codeStr,String title)
	{
		this.setTitle(title);//设置标题
		
		this.add(jsp);//添加JScrollPane
		
		jta.setText(codeStr);//向JTextArea中添加内容
		
		this.setBounds(100,100,400,300);//设置大小
		this.setVisible(true);//设置可见
	}
	public static void main(String args[])
	{
		new MapColRowDialog();
	}
}