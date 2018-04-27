package com.zhg.tes;

import java.util.HashSet;
import java.util.Iterator;

public class Test1 {
	public static void main(String[] args){
		HashSet<String> it = new HashSet<String>();
		it.add("abc");
		it.add("bcd");
		it.add("def");
		
		System.out.println(it.contains("abc"));
		System.out.println(it.isEmpty());
		System.out.println(it.iterator());
		System.out.println("---------other-------------");
		Iterator<String> i = it.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
	}

}
