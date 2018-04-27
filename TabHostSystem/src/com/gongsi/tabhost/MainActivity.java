package com.gongsi.tabhost;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {
	private void findViews(){
		TabHost tabhost = getTabHost();
		
		tabhost.addTab(tabhost.newTabSpec("search")
			.setIndicator("search", getResources().getDrawable(android.R.drawable.ic_menu_search))
			.setContent(new Intent(this, SearchActivity.class))
				);
	}
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
    }

}