package com.sbs.sbsattend;

import java.util.List;

import com.sbs.sbsattend.ReivewLeaveActivity.ViewHolder;
import com.sbs.sbsattend.model.Leave;
import com.sbs.sbsattend.model.Logic;
import com.sbs.sbsattend.model.Work;
import com.sbs.tool.LayOut;
import com.sbs.tool.MySignal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReivewOverTimeActvity extends Activity {
	private ListView lv;
	private List<Work> wks;
	private Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reivewovertime);
		bt = (Button) findViewById(R.id.overtime_bt);
		lv = (ListView) findViewById(R.id.overtime_lv);

		wks = Logic.query_overtime();
		if (wks == null) {
			bt.setText("无审批信息，点此退出");
		}
		MyAdapter mAdapter = new MyAdapter(this);// 得到一个MyAdapter对象
		lv.setAdapter(mAdapter); // 为ListView绑定Adapter
		LayOut.setListViewHeightBasedOnChildren(lv);
	}

	public void commit(View v) {
		int count = 0;

		if (wks == null) {
			Toast.makeText(ReivewOverTimeActvity.this, "无可审批信息，退出！",
					Toast.LENGTH_LONG).show();
			this.finish();
			return;
		}

		for (Work l : wks) {
			count = count + Logic.approve_overtime(l);
		}

		if (count == wks.size()) {
			Toast.makeText(ReivewOverTimeActvity.this, "批量提交成功！",
					Toast.LENGTH_LONG).show();
		} else {
			StringBuilder temp = new StringBuilder();
			temp.append(wks.size() - count);
			Toast.makeText(ReivewOverTimeActvity.this,
					"本次操作有" + temp.toString() + "行提交失败", Toast.LENGTH_LONG)
					.show();
		}
		finish();
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
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.leaveitem, null);
				holder = new ViewHolder();
				/* 得到各个控件的对象 */
				holder.name = (TextView) convertView
						.findViewById(R.id.staff_name);
				holder.start = (TextView) convertView
						.findViewById(R.id.starttime);
				holder.end = (TextView) convertView.findViewById(R.id.endtime);
				holder.res = (TextView) convertView.findViewById(R.id.reason);
				holder.cb = (CheckBox) convertView.findViewById(R.id.ifagree);
				convertView.setTag(holder);// 绑定ViewHolder对象
			} else {
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			/* 设置TextView显示的内容，即我们存放在动态数组中的数据 */
			if (wks.get(position).getOriginrest() != "") {
				holder.name.setText(wks.get(position).getName());
				holder.start.setText(wks.get(position).getOriginrest()
						.substring(0, 10)+" "+wks.get(position).getOriginweek()+"\n"
						+ wks.get(position).getOriginshift());
				holder.end.setText(wks.get(position).getCurrentrest()
						.subSequence(0, 10)+" "+wks.get(position).getCurrentweek()+"\n"
						+ wks.get(position).getCurrentshift());
				holder.res.setText(wks.get(position).getReason());
				// 默认审批不通过
				wks.get(position).setApprove(MySignal.NOTAPPROVE);
			}

			holder.cb.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (holder.cb.isChecked()) {
						wks.get(position).setApprove(MySignal.APPROVE); // 如果被选中，则approve置为4
					} else {
						wks.get(position).setApprove(MySignal.NOTAPPROVE); // 如果未中，则approve置为5
					}
				}
			});

			int[] colors = { Color.WHITE, Color.rgb(219, 238, 244) };// RGB颜色
			convertView.setBackgroundColor(colors[position % 2]);// 每隔item之间颜色不同

			return convertView;
		}

	}

	/* 存放控件 */
	public final class ViewHolder {
		public TextView name;
		public TextView start;
		public TextView end;
		public TextView res;
		public CheckBox cb;
	}

}
