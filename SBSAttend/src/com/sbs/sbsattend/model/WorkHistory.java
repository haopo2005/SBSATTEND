package com.sbs.sbsattend.model;

public class WorkHistory {
	private String daywork;
	private String nightwork;
	private String worktime;
	
	public WorkHistory(String daywork, String nightwork, String worktime) {
		super();
		this.daywork = daywork;
		this.nightwork = nightwork;
		this.worktime = worktime;
	}
	
	public String getDaywork() {
		return daywork;
	}
	public void setDaywork(String daywork) {
		this.daywork = daywork;
	}
	public String getNightwork() {
		return nightwork;
	}
	public void setNightwork(String nightwork) {
		this.nightwork = nightwork;
	}
	public String getWorktime() {
		return worktime;
	}
	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}	
}
