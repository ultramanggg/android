package com.l.toggle;

import com.l.toggle.view.MyToggle;
import com.l.toggle.view.MyToggle.ToggleState;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		MyToggle mt=(MyToggle) findViewById(R.id.mt);
		
		mt.setToggleBackgroundResource(R.drawable.toggle_background);
		mt.setToggleButtonRescource(R.drawable.toggle_button);
		//mt.setToggleState(ToggleState.open);
	}

}
