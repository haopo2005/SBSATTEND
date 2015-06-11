package com.sbs.sbsattend;

import com.sbs.tool.MySignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StaffActivity extends Activity {
	private String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.staff);
		name = getIntent().getStringExtra("name");
	}

	// 请假
	public void dayleave(View v) {
		Intent intent = new Intent(StaffActivity.this, TakeLeaveActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}
	
	//调休
	public void dayoff(View v)
	{
		Intent intent = new Intent(StaffActivity.this, OverWorkActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}

	// 本月班次接口已作废,因为老的值班系统无法通过wlan访问
	public void watch_web(View v) {
		Intent intent = new Intent(StaffActivity.this, WatchTableActivity.class);
		StaffActivity.this.startActivity(intent);
	}
	
	//查看本月请假审批结果
	public void watch_leave(View v){
		Intent intent = new Intent(StaffActivity.this, QueryLeaveActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}
	
	//查看本月调休审批结果
	public void watch_overtime(View v){
		Intent intent = new Intent(StaffActivity.this, QueryOverTimeActivity.class);
		intent.putExtra("name", name);
		StaffActivity.this.startActivity(intent);
	}
	
	//本月班次，暂时废弃listview
	public void watch_table(View v)
	{
		Intent intent = new Intent(StaffActivity.this, QueryWorkInfoActivity.class);
		StaffActivity.this.startActivity(intent);
	}
	
	//显示本月值班表，用日历的方式
	public void watch_shift(View v){
		Intent intent = new Intent(StaffActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETSHIFT);
		StaffActivity.this.startActivity(intent);
	}
	
	public void watch_rest(View v){
		Intent intent = new Intent(StaffActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETREST);
		StaffActivity.this.startActivity(intent);
	}
	
	public void watch_leaveall(View v){
		Intent intent = new Intent(StaffActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETLEAVE);
		StaffActivity.this.startActivity(intent);
	}
}
