package com.zhg.entity;

public class student {
	
	
	
	public student() {
		super();
	}
	public student(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	private String name;
	private int age;
	@Override
	public String toString() {
		return "student [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	
	public boolean equals (Object arg0){
		student stu = (student) arg0;
		if (stu.getAge()==age && id == stu.getId() && stu.getName().equals(name)){
			return true;
		}
		return false;
	}

	

}
