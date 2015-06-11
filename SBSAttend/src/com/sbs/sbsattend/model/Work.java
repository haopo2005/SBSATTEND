package com.sbs.sbsattend.model;

public class Work {
	private String originrest;
	private String currentrest;
	private String name;
	private String originshift;
	private String currentshift;
	private String reason;
	private String originweek;
	private String currentweek;
	private int approve;
	private int id;

	public Work(String originrest, String currentrest, String name,
			String originshift, String currentshift, String originweek,
			String currentweek, String reason, int approve, int id) {
		super();
		this.originrest = originrest;
		this.currentrest = currentrest;
		this.name = name;
		this.originshift = originshift;
		this.currentshift = currentshift;
		this.reason = reason;
		this.originweek = originweek;
		this.currentweek = currentweek;
		this.approve = approve;
		this.id = id;
	}

	public String getOriginweek() {
		return originweek;
	}

	public void setOriginweek(String originweek) {
		this.originweek = originweek;
	}

	public String getCurrentweek() {
		return currentweek;
	}

	public void setCurrentweek(String currentweek) {
		this.currentweek = currentweek;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOriginshift() {
		return originshift;
	}

	public void setOriginshift(String originshift) {
		this.originshift = originshift;
	}

	public String getCurrentshift() {
		return currentshift;
	}

	public void setCurrentshift(String currentshift) {
		this.currentshift = currentshift;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOriginrest() {
		return originrest;
	}

	public void setOriginrest(String originrest) {
		this.originrest = originrest;
	}

	public String getCurrentrest() {
		return currentrest;
	}

	public void setCurrentrest(String currentrest) {
		this.currentrest = currentrest;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getApprove() {
		return approve;
	}

	public void setApprove(int approve) {
		this.approve = approve;
	}
}
