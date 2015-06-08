package com.example.rartonne.appftur;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by rartonne on 08/06/2015.
 */
public	class RequestTask  {

    private String response = "";
    public static final String TAG ="Mon_TAG";
    public SQLiteDatabase bdd;

    protected String doInBackground(String... urls) {
        //response=dwhInsert("http://admin.qr-ut.com/webservice/pdaws.php?action=insert&table=CATALOG&catalog_id=0001&name=test");
        response = pdaInsert("http://admin.qr-ut.com/webservice/pdaws.php?action=init&login=rartonne");
        return response;
    }

    public String pdaInsert(String url){
        response="Ca ne marche pas...";
        HttpClient httpclient= new DefaultHttpClient();
        try {
            HttpGet httpGet=
                    new HttpGet(url);
            HttpResponse httpresponse=httpclient.execute(httpGet);
            HttpEntity httpentity=httpresponse.getEntity();

            if (httpentity!=null){
                InputStream inputstream=httpentity.getContent();
                BufferedReader bufferedreader=new BufferedReader(
                        new InputStreamReader(inputstream));
                StringBuilder strinbulder=new StringBuilder();
                String ligne=bufferedreader.readLine();
                while (ligne!=null){
                    strinbulder.append(ligne+"n");
                    ligne=bufferedreader.readLine();
                }
                bufferedreader.close();

                JSONObject jso=new JSONObject(strinbulder.toString());
                //JSONObject jsomain=jso.getJSONObject("pht");
                response=jso.getString("requete");
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return response;
    }

    protected void onPostExecute(String result) {
        String[] requetes = result.split(";");
        String instruction = "";
        for(String requete : requetes) {
            instruction += requete + "\n";
            bdd.execSQL(requete);
        }
        //letexte.setText("Insertion effectuée avec succès");
    }

}
