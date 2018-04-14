package com.l.toggle.view;

import com.l.toggle.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyToggle extends View {

	private Bitmap tbg;// 背景图片
	private Bitmap tbt;// 按钮图片
	boolean isDown=false;
	private int btWith;
	public MyToggle(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
	}

	public MyToggle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyToggle(Context context) {
		super(context);
	}

	public enum ToggleState {
		open, close;

	}

	/**
	 * 设置滑动开关的背景资源
	 * 
	 * @param id
	 */
	public void setToggleBackgroundResource(int id) {

		tbg = BitmapFactory.decodeResource(getResources(), id);

	}

	/**
	 * 设置滑动开关的按钮资源
	 * 
	 * @param id
	 */
	public void setToggleButtonRescource(int id) {

		tbt = BitmapFactory.decodeResource(getResources(), id);

	}

	ToggleState toggleState = ToggleState.close;
	private int with;
	

	public void setToggleState(ToggleState state) {
		this.toggleState = state;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 设置View的尺寸;
		setMeasuredDimension(tbg.getWidth(), tbg.getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawBitmap(tbg, 0, 0, null);
		with = btWith-tbt.getWidth()/2;
		
		
		if(!isDown){
			if(toggleState==ToggleState.open){
				
				canvas.drawBitmap(tbt, tbg.getWidth()-tbt.getWidth(), 0, null);
			}else{
				canvas.drawBitmap(tbt, 0, 0, null);
			}
			
			
			
		}else{
			
		
			if(with<0){
				with=0;
			}
			if(with>tbg.getWidth()-tbt.getWidth()){
				with=tbg.getWidth()-tbt.getWidth();
			}
			canvas.drawBitmap(tbt, with, 0, null);
			
		}
		
//		if (toggleState == ToggleState.open) {
//			canvas.drawBitmap(tbt, 0, 0, null);
//		} else {
//			canvas.drawBitmap(tbt, tbg.getWidth()-tbt.getWidth(), 0, null);
//		}

	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		isDown=true;
		switch (event.getAction()) {
		
		case MotionEvent.ACTION_DOWN:
			btWith = (int) event.getX();
			//System.out.println("抬起");
			break;
		case MotionEvent.ACTION_MOVE:
			
			btWith = (int) event.getX();
			//System.out.println("抬起");
			
			break;
		case MotionEvent.ACTION_UP:
			
			System.out.println("抬起");
			btWith = (int) event.getX();
			
			
			if(with<tbg.getWidth()/2-tbt.getWidth()/2){
				//canvas.drawBitmap(tbt, 0, 0, null);
				toggleState=ToggleState.close;
			}
			if(with>=tbg.getWidth()/2-tbt.getWidth()/2){
				//canvas.drawBitmap(tbt, tbg.getWidth()-tbt.getWidth(), 0, null);
				toggleState=ToggleState.open;
			}
			
			isDown=false;
			break;

		default:
			break;
		}
		
		invalidate();
		return true;
	}
}
