package com.l.la;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView zk;
	private EditText et;
	private ArrayList<String> list;
	private ListView listView;
	private PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initListener();
	}

	private void initView() {

		et = (EditText) findViewById(R.id.et);

		zk = (ImageView) findViewById(R.id.zhankai);
	}

	public void initData() {
		list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {

			list.add("10086" + i);

		}

		

		listView = new ListView(this);
		listView.setBackgroundColor(Color.CYAN);
		listView.setVerticalScrollBarEnabled(false);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView tv = (TextView) view.findViewById(R.id.num);
				String string = tv.getText().toString();

				et.setText(string);

			}
		});
		
		myAdapter adapter = new myAdapter();
		listView.setAdapter(adapter);

	}
	boolean isOn=false;
	public void initListener() {
		zk.setOnClickListener(new OnClickListener() {
			
			

			public void onClick(View v) {
				
				
				
					showPopupWindow();
					
				
			}
		});
	}

	public void showPopupWindow() {

		

		
		if (popupWindow == null) {
			popupWindow = new PopupWindow();
			popupWindow.setWidth(150);
			popupWindow.setHeight(270);
			
		
			popupWindow.setBackgroundDrawable(listView.getDivider());
			popupWindow.setOutsideTouchable(true);
			popupWindow.setFocusable(true);
			
		}
		
		popupWindow.setContentView(listView);
		// popupWindow.setOutsideTouchable(true);

		popupWindow.showAsDropDown(et);

	}

	class myAdapter extends BaseAdapter {

		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {

			final View v1;
			if (convertView == null) {
				v1 = View.inflate(MainActivity.this, R.layout.item, null);
			} else {
				v1 = convertView;
			}
			TextView num = (TextView) v1.findViewById(R.id.num);
			ImageView del = (ImageView) v1.findViewById(R.id.del);
			num.setText(list.get(position));

			del.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					list.remove(position);
					notifyDataSetChanged();

					popupWindow.update(
							150,
							v1.getHeight() * list.size() > 270 ? 270 : v1
									.getHeight() * list.size());
					if (list.size() == 0) {
						popupWindow.dismiss();

					}

				}
			});

			return v1;
		}

	}

	
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
}
