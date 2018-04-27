package com.example.android.menu02;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OptionsMenuActivity02 extends Activity {
//OptionsMenuActivity主程式
	private static final int GALLERY = 0;
	private static final int MANU01 = 0;
	private static final int MANU02 = 1;
	private static final int MANU03 = 2;
	private static final int MANU04 = 3;
	private static final int MANU05 = 4;
	private static final int MANU06 = 5;
	private static final int MANU07 = 6;
	private static final int MANU08 = 7;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    //建立選項表單
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(GALLERY, MANU01, Menu.NONE, "1-春")
    	.setShortcut('0', 'a').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU02, Menu.NONE, "2-夏")
    	.setShortcut('1', 'b').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU03, Menu.NONE, "3-秋")
    	.setShortcut('2', 'c').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU04, Menu.NONE, "4-冬")
    	.setShortcut('3', 'd').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU05, Menu.NONE, "5-春")
    	.setShortcut('4', 'e').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU06, Menu.NONE, "6-夏")
    	.setShortcut('5', 'f');
    	menu.add(GALLERY, MANU07, Menu.NONE, "7-秋")
    	.setShortcut('6', 'g');
    	menu.add(GALLERY, MANU08, Menu.NONE, "8-冬")
    	.setShortcut('7', 'h');
    	return true;
    }
    //處理選項表單
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