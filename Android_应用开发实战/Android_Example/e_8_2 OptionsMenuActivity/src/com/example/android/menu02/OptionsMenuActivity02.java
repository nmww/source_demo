package com.example.android.menu02;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class OptionsMenuActivity02 extends Activity {
//OptionsMenuActivity����ʽ
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
    //�����x헱��
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(GALLERY, MANU01, Menu.NONE, "1-��")
    	.setShortcut('0', 'a').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU02, Menu.NONE, "2-��")
    	.setShortcut('1', 'b').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU03, Menu.NONE, "3-��")
    	.setShortcut('2', 'c').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU04, Menu.NONE, "4-��")
    	.setShortcut('3', 'd').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU05, Menu.NONE, "5-��")
    	.setShortcut('4', 'e').setIcon(R.drawable.icon);
    	menu.add(GALLERY, MANU06, Menu.NONE, "6-��")
    	.setShortcut('5', 'f');
    	menu.add(GALLERY, MANU07, Menu.NONE, "7-��")
    	.setShortcut('6', 'g');
    	menu.add(GALLERY, MANU08, Menu.NONE, "8-��")
    	.setShortcut('7', 'h');
    	return true;
    }
    //̎���x헱��
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getGroupId()) {
    	case GALLERY:
    		String itemid = Integer.toString(item.getItemId());
    		String title = item.getTitle().toString();
    		showAlertDialog("��ĿID = " + itemid + "\n" +
    				"���� = " + title);
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    private void showAlertDialog(String message) {
    	new AlertDialog.Builder(this)
    	.setTitle("ѡ��˵���")
    	.setMessage(message)
    	.setPositiveButton("�ر�", null)
    	.show();
    }
}