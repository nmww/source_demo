package com.qianfeng.animation.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Animation_2Activitys extends Activity {
	
	Button b1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Animation_2Activitys.this,
						Activity2.class);
				startActivity(intent);
			}
		});
    }
    
	@Override
	protected void onRestart() {
		super.onRestart();
        this.overridePendingTransition(
        		R.anim.fadein, R.anim.fadeout);
	}
    
}