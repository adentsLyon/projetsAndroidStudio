package com.example.rartonne.appftur.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rartonne.appftur.HomeActivity;
import com.example.rartonne.appftur.InitActivity;
import com.example.rartonne.appftur.ManualLoginActivity;
import com.example.rartonne.appftur.R;
import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.UserDao;
import com.example.rartonne.appftur.model.User;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mohamed on 25/06/2015.
 * 1 tâche asynchrone = 1 thread
 */
public class HttpAsyncTask extends AsyncTask<String,Integer,String> {
    private SQLiteDatabase bdd;
    private Activity activity;
    private Context context;
    private UserDao userDao;
    private User user;

    public HttpAsyncTask(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        //on initalise la connexion à la base
        DataBaseHelper myDbHelper = new DataBaseHelper(context);


        try {
            myDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myDbHelper.openDataBase();


        //myDbHelper.close();

        bdd = myDbHelper.getWritableDatabase();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    //Manipulation après traitement
    @Override
    protected void onPostExecute(String result) {
        //InitActivity.progressBar.incrementProgressBy(1);
        InitActivity.progress++;
        InitActivity.progressBar.setProgress(InitActivity.progress);
        if(InitActivity.progress == InitActivity.tables.length) {
            InitActivity.progressBar.dismiss();
            UserDao userDao = new UserDao(context);
            user = userDao.select(InitActivity.input_login.getText().toString());
            GlobalClass.setLogin(InitActivity.input_login.getText().toString());
            GlobalClass.setUserId(user.getUser_id());
            GlobalClass.setInstaller_id(user.getInstaller_id());
            GlobalClass.setInstallerName(user.getInstaller_name());
            GlobalClass.setCustomer_id(user.getCustomer_id());
            String format = "yy/MM/dd HH:mm:ss";
            SimpleDateFormat formater = new SimpleDateFormat( format );
            String date = formater.format(new Date());
            GlobalClass.setStatus("sign_scan_qr_expected");
            GlobalClass.setLastUpdate(date);
            activity.startActivity(new Intent(context, HomeActivity.class));
        }
    }

    //Traitement de la tâche de fond
    @Override
    protected String doInBackground(String... params) {

        //simuler un traitement long
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        String result = null;
        String urlHttp = params[0];
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlHttp);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10*1000); //10s
            httpURLConnection.setReadTimeout(120*1000); //2mn
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            int code = httpURLConnection.getResponseCode();
            if(code== HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                StringBuilder sb = new StringBuilder();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = in.read(buffer)) > 0) {
                    sb.append(new String(buffer, 0, length));
                }
                in.close();
                result = sb.toString();

                String[] requetes = result.split(";");
                for (String requete : requetes) {
                    bdd.execSQL(requete);
                }
            }

        }catch(Exception ex){
           Log.e("Error", ex.getMessage());
        }finally {
            httpURLConnection.disconnect();
        }
        return result;
    }
}
