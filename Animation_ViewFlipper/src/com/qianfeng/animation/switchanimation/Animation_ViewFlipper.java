package com.qianfeng.animation.switchanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

public class Animation_ViewFlipper extends Activity {
	ViewFlipper vf;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        vf = (ViewFlipper)this.findViewById(R.id.flipper);
        vf.startFlipping();
        
        Animation in = AnimationUtils.loadAnimation(
        		this, R.anim.left_in);
        Animation out = AnimationUtils.loadAnimation(
        		this, R.anim.left_out);
//        vf.setInAnimation(this, R.anim.left_in);
//        vf.setInAnimation(this, R.anim.left_out);
        vf.setInAnimation(in);
        vf.setOutAnimation(out);
    }
}