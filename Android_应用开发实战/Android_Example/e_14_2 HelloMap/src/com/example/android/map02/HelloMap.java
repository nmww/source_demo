package com.example.android.map02;

import android.os.Bundle;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class HelloMap extends MapActivity {
	static final int INITIAL_ZOOM_LEVEL = 12;
	static final int INITIAL_LATITUDE = 25040255;
	static final int INITIAL_LONGITUDE = 121512377;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //设定MapView可以缩放
        MapView mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        //设定Zoom大小和地图的中心点
        MapController mc = mapView.getController();
        mc.setZoom(INITIAL_ZOOM_LEVEL);
        mc.setCenter(new GeoPoint(INITIAL_LATITUDE,INITIAL_LONGITUDE));
    }
	@Override
    protected boolean isRouteDisplayed() {
        return false;
    } 
}