package com.zhg.service;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.os.Handler;
import android.os.Message;

import com.zhg.entity.Music;

public class MusicXmlParser {
	Handler handler = null;
	public MusicXmlParser(Handler handler){
		this.handler = handler;
	}
	
	public void parse(InputStream in) throws Exception{
		SAXParserFactory factory = null;
		SAXParser parser = null;
		MusicHandler handler = null;
		
		factory = SAXParserFactory.newInstance();
		parser = factory.newSAXParser();
		handler = new MusicHandler();
		parser.parse(in, handler);
	}
	
	class MusicHandler extends DefaultHandler{
		Music currentMusic;
		String tagName;
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			if(tagName!=null){
				String data = new String(ch,start,length);
				if(tagName.equals("name")){
					currentMusic.setName(data);
				}else if(tagName.equals("singer")){
					currentMusic.setSinger(data);
				}else if(tagName.equals("zuoci")){
					currentMusic.setZuoci(data);
				}else if(tagName.equals("zuoqu")){
					currentMusic.setZuoqu(data);
				}else if(tagName.equals("zhuanji")){
					currentMusic.setZhuanji(data);
				}else if(tagName.equals("zjpath")){
					currentMusic.setZjPath(data);
				}else if(tagName.equals("musicpath")){
					currentMusic.setMusicPath(data);
				}else if(tagName.equals("type")){
					currentMusic.setType(data);
				}else if(tagName.equals("time")){
					currentMusic.setTime(data);
				}else if(tagName.equals("downcount")){
					currentMusic.setDownCount(Integer.parseInt(data));
				}else if(tagName.equals("favcount")){
					currentMusic.setFavCount(Integer.parseInt(data));
				}
			}
		}
		
		
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			if(handler != null){
				Message msg = handler.obtainMessage();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			if(localName.equalsIgnoreCase("music")){
				if(handler!=null){
					Message msg = handler.obtainMessage();
					msg.what = 0;
					msg.obj = currentMusic;
					handler.sendMessage(msg);
				}
				currentMusic = null;
			}
			tagName = null;
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			if(localName.equalsIgnoreCase("music")){
				currentMusic = new Music();
				currentMusic.setId(Integer.parseInt(attributes.getValue("id")));
			}
			tagName = localName;
		}
		
	}
}











