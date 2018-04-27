package com.zhg.tes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.zhg.entity.student;

public class HashMap1 {
	public static void main(String[] args){
		HashMap<String, student> map = new HashMap<String, student>(); 
		map.put("zs", new student(1,"as",29));
		map.put("wd", new student(2,"hs",40));
		map.put("ls", new student(3,"ks",54));
		
		System.out.println(map.get("zs"));
		System.out.println("------------other--------------------");
		
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			//System.out.println(key);
			System.out.println(map.get(key));
		}
		System.out.println("------------other--------------------");
		Collection<student> c = map.values();
		Iterator<student> it1 = c.iterator();
		while(it1.hasNext()){
			System.out.println(it1.next());
		}
		
		
	}

}
