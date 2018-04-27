package com.zhg.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.zhg.entity.Student;

public class HashMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String,Student> map = new HashMap<String,Student>();
		map.put("zs", new Student(1,"zs",19));
		map.put("ls", new Student(2,"ls",18));
		map.put("ls", new Student(3,"ls",20));
		
//		System.out.println(map.get("zs"));
//		map.remove("ls");
		
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			System.out.println(map.get(key));
		}
		
		Collection<Student> c = map.values();
		Iterator<Student> it1 = c.iterator();
		while(it1.hasNext()){
			System.out.println(it1.next());
		}
	}

}
