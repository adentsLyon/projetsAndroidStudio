package com.example.rartonne.appftur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.UserDao;
import com.example.rartonne.appftur.tools.GlobalViews;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MaintenanceActivity extends GlobalViews {
    public Spinner spinTables;
    public TextView textView2;
    public Button btnRefresh;
    public String chosenTable;
    public SQLiteDatabase bdd;
    public String texte;
    public Button btnSync;
    public Toast toast;
    public static final String TAG ="Mon_TAG";
    public TextView txtProgress;
    public Integer step = 0;
    public Integer progress = 0;
    public ProgressBar pgbSteps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //on remplit le header
        setHeader();

        //on lie les views aux variables
        spinTables = (Spinner) findViewById(R.id.spinTables);
        textView2 = (TextView) findViewById(R.id.textView2);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnSync = (Button) findViewById(R.id.btnSync);
        txtProgress = (TextView) findViewById(R.id.txtProgress);
        pgbSteps = (ProgressBar) findViewById(R.id.pgbSteps);

        //on remplit le spinner avec le contenu tables_array
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tables_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTables.setAdapter(adapter);

        //on initalise la connexion à la base
        DataBaseHelper myDbHelper = new DataBaseHelper(this);


        try {
            myDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myDbHelper.openDataBase();


        //myDbHelper.close();

        bdd = myDbHelper.getWritableDatabase();

        //on met un listener sur le bouton Refresh
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chosenTable = spinTables.getSelectedItem().toString();

                texte = displayTable(chosenTable);

                textView2.setText(texte);
            }
        });

        //on met un listener sur le bouton Sync
        btnSync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new RequestTask().execute();
            }
        });

    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maintenance, menu);
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

    public String displayTable(String table){
        String name = "";

        Cursor curseur = bdd.rawQuery("SELECT COUNT(*) FROM " + table, null);

        curseur.moveToFirst();
        name = curseur.getString(0);

        curseur.close();

        return name;
    }

    private	class RequestTask extends AsyncTask<String, Void, String> {
        private String response = "";
        private String[] tables = {//"ARTICLE_CATALOG",
                //"ARTICLE_FEATURE",
                "pda_data_type",
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

        @Override
        protected String doInBackground(String... urls) {
            final Integer total_steps = tables.length + 1;

            for(String table : tables) {
                response = pdaInsert("http://admin.qr-ut.com/webservice/pdaws.php?action=insert_all&login=Installer1&table=" + table);

                if(response != null && !response.isEmpty()) {
                    String[] requetes = response.split(";");
                    for (String requete : requetes) {
                        bdd.execSQL(requete);
                    }
                }

                step += 1;
                progress += 5;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtProgress.setText(step.toString() + "/" + total_steps.toString());
                        pgbSteps.setProgress(progress);
                    }
                });
            }

            //on redescend les données d'ARTICLE_CATALOG
            response = pdaInsert("http://admin.qr-ut.com/webservice/pdaws.php?action=insert_all&login=Installer1&table=T_DDD_LAB");
            if(response != null && !response.isEmpty()) {
                String[] requetes = response.split(";");
                for (String requete : requetes) {
                    bdd.execSQL(requete);
                }
            }

            step += 1;
            progress += 5;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtProgress.setText(step.toString() + "/" + total_steps.toString());
                    pgbSteps.setProgress(progress);
                }
            });

            return response;
        }

        public String pdaInsert(String url){
                response = "Ca ne marche pas...";
                HttpClient httpclient = new DefaultHttpClient();
                try {
                    HttpGet httpGet =
                            new HttpGet(url);
                    HttpResponse httpresponse = httpclient.execute(httpGet);
                    HttpEntity httpentity = httpresponse.getEntity();

                    if (httpentity != null) {
                        InputStream inputstream = httpentity.getContent();
                        BufferedReader bufferedreader = new BufferedReader(
                                new InputStreamReader(inputstream));
                        StringBuilder strinbulder = new StringBuilder();
                        String ligne = bufferedreader.readLine();
                        while (ligne != null) {
                            strinbulder.append(ligne + "n");
                            ligne = bufferedreader.readLine();
                        }
                        bufferedreader.close();

                        JSONObject jso = new JSONObject(strinbulder.toString());
                        response = jso.getString("requete");
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            textView2.setText("Synchronisation effectuée avec succès");
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            String contents = intent.getStringExtra("SCAN_RESULT");
            homeQR(contents);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }
}
