package com.example.android.menu04;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class SubMenuActivity extends Activity {
//SubMenuActivity主程序
	private static final int GALLERY = 0;
	private static final int SUBMANU01 = 7;
	private static final int SUBMANU02 = 8;
	private static final int MANU01 = 1;
	private static final int MANU02 = 2;
	private static final int MANU03 = 3;
	private static final int MANU04 = 4;
	private static final int MANU05 = 5;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    //建立子菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	SubMenu fileMenu = menu.addSubMenu(GALLERY, SUBMANU01, Menu.NONE, "File");  
    	SubMenu editMenu = menu.addSubMenu(GALLERY, SUBMANU02, Menu.NONE, "Edit");  
    	fileMenu.add(GALLERY, MANU01, Menu.NONE, "new");  
    	fileMenu.add(GALLERY, MANU02, Menu.NONE, "open");  
    	fileMenu.add(GALLERY, MANU03, Menu.NONE, "save");  
    	editMenu.add(GALLERY, MANU04, Menu.NONE, "undo");  
    	editMenu.add(GALLERY, MANU05, Menu.NONE, "redo");  
    	return true;
    }
    //处理子菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case MANU01:
    	case MANU02:
    	case MANU03:
    	case MANU04:
    	case MANU05:
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