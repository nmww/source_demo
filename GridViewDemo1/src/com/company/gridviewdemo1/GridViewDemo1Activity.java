package com.company.gridviewdemo1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GridViewDemo1Activity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		GridView gridview = (GridView) findViewById(R.id.gridview);
		GridView gridview1 = (GridView) findViewById(R.id.gridview1);
		
//		// 生成动态数组，并且转入数据
//		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < 50; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			if (i % 2 == 0) {
//				map.put("ItemImage", R.drawable.ic_launcher);// 添加图像资源的ID
//				map.put("ItemText", "NO." + String.valueOf(i));// 按序号做ItemText
//			} else {
//				map.put("ItemImage", R.drawable.ic_launcher);// 添加图像资源的ID
//				map.put("ItemText", "NO." + String.valueOf(i)
//						+ "0000000000000000000000000000000"
//						+ "00000000000000000000000000000000000000000000");// 按序号做ItemText
//			}
//			lstImageItem.add(map);
//		}
//		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
//		SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
//				lstImageItem,// 数据来源
//				R.layout.night_item,// night_item的XML实现
//
//				// 动态数组与ImageItem对应的子项
//				new String[] { "ItemImage", "ItemText" },
//
//				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
//				new int[] { R.id.ItemImage, R.id.ItemText });
//		
//		
//		
//		//---------第二个简单的自定义Adapter--------------------------------------
//		// 生成动态数组，并且转入数据
//		ArrayList<HashMap<String, Object>> lstImageItem1 = new ArrayList<HashMap<String, Object>>();
//		for (int i = 0; i < 50; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//				map.put("ItemImage1", R.drawable.ic_launcher);// 添加图像资源的ID
//				map.put("ItemText1", "NO." + String.valueOf(i));// 按序号做ItemText
//			lstImageItem1.add(map);
//		}
//		// 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
//		SimpleAdapter saImageItems1 = new SimpleAdapter(this, // 没什么解释
//				lstImageItem1,// 数据来源
//				R.layout.night_item1,// night_item的XML实现
//
//				// 动态数组与ImageItem对应的子项
//				new String[] { "ItemImage1", "ItemText1" },
//
//				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
//				new int[] { R.id.ItemImage1, R.id.ItemText1 });
		
		ListAdapter saImageItems = new GridAdapter();
		// 添加并且显示
		gridview.setAdapter(saImageItems);
		gridview1.setAdapter(saImageItems);
		// 添加消息处理
//		gridview.setOnItemClickListener(new ItemClickListener());
//		gridview1.setOnItemClickListener(new ItemClickListener());
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			// 在本例中arg2=arg3
			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);
			// 显示所选Item的ItemText
			setTitle((String) item.get("ItemText"));
			// Toast.makeText(GridViewDemoActivity.this,
			// String.valueOf(item.get("")), Toast.LENGTH_SHORT);
		}
	}
	
	//自定义Adapter
	private class GridAdapter extends BaseAdapter {
		private Context context = null;// 上下文  
	    private ArrayList<String> list = null;// 数据源  
	    String[] str = new String[]{"111","222","111","222",
	    		"111","222","111","222","111","222",
	    		"111","222","111","222","111","222","111","222",
	    		"111","222","111","222","111","222","333"};
	    
	    // 下面三个是实现抽象函数，可以无视  
	    public int getCount() {  
	        return str.length;  
	    }  
	    public Object getItem(int position) {  
	        return str[position];  
	    }  
	    public long getItemId(int position) {  
	        return position;  
	    }  

		@Override
		public View getView( final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			if (convertView == null) {
				//获取布局文件
				convertView = getLayoutInflater().inflate(R.layout.night_item, null);
			}
			//获取控件,并监听
			ImageView imageview = (ImageView) convertView.findViewById(R.id.ItemImage);
			TextView textview = (TextView) convertView.findViewById(R.id.ItemText);
			
			imageview.setBackgroundResource(R.drawable.ic_launcher);
			imageview.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 Toast.makeText(GridViewDemo1Activity.this,
							position+ ":-----item 中 ImageView 点击事件",Toast.LENGTH_SHORT).show();
				}
			});
			
			textview.setText("1111111111111");
			textview.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(GridViewDemo1Activity.this,
							position+ ":-----item 中 TextView 点击事件",Toast.LENGTH_SHORT).show();
				}
			});
			return convertView;
		}
	}
}