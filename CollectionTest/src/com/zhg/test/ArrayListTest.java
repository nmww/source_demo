package com.zhg.test;

import java.util.ArrayList;
import java.util.Iterator;

import com.zhg.entity.Student;

public class ArrayListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Student s1 = new Student(1,"zs",19);
		Student s2 = new Student(2,"ls",20);
		Student s3 = new Student(3,"ww",18);
		Student s4 = new Student(4,"cl",17);
		Student s5 = new Student(5,"ss",21);
		
		ArrayList<Student> list = new ArrayList<Student>(6);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		list.add(s5);
		
		System.out.println(list.get(5));
		
//		int length = list.size();
//		for(int i=0;i<length;i++){
//			list.remove(0);
//		}
//		list.clear();
//		Iterator<Student> it = list.iterator();
//		while(it.hasNext()){
//			it.next();
//			it.remove();
//		}
		
		list.set(0, new Student());
		System.out.println(list.isEmpty());
		
		
		int len = list.size();
		for(int i=0;i<len;i++){
			System.out.println(list.get(i));
		}
		
		for(Student stu : list){
			System.out.println(stu.getName());
		}
		
		Iterator<Student> iterator = list.iterator();
		while(iterator.hasNext()){
			Student stu = iterator.next();
			System.out.println();
		}
		
		//以下几个方法都要求集合中的对象重写过equals
		if(list.contains(new Student(1,"zs",19))){
			System.out.println("包含");
		}else {
			System.out.println("不包含");
		}
		System.out.println(list.indexOf(new Student(1,"zs",19)));
		list.remove(new Student(1,"zs",19));
	}

}
