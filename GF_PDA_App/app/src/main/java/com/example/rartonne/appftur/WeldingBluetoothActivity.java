package com.example.rartonne.appftur;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.util.Scanner;


public class WeldingBluetoothActivity extends GlobalViews {
    TextView tv_fusionCode;
    private RelativeLayout rel_Welding1;
    private RelativeLayout rel_Welding2;
    private RelativeLayout rel_Welding3;
    private RelativeLayout rel_Welding4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welding_bluetooth);

        tv_fusionCode = (TextView) findViewById(R.id.tv_fusion);
        rel_Welding1 = (RelativeLayout) findViewById(R.id.rel_Welding1);
        rel_Welding2 = (RelativeLayout) findViewById(R.id.rel_Welding2);
        rel_Welding3 = (RelativeLayout) findViewById(R.id.rel_Welding3);
        rel_Welding4 = (RelativeLayout) findViewById(R.id.rel_Welding4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welding_bluetooth, menu);
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

    public void startFusionClick (View view){
        SecIdDataDao secIdDataDao = new SecIdDataDao(this);
        SecIdData wcert = secIdDataDao.select(GlobalClass.getGf_sec_id(), "WCERT");
        SecIdData pipeLength = secIdDataDao.select(GlobalClass.getGf_sec_id(), "pl");
        SecIdData element1 = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e1");
        SecIdData element2 = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e2");
        SecIdData element3 = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e3");
        SecIdData element4 = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e4");
        SecIdData depth = secIdDataDao.select(GlobalClass.getGf_sec_id(), "td");
        ScanlogDao scanlogDao = new ScanlogDao(this);
        Scanlog scanlog = scanlogDao.select(GlobalClass.getGf_sec_id());
        String protocol = "";

        if(scanlog.getCustomer_order_nr() != null && !scanlog.getCustomer_order_nr().isEmpty())
            protocol = "Job number : " + scanlog.getCustomer_order_nr();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "WCERT") > 0)
            protocol += "\nWelder Certificate : " + wcert.getValue();
        if(scanlog.getSerial_wm_nr() != null && !scanlog.getSerial_wm_nr().isEmpty())
            protocol += "\nWelding Serial No Paired : " + scanlog.getSerial_wm_nr();
        if(scanlog.getWelding_sketch_nr() != null && !scanlog.getWelding_sketch_nr().isEmpty())
            protocol += "\nSketch number : " + scanlog.getWelding_sketch_nr();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "pl") > 0)
            protocol += "\nPipe length : " + pipeLength.getValue();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e1") > 0)
            protocol += "\nTraceability pipe 1 : " + element1.getValue();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e2") > 0)
            protocol += "\nTraceability pipe 2 : " + element2.getValue();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e3") > 0)
            protocol += "\nTraceability pipe 3 : " + element3.getValue();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e4") > 0)
            protocol += "\nTraceability pipe 4 : " + element4.getValue();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "td") > 0)
            protocol += "\nTrench depth : " + depth.getValue();
        if(scanlog.getGps_lat() != 0 && scanlog.getGps_long() != 0)
            protocol += "\nGPS : lat " + scanlog.getGps_lat() + " lon " + scanlog.getGps_long();
        if(tv_fusionCode.getText().toString() != null && !tv_fusionCode.getText().toString().isEmpty())
            protocol += "\nCurrent Fusion Data : " + tv_fusionCode.getText().toString();
        Toast.makeText(this, protocol,Toast.LENGTH_LONG).show();
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "fu1") > 0)
            rel_Welding1.setVisibility(View.VISIBLE);
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "fu2") > 0)
            rel_Welding2.setVisibility(View.VISIBLE);
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "fu3") > 0)
            rel_Welding3.setVisibility(View.VISIBLE);
        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "fu4") > 0)
            rel_Welding4.setVisibility(View.VISIBLE);

    }
    private void fillFusionCode() {
        if (tv_fusionCode.getText().toString() != null && !tv_fusionCode.getText().toString().isEmpty()) {
            tv_fusionCode.setBackgroundColor(Color.parseColor("#66c266"));
            tv_fusionCode.setEnabled(false);
        }
    }
}
