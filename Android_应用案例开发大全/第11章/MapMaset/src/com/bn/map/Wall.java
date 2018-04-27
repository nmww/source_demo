package com.bn.map;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;
import static com.bn.map.GameSurfaceView.*;
import static com.bn.map.Constant.*;

//��ʾǽ����
public class Wall {
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//�����������ݻ���
    private FloatBuffer   mNormalBuffer;//���㷨�������ݻ���
    int vCount;//��������
    private int[][] indexFlag;//���ڼ�¼��ǰ���Ƿ�ɨ���   1 ��ʾ�˵㲻��Ҫ��ɨ��   0   ��ʾ�˵���Ҫɨ��
    
    public Wall()
    {
    	//�����������ݵĳ�ʼ��================begin============================
        int rows=MAP.length;
        int cols=MAP[0].length;
        indexFlag=new int[rows][cols];
        
        ArrayList<Float> alVertex=new ArrayList<Float>();
        ArrayList<Float> alNormal=new ArrayList<Float>();
        ArrayList<Float> alTexture=new ArrayList<Float>();
        
        for(int i=0;i<rows;i++)//��ɨ��
        {
        	for(int j=0;j<cols;j++)//��ɨ��
        	{//�Ե�ͼ�е�ÿһ����д���
        		if(MAP[i][j]==1)//��ǰ��Ϊǽ
        		{
        			int [][] area=returnMaxBlock(i,j);// area[0]��ʾ��ʼ��    �� ��  area[1]��ʾ��Ⱥ͸߶�
        			for(int k=area[0][0];k<area[0][0]+area[1][1];k++)//�������ڵ�ÿ�� ��    ����Χǽ
        			{
        				for(int t=area[0][1];t<area[0][1]+area[1][0];t++)
        				{
        					//���춥��ǽ
        					float xx1=t*UNIT_SIZE;       //    1
                			float y=FLOOR_Y+WALL_HEIGHT;
                			float zz1=k*UNIT_SIZE;
                			
                			float xx2=t*UNIT_SIZE;       //     2
                			float zz2=(k+1)*UNIT_SIZE;
                			
                			float xx3=(t+1)* UNIT_SIZE;    //   3
                			float zz3=(k+1)*UNIT_SIZE;
                			
                			float xx4=(t+1)*UNIT_SIZE;       //    4
                			float zz4=k*UNIT_SIZE;
                			//����������
                			alVertex.add(xx1);alVertex.add(y);alVertex.add(zz1);
            				alVertex.add(xx2);alVertex.add(y);alVertex.add(zz2);
            				alVertex.add(xx3);alVertex.add(y);alVertex.add(zz3);

            				alVertex.add(xx3);alVertex.add(y);alVertex.add(zz3);
            				alVertex.add(xx4);alVertex.add(y);alVertex.add(zz4);
            				alVertex.add(xx1);alVertex.add(y);alVertex.add(zz1);
            				
            				//�������   ����ƽ��
            				alTexture.add((float)((float)t/cols));alTexture.add((float)k/rows);
            				alTexture.add((float)((float)t/cols));alTexture.add((float)((float)(k+1)/rows));        				
            				alTexture.add((float)((float)(t+1)/cols));alTexture.add((float)((float)(k+1)/rows));
            				
            				alTexture.add((float)((float)(t+1)/cols));alTexture.add((float)((float)(k+1)/rows));
            				alTexture.add((float)((float)(t+1)/cols));alTexture.add((float)k/rows);
            				alTexture.add((float)((float)t/cols));alTexture.add((float)k/rows);
            				
            			

            				//��������
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);
            				alNormal.add(0f);alNormal.add(1f);alNormal.add(0f);

            				//����ǽ������
            				if(k==0||MAP[k-1][t]==0)
            				{
            					float x1=t*UNIT_SIZE;
                				float y1=FLOOR_Y;
                				float z1=k*UNIT_SIZE;   //  1
                				
                				float x2=t*UNIT_SIZE;
                				float y2=FLOOR_Y+WALL_HEIGHT;
                				float z2=k*UNIT_SIZE;    // 2
                				
                				float x3=(t+1)*UNIT_SIZE;
                				float y3=FLOOR_Y+WALL_HEIGHT;
                				float z3=k*UNIT_SIZE;    //  3
                				
                				float x4=(t+1)*UNIT_SIZE;
                				float y4=FLOOR_Y;
                				float z4=k*UNIT_SIZE;    //  4
                				//����������
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//��������
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)((float)1/rows));        				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)1/rows);
                                //��������
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(-1f);
            				}
            				//����ǽ������
            				if(k==rows-1||MAP[k+1][t]==0)
            				{
            					float x2=t*UNIT_SIZE;
                				float y2=FLOOR_Y;
                				float z2=(k+1)*UNIT_SIZE;
                				
                				float x1=t*UNIT_SIZE;
                				float y1=FLOOR_Y+WALL_HEIGHT;
                				float z1=(k+1)*UNIT_SIZE;
                				
                				float x4=(t+1)*UNIT_SIZE;
                				float y4=FLOOR_Y+WALL_HEIGHT;
                				float z4=(k+1)*UNIT_SIZE;
                				
                				float x3=(t+1)*UNIT_SIZE;
                				float y3=FLOOR_Y;
                				float z3=(k+1)*UNIT_SIZE;
                				//����������
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//��������
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)((float)1/rows));        				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add((float)((float)1/rows));
                				alTexture.add((float)((float)(t+1-area[0][1])/cols));alTexture.add(0f);
                				alTexture.add((float)((float)(t-area[0][1])/cols));alTexture.add((float)1/rows);
                				//��������
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
                				alNormal.add(0f);alNormal.add(0f);alNormal.add(1f);
            				}
            				//����ǽ������
            				if(t==0||MAP[k][t-1]==0)
            				{
            					float x2=t*UNIT_SIZE;
                				float y2=FLOOR_Y;
                				float z2=(k+1)*UNIT_SIZE;
                				
                				float x3=t*UNIT_SIZE;
                				float y3=FLOOR_Y+WALL_HEIGHT;
                				float z3=(k+1)*UNIT_SIZE;
                				
                				float x4=t*UNIT_SIZE;
                				float y4=FLOOR_Y+WALL_HEIGHT;
                				float z4=k*UNIT_SIZE;
                				
                				float x1=t*UNIT_SIZE;
                				float y1=FLOOR_Y;
                				float z1=k*UNIT_SIZE;
                				//����������
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//��������
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)((float)(k+1-area[0][0])/rows));        				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				alTexture.add((float)((float)1/cols));alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				//��������
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(-1f);alNormal.add(0f);alNormal.add(0f);
            				}
            				//����ǽ������
            				if(t==cols-1||MAP[k][t+1]==0)
            				{
            					float x3=(t+1)*UNIT_SIZE;
                				float y3=FLOOR_Y;
                				float z3=(k+1)*UNIT_SIZE;
                				
                				float x2=(t+1)*UNIT_SIZE;
                				float y2=FLOOR_Y+WALL_HEIGHT;
                				float z2=(k+1)*UNIT_SIZE;
                				
                				float x1=(t+1)*UNIT_SIZE;
                				float y1=FLOOR_Y+WALL_HEIGHT;
                				float z1=k*UNIT_SIZE;
                				
                				float x4=(t+1)*UNIT_SIZE;
                				float y4=FLOOR_Y;
                				float z4=k*UNIT_SIZE;
                				//����������
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				alVertex.add(x2);alVertex.add(y2);alVertex.add(z2);
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				
                				alVertex.add(x3);alVertex.add(y3);alVertex.add(z3);
                				alVertex.add(x4);alVertex.add(y4);alVertex.add(z4);
                				alVertex.add(x1);alVertex.add(y1);alVertex.add(z1);
                				//��������
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)((float)(k+1-area[0][0])/rows));        				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				
                				alTexture.add((float)((float)1/cols));alTexture.add((float)((float)(k+1-area[0][0])/rows));
                				alTexture.add((float)((float)1/cols));alTexture.add((float)(k-area[0][0])/rows);
                				alTexture.add(0f);alTexture.add((float)(k-area[0][0])/rows);
                				//��������
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
                				alNormal.add(1f);alNormal.add(0f);alNormal.add(0f);
            				}
        				}
        			}
        		}
        	}
        }
    	vCount=alVertex.size()/3;        
        float vertices[]=new float[alVertex.size()];
        for(int i=0;i<alVertex.size();i++)
        {
        	vertices[i]=alVertex.get(i);
        }
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //���㷨�������ݳ�ʼ��================begin============================
        float normals[]=new float[vCount*3];
        for(int i=0;i<vCount*3;i++)
        {
        	normals[i]=alNormal.get(i);
        }
        
        ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = nbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(normals);//�򻺳����з��붥����ɫ����
        mNormalBuffer.position(0);//���û�������ʼλ��
        //���㷨�������ݳ�ʼ��================end============================ 
        
        //�����������ݵĳ�ʼ��================begin============================
        float textures[]=new float[alTexture.size()];
        for(int i=0;i<alTexture.size();i++)
        {
        	textures[i]=alTexture.get(i);
        }
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
    }

    public void drawSelf(GL10 gl,int texId)
    {        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        
        //Ϊ����ָ�����㷨��������
        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0,
        		vCount 
        );
    }
    //���ݵ�ǰ��  ��ǰ�����Ϊǽ   �жϳ��˵���Χ�����������
    public int[][] returnMaxBlock(int rowIndex,int colIndex)
    {
    	int rowindex=rowIndex;
    	int colindex=colIndex;
    	int rowsize;//���ڼ�¼�еĴ�С
    	int colsize;//���ڼ�¼�еĴ�С
    
    	int [][] area=new int[2][2];//���ڼ�¼λ�� area[0]��ʾ��ʼ������  area[1]��ʾ��Ⱥ͸߶�
    	area[0][0]=rowIndex;area[0][1]=colIndex;
    	//���� �߶�Ϊ1
    	int tempRowSize=1;//�߶�
    	int tempColSize=1;//���
    	while(colindex+1<MAP[0].length&&MAP[rowindex][colindex+1]==1&&indexFlag[rowindex][colindex+1]==0)
    	{
    		tempColSize++;//��ȼ�һ
    		colindex++;//��������һ
    	}
    	rowsize=tempRowSize;colsize=tempColSize;
    	area[1][0]=colsize;area[1][1]=rowsize;//���ص�ǰ�����λ��
    	
    	//������Ϊ2
    	tempRowSize=0;tempColSize=0;rowindex=rowIndex;colindex=colIndex;//���ݳ�ʼ��
    	while(colindex+1<MAP[0].length&&rowindex+1<MAP.length&&MAP[rowindex][colindex]==1&&
    	   indexFlag[rowindex][colindex]==0&&MAP[rowindex+1][colindex]==1&&indexFlag[rowindex+1][colindex]==0)
    	{
    		colindex++;
    		tempRowSize=2;
    		tempColSize++;//������һ
    	}
    	if(tempColSize*tempRowSize>colsize)
    	{
    		rowsize=tempRowSize;colsize=tempColSize;
    		area[1][0]=colsize;area[1][1]=rowsize;//���ص�ǰ�����λ��
    	}
//    	����  ���Ϊ1
    	tempRowSize=1;tempColSize=1;rowindex=rowIndex;colindex=colIndex;//���ݳ�ʼ��
    	while(rowindex+1<MAP.length&&MAP[rowindex+1][colindex]==1&&indexFlag[rowindex+1][colindex]==0)
    	{
    		rowindex++;
    		tempRowSize++;
    	}
    	if(tempRowSize*tempColSize>rowsize*colsize)
    	{
    		rowsize=tempRowSize;colsize=tempColSize;
    		area[1][0]=1;area[1][1]=rowsize;//���ص�ǰ�����λ��
    	}
    	//������Ϊ2
    	tempRowSize=0;tempColSize=0;rowindex=rowIndex;colindex=colIndex;//���ݳ�ʼ��
    	while(colindex+1<MAP[0].length&&rowindex+1<MAP.length&&MAP[rowindex][colindex]==1&&
    	    	   indexFlag[rowindex][colindex]==0&&MAP[rowindex][colindex+1]==1&&indexFlag[rowindex][colindex+1]==0)
    	{
    	    rowindex++;
    	    tempColSize=2;
    	    tempRowSize++;//�߶ȼ�1
    	}
    	if(tempColSize*tempRowSize>colsize*rowsize)
    	{
    	    rowsize=tempRowSize;colsize=tempColSize;
    	    area[1][0]=colsize;area[1][1]=rowsize;//���ص�ǰ�����λ��
    	}
    	//��indexFlagɨ����ĸ�����Ϊ1
    	for(int i=area[0][0];i<area[0][0]+area[1][1];i++)
    	{
    		for(int j=area[0][1];j<area[0][1]+area[1][0];j++)
    		{
    			indexFlag[i][j]=1;//����ֵ����Ϊ    1     ��ʾ������ɨ��
    		}
    	}
    	return area;
    }
}
