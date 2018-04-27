package com.debby;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RadioButtonDemo extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button searchRadioShare = (Button) findViewById(R.id.searchRadioShare);
        Button searchRadioUser = (Button) findViewById(R.id.searchRadioUser);
        
        searchRadioShare.setOnClickListener(l);
        searchRadioUser.setOnClickListener(l);
    }
    
    private View.OnClickListener l = new OnClickListener(){

		public void onClick(View v) {
			if(v.getId()==R.id.searchRadioShare){
				Toast.makeText(RadioButtonDemo.this, "搜索微博", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(RadioButtonDemo.this, "搜索用户", Toast.LENGTH_LONG).show();
			}
			
		}
    	
    };
}