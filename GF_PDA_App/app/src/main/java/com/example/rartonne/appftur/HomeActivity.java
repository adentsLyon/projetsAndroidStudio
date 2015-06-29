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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rartonne.appftur.dao.DaoBase;
import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.FittingDao;
import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.IOException;

public class HomeActivity extends GlobalViews {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public SQLiteDatabase bdd;
    public String art_id;
    public String name;
    public String druck;
    public String sdr;
    public String dim;
    public String catalog;
    public String login;
    public boolean checkJob;
    public boolean checkInstallation;
    public boolean checkGeo;
    public boolean checkWelding;
    public boolean checkPictures;
    public boolean checkComment;
    public RelativeLayout rel_job_data;
    public RelativeLayout rel_installation_data;
    public RelativeLayout rel_geo_position;
    public RelativeLayout rel_welding;
    public RelativeLayout rel_pictures;
    public RelativeLayout rel_comment;
    public RelativeLayout rel_installation_manual;
    public RelativeLayout rel_server_updates;
    public RelativeLayout rel_scan_qr;
    private FittingDao fittingDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        rel_job_data = (RelativeLayout) findViewById(R.id.rel_job_data);
        rel_installation_data = (RelativeLayout) findViewById(R.id.rel_installation_data);
        rel_geo_position = (RelativeLayout) findViewById(R.id.rel_geo_position);
        rel_welding = (RelativeLayout) findViewById(R.id.rel_welding);
        rel_pictures = (RelativeLayout) findViewById(R.id.rel_pictures);
        rel_comment = (RelativeLayout) findViewById(R.id.rel_comment);
        rel_installation_manual = (RelativeLayout) findViewById(R.id.rel_installation_manual);
        rel_server_updates = (RelativeLayout) findViewById(R.id.rel_server_updates);
        rel_scan_qr = (RelativeLayout)findViewById(R.id.rel_scan_qr);


        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //on remplit le header
        TextView textUsername = (TextView) findViewById(R.id.textUsername);
        login = GlobalClass.getLogin();
        textUsername.setText(login);

        //on remplit le article header
        art_id = GlobalClass.getArt_id();
        name = GlobalClass.getDesignation();
        druck = GlobalClass.getDruck();
        sdr = GlobalClass.getSdr();
        dim = GlobalClass.getDim();
        catalog = GlobalClass.getCatalog();
        setArticleHeader(art_id, name, druck, sdr, dim, catalog);
        setIcones();
        setPastilles();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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


    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            String contents = intent.getStringExtra("SCAN_RESULT");
            String[] params = contents.split("ART=");
            params = params[1].split("&");
            art_id = params[0];

            //select sur lab
            fittingDao = new FittingDao(this);
            Fitting fitting = fittingDao.select(art_id);
            name = fitting.getDesignation();
            druck = fitting.getDruck();
            sdr = fitting.getSdr();
            dim = fitting.getDim();
            catalog = fitting.getCatalog();

            //on change les variables globales
            global.setArt_id(art_id);
            global.setDesignation(name);
            global.setDruck(druck);
            global.setDim(dim);
            global.setSdr(sdr);
            global.setCatalog(catalog);
            global.setStatus("sign_status_ok");

            setArticleHeader(art_id, name, druck, sdr, dim, catalog);
        }catch(NullPointerException e){

        };
    }

    public void setPastilles(){
        //on affiche ou non les pastilles
        checkJob = GlobalClass.getCheckJob();
        checkInstallation = GlobalClass.getCheckInstallation();
        checkGeo = GlobalClass.getCheckGeo();
        checkWelding = GlobalClass.getCheckWelding();
        checkPictures = GlobalClass.getCheckPictures();
        checkComment = GlobalClass.getCheckComment();

        if (checkJob == false) {
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_job);
            imgView.setVisibility(View.INVISIBLE);
        }else{
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_job);
            imgView.setVisibility(View.VISIBLE);
        }

        if (checkInstallation == false) {
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_installation);
            imgView.setVisibility(View.INVISIBLE);
        }else{
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_installation);
            imgView.setVisibility(View.VISIBLE);
        }

        if (checkGeo == false) {
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_gps);
            imgView.setVisibility(View.INVISIBLE);
        }else{
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_gps);
            imgView.setVisibility(View.VISIBLE);
        }

        if (checkWelding == false) {
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_welding);
            imgView.setVisibility(View.INVISIBLE);
        }else{
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_welding);
            imgView.setVisibility(View.VISIBLE);
        }

        if (checkPictures == false) {
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_picture);
            imgView.setVisibility(View.INVISIBLE);
        }else{
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_picture);
            imgView.setVisibility(View.VISIBLE);
        }

        if (checkComment == false) {
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_comment);
            imgView.setVisibility(View.INVISIBLE);
        }else{
            ImageView imgView = (ImageView) findViewById(R.id.pastille_ok_comment);
            imgView.setVisibility(View.VISIBLE);
        }
    }

    public void setIcones(){
        //on rend les icones visibles ou non
        if(login.isEmpty()){
            rel_job_data.setVisibility(View.GONE);
            rel_installation_data.setVisibility(View.GONE);
            rel_geo_position.setVisibility(View.GONE);
            rel_welding.setVisibility(View.GONE);
            rel_pictures.setVisibility(View.GONE);
            rel_comment.setVisibility(View.GONE);
            rel_installation_manual.setVisibility(View.GONE);
            rel_server_updates.setVisibility(View.GONE);
            rel_scan_qr.setVisibility(View.GONE);
        }
    }
}
