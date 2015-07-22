package com.example.rartonne.appftur;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class InstallationManualActivity extends GlobalViews {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation_manual);

        setHeader();

        WebView wv_imc = (WebView) findViewById(R.id.wv_installationManual);
        wv_imc.loadUrl("file:///android_asset/GeorgeFISCHER_manualCouplerTest.html");


        /*try {

            InputStream is = getAssets().open("GeorgeFISCHER_manualCouplerTest.html");
            Scanner sc = new Scanner(is,"UTF-8").useDelimiter("\\A");
            String res = sc.hasNext()?sc.next():"";
            wv_imc.loadData(res,"text/html","utf-8");

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),
                    "Erreur de fichier",
                    Toast.LENGTH_SHORT
            ).show();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_installation_manual, menu);
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
            homeQR(contents);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }
}
