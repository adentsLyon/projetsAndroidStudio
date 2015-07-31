package com.example.rartonne.appftur;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.DaoBase;
import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.tasks.HttpAsyncTask;
import com.example.rartonne.appftur.tools.Connectivity;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class InitActivity extends GlobalViews {
    public static Integer progress = 0;
    public static ProgressDialog progressBar;
    public static EditText input_login;
    public static String[] tables = {//"ARTICLE_CATALOG",
            //"ARTICLE_FEATURE",
            "USER",
            "pda_data_type",
            "LANG",
            "PDF",
            "STATUS_NAME",
            "T_DDD_WERK",
            "PROCESS_TYPE",
            "batch_blacklist",
            "customer_incident_type",
            "LICENSE",
            "CUSTOMER",
            "CONSTRUCTION_SITE",
            "CUSTOMER_SUPPLIER",
            "INSTALLER",
            "TRANSLATION",
            "T_DDD_LAB",
            "SUPPLIER",
            "ordernr_sites",
            "operator",
            "wm_serial",
            "USER_LOG"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        setHeader();
        input_login = (EditText) findViewById(R.id.input_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_init, menu);
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

    public void AccesHttpClick(View view){
        if(Connectivity.isConnected(getApplicationContext())) {
            String login = input_login.getText().toString();
            progressBar = new ProgressDialog(view.getContext());
            progressBar.setCancelable(false);
            progressBar.setMessage("Download in progress ...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setMax(tables.length);
            progressBar.show();

            try {
                for (String table : tables) {
                    new HttpAsyncTask(this, getApplicationContext()).execute("http://admin.qr-ut.com/webservice/pdaws.php?action=insert_all&login=" + login + "&table=" + table);
                }
            } catch (Exception ex) {
                Log.e("ERROR", ex.getMessage());
            }

            try {
                new HttpAsyncTask(this, getApplicationContext()).execute("http://admin.qr-ut.com/webservice/pdaws.php?action=pdaSettings&pda_id=" + GlobalClass.getSerialNumber());
            } catch (Exception ex) {
                Log.e("ERROR", ex.getMessage());
            }
        }else{
            Toast.makeText(getApplicationContext(), getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
    }

}
