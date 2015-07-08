package com.example.rartonne.appftur.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.HomeActivity;
import com.example.rartonne.appftur.ManualLoginActivity;
import com.example.rartonne.appftur.R;
import com.example.rartonne.appftur.dao.FittingDao;
import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.model.Scanlog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rartonne on 17/06/2015.
 */
public class GlobalViews extends Activity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    public TextView textArtId;
    public TextView textArticle;
    public TextView textStandard;
    public TextView textDiametre;
    public ImageView imageView2;
    public ImageView imageStatus;
    public GlobalClass global;

    public void toActivity(View view) {
        String name = view.getTag().toString();
        String activity = "com.example.rartonne.appftur." + name;
        Class act;
        try {
            act = Class.forName(activity);

            Intent intent = new Intent(getApplicationContext(), act);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ClassNotFoundException e){

        }
    }

    public void scanBar(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            HomeActivity act = new HomeActivity();
            showDialog(act, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            HomeActivity act = new HomeActivity();
            showDialog(act, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
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
    }

    public void setHeader(){
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView textUsername = (TextView) findViewById(R.id.textUsername);
        TextView textCompany = (TextView) findViewById(R.id.textCompany);
        textUsername.setText(GlobalClass.getLogin());
        textCompany.setText(GlobalClass.getInstallerName());
    }

    public void setArticleHeader(){
        String art_id = GlobalClass.getArt_id();
        String name = GlobalClass.getDesignation();
        String druck = GlobalClass.getDruck();
        String sdr = GlobalClass.getSdr();
        String dim = GlobalClass.getDim();
        String catalog = GlobalClass.getCatalog();

        textArtId = (TextView) findViewById(R.id.textArtId);
        textArticle = (TextView) findViewById(R.id.textArticle);
        textStandard = (TextView) findViewById(R.id.textStandard);
        textDiametre = (TextView) findViewById(R.id.textDiametre);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageStatus = (ImageView) findViewById(R.id.imageStatus);

        textArtId.setText(art_id);
        textArticle.setText(name);
        textStandard.setText(druck + " " + sdr);
        textDiametre.setText(dim);
        int id = getResources().getIdentifier(catalog, "drawable", getPackageName());
        imageView2.setImageResource(id);

        int id_icone = getResources().getIdentifier(GlobalClass.getStatus(), "drawable", getPackageName());
        imageStatus.setImageResource(id_icone);
    }

    public void homeQR(String contents){
        try {
            //on éclate l'url du QR
            String[] params = contents.split("\\?");
            params = params[1].split("&");
            String gf_sec_id = params[0];
            params = params[1].split("ART=");
            String art_id = params[1];

            //select sur lab
            FittingDao fittingDao = new FittingDao(this);
            Fitting fitting = fittingDao.select(art_id);

            //on change les variables globales
            GlobalClass.setArt_id(art_id);
            GlobalClass.setDesignation(fitting.getDesignation());
            GlobalClass.setDruck(fitting.getDruck());
            GlobalClass.setDim(fitting.getDim());
            GlobalClass.setSdr(fitting.getSdr());
            GlobalClass.setCatalog(fitting.getCatalog());
            GlobalClass.setStatus("sign_status_ok");
            GlobalClass.setGf_sec_id(gf_sec_id);

            //on insert dans scan_log
            Integer userId = GlobalClass.getUserId();
            ScanlogDao scanlogDao = new ScanlogDao(this);
            Scanlog scanlog = new Scanlog(gf_sec_id, userId, art_id);
            if(scanlogDao.count(gf_sec_id) >= 1){
                scanlogDao.updateScan(gf_sec_id);
                Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_LONG).show();
            }else {
                scanlogDao.insert(scanlog);
                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_LONG).show();
            }

            //si les données sont saisies on change les variables check
            scanlog = scanlogDao.select(gf_sec_id);
                //Job Number
                if(scanlog.getCustomer_order_nr() != null) {
                    GlobalClass.setJobNumber(scanlog.getCustomer_order_nr());
                    GlobalClass.setCheckJob(true);
                }else{
                    GlobalClass.setCheckJob(false);
                }

                //GPS
                if(scanlog.getGps_lat() != 0){
                    GlobalClass.setCheckGeo(true);
                }else{
                    GlobalClass.setCheckGeo(false);
                }

                //Serial WM et Fusion Nr
               if(scanlog.getSerial_wm_nr() != null && !scanlog.getSerial_wm_nr().isEmpty() && scanlog.getFusion_nr() != 0){
                    GlobalClass.setCheckWelding(true);
               }else{
                   GlobalClass.setCheckWelding(false);
               }

            //Installation
            SecIdDataDao secIdDataDao = new SecIdDataDao(this);
            if(secIdDataDao.select(GlobalClass.getGf_sec_id(), "td") != null && !scanlogDao.select(GlobalClass.getGf_sec_id()).getWelding_sketch_nr().isEmpty())
                GlobalClass.setCheckInstallation(true);
            else
                GlobalClass.setCheckInstallation(false);

            //on retourne sur la home
            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void redirect(){
        if(GlobalClass.getLogin().isEmpty()){
            startActivity(new Intent(getApplicationContext(), ManualLoginActivity.class));
        }
    }

    public void addPicture(View view){
        Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(myIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(myIntent, 1);
        }
    }

    public static boolean copyFile(File source, File dest){
        try{
            // Declaration et ouverture des flux
            java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);

            try{
                java.io.FileOutputStream destinationFile = null;

                try{
                    destinationFile = new FileOutputStream(dest);

                    // Lecture par segment de 0.5Mo
                    byte buffer[] = new byte[512 * 1024];
                    int nbLecture;

                    while ((nbLecture = sourceFile.read(buffer)) != -1){
                        destinationFile.write(buffer, 0, nbLecture);
                    }
                } finally {
                    destinationFile.close();
                }
            } finally {
                sourceFile.close();
            }
        } catch (IOException e){
            e.printStackTrace();
            return false; // Erreur
        }

        return true; // Résultat OK
    }
}
