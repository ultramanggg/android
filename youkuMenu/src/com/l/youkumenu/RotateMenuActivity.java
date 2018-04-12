package com.l.youkumenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RotateMenuActivity extends Activity implements OnClickListener {

	private RelativeLayout level1, level2, level3;
	private ImageView menu;
	boolean isShowLevel2 = true;
	boolean isShowLevel3 = true;
	boolean KeyMenu = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initClickListener();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_MENU){
			if(KeyMenu){
				
				int offset=0;
				if(isShowLevel3){
					 AnimUtils.setCloseAnim(level3,offset);
					 offset+=200;
					 isShowLevel3=false;
				}
				if(isShowLevel2){
					 AnimUtils.setCloseAnim(level2,offset);
					 offset+=200;
					 isShowLevel2=false;
				}
				 AnimUtils.setCloseAnim(level1,offset);
				 level1.setEnabled(false);
				
			 
			}else{
				
				 AnimUtils.setShowAnim(level1,0);
				 AnimUtils.setShowAnim(level2,200);
				 AnimUtils.setShowAnim(level3,400);
				 isShowLevel2=true;
				 isShowLevel3=true;
				 level1.setEnabled(true);
			}
			
			KeyMenu=!KeyMenu;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	private void initClickListener() {

		level1.setOnClickListener(this);
		menu.setOnClickListener(this);

	}

	private void initView() {

		level1 = (RelativeLayout) findViewById(R.id.level1);
		level2 = (RelativeLayout) findViewById(R.id.level2);
		level3 = (RelativeLayout) findViewById(R.id.level3);
		menu = (ImageView) findViewById(R.id.menu);

	}

	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.level1:
			if (AnimUtils.isAnimationSate == 0) {

				if (isShowLevel2) {
					int offset = 0;
					if (isShowLevel3) {
						AnimUtils.setCloseAnim(level3, offset);
						offset += 200;
						isShowLevel3 = false;
					}
					AnimUtils.setCloseAnim(level2, offset);

				} else {
					AnimUtils.setShowAnim(level2,0);
				}

				isShowLevel2 = !isShowLevel2;

			}

			break;
		case R.id.menu:
			if (AnimUtils.isAnimationSate == 0) {
				if (isShowLevel3) {

					AnimUtils.setCloseAnim(level3, 0);

				} else {
					AnimUtils.setShowAnim(level3,0);
				}
				isShowLevel3 = !isShowLevel3;

			}

			break;
		default:
			break;
		}

	}

}
