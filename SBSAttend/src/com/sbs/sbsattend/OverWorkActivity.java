package com.sbs.sbsattend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.sbs.sbsattend.db.DBConnection;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Person;
import com.sbs.tool.DateTimePickDialogUtil;
import com.sbs.tool.MySignal;
import com.sbs.tool.SpecialCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class OverWorkActivity extends Activity {
	private String initTime = "";
	private EditText input2;
	private EditText reason;
	private Spinner dataSpinner;
	private Spinner current;
	private String name;
	private String DateTime;
	private String shift_origin;
	private String shift_current;
	private List<String> works;
	private ArrayAdapter myadapter_current;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.overtime);
		
		init_components();	    
	}
	
	//初始化各个控件
	public void init_components()
	{
		dataSpinner = (Spinner)findViewById(R.id.data_pick);
		input2 = (EditText) findViewById(R.id.overtimepick2);	//当前调休日期
		current = (Spinner)findViewById(R.id.Spinner_current);
		reason = (EditText) findViewById(R.id.over_reason);
		
		name = getIntent().getStringExtra("name");
		works = Logic.query_work(name);
		
		adapter = new ArrayAdapter<String> (OverWorkActivity.this, android.R.layout.simple_spinner_item, works);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataSpinner.setAdapter(adapter);       
        dataSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				DateTime = (dataSpinner.getItemAtPosition(position)).toString();	
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}      	
        });
        // 设置默认值
        dataSpinner.setVisibility(View.VISIBLE);
        dataSpinner.setSelection(0);
		
    	// 将可选内容与ArrayAdapter连接起来
		myadapter_current = ArrayAdapter.createFromResource(this,R.array.class_kind, android.R.layout.simple_spinner_item);
		// 设置下拉列表的风格	
		myadapter_current.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		current.setAdapter(myadapter_current);				
		// 添加事件Spinner事件监听
		current.setOnItemSelectedListener(new CurrentSpinnerListener());
		// 设置默认值
		current.setVisibility(View.VISIBLE);
		current.setSelection(0);
	}
	
	class CurrentSpinnerListener implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> adpater, View view, int position,
				long id) {
			shift_current = (String) myadapter_current.getItem(position);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}
	
	public void select_current(View v) {
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
				OverWorkActivity.this, initTime);
		dateTimePicKDialog.dateTimePicKDialog(input2);
	}
	
	public void quit(View v){
		this.finish();
	}
	
	public void commit(View v){
		//获取选择日期
		String origin = DateTime.substring(0,10);
		String current = input2.getText().toString();
		String res = reason.getText().toString();
		String currentweek = SpecialCalendar.getWeekdayofYear(Integer.parseInt(current.substring(0, 4)), Integer.parseInt(current.substring(5, 7)), Integer.parseInt(current.substring(8, 10)));
		String originweek = SpecialCalendar.getWeekdayofYear(Integer.parseInt(origin.substring(0, 4)), Integer.parseInt(origin.substring(5, 7)), Integer.parseInt(origin.substring(8, 10)));
		shift_origin = DateTime.substring(11);
		
		if(current.equals("当前调休日期"))
		{
			 Toast.makeText(OverWorkActivity.this,"未选择日期，无法提交", Toast.LENGTH_LONG).show(); 
			 this.finish();
			 return;
		}
		//提交数据库
		int flag = Logic.commit_overtime(name, origin, current, shift_origin, shift_current, currentweek, originweek, res);
 
		if(flag != MySignal.CORRECTCOMMIT)
		{
			Toast.makeText(OverWorkActivity.this,"数据库提交失败！", Toast.LENGTH_LONG).show();
		}else
		{
			Toast.makeText(OverWorkActivity.this,"调休请求提交成功！", Toast.LENGTH_LONG).show();
		}
		this.finish();
	} 
}
