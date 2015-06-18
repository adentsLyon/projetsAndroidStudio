package com.example.rartonne.appftur;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class HomeActivity extends GlobalViews {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public TextView textArtId;
    public TextView textArticle;
    public TextView textStandard;
    public TextView textDiametre;
    public ImageView imageView2;
    public SQLiteDatabase bdd;
    public String art_id;
    public String name;
    public String druck;
    public String sdr;
    public String dim;
    public GlobalClass global;
    public boolean checkJob;
    public boolean checkInstallation;
    public boolean checkGeo;
    public boolean checkWelding;
    public boolean checkPictures;
    public boolean checkComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        textArtId = (TextView) findViewById(R.id.textArtId);
        textArticle = (TextView) findViewById(R.id.textArticle);
        textStandard = (TextView) findViewById(R.id.textStandard);
        textDiametre = (TextView) findViewById(R.id.textDiametre);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //on remplit le header
        global = (GlobalClass) getApplicationContext();
        TextView textUsername = (TextView) findViewById(R.id.textUsername);
        textUsername.setText(global.getLogin());

        //on affiche ou non les pastilles
        checkJob = global.getCheckJob();
        checkInstallation = global.getCheckInstallation();
        checkGeo = global.getCheckGeo();
        checkWelding = global.getCheckWelding();
        checkPictures = global.getCheckPictures();
        checkComment = global.getCheckComment();

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
        String contents = intent.getStringExtra("SCAN_RESULT");
        String[] params = contents.split("ART=");
        params = params[1].split("&");
        art_id = params[0];

        //select sur lab
        Cursor curseur = bdd.rawQuery("SELECT gf_art_name3_ln5, ddd_art_druck, ddd_art_sdr, ddd_art_dim FROM t_ddd_lab WHERE ddd_art_id = '" + art_id + "'", null);

        curseur.moveToFirst();
        name = curseur.getString(0);
        druck = curseur.getString(1);
        sdr = curseur.getString(2);
        dim = curseur.getString(3);

        curseur.close();

        textArtId.setText(art_id);
        textArticle.setText(name);
        textStandard.setText(druck + " " + sdr);
        textDiametre.setText(dim);
    }
}
