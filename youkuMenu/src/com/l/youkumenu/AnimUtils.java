package com.l.youkumenu;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

public class AnimUtils {

	static int isAnimationSate=0;
	
	public static void setCloseAnim(RelativeLayout rl,int offset) {
		
		for (int i=0;i<rl.getChildCount();i++) {
			rl.getChildAt(i).setEnabled(false);			
		}

		RotateAnimation ra = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1f);
		
		ra.setAnimationListener(new MyAnimatiomListener());
		
		ra.setStartOffset(offset);
		ra.setDuration(500);
		ra.setFillAfter(true);
		
		rl.startAnimation(ra);

	}
	
	public static void setShowAnim(RelativeLayout rl,int offset){
		for (int i=0;i<rl.getChildCount();i++) {
			rl.getChildAt(i).setEnabled(true);			
		}
		RotateAnimation ra = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 1f);
		ra.setAnimationListener(new MyAnimatiomListener());
		ra.setStartOffset(offset);
		ra.setDuration(500);
		ra.setFillAfter(true);
		
		rl.startAnimation(ra);
	}
	
	static class MyAnimatiomListener implements AnimationListener{

		public void onAnimationStart(Animation animation) {
			isAnimationSate++;
		}

		public void onAnimationEnd(Animation animation) {
			isAnimationSate--;
		}

		public void onAnimationRepeat(Animation animation) {
			
		}
		
	}

}
