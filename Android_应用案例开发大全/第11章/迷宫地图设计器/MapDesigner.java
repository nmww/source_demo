package wyf;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MapDesigner extends JFrame
implements ActionListener
{
	int row;//����
	int col;//����
	MapDesignPanel mdp;//����MapDesignPanel������
	JScrollPane jsp;//����JScrollPane������
	JButton jbGenerate=new JButton("���ɵ�ͼ");//���ɵ�ͼ��ť
	JButton jbGenerateD=new JButton("����Բ��");//����Բ�װ�ť
	JRadioButton jrBlack=new JRadioButton("ǽ",null,true);//ǽ��ѡ��ť
	JRadioButton jrWhite=new JRadioButton("��ǽ",null,false);//��ɫ��ѡ��ť
	JRadioButton jrCrystal=new JRadioButton("Բ��",null,false);//Բ�׵�ѡ��ť
	ButtonGroup bg=new ButtonGroup();//����ButtonGroup
	Image icrystal;//Բ��ͼ��
	JPanel jp=new JPanel();//����JPanel���
	
	public MapDesigner(int row,int col)
	{
		this.row=row;//��������
		this.col=col;//��������		
		this.setTitle("3D�Թ��������ͼ�����");//���ñ���
		icrystal=new ImageIcon("img/Diamond.png").getImage();//Բ�׵ı�־ͼ	
		mdp=new MapDesignPanel(row,col,this);//������ͼ��������
		jsp=new JScrollPane(mdp);//����JScrollPane���
		
		this.add(jsp);//���JScrollPane���
		
		jp.add(jbGenerate);//������ɵ�ͼ��ť
		jp.add(jbGenerateD);//�������Բ�װ�ť
		jp.add(jrBlack);bg.add(jrBlack);//��jp����Ӻ�ɫ��ѡ��ť
		jp.add(jrWhite);bg.add(jrWhite);//��jp����Ӱ�ɫ��ѡ��ť
		jp.add(jrCrystal);bg.add(jrCrystal);//��jp�����Բ�׵�ѡ��ť
		this.add(jp,BorderLayout.NORTH);//jp��ӽ�������
		jbGenerate.addActionListener(this);//���ɵ�ͼ��ť���ü���
		jbGenerateD.addActionListener(this);//����Բ�װ�ť���ü���
		this.setBounds(10,10,800,600);//���ô��ڴ�С
		this.setVisible(true);//���ÿɼ�
		this.mdp.requestFocus(true);//MapDesignPanel��ȡ����	
	}
	
	public void actionPerformed(ActionEvent e)
	{		
	    if(e.getSource()==this.jbGenerate)//���ɵ�ͼ����
	    {
	    	String s="public static final int[][] MAP=//0 ��ͨ�� 1 ����ͨ��\n{";
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
			
			new CodeFrame(s,"3D��������Ϸ��ͼ");			
	    }
	    else if(e.getSource()==this.jbGenerateD)
	    {//����Բ��λ�ô���
	    	String s="public static final int[][] MAP_OBJECT=//��ʾ����Բ��λ�õľ���\n{";
		
			
			for(int i=0;i<mdp.row;i++)
			{
				s=s+"\n\t{";
				for(int j=0;j<mdp.col;j++)
				{
					s=s+mdp.diamondMap[i][j]+",";
				}
				s=s.substring(0,s.length()-1)+"},";//ȥ�����Ķ���
			}
			s=s.substring(0,s.length()-1)+"\n};";
			new CodeFrame(s,"Բ�׷ֲ�����");
	    }
	}
	public static void main(String args[])
	{
		new MapColRowDialog();
	} 
}