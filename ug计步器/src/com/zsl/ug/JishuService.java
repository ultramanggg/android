package com.zsl.ug;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.widget.SlidingDrawer;

public class JishuService extends Service {

	public SharedPreferences sp;
	int flag=0;//循环是否继续
	private SensorManager sm;
	private Sensor mAccelerometer;
	private JsSensorListenter jsSensorListenter;

	@Override
	public IBinder onBind(Intent intent) {

		return new Mybinder();
	}

	@Override
	public void onCreate() {
		
		flag=1;
		System.out.println("服务成功的开启");
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		//开启传感器并注册事件-----------------------------------------
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		jsSensorListenter = new JsSensorListenter();
		sm.registerListener(jsSensorListenter, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		
		int SaveBushu=0;
		
		//-----------------------------------------------------------

		super.onCreate();
	}
		
	@Override
	public void onDestroy() {
		//取消传感器的制作-----------------------------------------------------
		sm.unregisterListener(jsSensorListenter);
		//---------------------------------------------------------------------
		System.out.println("关闭");
		flag=0;
		super.onDestroy();
	}
	
	//传感器的事件中获得步数
	public String jibu() {
		
		
		//jsSensorListenter.setI(Integer.valueOf(sp.getString("bushu", "0")));
		//int c = 0;
		//获取sp中保存的步数
		//Integer.valueOf(sp.getString("bushu", "0"));
		//初始化为当前保存的步数
		
		//获取事件发生后的步数
		
		//将步数转换为String 返回；
		return String.valueOf(jsSensorListenter.getI());
	}

	
	
	public class JsSensorListenter implements SensorEventListener{
		
		int i=0;
		
		/**
		 * 初始化i的方法;
		 * @param i  步数的初始值；
		 */
		public void setI(int i){
			
			this.i=i;
		}
		
		/**
		 * 从事件中获取步数的方法
		 * @return  当前传感器感应到的步数；
		 */
		public int getI(){
			
			return i;
			
		}
		
		public void onSensorChanged(SensorEvent event) {
			
			float a = event.values[0];
			
			if(a>5||a<-5){
				
				i=i+1;	
			}
			if(i>100000){
				i=0;
			}
					
			//System.out.println("传感器调用成功"+a);
			
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
			
		}
		
	}

	/**
	 * 这是fang'hu
	 * @author ug
	 *
	 */
	private class Mybinder extends Binder implements Bushu {

		public String getBushu() {

			return jibu();
		}

		public boolean getClear() {
			jsSensorListenter.setI(0);
			return true;
		}

	}

	
	/**
	 * 
	 * 这个累是自动产生步数的类别，主要在用于测试前台逻辑是否正常；
	 * @author ug
	 *
	 */
	public class Myrun extends Thread {
		int i;

		public Myrun(int i) {
			this.i = i;
		}

		@Override
		public void run() {

			int b = 0;

			while (b == 0) {

				i=i++;
				SystemClock.sleep(3000);
				
				if(flag==0){
					break;
				}
			}
		}
		
		public int getI(){
			return i;
		}

	}
}
