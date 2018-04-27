package com.example.android.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class HelloGPS extends MapActivity implements LocationListener {
	static final int INITIAL_ZOOM_LEVEL = 13;
	static final int INITIAL_LATITUDE = 25040255;
	static final int INITIAL_LONGITUDE = 121512377;
	MapController mc;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //����λ���б仯ʱ����¼���õķ���
        LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        //�趨MapView��������
        MapView mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        //�趨Zoom��С�͵�ͼ�����ĵ�
        mc = mapView.getController();
        mc.setZoom(INITIAL_ZOOM_LEVEL);
        mc.setCenter(new GeoPoint(INITIAL_LATITUDE,INITIAL_LONGITUDE)); 
    }
    @Override
	protected boolean isRouteDisplayed() {
		return false;
	}
    //���ĵ�λ���б仯ʱ��������ʾ��ͼ
	public void onLocationChanged(Location location) {
		GeoPoint gp = 
			new GeoPoint((int)(location.getLatitude()*1E6),
				         (int)(location.getLongitude()*1E6));
		mc.animateTo(gp);
	}
	public void onProviderDisabled(String provider) {
		// TODO 
	}
	public void onProviderEnabled(String provider) {
		// TODO��
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO 
	}
}
