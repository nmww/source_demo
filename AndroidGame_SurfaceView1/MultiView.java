package com.qianfeng.tingting.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class MultiView extends FrameLayout {
	Animation mInAnimation;
	Animation mOutAnimation;

	public MultiView(Context context) {
		super(context);
	}

	public MultiView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public MultiView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setInAnimation(Animation inAnimation) {
		mInAnimation = inAnimation;
	}

	public void setInAnimation(Context context, int resourceID) {
		setInAnimation(AnimationUtils.loadAnimation(context, resourceID));
	}

	public void setOutAnimation(Context context, int resourceID) {
		setOutAnimation(AnimationUtils.loadAnimation(context, resourceID));
	}

	public void setOutAnimation(Animation outAnimation) {
		mOutAnimation = outAnimation;
	}

	// public void selectView(int nView) {
	// if (nView >= 0 && nView < getChildCount()) {
	// hideAllView();
	// getChildAt(nView).setVisibility(View.VISIBLE);
	// }
	// }

	public void selectView(int nView) {
		if (nView >= 0 && nView < getChildCount()) {
			if (getCurrView()!=-1 && mOutAnimation!=null){
				View curChild=getCurrShowView();
				curChild.startAnimation(mOutAnimation);
			}
			hideAllView();
			View child = getChildAt(nView);
			if (mInAnimation != null) {
				child.startAnimation(mInAnimation);
			}
			child.setVisibility(View.VISIBLE);
		}
	}
	
	public View getCurrShowView(){
		int nTotalView = getChildCount();
		for (int i = 0; i < nTotalView; i++) {
			if (getChildAt(i).getVisibility() == View.VISIBLE)
				return getChildAt(i);
		}
		return null;
	}

	public void hideAllView() {
		int nTotalView = getChildCount();
		for (int i = 0; i < nTotalView; i++) {
			getChildAt(i).setVisibility(View.INVISIBLE);
		}
	}

	public int getCurrView() {
		int nTotalView = getChildCount();
		for (int i = 0; i < nTotalView; i++) {
			if (getChildAt(i).getVisibility() == View.VISIBLE)
				return i;
		}
		return -1;
	}

}
