package com.sbs.sbsattend;

import com.sbs.tool.MySignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AdminActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.admin);
	}
	
	//审批请假
	public void review_leave(View v)
	{
		 Intent intent = new Intent(AdminActivity.this, ReivewLeaveActivity.class);
		 AdminActivity.this.startActivity(intent);
	}
	
	public void review_over(View v)
	{
		Intent intent = new Intent(AdminActivity.this, ReivewOverTimeActvity.class);
		AdminActivity.this.startActivity(intent);
	}
	
	//本月班次网页接口，已废弃
	public void watch_web(View v)
	{
		 Intent intent = new Intent(AdminActivity.this, WatchTableActivity.class);
		 AdminActivity.this.startActivity(intent);
	}
	
	//本月班次，暂时作废
	public void watch_table(View v)
	{
		Intent intent = new Intent(AdminActivity.this, QueryWorkInfoActivity.class);
		AdminActivity.this.startActivity(intent);
	}
	
	//显示本月值班表，用日历的方式
	public void watch_shift(View v){
		Intent intent = new Intent(AdminActivity.this, QueryShiftActivity.class);
		AdminActivity.this.startActivity(intent);
	}
	
	//显示本月调休表，日历形式
	public void watch_rest(View v){
		Intent intent = new Intent(AdminActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETREST);
		AdminActivity.this.startActivity(intent);
	}
	
	//显示本月请假表，列表形式
	public void watch_leaveall(View v){
		Intent intent = new Intent(AdminActivity.this, QueryShiftActivity.class);
		intent.putExtra("FLAG", MySignal.GETLEAVE);
		AdminActivity.this.startActivity(intent);
	}

}
