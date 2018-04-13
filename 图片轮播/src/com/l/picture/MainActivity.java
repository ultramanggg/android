package com.l.picture;

import java.util.ArrayList;
import java.util.List;

import com.l.picture.bean.PictureInfo;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ViewPager vp;

	private List<PictureInfo> ps = new ArrayList<PictureInfo>();

	private TextView tv;

	Handler mhandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				vp.setCurrentItem(vp.getCurrentItem() + 1);

				mhandler.sendEmptyMessageDelayed(0, 5000);
				break;

			case 1:
				vp.setCurrentItem((Integer.MAX_VALUE / 2) - 3);

				break;

			default:
				break;
			}

		};
	};

	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initDATA();
		
		mhandler.sendEmptyMessageDelayed(1, 0);
	}

	private void initView() {

		vp = (ViewPager) findViewById(R.id.vp);
		tv = (TextView) findViewById(R.id.title);

		ll = (LinearLayout) findViewById(R.id.ll_dot);

	}

	private void initDATA() {
		ps.add(new PictureInfo(R.drawable.a, "图片一"));
		ps.add(new PictureInfo(R.drawable.b, "图片二"));
		ps.add(new PictureInfo(R.drawable.c, "图片三"));
		ps.add(new PictureInfo(R.drawable.d, "图片四"));
		ps.add(new PictureInfo(R.drawable.e, "图片五"));

		for (int i = 0; i < 5; i++) {
			View view = new View(this);

			LayoutParams params = new LayoutParams(8, 8);

			if (i != 0) {
				params.leftMargin = 10;
			}
			params.gravity = Gravity.CENTER_HORIZONTAL;

			view.setLayoutParams(params);

			view.setBackgroundResource(R.drawable.dot_selector);

			ll.addView(view);

		}

		MyAdpater myAdpater = new MyAdpater();

		vp.setAdapter(myAdpater);

		// mhandler.sendEmptyMessageDelayed(0, 5000);
		
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			public void onPageSelected(int position) {
				int item = vp.getCurrentItem();

				tv.setText(ps.get(item % 5).getMessage());

				for (int i = 0; i < ll.getChildCount(); i++) {

					if (item % 5 == i) {
						ll.getChildAt(i).setEnabled(false);
					} else {
						ll.getChildAt(i).setEnabled(true);
					}

				}

				
			}
			
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	class MyAdpater extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			View v = View.inflate(MainActivity.this, R.layout.view_item, null);

			ImageView iv = (ImageView) v.findViewById(R.id.iv);

			iv.setImageResource(ps.get(position % 5).getId());

			container.addView(v);

			return v;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView((View) object);
		}

		@Override
		public int getCount() {

			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			
			return view == object;
		}
	}
}
