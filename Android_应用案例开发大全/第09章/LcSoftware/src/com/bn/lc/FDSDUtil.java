package com.bn.lc;
import java.text.*;
public class FDSDUtil
{
	//������λС��
	public static String formatData(double d)
	{
		DecimalFormat myformat = new  DecimalFormat("0.00");
		return myformat.format(d);
	}
	//��������
	public static String formatDataInt(double d)
	{
		DecimalFormat myformat = new  DecimalFormat("0");
		return myformat.format(d);
	}
}