package com.qianfeng.animation.animation3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Animation3 extends Activity implements AdapterView.OnItemSelectedListener {
    private static final String[] INTERPOLATORS = {
            "Accelerate", "Decelerate", "Accelerate/Decelerate",
            "Anticipate", "Overshoot", "Anticipate/Overshoot",
            "Bounce"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_3);

        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, INTERPOLATORS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView parent, View v, int position, long id) {
        final View target = findViewById(R.id.target);
        final View targetParent = (View) target.getParent();

        Animation a = new TranslateAnimation(0.0f,
                targetParent.getWidth() - target.getWidth() - targetParent.getPaddingLeft() -
                targetParent.getPaddingRight(), 0.0f, 0.0f);
        /**
         * target表示TextView 它的父亲是LinearLayout
         * 我们要实现TextView左右摆动(y坐标不动)
         * 0.0f ---
         * targetParent.getWidth()得到targetParent的宽
         * target.getWidth() 得到TextView的宽度
         * targetParent.getPaddingLeft()就是targetParent相对于父布局的间隔
         * */
        a.setDuration(1000);
        /* 设置动画的持续时间 */
        a.setStartOffset(300);
        /* 就是设置动画开启之后多久开始运行动画 */
        a.setRepeatMode(Animation.RESTART);
        /* 设置重复的模式 */
        a.setRepeatCount(Animation.INFINITE);
        /* 设置重复的时间 INFINITE表示无限 */

        switch (position) {
            case 0:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.accelerate_interpolator));
                break;
            case 1:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.decelerate_interpolator));
                break;
            case 2:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.accelerate_decelerate_interpolator));
                break;
            case 3:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.anticipate_interpolator));
                break;
            case 4:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.overshoot_interpolator));
                break;
            case 5:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.anticipate_overshoot_interpolator));
                break;
            case 6:
                a.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.anim.bounce_interpolator));
                break;
        }

        target.startAnimation(a);
    }

    public void onNothingSelected(AdapterView parent) {
    }
}