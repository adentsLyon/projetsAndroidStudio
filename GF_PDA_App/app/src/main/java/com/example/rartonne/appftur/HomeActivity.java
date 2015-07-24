package com.example.rartonne.appftur;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.example.rartonne.appftur.dao.PdaSettingsDao;
import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.model.PdaSettings;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.User;
import com.example.rartonne.appftur.tasks.HttpAsyncTask;
import com.example.rartonne.appftur.tasks.HttpAsyncTaskPost;
import com.example.rartonne.appftur.tasks.HttpAsyncTaskSync;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    int serverResponseCode = 0;
    String upLoadServerUri = "";
    private String uploadFilePath;

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
        createFiles();
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

    public void createFiles(){
        if(!GlobalClass.getGf_sec_id().isEmpty()) {
            String dir1 = "/sdcard/" + GlobalClass.getCustomer_id().toString();
            String dir2 = dir1 + "/netmap";
            String dir3 = dir2 + "/" + GlobalClass.getGf_sec_id();

            File file1 = new File(dir1);
            File file2 = new File(dir2);
            File file3 = new File(dir3);

            file1.mkdir();
            file2.mkdir();
            file3.mkdir();
        }
    }

    public void dialogBlacklist(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This product is involved in an incident, are you sure you want to continue using it ?")
                .setTitle("ALERT");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SecIdDataDao secIdDataDao = new SecIdDataDao(getApplicationContext());
                secIdDataDao.insert(GlobalClass.getGf_sec_id(), "forced", GlobalClass.getLogin());
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
        syncDwh();
        syncPictures();
        syncPdaInsert();
    }

    public void syncPictures(){
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("uploading started.....");
                    }
                });

                uploadFilePath = "/sdcard/" + GlobalClass.getCustomer_id().toString() + "/Netmap/" + GlobalClass.getGf_sec_id() + "/";
                upLoadServerUri = "http://admin.qr-ut.com/webservice/UploadToServer.php?customer_id=" + GlobalClass.getCustomer_id().toString() + "&gf_sec_id=" + GlobalClass.getGf_sec_id() + "&type=netmap";

                File yourDir = new File(uploadFilePath);
                for (File f : yourDir.listFiles()) {
                    if (f.isFile()) {
                        String filename = f.getName();
                        Date date = new Date(f.lastModified());
                        if(date.after(new Date(GlobalClass.getLastUpdate())))
                            uploadFile(uploadFilePath + "" + filename);
                    }
                }

                uploadFilePath = "/sdcard/" + GlobalClass.getCustomer_id().toString() + "/";
                upLoadServerUri = "http://admin.qr-ut.com/webservice/UploadToServer.php?customer_id=" + GlobalClass.getCustomer_id().toString() + "&gf_sec_id=" + GlobalClass.getGf_sec_id() + "&type=comment";

                yourDir = new File(uploadFilePath);
                for (File f : yourDir.listFiles()) {
                    if (f.isFile()) {
                        String filename = f.getName();
                        Date date = new Date(f.lastModified());
                        if(date.after(new Date(GlobalClass.getLastUpdate())))
                            uploadFile(uploadFilePath + "" + filename);
                    }
                }
            }
        }).start();
    }

    public void syncDwh(){
        String[] tables = {
                "pda_sec_id_data",
                "batch_nr_checking",
                "customer_incident",
                "PROCESS_LOG",
                "\"SCAN_LOG\"",
                "ordernr_sites",
        };

        for (String table : tables) {
            try {
                String fields = "";
                String values = "";
                String urlPost = "http://admin.qr-ut.com/webservice/pdaws.php?action=syncDwh";
                List<NameValuePair> data = new ArrayList<>();
                String param = "";
                String updateScanlog = "";
                //on initalise la connexion Ã  la base
                SQLiteDatabase bdd;
                DataBaseHelper myDbHelper = new DataBaseHelper(getApplicationContext());
                String format = "yyyy/MM/dd HH:mm:ss";
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
                    cursor = bdd.rawQuery("SELECT * FROM " + table + " WHERE scan_date > '" + GlobalClass.getLastUpdate() + "'", null);
                }else if(table == "ordernr_sites"){
                    cursor = bdd.rawQuery("SELECT ordernr, status_code, modified_by, modified_on, installer_id FROM " + table + " WHERE modified_on > '" + GlobalClass.getLastUpdate() + "'", null);
                }else{
                    cursor = bdd.rawQuery("SELECT * FROM " + table + " WHERE createdon > " + "'" + GlobalClass.getLastUpdate() + "'", null);
                }

                while (cursor.moveToNext()) {
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

                        case "PROCESS_LOG":
                            fields = "process_type, process_date, comment, object_name, status_code, interface_type, action";
                            values = "'" + cursor.getString(0) + "', '" + cursor.getString(1) + "', '" + cursor.getString(2) + "', '" + cursor.getString(3) + "', " + cursor.getInt(4) + ", '" + cursor.getString(5) + "', '" + cursor.getString(6) + "'";
                            break;

                        case "\"SCAN_LOG\"":
                            fields = "gf_sec_id, gps_lat, gps_long, scan_date, user_id, status_code, art_id, customer_order_nr, welding_sketch_nr, serial_wm_nr, fusion_nr, source";
                            values = "'" + cursor.getString(0) + "', " + cursor.getDouble(1) + ", " + cursor.getDouble(2) + ", '" + cursor.getString(3) + "', " + cursor.getInt(4) + ", " +
                                    cursor.getInt(5) + ", '" + cursor.getString(6) + "', '" + cursor.getString(7) + "', '" + cursor.getString(8) + "', '" + cursor.getString(9) + "', " +
                                    cursor.getInt(10) + ", '" + cursor.getString(11) + "'";
                            updateScanlog += "UPDATE \"SCAN_LOG\" SET gps_lat = " + cursor.getDouble(1) + ", gps_long = " + cursor.getDouble(2) + ", scan_date = '" + cursor.getString(3) + "'" +
                                    ", user_id = " + cursor.getInt(4) + ", status_code = " + cursor.getInt(5) + ", art_id = '" + cursor.getString(6) + "', customer_order_nr = '" + cursor.getString(7) + "'" +
                                    ", welding_sketch_nr = '" + cursor.getString(8) + "', serial_wm_nr = '" + cursor.getString(9) + "', fusion_nr = " + cursor.getInt(10) +
                                    " WHERE gf_sec_id = '" + cursor.getString(0) + "' AND source = 'PDA';";
                            break;

                        case "ordernr_sites":
                            fields = "ordernr, status_code, modified_by, modified_on, installer_id";
                            values = "'" + cursor.getString(0) + "', " + cursor.getInt(1) + ", " + cursor.getInt(2) + ", '" + cursor.getString(3) + "', " + cursor.getInt(4);
                            break;
                    }
                    param += "INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ");";
                    //break;
                }

                param += updateScanlog;
                param += "UPDATE pda_settings SET last_update = '" + GlobalClass.getLastUpdate() + "' WHERE pda_id = '" + GlobalClass.getSerialNumber() +"'";

                data.add(new BasicNameValuePair("data", param));

                //on envoie les INSERT
                new HttpAsyncTaskPost(this, data).execute(urlPost);

                cursor.close();

                bdd.close();

                GlobalClass.setLastUpdate(date);
                            startActivity(new Intent(this, HomeActivity.class));
        } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void syncPdaInsert(){
        String[] tables_insert = {"pda_data_type",
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
                "USER",
                //"TRANSLATION",
                //"T_DDD_LAB",
                "SUPPLIER",
                "ordernr_sites",
                "operator",
                "wm_serial",
                "USER_LOG"};

        /*progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Download in progress ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setMax(tables_insert.length);
        progressBar.show();*/

        //on redescend entièrement toutes les tables du tableau
        try {
            for (String table : tables_insert) {
                new HttpAsyncTaskSync(this, getApplicationContext(), "insert_all", table).execute("http://admin.qr-ut.com/webservice/pdaws.php?action=insert_all&login=" + GlobalClass.getLogin() + "&table=" + table);
            }
        }catch(Exception ex){
            Log.e("ERROR", ex.getMessage());
        }

        //on redescend les nouveaux produits de LAB
        try {
            new HttpAsyncTaskSync(this, getApplicationContext(), "insert", "T_DDD_LAB").execute("http://admin.qr-ut.com/webservice/pdaws.php?action=insertPdal&table=T_DDD_LAB&last_update=" + GlobalClass.getLastUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int uploadFile(String sourceFileUri) {


        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            //dialog.dismiss();

            Log.e("uploadFile", "Source File not exist");

            runOnUiThread(new Runnable() {
                public void run() {
                    //messageText.setText("Source File not exist :"+uploadFilePath + "" + uploadFileName);
                }
            });

            return 0;

        }
        else
        {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){

                    runOnUiThread(new Runnable() {
                        public void run() {

                            /*String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" http://www.androidexample.com/media/uploads/"
                                    +uploadFileName;*/

                            //messageText.setText(msg);
                            /*Toast.makeText(getApplicationContext(), "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();*/
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                //dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        /*Toast.makeText(getApplicationContext(), "MalformedURLException",
                                Toast.LENGTH_SHORT).show();*/
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("Got Exception : see logcat ");
                        /*Toast.makeText(getApplicationContext(), "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();*/
                    }
                });
                Log.e("Upload file to server", "Exception : " + e.getMessage(), e);
            }
           // dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
}
