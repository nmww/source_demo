package wyf;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class CodeFrame extends JFrame//���ɶ�ά���鴰��
{
	JTextArea jta=new JTextArea();//����JTextArea
	JScrollPane jsp=new JScrollPane(jta);//����JScrollPane
	
	public CodeFrame(String codeStr,String title)
	{
		this.setTitle(title);//���ñ���
		
		this.add(jsp);//���JScrollPane
		
		jta.setText(codeStr);//��JTextArea���������
		
		this.setBounds(100,100,400,300);//���ô�С
		this.setVisible(true);//���ÿɼ�
	}
	public static void main(String args[])
	{
		new MapColRowDialog();
	}
}