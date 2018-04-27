package com.zhg.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Handler;

public class StreamTool {
	private Handler handler;
	public StreamTool(Handler handler){
		this.handler = handler;
	}
	
	public void read(InputStream in, OutputStream out) throws IOException{
		if(in!=null && out !=null){
			int len = 0;
			byte[] bytes = new byte[1024];
			int downloadedLength = 0;
			while((len = in.read(bytes)) != -1){
				out.write(bytes, 0, len);
			}
		}
	}
	
	public void close(InputStream in, OutputStream out) throws IOException{
		if(in != null){
			in.close();
			in = null;
		}
		if(out != null){
			out.close();
			out = null;
		}
	}
	
	public byte[] getBytes(InputStream in) throws IOException{
		byte[] bytes = null;
		if (in != null){
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			read(in, out);
			bytes = out.toByteArray();
			close(in, out);
		}
		return bytes;
	}
	
	public void saveTo (InputStream in, String path) throws IOException{
		if (in!=null && path!=null){
			File file = new File(path);
			File dir = file.getParentFile();
			if(!dir.exists())
				dir.mkdirs();
			
			FileOutputStream out = new FileOutputStream(file);
			read(in, out);
			close(in, out);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
