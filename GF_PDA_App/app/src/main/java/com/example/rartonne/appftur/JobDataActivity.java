package com.example.rartonne.appftur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    public SQLiteDatabase bdd;
    public String login;
    public String art_id;
    public String name;
    public String sdr;
    public String druck;
    public String dim;
    public String catalog;
    public int userId;
    public EditText fieldWelding;
    private OperatorDao operatorDao;
    private Operator operator;
    private OrdernrSitesDao ordernrSitesDao;
    private ArrayList<OrdernrSites> ordernrSites;
    private String gf_sec_id;
    private FittingDao fittingDao;
    private ArrayAdapter<String> adapter;
    private List<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_data);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fieldWelding = (EditText) findViewById(R.id.field_welding);

        //on remplit le header
        TextView textUsername = (TextView) findViewById(R.id.textUsername);
        login = GlobalClass.getLogin();
        userId = GlobalClass.getUserId();
        art_id = GlobalClass.getArt_id();
        name = GlobalClass.getDesignation();
        druck = GlobalClass.getDruck();
        sdr = GlobalClass.getSdr();
        dim = GlobalClass.getDim();
        catalog = GlobalClass.getCatalog();
        textUsername.setText(login);

        //on remplit le article header
        setArticleHeader(art_id, name, druck, sdr, dim, catalog);

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

        spin_job = (Spinner) findViewById(R.id.spin_job);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_job.setAdapter(adapter);

        //on vide les textes de spinner
        field_job = (EditText) findViewById(R.id.field_job);
        field_site = (EditText) findViewById(R.id.field_site);

        spin_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(null);

                String value = spin_job.getSelectedItem().toString();

                if(value.equals("None")) {
                    field_job.setText(null);
                    field_site.setText("Unrelevant");
                } else {
                    field_job.setText(value);
                    field_site.setText(ordernrSitesDao.selectSite(value));
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
            String contents = intent.getStringExtra("SCAN_RESULT");
            /*String[] params = contents.split("\\?");
            params = params[1].split("&");
            gf_sec_id = params[0];
            params = params[1].split("ART=");
            art_id = params[1];

            //select sur lab
            fittingDao = new FittingDao(this);
            Fitting fitting = fittingDao.select(art_id);
            name = fitting.getDesignation();
            druck = fitting.getDruck();
            sdr = fitting.getSdr();
            dim = fitting.getDim();
            catalog = fitting.getCatalog();

            //on change les variables globales
            GlobalClass.setArt_id(art_id);
            GlobalClass.setDesignation(name);
            GlobalClass.setDruck(druck);
            GlobalClass.setDim(dim);
            GlobalClass.setSdr(sdr);
            GlobalClass.setCatalog(catalog);
            GlobalClass.setStatus("sign_status_ok");

            //setArticleHeader(art_id, name, druck, sdr, dim, catalog);

            //on insert dans scan_log
            Integer userId = GlobalClass.getUserId();
            ScanlogDao scanlogDao = new ScanlogDao(this);
            Scanlog scanlog = new Scanlog(gf_sec_id, userId, art_id);
            if(scanlogDao.count(gf_sec_id) >= 1){
                Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();
            }else {
                scanlogDao.insert(scanlog);
                Toast.makeText(getApplicationContext(), "Inserted lines : " + scanlogDao.count().toString(), Toast.LENGTH_LONG).show();
            }

            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);*/
            
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
