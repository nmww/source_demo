package com.zhg.test;

import java.util.HashSet;
import java.util.Iterator;

public class SetTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HashSet<String> set = new HashSet<String>();
		set.add("abc");
		set.add("abc");
		set.add("bcd");
		set.add("efg");
		
		set.remove("efg");
		System.out.println(set.contains("abc"));
		System.out.println(set.isEmpty());
		System.out.println(set.size());
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
}
