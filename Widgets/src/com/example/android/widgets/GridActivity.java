package com.example.android.widgets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridActivity extends Activity {
	//GridActivity~{Vw3LJ=~}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_activity);	
		GridView gridview = (GridView)findViewById(R.id.GridView01);    
		gridview.setAdapter(new ImageAdapter(this));    
		gridview.setOnItemClickListener(new OnItemClickListener() {        
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {            
				Toast.makeText(GridActivity.this, "" + position, Toast.LENGTH_SHORT).show();        
				}    
			});
		}
	//~{=(~}?ImageAdapter??~{3LJ=~}
	public class ImageAdapter extends BaseAdapter {    
		private Context mContext;    
		public ImageAdapter(Context c) {        
			mContext = c;    
			}    
		public int getCount() {        
			return mThumbIds.length;    
			}    
		public Object getItem(int position) {        
			return null;    
			}    
		public long getItemId(int position) {        
			return 0;    
			}    
		//~{=(A"R;~}?imageView?~{02VCC?R;~}??~{Oq#,~}?~{La9)~}?Adapter    
		public View getView(int position, View convertView, ViewGroup parent) {        
			ImageView imageView;        
			if (convertView == null) {  
				// if it's not recycled, initialize some attributes            
				imageView = new ImageView(mContext);            
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));            
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);            
				imageView.setPadding(8, 8, 8, 8);        
				} else {            
				imageView = (ImageView) convertView;        
				}        
			imageView.setImageResource(mThumbIds[position]);        
			return imageView;    
			}    
		//?~{6(~}?~{Oq~}?~{08~}?~{AP~}   
		private Integer[] mThumbIds = {            
				R.drawable.sakura01s, R.drawable.sakura02s,            
				R.drawable.sakura03s, R.drawable.sakura04s,            
				R.drawable.sakura05s, R.drawable.sakura06s,            
				R.drawable.sakura07s, R.drawable.sakura08s,            
				R.drawable.sakura09s, R.drawable.sakura10s,
				R.drawable.sakura11s, R.drawable.sakura12s,            
				R.drawable.sakura02s, R.drawable.sakura04s,             
				R.drawable.sakura06s, R.drawable.sakura08s,
				R.drawable.sakura10s, R.drawable.sakura12s,
				R.drawable.icon, R.drawable.icon, R.drawable.icon    
				};
		public View getView1(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
	}	
}
