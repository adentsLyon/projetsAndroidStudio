package com.example.rartonne.appftur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.FittingDao;
import com.example.rartonne.appftur.dao.OperatorDao;
import com.example.rartonne.appftur.dao.OrdernrSitesDao;
import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.model.Operator;
import com.example.rartonne.appftur.model.OrdernrSites;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class JobDataActivity extends GlobalViews {
    public Spinner spin_job;
    public EditText field_job;
    public EditText field_site;
    public EditText fieldWelding;
    private OperatorDao operatorDao;
    private Operator operator;
    private OrdernrSitesDao ordernrSitesDao;
    private ArrayList<OrdernrSites> ordernrSites;
    private ArrayAdapter<String> adapter;
    private List<String> spinnerArray;
    private String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_data);

        setHeader();
        fillJobData();

        //spinJob listener
        spin_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(null);

                changeJobData();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //text watcher
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                field_site.setText("Unrelevant");
            }
        };
        field_job.addTextChangedListener(watcher);
    }

    private void fillJobData() {
        fieldWelding = (EditText) findViewById(R.id.field_welding);
        field_job = (EditText) findViewById(R.id.field_job);
        field_site = (EditText) findViewById(R.id.field_site);
        spin_job = (Spinner) findViewById(R.id.spin_job);

        operatorDao = new OperatorDao(this);
        operator = operatorDao.select(GlobalClass.getUserId());
        fieldWelding.setText(operator.getOperator_id());

        //on remplit le spinner job
        spinnerArray =  new ArrayList<String>();
        spinnerArray.add("None");

        ordernrSitesDao = new OrdernrSitesDao(this);
        ordernrSites = ordernrSitesDao.selectAll();

        for(OrdernrSites order : ordernrSites){
            spinnerArray.add(order.getOrdernr());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_job.setAdapter(adapter);
    }

    private void changeJobData() {
        String jobValue = spin_job.getSelectedItem().toString();
        String siteValue = ordernrSitesDao.selectSite(jobValue);

        if(jobValue.equals("None")){
            field_job.setText(contents);
            field_site.setText("Unrelevant");
            contents = null;
        }else if (siteValue == null) {
            field_job.setText(jobValue);
            field_site.setText("Unrelevant");
        } else {
            field_job.setText(jobValue);
            field_site.setText(siteValue);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            contents = intent.getStringExtra("SCAN_RESULT");
            String sub = contents.substring(0, 4);
            if(sub.equals("HTTP")) {
                homeQR(contents);
            }else{
                int spinnerPostion = adapter.getPosition(contents);

                if(spinnerPostion == -1){
                    if(spin_job.getSelectedItemPosition() != 0) {
                        spin_job.setSelection(0);
                    }else {
                        field_job.setText(contents);
                        contents = null;
                    }
                }else {
                    contents = null;
                    spin_job.setSelection(spinnerPostion);
                }
            }
        }catch(NullPointerException e){

        };
    }

    public void resetData(View view){
        field_job.setText(null);
        field_site.setText("Unrelevant");
    }

    public void confirmJob(View view){
        ScanlogDao scanlogDao = new ScanlogDao(this);
        scanlogDao.updateJob(GlobalClass.getGf_sec_id(), field_job.getText().toString());
        //Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();

        OrdernrSitesDao ordernrSitesDao = new OrdernrSitesDao(this);
        if(ordernrSitesDao.count(field_job.getText().toString()) == 0) {
            boolean bool = ordernrSitesDao.insert(field_job.getText().toString(), GlobalClass.getInstaller_id());
            spinnerArray.add(field_job.getText().toString());
            adapter.notifyDataSetChanged();
            //spin_job.setAdapter(adapter);
            Toast.makeText(getApplicationContext(), "Data updated " + bool, Toast.LENGTH_LONG).show();
        }
    }

}
