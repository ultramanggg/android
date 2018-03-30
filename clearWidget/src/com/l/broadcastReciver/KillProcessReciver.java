package com.l.broadcastReciver;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class KillProcessReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("按钮被点击了");

		ActivityManager am=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
		
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			
			am.killBackgroundProcesses(runningAppProcessInfo.processName);
			//System.out.println(runningAppProcessInfo.processName);
		}
		
		Toast.makeText(context, "内存已经清理", 0).show();

	}
}
