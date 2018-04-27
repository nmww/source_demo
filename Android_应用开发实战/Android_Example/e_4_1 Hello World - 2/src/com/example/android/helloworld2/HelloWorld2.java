package com.example.android.helloworld2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelloWorld2 extends Activity {
	OnClickListener listener1 = null;
	Button button1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener1 = new OnClickListener() {
			public void onClick(View v) {
				TextView text_view = (TextView) findViewById(R.id.TextView01);
		        CharSequence text_view_old = text_view.getText();
		        text_view.setText("修改前: "+ text_view_old +"\n增加新的信息: Hello World again !");
			}
		};
        setContentView(R.layout.main); 
        button1 = (Button) findViewById(R.id.Button01);
		button1.setOnClickListener(listener1);
    }
}
