package com.example.rartonne.appftur.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Mohamed on 25/06/2015.
 * 1 tâche asynchrone = 1 thread
 */
public class HttpAsyncTaskPost extends AsyncTask<String,Integer,String> {

    private Activity activity;
    private List<NameValuePair> postData;
    private ProgressDialog dialog;

    public HttpAsyncTaskPost(Activity activity, List<NameValuePair> postData){
        this.activity = activity;
        this.postData = postData;
        dialog = new ProgressDialog(activity);
        dialog.setMax(100);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Envoi en cours ....");
        dialog.setProgress(10);
        dialog.show();
    }

    //Manipulation après traitement
    @Override
    protected void onPostExecute(String result) {
        if(dialog.isShowing())
            dialog.dismiss();

        //((HomeActivity)activity).updateDisplayPostSent(result);
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
            httpURLConnection.setConnectTimeout(10*1000); //10s
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");


            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(postData));
            writer.flush();
            writer.close();

            if(httpURLConnection.getResponseCode()== HttpURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                Log.i("Retour serveur", sb.toString());
            }
        }catch(Exception ex){
            Log.e("Error", ex.getMessage());
        }finally {
            httpURLConnection.disconnect();
        }
        return result;
    }

    /**
     * Encoding the post parameters
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }



}
