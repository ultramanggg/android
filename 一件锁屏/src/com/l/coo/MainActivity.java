package com.l.coo;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private DevicePolicyManager mdPM;
    private ComponentName mDeviceAdminSample;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button bt =(Button) findViewById(R.id.bt);
        
        mDeviceAdminSample = new ComponentName(this, AdnimReceiver.class);
        
       mdPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
    }
    
    public void lockScreen(View v){
    	
    	if(mdPM.isAdminActive(mDeviceAdminSample)){
    			mdPM.lockNow();
    			
    			mdPM.resetPassword("123456", 0);
    			
    	}else{
    		Toast.makeText(MainActivity.this,"设备管理器必须被激活", 0).show();
    	}
    }
    public void activedveice(View v){
    	
    	Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    	
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
		
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"开启了设备管理功能");
		
		
		startActivity(intent);
    }
    
    public void unInstall(View v){
    	
    	mdPM.removeActiveAdmin(mDeviceAdminSample);
    	
    	Intent intent = new Intent(Intent.ACTION_VIEW);
    	
    	intent.addCategory(Intent.CATEGORY_DEFAULT);
    	
    	intent.setData(Uri.parse("package:"+getPackageName()));
    	
    	startActivity(intent);
    }
    
    public void wipeData(View v){
    	if(mdPM.isAdminActive(mDeviceAdminSample)){
			
    		mdPM.wipeData(0);
			
    	}else{
		Toast.makeText(MainActivity.this,"设备管理器必须被激活", 0).show();
    	}
    }
}
