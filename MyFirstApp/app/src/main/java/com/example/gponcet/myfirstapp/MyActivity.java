package com.example.gponcet.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my);


            // Bouton de scanner qui lance une nouvelle activité
            final Button btnScanner = (Button) findViewById(R.id.btnScanner);
            btnScanner.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MyActivity.this, ScannerActivity.class);
                    startActivity(intent);
                }

            });

            // Insertion d'un Article
            final Button btnInsertArticle = (Button) findViewById(R.id.btnInsertArticle);
            btnInsertArticle.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {

                        EditText txtArticleId = (EditText) findViewById(R.id.txtArticleId);
                        EditText txtDesignation = (EditText) findViewById(R.id.txtDesignation);

                        String article_id = txtArticleId.getText().toString();
                        String designation = txtDesignation.getText().toString();

                        Article newArticle = new Article(article_id, designation);


                        // Ouverture du service de connexion à la base de données
                        SQLiteService myService = new SQLiteService(getApplicationContext());
                        myService.open();


                        TextView txtResult = (TextView) findViewById(R.id.txtResultQuery);
                        txtResult.setText("Article insére avec succès : " + String.valueOf(myService.insertArticle(newArticle)));

                    } catch (Exception e) {
                        Toast toast = Toast.makeText(getApplicationContext(), e.getMessage() + "\n", Toast.LENGTH_LONG);
                        toast.show();
                    }


                }

            });

            final Button btnGetArticle = (Button) findViewById(R.id.btnGetArticle);
            btnGetArticle.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    EditText txtArticleId = (EditText) findViewById(R.id.txtArticleId);
                    String article_id = txtArticleId.getText().toString();

                    // Ouverture du service de connexion à la base de données
                    SQLiteService myService = new SQLiteService(getApplicationContext());


                    myService.open();
                    Article retrievedArticle = myService.getArticleById(article_id);
                    // Fermeture du service
                    myService.close();

                    String message = "L'article retrouvé est : ";

                    if (retrievedArticle != null) {
                        message += retrievedArticle.toString();

                    } else {
                        message = "Aucun article trouvé avec le numéro : " + article_id;
                    }

                    Toast toastArticle = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                    toastArticle.show();

                }

            });


        }
        catch(Exception error)
        {

            Toast toastArticle = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
            toastArticle.show();
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
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


}
