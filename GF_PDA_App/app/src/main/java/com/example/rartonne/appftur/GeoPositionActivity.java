package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class GeoPositionActivity extends GlobalViews {
    private GoogleMap mMap;
    public EditText field_lat;
    public EditText field_lon;
    double longitude;
    double latitude;
    LocationManager lm;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_position);

        field_lat = (EditText) findViewById(R.id.field_lat);
        field_lon = (EditText) findViewById(R.id.field_lon);

        setHeader();
        setArticleHeader();

        lm = (LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //setUpMapIfNeeded();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        setUpMapIfNeeded();

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_geo_position, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpMapIfNeeded() {
        if(mMap != null)
            mMap.clear();
        field_lat.setText("");
        field_lon.setText("");
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        // Do a null check to confirm that we have not already instantiated the map.
        //if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            //mMap = null;
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            //if (mMap != null) {
                setUpMap();

            //}
        //}
    }

    private void setUpMap() {
        //Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        LatLng latlng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latlng).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        field_lat.setText(String.valueOf(latitude));
        field_lon.setText(String.valueOf(longitude));
    }

    public void confirmGps(View view){
        try {
            ScanlogDao scanlogDao = new ScanlogDao(this);
            scanlogDao.updateGps(GlobalClass.getGf_sec_id(), latitude, longitude);
            GlobalClass.setCheckGeo(true);
            Toast.makeText(this, "Data updated", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error updating data", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            String contents = intent.getStringExtra("SCAN_RESULT");
            homeQR(contents);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }

    public void refreshGps(View view) {
        setUpMapIfNeeded();
    }
}
