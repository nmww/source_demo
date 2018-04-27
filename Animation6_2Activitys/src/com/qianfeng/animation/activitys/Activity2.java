package com.qianfeng.animation.activitys;

import android.app.Activity;
import android.os.Bundle;

public class Activity2 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity2);
    }
}
