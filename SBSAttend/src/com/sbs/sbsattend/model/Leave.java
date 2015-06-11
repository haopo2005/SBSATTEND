package com.sbs.sbsattend.model;

public class Leave {
	private String name;
	private String starttime;
	private String endtime;
	private String originshift;
	private String currentshift;
	private String originweek;
	private String currentweek;
	private String reason;
	private int approve;
	private int ID;	
	
	public Leave(String name, String starttime, String endtime,
			String originshift, String currentshift, String originweek,
			String currentweek, String reason, int approve, int iD) {
		super();
		this.name = name;
		this.starttime = starttime;
		this.endtime = endtime;
		this.originshift = originshift;
		this.currentshift = currentshift;
		this.originweek = originweek;
		this.currentweek = currentweek;
		this.reason = reason;
		this.approve = approve;
		ID = iD;
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
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getApprove() {
		return approve;
	}
	public void setApprove(int approve) {
		this.approve = approve;
	}
	
	
}
