package com.example.android.location00;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LocationGPS extends Activity implements LocationListener {
	private LocationManager mLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    	TextView mTextView08 = (TextView)findViewById(R.id.TextView08);  	
    	mTextView08.setText("Location-GPS");
    }
    @Override    
    protected void onResume() {        
    	if (mLocationManager != null) {            
    		mLocationManager.requestLocationUpdates(                
    				LocationManager.GPS_PROVIDER,
    				//LocationManager.NETWORK_PROVIDER,                
    				0,                
    				0,                
    				this);        
    		}                
    	super.onResume();    
    	} 
    @Override    
    protected void onPause() {        
    	if (mLocationManager != null) {            
    		mLocationManager.removeUpdates(this);        
    		}                
    	super.onPause();    
    	}  
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
    	mTextView03.setText("精度-Accuracy：  " + String.valueOf(location.getAccuracy()));
    	mTextView04.setText("标高-Latitude：  " + String.valueOf(location.getAltitude()));
    	mTextView05.setText("时间-Time：  " + String.valueOf(location.getTime()));
    	mTextView06.setText("速度-Speed：  " + String.valueOf(location.getSpeed()));
    	mTextView07.setText("方位-Bearing：  " + String.valueOf(location.getBearing()));   
    	}
    public void onProviderDisabled(String provider) {    
    	
    }     
    public void onProviderEnabled(String provider) {    
    	
    }  
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
}