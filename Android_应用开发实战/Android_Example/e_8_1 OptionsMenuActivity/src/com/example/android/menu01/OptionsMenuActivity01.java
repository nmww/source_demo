package com.example.android.menu01;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OptionsMenuActivity01 extends Activity {
	private static final int GALLERY = 0;
	private static final int MANU01 = 0;
	private static final int MANU02 = 1;
	private static final int MANU03 = 2;
	private static final int MANU04 = 3;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(GALLERY, MANU01, Menu.NONE, "0-春")
    	.setShortcut('0', 'k').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU02, Menu.NONE, "1-夏")
    	.setShortcut('1', 's').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU03, Menu.NONE, "2-秋")
    	.setShortcut('2', 'u').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU04, Menu.NONE, "3-冬")
    	.setShortcut('3', 'n').setIcon(R.drawable.icon);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getGroupId()) {
    	case GALLERY:
    		String itemid = Integer.toString(item.getItemId());
    		String title = item.getTitle().toString();
    		showAlertDialog("项目ID = " + itemid + "\n" +
    				"标题 = " + title);
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    private void showAlertDialog(String message) {
    	new AlertDialog.Builder(this)
    	.setTitle("选择菜单项")
    	.setMessage(message)
    	.setPositiveButton("关闭", null)
    	.show();
    }
}