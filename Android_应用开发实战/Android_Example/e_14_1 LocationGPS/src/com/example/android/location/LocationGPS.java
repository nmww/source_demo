package com.example.android.location;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationGPS extends Activity {
	private LocationManager mLocationManager;
	//LocationGPS主程序，实例化一个LocationManager对象mLocationManager
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    	TextView mTextView08 = (TextView)findViewById(R.id.TextView08);  	
    	mTextView08.setText("Location-GPS");
    }
    //在Resume阶段设定mLocationListener界面，可以获得地理位置的更新数据
    @Override    
    protected void onResume() {        
    	if (mLocationManager != null) {            
    		mLocationManager.requestLocationUpdates(                
    				LocationManager.GPS_PROVIDER,                
    				0,                
    				0,                
    				mLocationListener);        
    		}                
    	super.onResume();    
    	} 
    //在Pause阶段关闭mLocationListener界面，不再获得地理位置的更新数据
    @Override    
    protected void onPause() {        
    	if (mLocationManager != null) {            
    		mLocationManager.removeUpdates(mLocationListener);        
    		}                
    	super.onPause();    
    	}
    //实例化mLocationListener界面
    public LocationListener mLocationListener = new LocationListener() 
    { 
    	//GPS位置数据被更新
    	public void onLocationChanged(Location location) {        
    		TextView mTextView01 = (TextView)findViewById(R.id.TextView01);
    		TextView mTextView02 = (TextView)findViewById(R.id.TextView02);
    		TextView mTextView03 = (TextView)findViewById(R.id.TextView03);
    		TextView mTextView04 = (TextView)findViewById(R.id.TextView04);
    		TextView mTextView05 = (TextView)findViewById(R.id.TextView05);
    		TextView mTextView06 = (TextView)findViewById(R.id.TextView06);
    		TextView mTextView07 = (TextView)findViewById(R.id.TextView07);
    		mTextView01.setText("纬度-Latitude：  " + String.valueOf(location.getLatitude()));
    		mTextView02.setText("经度-Longitude：  " + String.valueOf(location.getLongitude()));
    		mTextView03.setText("精确度-Accuracy：  " + String.valueOf(location.getAccuracy()));
    		mTextView04.setText("标高-Latitude：  " + String.valueOf(location.getAltitude()));
    		mTextView05.setText("时间-Time：  " + String.valueOf(location.getTime()));
    		mTextView06.setText("速度-Speed：  " + String.valueOf(location.getSpeed()));
    		mTextView07.setText("方位-Bearing：  " + String.valueOf(location.getBearing()));   
    		}
    	public void onProviderDisabled(String provider) {    
    	
    	}     
    	public void onProviderEnabled(String provider) {    
    	
    	}  
    	//GPS位置数据的状态被更新
    	public void onStatusChanged(String provider, int status, Bundle extras) {        
    		switch (status) {        
    			case LocationProvider.AVAILABLE:            
    				Log.v("Status", "AVAILABLE");            
    				break;        
    			case LocationProvider.OUT_OF_SERVICE:            
    				Log.v("Status", "OUT_OF_SERVICE");            
    				break;        
    			case LocationProvider.TEMPORARILY_UNAVAILABLE:            
    				Log.v("Status", "TEMPORARILY_UNAVAILABLE");            
    				break;        
    				}    
    		}
    };
}