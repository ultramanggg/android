package com.l.listview;

import com.l.listview.view.RefreshListView;
import com.l.listview.view.RefreshListView.OnRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final RefreshListView rlv = (RefreshListView) findViewById(R.id.rlv);

		myAdapter myAdapter = new myAdapter();
		// View header=View.inflate(this,R.layout.header,null);
		rlv.setAdapter(myAdapter);
		// rlv.addHeaderView(header);

		rlv.setPadding(0, 0, 0, 0);

		rlv.setRefreshListener(new OnRefreshListener() {

			public void OnPullRefresh() {

				new Thread() {

					public void run() {

						SystemClock.sleep(3000);

						System.out.println("更新数据");
						runOnUiThread(new Runnable() {

							public void run() {
								rlv.completeRefresh();

							}
						});

					};

				}.start();

			}

			public void OnLoadingMore() {
				new Thread() {

					public void run() {

						SystemClock.sleep(3000);

						System.out.println("加载更多");
						runOnUiThread(new Runnable() {

							public void run() {
								rlv.completeRefresh();

							}
						});

					};

				}.start();

			}
		});

	}

	class myAdapter extends BaseAdapter {

		public int getCount() {
			return 20;
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View v1 = null;

			if (convertView == null) {

				v1 = View.inflate(MainActivity.this, R.layout.item, null);

			} else {
				v1 = convertView;
			}

			TextView tv = (TextView) v1.findViewById(R.id.tv_i);
			tv.setText("条目" + position);

			return v1;
		}

	}

}
