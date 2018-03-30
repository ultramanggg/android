package com.l.widgetProvider;

import com.l.service.UpdateWidgetService;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class ClearWidgetProvider extends AppWidgetProvider {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
	}

	@Override
	public void onEnabled(Context context) {
		
		super.onEnabled(context);
		context.startService(new Intent(context,UpdateWidgetService.class));
	}

	
	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		context.stopService(new Intent(context,UpdateWidgetService.class));
	}
	
	
}
