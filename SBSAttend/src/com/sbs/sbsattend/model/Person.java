package com.sbs.sbsattend.model;

public class Person{
	private String name;
	private int permission;
	private int ID;
	private double quotan;
	
	public double getQuotan() {
		return quotan;
	}
	public void setQuotan(double quotan) {
		this.quotan = quotan;
	}
	public Person(String name, int permission, int iD, double quotan) {
		super();
		this.name = name;
		this.permission = permission;
		this.ID = iD;
		this.quotan = quotan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
}
