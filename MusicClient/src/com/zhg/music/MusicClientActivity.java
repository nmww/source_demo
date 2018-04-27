package com.zhg.music;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AbsListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.zhg.entity.Music;
import com.zhg.service.MusicListAdapter;
import com.zhg.service.MusicXmlParser;
import com.zhg.utils.HttpTool;

public class MusicClientActivity extends Activity {
	private static final int DOWNLOAD = 1;
	private static final int PLAY = 2;
	private static final int DELETE = 3;
	private static final int DETAILS = 4;
	private ListView lvSounds ;
	private MusicListAdapter adapter;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0:
				Music music = (Music)msg.obj;
				adapter.addItem(music);
				break;
			case 1:
				Toast.makeText(MusicClientActivity.this,
						"finished for download music.xml", Toast.LENGTH_SHORT);
				break;
				
			}
		}
		
	};
	private void findViews(){
		lvSounds = (ListView)findViewById(R.id.listSounds);
		adapter = new MusicListAdapter(this, new ArrayList<Music>(), lvSounds);
		lvSounds.setAdapter(adapter);
		
		downList();
		
		lvSounds.setOnScrollListener(new ListView.OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(view.getLastVisiblePosition() == adapter.getCount()-1)
					downList();
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		lvSounds.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.setHeaderTitle("菜单标题头").setHeaderIcon(R.drawable.icon);
				menu.add(0, DOWNLOAD, 1, "下载");
				menu.add(0, PLAY, 2, "播放");
				menu.add(0, DELETE, 3, "删除");
				menu.add(0, DETAILS, 4, "详情");
			}
		});
	}	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		/*item.getMenuInfo()通过此项可以获得具体是listview的第几项的弹出窗口的第几个选项*/
		final int position = ((AdapterContextMenuInfo)item.getMenuInfo()).position;
		switch (item.getItemId()) {
		case DOWNLOAD:
			break;
		case PLAY:
			break;
		case DELETE:
			Builder builder = new Builder(this);
			builder.setTitle("信息标题头")
			.setMessage("提示信息内容")
			.setIcon(R.drawable.default1)
			.setCancelable(false)
			.setPositiveButton("正选项/正确", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					adapter.removeItem(position);
				}
			}).setNegativeButton("反面/取消", null)
			.show();
			break;
		case DETAILS:
			Music music = (Music)adapter.getItem(position);
			Intent intent = new Intent(MusicClientActivity.this, MusicDetailActivity.class);
			intent.putExtra("musicName", music.getName());
			startActivity(intent);
			break;
		}
		
		return super.onContextItemSelected(item);
		
	}

	private void downList(){
		new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
			try {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("pageNo", String.valueOf(1));
				InputStream in = HttpTool.getInputStream
						(HttpTool.URL + "/musiclist", map, HttpTool.POST);
				MusicXmlParser parser = new MusicXmlParser(handler);
				parser.parse(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}.start();
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);/*渐出效果*/
        
        setContentView(R.layout.main);
        findViews();
    }
}