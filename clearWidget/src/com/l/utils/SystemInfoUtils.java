package com.l.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.text.format.Formatter;

public class SystemInfoUtils {
	
	public static boolean isRunningProcess(Context context,String serviceClassName){
		
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		
		List<RunningServiceInfo> runningServices = am.getRunningServices(200);
		
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			
			if(runningServiceInfo.service.getClassName().equals(serviceClassName)){
				return true;
			}
		}
		
		
		return false;
	}
	
	
	/**
	 * 获取运行的进程的个数
	 * @param context
	 * @return
	 */
	public static int getRunningProcessCount(Context context){
		
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

		
		return runningAppProcesses.size();
	}
	
	/**
	 * 
	 * 获取剩余内存的大小
	 * @param context
	 * @return
	 */
	public static String getAvailMem(Context context){
		
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		
		long availMem = memoryInfo.availMem;
		return Formatter.formatFileSize(context, availMem);
		
	}
	
	/**
	 * 获取总内存的大小
	 * @return
	 */
	public static String getTotalMen(){
		
		try {
			FileInputStream fls=new FileInputStream(new File("proc/meminfo"));
			
			BufferedReader br=new BufferedReader(new InputStreamReader(fls));
			
			String str=br.readLine();
			
			String s=str.split(":")[1].trim().split(" kB")[0].trim();
			
			//int a=(int)Integer.valueOf(s);
			
			
			return Integer.valueOf(s)/1024+"MB";
			
		} catch (Exception e) {
			return "读取失败了";
			
		}
		
		
	}

}
