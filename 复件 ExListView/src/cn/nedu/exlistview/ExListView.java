package cn.nedu.exlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExListView extends Activity {
	private static final String G_TEXT = "g_text";

	private static final String C_TEXT1 = "c_text1";
	private static final String C_TEXT2 = "c_text2";

	List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

	ExAdapter adapter;
	ExpandableListView exList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		for (int i = 0; i < 5; i++) {
			Map<String, String> curGroupMap = new HashMap<String, String>();
			groupData.add(curGroupMap);
			if (i == 0) {
				curGroupMap.put(G_TEXT, "在线好友 " + "(" + (i+10) + ")");
			} else if (i == 1) {
				curGroupMap.put(G_TEXT, "嘻嘻哈哈(" + i + "/20" + ")");
			} else if (i == 2) {
				curGroupMap.put(G_TEXT, "老师(" + i + "/23" + ")");
			} else if (i == 3) {
				curGroupMap.put(G_TEXT, "父母(" + i + "/10" + ")");
			} else {
				curGroupMap.put(G_TEXT, "其他(" + i + "/35" + ")");
			}

			List<Map<String, String>> children = new ArrayList<Map<String, String>>();
			for (int j = 0; j < 5; j++) {
				Map<String, String> curChildMap = new HashMap<String, String>();
				curChildMap.put(C_TEXT1, "Name..1. " + j);
				curChildMap.put(C_TEXT2, "[doing something.e." + j + "]");
				children.add(curChildMap);
			}
			childData.add(children);
		}

		adapter = new ExAdapter(ExListView.this);
		exList = (ExpandableListView) findViewById(R.id.list);
		exList.setCacheColorHint(0x00000000);
		exList.setAdapter(adapter);
		exList.setGroupIndicator(null);
		exList.setDivider(null);
//		
		exList.setOnChildClickListener(new OnChildClickListener() {
			
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(ExListView.this, "弹出聊天窗口:  组:" + groupPosition + " de " + childPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	class ExAdapter extends BaseExpandableListAdapter {
		ExListView exlistview;

		public ExAdapter(ExListView elv) {
			super();
			exlistview = elv;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.member_listview, null);
			}
			// 设置组名称
			TextView title = (TextView) view.findViewById(R.id.content_001);
			title.setText(getGroup(groupPosition).toString());

			ImageView image = (ImageView) view.findViewById(R.id.tubiao);
			if (isExpanded)
				image.setBackgroundResource(R.drawable.btn_browser2);
			else
				image.setBackgroundResource(R.drawable.btn_browser);

			return view;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public Object getGroup(int groupPosition) {
			return groupData.get(groupPosition).get(G_TEXT).toString();
		}

		public int getGroupCount() {
			return groupData.size();

		}

		// ******************getChildView********************
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.member_childitem_text, null);
			}
			// 成员名称
			TextView title = (TextView) view
					.findViewById(R.id.child_text);
//			Log.v("info",C_TEXT1 + " in the C_TEXT1 adapter setText childData......> " + groupPosition);
			Log.v("info",childData.get(groupPosition).get(childPosition).get(C_TEXT1));
			
			title.setText((CharSequence) getChild(groupPosition,childPosition));
			// 成员当前活动文字
			final TextView title2 = (TextView) view
					.findViewById(R.id.child_text2);
			title2.setText(childData.get(groupPosition).get(childPosition)
					.get(C_TEXT2).toString());

			// 设置成员头像
			ImageView iv_child_head = (ImageView) view
					.findViewById(R.id.child_image);
			iv_child_head.setBackgroundResource(R.drawable.child_image);
			// 成员在线图标
			ImageView iv_child_online = (ImageView) view
					.findViewById(R.id.child_image_online);
			iv_child_online.setBackgroundResource(R.drawable.btn_browser);
			// 成员勋章图标
			ImageView iv_child_medal01 = (ImageView) view
					.findViewById(R.id.child_image_medal01);
			iv_child_medal01.setBackgroundResource(R.drawable.btn_browser);
			ImageView iv_child_medal02 = (ImageView) view
					.findViewById(R.id.child_image_medal02);
			iv_child_medal02.setBackgroundResource(R.drawable.btn_browser);
			ImageView iv_child_medal03 = (ImageView) view
					.findViewById(R.id.child_image_medal03);
			iv_child_medal03.setBackgroundResource(R.drawable.btn_browser);
			ImageView iv_child_medal04 = (ImageView) view
					.findViewById(R.id.child_image_medal04);
			iv_child_medal04.setBackgroundResource(R.drawable.btn_browser);
			return view;
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public Object getChild(int groupPosition, int childPosition) {
			return childData.get(groupPosition).get(childPosition).get(C_TEXT1)
					.toString();
		}

		public int getChildrenCount(int groupPosition) {
			return childData.get(groupPosition).size();
		}

		// **************************************
		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}
}