package com.l.clearwidget;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void clear (View v){
		
		ActivityManager am=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
		
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			
			am.killBackgroundProcesses(runningAppProcessInfo.processName);
			//System.out.println(runningAppProcessInfo.processName);
		}
		
		Toast.makeText(this, "内存已经清理", 0).show();
		
	}
//	public void clearRam(View v){
//		
//		System.out.println("按钮被点击了");
//		
//	}

}
