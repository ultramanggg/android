package com.l.listview.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import com.l.listview.R;

public class RefreshListView extends ListView implements OnScrollListener{

	private View header;
	private ImageView arrow;
	private ProgressBar rotate;
	private TextView tv;
	private int headerWidth;
	private int headerHeight;

	private final int PUll_REFRESH = 0; // 向下的刷新状态
	private final int RELEASE_REFRESH = 1;// 松开刷新的状态
	private final int REFRESH_ING = 2;// 坑在刷新

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		initHeader();
		initFooterView();
		initAnimation();

	}

	public RefreshListView(Context context) {
		super(context);
		initHeader();
		initFooterView();
		initAnimation();

	}

	public void initHeader() {

		header = View.inflate(this.getContext(), R.layout.header, null);
		arrow = (ImageView) header.findViewById(R.id.arrow);
		rotate = (ProgressBar) header.findViewById(R.id.pb_rotate);
		tv = (TextView) header.findViewById(R.id.state);

		header.measure(0, 0);

		headerWidth = header.getMeasuredWidth();
		headerHeight = header.getMeasuredHeight();

		header.setPadding(0, -headerHeight, 0, 0);

		addHeaderView(header);

	}

	public void initAnimation() {

		rotateDown = new RotateAnimation(-180, -360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);

		rotateDown.setDuration(300);
		rotateDown.setFillAfter(true);

		rotateUP = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);

		rotateUP.setDuration(300);
		rotateUP.setFillAfter(true);

	}
	
	public void initFooterView(){
		
		foot = View.inflate(getContext(), R.layout.footer,null);
		
		foot.measure(0,0);
		
		
		footheight = foot.getMeasuredHeight();
		
		foot.setPadding(0,-footheight,0,0);
		
		addFooterView(foot);
		this.setOnScrollListener(this);
		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// setPadding(0, -headerHeight, 0,0);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
	}

	int y0 = 0;
	int dy = 0;
	int currentState = 0;
	private RotateAnimation rotateDown;
	private RotateAnimation rotateUP;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {

		case MotionEvent.ACTION_DOWN:

			y0 = (int) ev.getY();

			break;

		case MotionEvent.ACTION_MOVE:
			if(currentState==REFRESH_ING){
				break;
				}
			

			dy = (int) (ev.getY() - y0);

			int padding = (int) (-headerHeight + dy);

			if (padding > -headerHeight && getFirstVisiblePosition() == 0) {

				if (padding > 0 && currentState == PUll_REFRESH) {

					currentState = RELEASE_REFRESH;
					refreshState();

				} else if (padding < 0 && currentState == RELEASE_REFRESH) {
					currentState = PUll_REFRESH;
					refreshState();
				}

				header.setPadding(0, padding, 0, 0);
				return true;
			}

			// break;

		case MotionEvent.ACTION_UP:

			if (currentState == PUll_REFRESH) {

				header.setPadding(0, -headerHeight, 0, 0);
			} else if (currentState == RELEASE_REFRESH) {
				header.setPadding(0, 0, 0, 0);
				currentState = REFRESH_ING;
				refreshState();
			}

			// y0 = 0;
			// dy = 0;
			break;

		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	public void refreshState() {

		switch (currentState) {

		case PUll_REFRESH:
			tv.setText("下拉刷新");

			arrow.startAnimation(rotateDown);
			break;

		case RELEASE_REFRESH:
			tv.setText("松开刷新");
			arrow.startAnimation(rotateUP);
			break;
		case REFRESH_ING:
			
			tv.setText("正在刷新");
			arrow.setVisibility(INVISIBLE);
			arrow.clearAnimation();
			rotate.setVisibility(VISIBLE);
			
			listener.OnPullRefresh();

			break;

		default:
			break;

		}

	}
	
	public void completeRefresh(){
		
		if(isLoadingMore){
			
			foot.setPadding(0, -footheight, 0, 0);
			isLoadingMore=false;
			
		}else{
			
			header.setPadding(0, -headerHeight, 0, 0);
			
			currentState=PUll_REFRESH;
			
			rotate.setVisibility(INVISIBLE);
			
			arrow.setVisibility(VISIBLE);
			
			tv.setText("下拉刷新"+":上次更新的时间:"+getCurrentTime());
		}
		
		
	}
	
	
	private String  getCurrentTime(){
		
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		return format.format(new Date());
		
	}
	
	private OnRefreshListener listener;
	private View foot;
	private int footheight;
	
	public void setRefreshListener(OnRefreshListener listener){
		this.listener=listener;
	}
	
	public interface OnRefreshListener{
		
		public void OnPullRefresh();
		public void OnLoadingMore();
	}
	
	
	boolean isLoadingMore=false;
	
	
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		//foot.setPadding(0,0, 0, 0);
		//System.out.println(scrollState+"+++++++++");
		
		
		
			if(scrollState==OnScrollListener.SCROLL_STATE_IDLE 
					&& getLastVisiblePosition()==(getCount()-1) &&!isLoadingMore){
				isLoadingMore = true;
				
				foot.setPadding(0, 0, 0, 0);
				setSelection(getCount());
				
				if(listener!=null){
					listener.OnLoadingMore();
				}
			
			
			
		}
		
	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		//System.out.println("滚动");
	} 
	
}
