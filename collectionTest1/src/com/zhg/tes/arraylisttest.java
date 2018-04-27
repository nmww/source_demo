package com.zhg.tes;

import java.util.ArrayList;
import java.util.Iterator;

import com.zhg.entity.student;

public class arraylisttest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		student s = new student();
		student s1 = new student(0, "ls", 30);
		student s2 = new student(2, "zs", 24);
		student s3 = new student(3, "ww", 34);
		student s4 = new student(5, "lt", 23);
		
		ArrayList<student> A = new ArrayList<student>(5);
		A.add(s1);
		A.add(s2);
		A.add(s3);
		A.add(s4);
		
		System.out.println(A.get(3));
		
		A.remove(s4);
		
//		int len = A.size();
//		for (int i=0; i<len; i++){
//			A.remove(A.get(0));
//		}
		
		Iterator<student> iterator = A.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		System.out.println("=============A.set(0)================");
		A.set(0, new student());
		System.out.println(A.get(0));
		
		System.out.println("=============(0)================");
		for (student stu : A){
			System.out.println(stu.getName()+" "+stu.getId());
			//System.out.println(stu.getId());
		}
		

	}

}
