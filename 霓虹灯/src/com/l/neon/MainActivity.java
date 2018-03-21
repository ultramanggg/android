package com.l.neon;




import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Text;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private LinearLayout ll;
	private LinearLayout yuan;
	private TextView huan;
	private TextView ying;
	private TextView guang;
	private TextView lin;
	private TextView[] tvs;
	boolean isON = true;
	boolean yuanF = false;
	boolean shan=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ll = (LinearLayout) findViewById(R.id.tv_deng);
		yuan = (LinearLayout) findViewById(R.id.tv_yuan);
		huan = (TextView) findViewById(R.id.huan);
		ying = (TextView) findViewById(R.id.ying);
		guang = (TextView) findViewById(R.id.guang);
		lin = (TextView) findViewById(R.id.lin);

		tvs = new TextView[] { huan, ying, guang, lin };

	}

	int i = 0;
	Handler handler = new Handler() {

		boolean x = true;

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case 0:

				tvs[i].setTextColor(Color.YELLOW);

				i++;

				break;
			case 1:

				tvs[i].setTextColor(Color.BLACK);
				i--;
				break;
			case 2:

				if (x) {
					yuan.setBackgroundResource(R.drawable.xiao1_light);

					x = false;
				} else {
					yuan.setBackgroundResource(R.drawable.xiao1);
					x = true;

				}

				break;

			case 3:
				//停止其他的灯泡
				yuan.setBackgroundResource(R.drawable.xiao1mr);
				ll.setBackgroundResource(R.drawable.huan);
				
				break;
			case 4:
				//设置背景灯亮
				ll.setBackgroundResource(R.drawable.huan_light);
				break;
				
			case 5:
				
				
				if (shan) {
				
				System.out.println("正常进入系统");
				for (TextView tv : tvs) {
					tv.setTextColor(Color.GRAY);
				}
				shan = false;
			}else{
				for (TextView tv : tvs) {
					tv.setTextColor(Color.YELLOW);
				}
				shan = true;
			}
				
				break;

			default:
				break;
			}

		};
	};
	private Timer timer;

	public void start(View v) {

		new Thread() {

			public void run() {
				SystemClock.sleep(3000);
				Message msg1 = Message.obtain();
				msg1.what = 4;
				handler.sendMessage(msg1);

			};

		}.start();

		if (isON) {
			yuanF = false;
			isON = false;
			i = 0;
			ONhuanse();
			shanshuo();
			yuan();
		} else {
			Toast.makeText(MainActivity.this, "现在灯是开启的，不要再按了", 0).show();

		}

	}

	public void stop(View v) {
		
		
		if (!isON) {
		//关闭闪烁器，并改变颜色为黄色	
			stopTimer();
			
		//--------------------------------------
			
			yuanF = true;
			isON = true;
			i = 3;
			offhuanse();

		} else {
			Toast.makeText(MainActivity.this, "现在灯是关闭的，不要再按了", 0).show();

		}

	}

	public void ONhuanse() {

		new Thread() {
			@Override
			public void run() {
				int i = 0;
				while (true) {

					Message msg = Message.obtain();
					msg.what = 0;
					handler.sendMessage(msg);
					SystemClock.sleep(1000);

					i++;

					if (i > 3) {
						break;
					}

				}

			}
		}.start();
	}

	public void offhuanse() {

		new Thread() {
			@Override
			public void run() {
				int i = 0;
				SystemClock.sleep(3000);
				while (true) {

					Message msg = Message.obtain();
					msg.what = 1;
					handler.sendMessage(msg);
					SystemClock.sleep(1000);

					i++;

					if (i > 3) {
						break;
					}

				}

			}
		}.start();
	}

	public void yuan() {

		new Thread() {
			public void run() {

				SystemClock.sleep(4000);

				while (true) {

					Message msg = Message.obtain();
					msg.what = 2;
					handler.sendMessage(msg);
					SystemClock.sleep(1000);

					if (yuanF) {
						SystemClock.sleep(7000);
						Message msg1 = Message.obtain();
						msg1.what = 3;
						handler.sendMessage(msg1);
						break;
					}

				}

			};

		}.start();
	}

	/**
	 * 
	 * 控制霓虹灯闪烁的方法
	 * 
	 */
	public void shanshuo() {
	
        timer = new Timer();
        
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				new Thread(){
					public void run() {
						
						int i=0;
						while(true){
							
							
							SystemClock.sleep(500);
							
							Message msg=Message.obtain();
							msg.what=5;
							
							handler.sendMessage(msg);		
							
							i++;
							
							if(i>5){
								break;
							}
						}
						
						
						
					};
					
					
				}.start();
				
				
				
			}
		}, 5000,6000);
	}
	
	
	private void stopTimer(){
	    if(timer != null){
	        timer.cancel();
	        // 一定设置为null，否则定时器不会被回收
	        timer = null;
	    }
	}

	public void ts(View v){
		AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
		
		builder.setMessage("本软件是霓虹灯广告屏幕效果模拟，制作人 zsl，功能比较简单无需权限。点击启动开始，点击停止后停止。");
		builder.show();
	}

	
	
}
