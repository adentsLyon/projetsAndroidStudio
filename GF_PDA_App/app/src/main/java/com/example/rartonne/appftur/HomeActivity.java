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

public class HomeActivity extends GlobalClass {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textArtId = (TextView) findViewById(R.id.textArtId);
        textArticle = (TextView) findViewById(R.id.textArticle);
        textStandard = (TextView) findViewById(R.id.textStandard);
        textDiametre = (TextView) findViewById(R.id.textDiametre);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

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

    public void gotoNetmap(View view)
    {
        Intent intent = new Intent(HomeActivity.this, NetmapActivity.class);
        startActivity(intent);

    }

    public void gotoSettings(View view)
    {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);

    }

    public void gotoLogin(View view)
    {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    /*public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(HomeActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }*/

    //alert dialog for downloadDialog
    /*private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }*/

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
