package com.zsl.ug;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView tv;
	public SharedPreferences sp;
	private ServiceConnection conn;
	private Intent intent;// 服务对应的意图
	public int flag = 0;// 服务是否继续的标记位 1可以进行几步，0不可几步状态
	int may = 0;// 控制unbindService能不能调用，0可以，1不可以
	int runing=0;// 服务是否在运行的标志  1表示在运行；

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		tv = (TextView) findViewById(R.id.bushu);
		tv.setText(sp.getString("bushu", "0"));
		intent = new Intent(this, JishuService.class);
		
		if(runing==1){
			
			
			bindBuService();
			
			
			thread = new Thread() {

				public void run() {

					while (true) {
						
						System.out.println("进入了循环体");
						

						if (flag == 0) {
							//break;
							return;
						}
						
						getBSetB();

						SystemClock.sleep(1000);

					}
				}

			};

			thread.start();
		}
		

	}
	
	protected void onDestroy() {
		
		if(may==0&&conn!=null){
			flag=1;
		  unbindService(conn);
		  may=1;
		}
		bs=null;
		super.onDestroy();
	};
	
	public void stop(View v) {
		
		runing=0;
		flag = 0;

		if (may == 0&&conn!=null) {
			unbindService(conn);
			may = 1;
		}

		if (intent != null) {

			stopService(intent);
			
			if(bs!=null){
				bs=null;
			}
		}

		cleanBushu();
	}
	
	

	Handler hanlder = new Handler() {

		public void handleMessage(android.os.Message msg) {

			if (msg.what == 0) {
				setBushu((String) msg.obj);
			}
		};
	};

	public void startOrPause(View v) {
		
		runing=1;

		if(flag==0){
			startJiBu();
		}
		
		
	}



	public void clean(View v) {
		if(runing!=0){
		 bs.getClear();
		}
		cleanBushu();
	}

	/**
	 * 清空计数器的方法
	 */
	public void cleanBushu() {
		tv.setText("0");
		sp.edit().putString("bushu", "0").commit();
	}

	/**
	 * 把得到的步数设置到计数器
	 * 
	 * @param bushu
	 *            从服务中得到的步数
	 */
	public void setBushu(String bushu) {
		tv.setText(bushu);
		sp.edit().putString("bushu", bushu).commit();
	}

	private Bushu bs;
	private Thread thread;

	public void bindBuService() {

		conn = new ServiceConnection() {

			public void onServiceDisconnected(ComponentName name) {

				if (conn != null) {
					conn = null;
					
				}

			}

			public void onServiceConnected(ComponentName name, IBinder service) {

				bs = (Bushu) service;

			}
		};

		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	public void getBSetB() {

		if (bs != null) {
			String bushu = bs.getBushu();
			
			//System.out.println("service"+bushu);
			
			Message message = Message.obtain();
			message.what = 0;
			message.obj = bushu;
			hanlder.sendMessage(message);
		} 

	}

	public void startJiBu() {
		flag = 1;
		may = 0;
		
		
		setBushu(sp.getString("bushu", "0"));

		
		
		
			startService(intent);
			
		

		
		
		bindBuService();

		thread = new Thread() {

			public void run() {

				while (true) {
					
					

					if (flag == 0) {
						//break;
						return;
					}
					
					getBSetB();

					SystemClock.sleep(2000);

				}
			}

		};

		thread.start();

	}

	public void pauseJibu() {

	}
	
	public void tishi(View v){
		
		AlertDialog.Builder builder=new Builder(this);
		
		builder.setView(View.inflate(this,R.layout.tishi, null));
		builder.show();
	}
}
