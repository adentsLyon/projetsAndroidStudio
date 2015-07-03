package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
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
    public ImageButton Btn_refresh;
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_position);
        setHeader();
        setArticleHeader();
        setUpMapIfNeeded();

        Btn_refresh = (ImageButton) findViewById(R.id.Btn_refresh);
        Btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpMap();
            }
        });

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
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();

            }
        }
    }

    private void setUpMap() {
        LocationManager lm = (LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        LatLng latlng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latlng).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        field_lat = (EditText) findViewById(R.id.field_lat);
        field_lon = (EditText) findViewById(R.id.field_lon);
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
}
