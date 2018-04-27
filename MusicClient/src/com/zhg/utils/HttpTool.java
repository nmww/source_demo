package com.zhg.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpTool {
	public static final int GET = 1;
	public static final int POST = 2;
	public static final String URL= "http://192.168.1.2:8080/musiconline";
	public static InputStream getInputStream(String uri,Map<String,String> params,int method) throws  IOException{
		InputStream in = null;
		HttpEntity entity = getEntity(uri, params, method);
		if(entity!=null){
			in = entity.getContent();
		}
		return in;
	}
	
	public static  byte[] getBytes(String uri,Map<String,String> params,int method) throws IOException{
		byte[] bytes = null;
		HttpEntity entity = getEntity(uri, params, method);
		if(entity!=null){
			bytes = EntityUtils.toByteArray(entity);
		}
		return bytes;
	}
	
	private static HttpEntity getEntity(String uri,Map<String,String> params,int method) throws IOException{
		HttpEntity entity = null;
		HttpClient client = new DefaultHttpClient();
		HttpUriRequest request = null;
		switch(method){
		case GET:
			StringBuilder sb = new StringBuilder(uri);
			if(params!=null && !params.isEmpty()){
				sb.append('?');
				for(Map.Entry<String, String> entry : params.entrySet()){
					sb.append(entry.getKey())
					.append('=')
					.append(URLEncoder.encode(entry.getValue(),"utf-8"))
					.append('&');
				}
				sb.deleteCharAt(sb.length()-1);
				
			}
			request = new HttpGet(sb.toString());
			break;
		case POST:
			request = new HttpPost(uri);
			if(params!=null && !params.isEmpty()){
				ArrayList<NameValuePair> p = new ArrayList<NameValuePair>();
				for(Map.Entry<String, String> entry : params.entrySet()){
					BasicNameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
					p.add(pair);
				}
				((HttpPost)request).setEntity(new UrlEncodedFormEntity(p));
			}
			break;
		}
		HttpResponse response = client.execute(request);
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			entity = response.getEntity();
		}
		return entity;
	}
}
