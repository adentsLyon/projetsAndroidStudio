package com.adents.projet1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.adents.projet1.dao.DataBaseHelper;
import com.adents.projet1.dao.UserDao;
import com.adents.projet1.tasks.HttpAsyncTask;
import com.adents.projet1.tasks.HttpAsyncTaskPost;
import com.adents.projet1.tools.Connectivity;
import com.adents.projet1.tools.FileOpenIntentTool;
import com.adents.projet1.tools.FilesTool;
import com.adents.projet1.tools.ImageTools;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Article;


public class MainSessionActivity extends ActionBarActivity {

    private ImageView imgView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_session);


        try {
            imgView1 = (ImageView) findViewById(R.id.imageView1);
            imgView1.setImageDrawable(ImageTools.getDrawableFromAssets(this, "img/video-calories-2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
            helper.createDataBase("pda_db");
        } catch (IOException e) {
            e.printStackTrace();
        }
        new DbHelper(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_session, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id){
            case R.id.item1 :
                btnAccesHttpClick(null);
                break;
            case R.id.item2 :
                btnCapturePhotoClick(null);
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Actions des boutons
    public void btnControleClick(View view){

        //ouvrir un activité
        Intent myIntent = new Intent(this, ImcActivity.class);


        //On peut passer des paramètres : Extra
        myIntent.putExtra("title", "calcul de l'IMC");
        myIntent.putExtra("art1", new Article("ART0001", 230.0));

        //lancement de l'intention
        startActivity(myIntent);
    }

    public void btnListeClick(View view) {
        startActivity(new Intent(this,ManipListeActivity.class));
    }

    public void btnPhotoClick(View view) {
        startActivity(new Intent(this, ManipListesAdapterActivity.class));
        }
    public void btnBddClick(View view) {
        startActivity(new Intent(this, BddActivity.class));
    }

    public void btnFichiersClick(View view){
        try {
            //création d'un fichier .html publique
            String content = "<html><body><h1>Ceci est ma page</h1></body></html>";
            boolean file1Created = FilesTool.writeFileInInternalStorage(
                    this,
                    Context.MODE_WORLD_READABLE,
                    null,
                    "page1.html",
                    content.getBytes()
            );

            boolean file2Created = FilesTool.writeFileInInternalStorage(
                    this,
                    Context.MODE_PRIVATE,
                    "folder1",
                    "page2.html",
                    content.getBytes()
            );

            Toast.makeText(this,"file1Created : " + file1Created
                            + " - file2Created :" + file2Created,
                    Toast.LENGTH_SHORT).show();

            //lecture

            //ouverture avec une appli du terminal
            String contentPage1 = FilesTool.readFileFromInternalStorage(this,null,"page1.html");
            Toast.makeText(this, "file1Content : " + contentPage1,
                    Toast.LENGTH_SHORT).show();

            String contentPage2 = FilesTool.readFileFromInternalStorage(this,"folder1","page2.html");
            Toast.makeText(this,"file2Content : "+contentPage2,
                    Toast.LENGTH_LONG).show();


            //Ecriture d'un fichier temporaire
            boolean filetmpCreated = FilesTool.writeTempFileInInternalStorage(this, "toto.tmp", contentPage1.getBytes());
            Toast.makeText(this,"filetmpCreated : "+filetmpCreated,
                    Toast.LENGTH_SHORT).show();



            boolean fileSDCreated = FilesTool.writeFileInExternalStorage(
                    this,
                    Environment.DIRECTORY_DCIM,
                    "totoSD.html",
                    contentPage1.getBytes());
            Toast.makeText(this,"fileSDCreated : "+fileSDCreated,
                    Toast.LENGTH_SHORT).show();

            String fileSDContent = FilesTool.readFileFromExternalStorage(
                    this,
                    Environment.DIRECTORY_DCIM,
                    "totoSD.html");
            Toast.makeText(this,"fileSDContent : "+fileSDContent,
                    Toast.LENGTH_SHORT).show();



            //Ouvrir le fichier avec l'appli par défaut du terminal
            FileOpenIntentTool.openFile(
                    this,
                    new File(getFilesDir() + "/page1.html"));






            //suppression
            //File f = new File(getFilesDir() + "/page1.html");
            //f.delete();







        }catch(Exception ex){
            Log.e("IO",ex.getMessage());
        }
    }


    public void btnPrefClick(View v){
/*
        //Préférences globales _______________
        SharedPreferences globPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = globPrefs.edit();
        editor.putString("langue","fr");
        editor.putInt("qty",1);
        editor.commit();

        Toast.makeText(this,"Pref ok",
                Toast.LENGTH_SHORT).show();


        String prefLangue = globPrefs.getString("langue","en");
        Toast.makeText(this,"prefLangue :" + prefLangue,
                Toast.LENGTH_SHORT).show();

        //______________ pref. spécifiques à un user
        SharedPreferences prefUser1 = getSharedPreferences("prfUser1", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = prefUser1.edit();
        editor1.putString("langue","PL");
        editor1.putInt("qty",1);
        editor1.commit();

        Toast.makeText(this,"Pref user ok",
                Toast.LENGTH_SHORT).show();


        String prefLangue1 = prefUser1.getString("langue","en");
        Toast.makeText(this,"prefLangue :" + prefLangue1,
                Toast.LENGTH_SHORT).show();*/

        startActivity(new Intent(this,SettingsActivity.class));
    }

    public void btnCapturePhotoClick(View v){
        Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (myIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(myIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode==1 && resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                imgView1.setImageBitmap(imgBitmap);

                File file = new File(getFilesDir()+"/test.png");
                OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                imgBitmap.compress(Bitmap.CompressFormat.PNG,90,os);
                os.flush();
                os.close();
            }
        } catch (Exception e) {

        }
    }

    public void btnAccesHttpClick(View v){

       // String urlPost = "http://admin.qr-ut.com/webservice/pdaws.php?action=post_from_device";
       // String data = "{\"requete\":\"INSERT INTO LANG (lang_id,language,culture_info,picture_id,status_code) VALUES (\"sp23\",\"Spanish23\",\"\",\"\",\"1\");\"}";
       // new HttpAsyncTaskPost(this, data).execute(urlPost);

        //Exple POST - OK
        String urlPost = "http://admin.qr-ut.com/webservice/pdaws.php?action=post_from_device";
        String paramValue1 = "{\"requete\":\"INSERT INTO LANG (lang_id,language,culture_info,picture_id,status_code) VALUES (\"sp23\",\"Spanish23\",\"\",\"\",\"1\");\"}";
        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("data", paramValue1));

        new HttpAsyncTaskPost(this, data).execute(urlPost);




    }

    public void updateDisplay(String result) {
        // Toast.makeText(this,result, Toast.LENGTH_SHORT).show();

        try {
            JSONObject jsonObject = new JSONObject(result);
            String s = (String) jsonObject.get("requete");
            String[] reqs = s.split(";");
            UserDao dao = new UserDao(this);
            int nbSuccess  = 0;
            /*for(String rx : reqs) {
                boolean b = dao.execReq(rx);
                if(b) nbSuccess++;
            }*/
            Toast.makeText(this,"REQ OK =>>> : " + result, Toast.LENGTH_SHORT).show();
            Log.i("SUCCESS", "REQ OK =>>> : " + nbSuccess);
            Log.i("JSON", "JSON Requete : " + s);
        }catch(Exception ex){
            Log.e("Error", "==>" + ex.getMessage());
        }


        //Arguments variable  : avoir la possibilité de passer plusieurs paramètres
        /*Limites : 1 seul var-arg par méthode et il doit être positionné à la fin
        public static int somme(double w, int... t){
            int s = 0;
            for(int x : t)
                s+=x;
            return s;
        }


        //FileTools.somme(t);
        //FileTools.somme(12,34);
        //FileTools.somme(1,3,45);*/

    }
    public void updateDisplayPostSent(String result) {
        Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
    }



    public void btnTestLayoutClick(View v){
        startActivity(new Intent(this, TestLayoutActivity.class));
    }

    public void btnBluetoothClick(View v){
        startActivity(new Intent(this,BlurtoothActivity.class));
    }
}
