package com.zhg.service;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhg.entity.Music;
import com.zhg.music.R;
import com.zhg.utils.AsyncImageLoader;

public class MusicListAdapter extends BaseAdapter{
	private ArrayList<Music> musicList;
	private LayoutInflater inflater;
	private ListView ls;
	AsyncImageLoader loader = new AsyncImageLoader();
	
	public MusicListAdapter(Context context,ArrayList<Music> musicList, ListView ls){
		this.musicList = musicList;
		this.inflater = LayoutInflater.from(context);
		this.ls = ls;
	}
	
	public void addItem(Music music){
		if (musicList != null) {
			musicList.add(music);
			this.notifyDataSetChanged();
		}
	}
	
	public void removeItem(int position){
		if(musicList != null){
			musicList.remove(position);
			this.notifyDataSetChanged();/*改变更新*/
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musicList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return musicList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return musicList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.stuitem, null);
			holder.ivZhuanji = (ImageView)convertView.findViewById(R.id.ivZhuanji);
			holder.tvName = (TextView)convertView.findViewById(R.id.tvName);
			holder.tvSinger = (TextView)convertView.findViewById(R.id.tvSinger);
			holder.tvZhuanji = (TextView)convertView.findViewById(R.id.tvZhuanJi);
			holder.tvTime = (TextView)convertView.findViewById(R.id.tvTime);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		Music music = musicList.get(position);
		holder.ivZhuanji.setTag(music.getZjPath());
		Bitmap bitmap = loader.loadImage(music.getZjPath(), new AsyncImageLoader.CallBack() {
			
			@Override
			public void loaded(String path, Bitmap bitmap) {
				// TODO Auto-generated method stub
				ImageView iv = (ImageView)ls.findViewWithTag(path);
				if(iv!=null){
					iv.setImageBitmap(bitmap);
				}
			}
		});
		
		if(bitmap == null){
			holder.ivZhuanji.setImageResource(R.drawable.default1);
		} else{
			holder.ivZhuanji.setImageBitmap(bitmap);
		}
		holder.tvName.setText(music.getName());
		holder.tvSinger.setText(music.getSinger());
		holder.tvZhuanji.setText(music.getZhuanji());
		holder.tvTime.setText(music.getTime());
		
		return convertView;
	}
	class ViewHolder{
		ImageView ivZhuanji;
		TextView tvName;
		TextView tvSinger;
		TextView tvTime;
		TextView tvZhuanji;
	}
}
