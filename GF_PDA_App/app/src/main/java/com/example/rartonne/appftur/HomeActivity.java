package com.example.rartonne.appftur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.DaoBase;
import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.FittingDao;
import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.User;
import com.example.rartonne.appftur.tasks.HttpAsyncTaskPost;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends GlobalViews {
    public String name;
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
    private String contents;

    @Override
    protected void onResume() {
        super.onResume();
        redirect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        redirect();
        setHeader();
        setArticleHeader();
        fillHome();

        setIcones();
        setPastilles();
    }

    private void fillHome() {
        rel_job_data = (RelativeLayout) findViewById(R.id.rel_job_data);
        rel_installation_data = (RelativeLayout) findViewById(R.id.rel_installation_data);
        rel_geo_position = (RelativeLayout) findViewById(R.id.rel_geo_position);
        rel_welding = (RelativeLayout) findViewById(R.id.rel_welding);
        rel_pictures = (RelativeLayout) findViewById(R.id.rel_pictures);
        rel_comment = (RelativeLayout) findViewById(R.id.rel_comment);
        rel_installation_manual = (RelativeLayout) findViewById(R.id.rel_installation_manual);
        rel_server_updates = (RelativeLayout) findViewById(R.id.rel_server_updates);
        rel_scan_qr = (RelativeLayout)findViewById(R.id.rel_scan_qr);
        checkJob = GlobalClass.isCheckJob();
        checkInstallation = GlobalClass.isCheckInstallation();
        checkGeo = GlobalClass.isCheckGeo();
        checkWelding = GlobalClass.isCheckWelding();
        checkPictures = GlobalClass.isCheckPictures();
        checkComment = GlobalClass.isCheckComment();

        Intent intent = getIntent();

        if(GlobalClass.isBlacklisted() && intent.getStringExtra("checkBlacklisted") != null)
            dialogBlacklist();
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
            contents = intent.getStringExtra("SCAN_RESULT");
            String sub = contents.substring(0, 4);
            if(sub.equals("HTTP")) {
                homeQR(contents);
            }else{
                Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
            }
        }catch(NullPointerException e){
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        };
    }

    public void setPastilles(){
        //on affiche ou non les pastilles
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
        if(!GlobalClass.getLogin().isEmpty() && !GlobalClass.getGf_sec_id().isEmpty()){
            rel_job_data.setVisibility(View.VISIBLE);
            rel_installation_data.setVisibility(View.VISIBLE);
            rel_geo_position.setVisibility(View.VISIBLE);
            rel_welding.setVisibility(View.VISIBLE);
            rel_pictures.setVisibility(View.VISIBLE);
            rel_comment.setVisibility(View.VISIBLE);
            rel_installation_manual.setVisibility(View.VISIBLE);
            rel_server_updates.setVisibility(View.VISIBLE);
        }
    }

    public void dialogBlacklist(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This product is involved in an incident, are you sure you want to continue using it ?")
                .setTitle("ALERT");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                scanOk();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                scanBlacklisted();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void sync(View view){
        String[] tables = {
                "pda_sec_id_data",
                //"batch_nr_checking",
                //"customer_incident",
                //"process_log",
                "\"SCAN_LOG\""
        };

        for (String table : tables) {
            try {
                //on initalise la connexion à la base
                SQLiteDatabase bdd;
                DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
                String format = "yy/MM/dd HH:mm:ss";
                SimpleDateFormat formater = new SimpleDateFormat(format);
                String date = formater.format(new Date());

                try {
                    myDbHelper.createDataBase();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myDbHelper.openDataBase();

                bdd = myDbHelper.getWritableDatabase();

                Cursor cursor;
                if (table == "\"SCAN_LOG\"") {
                    cursor = bdd.rawQuery("SELECT * FROM " + table + " WHERE scan_date > ?", new String[]{GlobalClass.getLastUpdate()});
                }else{
                    cursor = bdd.rawQuery("SELECT * FROM " + table + " WHERE createdon > ?", new String[]{GlobalClass.getLastUpdate()});
                }
                while (cursor.moveToNext()) {
                    String fields = "";
                    String values = "";
                    String urlPost = "http://admin.qr-ut.com/webservice/pdaws.php?action=syncDwh";
                    switch(table) {
                        case "pda_sec_id_data":
                            fields = "type, value, createdon, modifiedon, gf_sec_id";
                            values = "'" + cursor.getString(1) + "', '" + cursor.getString(2) + "', '" + cursor.getString(3) + "', '" + cursor.getString(4) + "', '" + cursor.getString(5) + "'";
                            break;

                        case "batch_nr_checking":
                            fields = "createdon, modifiedon, gps_lat, gps_long, createdby, modifiedby, status_code, isonline, checking_source, last_update_batch, last_synchro_blacklist, gf_sec_id, batch_nr_checking_id, batch_nr, article_id";
                            values = "";
                            break;

                        case "customer_incident":
                            fields = "";
                            values = "";
                            break;

                        case "process_log":
                            fields = "";
                            values = "";
                            break;

                        case "\"SCAN_LOG\"":
                            fields = "gf_sec_id, gps_lat, gps_long, scan_date, user_id, status_code, art_id, customer_order_nr, welding_sketch_nr, serial_wm_nr, fusion_nr, source";
                            values = "'" + cursor.getString(0) + "', " + cursor.getDouble(1) + ", " + cursor.getDouble(2) + ", '" + cursor.getString(3) + "', " + cursor.getInt(4) + ", " +
                                    cursor.getInt(5) + ", '" + cursor.getString(6) + "', '" + cursor.getString(7) + "', '" + cursor.getString(8) + "', '" + cursor.getString(9) + "', " +
                                    cursor.getInt(10) + ", '" + cursor.getString(11) + "'";
                            break;
                    }
                    String paramValue1 = "INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ");";
                    List<NameValuePair> data = new ArrayList<>();
                    data.add(new BasicNameValuePair("data", paramValue1));

                    new HttpAsyncTaskPost(this, data).execute(urlPost);
                    break;
                }
                cursor.close();

                bdd.close();

                GlobalClass.setLastUpdate(date);
                            startActivity(new Intent(this, HomeActivity.class));
        } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
