package com.example.android.menu03;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;

public class ContextMenuActivity extends Activity {
//ContextMenuActivity主程序
	final static int CONTEXT_ITEM_ID1 = 1000;
	final static int CONTEXT_ITEM_ID2 = 1001;
    /** Called when the activity is first created. */
    //登记上下文菜单
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("Button-ContextMenu");
        registerForContextMenu(button);
        setContentView(button);
    }
    //建立上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	menu.add(0, CONTEXT_ITEM_ID1, 0, "CONTEXT_ITEM_1");
    	menu.add(0, CONTEXT_ITEM_ID2, 0, "CONTEXT_ITEM_2");
    }
    //处理上下文菜单
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Title");
    	final String message;
    	switch (item.getItemId()) {
		case CONTEXT_ITEM_ID1:
			message = "按下了CONTEXT_ITEM_1";
			break;
		case CONTEXT_ITEM_ID2:
			message = "按下了CONTEXT_ITEM_2";
			break;
		default:
			message = "Default";
			break;
		}
    	builder.setMessage(message);
    	builder.setPositiveButton("OK",new android.content.DialogInterface.OnClickListener() {
	        public void onClick(android.content.DialogInterface dialog,int whichButton) {
	            setResult(RESULT_OK);
	        }
	    });
    	builder.create();
    	builder.show();
    	return true;
    }
}