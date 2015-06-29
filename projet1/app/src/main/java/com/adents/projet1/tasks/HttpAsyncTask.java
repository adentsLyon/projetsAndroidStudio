package com.adents.projet1.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.adents.projet1.MainSessionActivity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by amachado on 25/06/2015.
 */
public class HttpAsyncTask extends AsyncTask<String,Integer,String> {

    private Activity activity;

    public HttpAsyncTask( Activity activity){
        this.activity = activity;
    }

    //Traitement de la tâche de fond
    @Override
    protected String doInBackground(String... params) {
        String result = null;
        String urlHttp = params[0];
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlHttp);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10*1000); //10s
            httpURLConnection.setConnectTimeout(10*1000); //10s
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            int code = httpURLConnection.getResponseCode();
            if(code==HttpURLConnection.HTTP_OK) {

                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                StringBuilder sb = new StringBuilder();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = in.read(buffer)) > 0) {
                    sb.append(new String(buffer, 0, length));
                }
                in.close();
                result = sb.toString();
            }

        }catch(Exception ex){
            // Log.e("Error", ex.getMessage());
        }finally {
            httpURLConnection.disconnect();
        }
        return result;
    }




    //Manipulation après traitement
    @Override
    protected void onPostExecute(String result) {
        ((MainSessionActivity)activity).updateDisplay(result);
    }
}