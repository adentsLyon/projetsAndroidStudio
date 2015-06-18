package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JobDataActivity extends GlobalViews {
    public GlobalClass global;
    public Spinner spin_job;
    public Spinner spin_site;
    public EditText field_job;
    public EditText field_site;
    public SQLiteDatabase bdd;
    public String login;
    public int userId;
    public EditText fieldWelding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_data);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fieldWelding = (EditText) findViewById(R.id.field_welding);

        //on remplit le header
        global = (GlobalClass) getApplicationContext();
        TextView textUsername = (TextView) findViewById(R.id.textUsername);
        login = global.getLogin();
        textUsername.setText(login);
        userId = global.getUserId();

        //on remplit les spinners
        //on initalise la connexion à la base
        DataBaseHelper myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        myDbHelper.close();

        bdd = myDbHelper.getWritableDatabase();
        //select sur operator
        Cursor curseur = bdd.rawQuery("SELECT operator_id FROM operator WHERE user_id = " + userId, null);

        curseur.moveToFirst();
        String welder_certificate = curseur.getString(0);
        fieldWelding.setText(welder_certificate);

        //on remplit le spinner job
        List<String> spinnerArray =  new ArrayList<String>();
        List<String> spinnerArray2 =  new ArrayList<String>();
        curseur = bdd.rawQuery("SELECT ordernr, name FROM ordernr_sites LEFT JOIN construction_site AS cs ON cs.site_id = ordernr_sites.site_id", null);
        curseur.moveToFirst();
        do {
            String ordernr = curseur.getString(0);
            String site_name = curseur.getString(1);
            spinnerArray.add(ordernr);
            spinnerArray2.add(site_name);
        }while(curseur.moveToNext());

        spin_job = (Spinner) findViewById(R.id.spin_job);
        spin_site = (Spinner) findViewById(R.id.spin_site);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_job.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_site.setAdapter(adapter2);

        //on vide les textes de spinner
        field_job = (EditText) findViewById(R.id.field_job);
        field_site = (EditText) findViewById(R.id.field_site);

        spin_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(null);

                String value = spin_job.getSelectedItem().toString();

                if(value.equals("None")) {
                    field_job.setText(null);
                } else {
                    field_job.setText(value);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spin_site.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(null);

                String value = spin_site.getSelectedItem().toString();

                if(value.equals("None")) {
                    field_site.setText(null);
                } else {
                    field_site.setText(value);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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

}
