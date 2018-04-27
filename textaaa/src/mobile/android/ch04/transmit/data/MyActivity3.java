package mobile.android.ch04.transmit.data;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Base64;
import android.widget.TextView;

public class MyActivity3 extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myactivity);
		TextView textView = (TextView) findViewById(R.id.textview);

		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		//从剪切板获得base64编码格式的字符串
		String base64Str = clipboard.getText().toString();
		//将base64格式的字符串还原成byte[]格式的数据
		byte[] buffer = Base64.decode(base64Str, Base64.DEFAULT);

		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		try
		{
			ObjectInputStream ois = new ObjectInputStream(bais);
			//将byte[]数据还原成Data对象
			Data data = (Data) ois.readObject();
			//输出原始的Base64格式的字符串和Data对象中的字段值
			textView.setText(base64Str + "\n\ndata.id��" + data.id
					+ "\ndata.name" + data.name);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
	}

}
