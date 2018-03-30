package com.l.service;

import java.util.Timer;
import java.util.TimerTask;

import com.l.clearwidget.R;
import com.l.utils.SystemInfoUtils;
import com.l.widgetProvider.ClearWidgetProvider;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		System.out.println("服务开启了");
		
		super.onCreate();
		
		final AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
		
		Timer timer=new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				
				
				//System.out.println("应该改变了");
				
				
				RemoteViews views = new RemoteViews(getPackageName(),R.layout.widget_clear);
				
				ComponentName provider=new ComponentName(getApplicationContext(),ClearWidgetProvider.class);
				
				String availMem = SystemInfoUtils.getAvailMem(UpdateWidgetService.this);
				int count = SystemInfoUtils.getRunningProcessCount(UpdateWidgetService.this);
				
				views.setTextViewText(R.id.process_count,"应用"+count+"个");
				
				views.setTextViewText(R.id.ram,"内存："+availMem);
				
				Intent intent = new Intent();
				intent.setAction("com.l.clearAllProcess");
				PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				views.setOnClickPendingIntent(R.id.clear, pendingIntent);
				
				widgetManager.updateAppWidget(provider, views);
				
				
				
			}
		}, 0, 5000);
		
		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
