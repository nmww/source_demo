package wyf;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapColRowDialog extends JFrame//���������������Ի���
implements ActionListener
{
	JLabel jlRow=new JLabel("��ͼ����");//��ͼ����
	JLabel jlCol=new JLabel("��ͼ����");//��ͼ����
	JTextField jtfRow=new JTextField("20");//��������JTextField
	JTextField jtfCol=new JTextField("20");//��������JTextField
	
	JButton jbOk=new JButton("ȷ��");//����ȷ����ť
	
	public MapColRowDialog()//������
	{
		this.setTitle("3D�������ͼ�����");
		
		this.setLayout(null);//���ò���Ϊ��
		jlRow.setBounds(10,5,60,20);//����λ��
		this.add(jlRow);//���jlRow
		jtfRow.setBounds(70,5,100,20);//����λ��
		this.add(jtfRow);//���jtfRow
		
		jlCol.setBounds(10,30,60,20);//����λ��
		this.add(jlCol);//���jlCol
		jtfCol.setBounds(70,30,100,20);//����λ��
		this.add(jtfCol);//���jtfCol
		
		jbOk.setBounds(180,5,60,20);//����λ��
		this.add(jbOk);//���jbOk
		jbOk.addActionListener(this);//��Ӽ�����
		
		this.setBounds(440,320,300,100);//����λ��
		this.setVisible(true);//���ÿɼ�
		
	}
	
	public void actionPerformed(ActionEvent e)//�����������
	{
		int row=Integer.parseInt(jtfRow.getText().trim());//��ȡ����
		int col=Integer.parseInt(jtfCol.getText().trim());//��ȡ����
		
		new MapDesigner(row,col);//����MapDesigner����
		this.dispose();//��������ʧ
	}
	
	public static void main(String args[])
	{
		new MapColRowDialog();//��������
	}   
}