package com.sbs.sbsattend;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sbs.tool.CalendarAdapter;
import com.sbs.tool.MySignal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class QueryShiftActivity extends Activity {
	private CalendarAdapter calV = null;
	private GridView gridView = null;
	private TextView topText = null;
	private int year_c = 0;
	private int month_c = 0;
	private int flag = 0;
	private String currentDate = "";

	public QueryShiftActivity() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		currentDate = sdf.format(date); // 当期日期
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.clander);
		flag = getIntent().getIntExtra("FLAG", MySignal.GETSHIFT);
		calV = new CalendarAdapter(this, this.getResources(), year_c, month_c, flag);
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(calV);
		topText = (TextView) findViewById(R.id.tv_month);
		addTextToTopTextView(topText);
	}

	// 添加头部的年份 闰哪月等信息
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(year_c).append("年").append(month_c).append("月").append("\t");
		view.setText(textDate);
		view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}
}
