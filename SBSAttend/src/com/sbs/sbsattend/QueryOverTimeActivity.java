package com.sbs.sbsattend;

import java.util.List;

import com.sbs.sbsattend.QueryLeaveActivity.ViewHolder;
import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Work;
import com.sbs.tool.LayOut;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QueryOverTimeActivity extends Activity {
	private ListView lv;
	private List<Work> wks;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.queryover);
		lv = (ListView) findViewById(R.id.overtime);
		name = getIntent().getStringExtra("name");
		wks = Logic.query_monthlyovertime(name);

		if (wks == null) {
			Toast.makeText(QueryOverTimeActivity.this, "无调休数据，退出",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}

		MyAdapter mAdapter = new MyAdapter(this); // 得到一个MyAdapter对象
		lv.setAdapter(mAdapter); // 为ListView绑定Adapter
		LayOut.setListViewHeightBasedOnChildren(lv);
	}

	public void quit(View v) {
		this.finish();
	}

	/*
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定
	 */
	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

		/* 构造函数 */
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (wks != null)
				return wks.size();// 返回数组的长度;
			else
				return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			final ViewHolder holder;
			String result = "";
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.queryitem, null);
				holder = new ViewHolder();
				/* 得到各个控件的对象 */
				holder.start = (TextView) convertView
						.findViewById(R.id.start_tv);
				holder.end = (TextView) convertView.findViewById(R.id.end_tv);
				holder.result = (TextView) convertView
						.findViewById(R.id.result_tv);
				convertView.setTag(holder);// 绑定ViewHolder对象
			} else {
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			/* 设置TextView显示的内容 */

			holder.start.setText(wks.get(position).getOriginrest()
					.substring(0, 10)
					+ wks.get(position).getOriginshift());
			holder.end.setText(wks.get(position).getCurrentrest()
					.subSequence(0, 10)
					+ wks.get(position).getCurrentshift());

			switch (wks.get(position).getApprove()) {
			case -1:
				result = "未审批";
				break;
			case 4:
				result = "通过";
				break;
			case 5:
				result = "未通过";
				break;
			}
			holder.result.setText(result);

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			convertView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return convertView;
		}
	}

	/* 存放控件 */
	public final class ViewHolder {
		public TextView result;
		public TextView start;
		public TextView end;
	}

}
