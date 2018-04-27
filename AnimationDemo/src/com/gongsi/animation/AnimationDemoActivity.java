package com.gongsi.animation;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class AnimationDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       /* 1.定义main.xml视图
        * 2.res中定义xml文件，类型Animation 四种补间动画效果演示
        * 3.找到对应键，开始动画。
        * */ 
        EditText tvTarget = (EditText)findViewById(R.id.tvTarget);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        tvTarget.startAnimation(animation);
    }
}