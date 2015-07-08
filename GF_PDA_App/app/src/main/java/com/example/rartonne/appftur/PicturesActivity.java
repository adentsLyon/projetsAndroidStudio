package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class PicturesActivity extends GlobalViews {
    private String contents;
    private ImageButton Btn_1;
    private ImageButton Btn_2;
    private ImageButton Btn_3;
    private ImageButton Btn_4;
    private ImageButton Btn_5;
    private String dir1;
    private String dir2;
    private String dir3;
    private File file1;
    private File file2;
    private File file3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        setHeader();
        setArticleHeader();
        fillPictures();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pictures, menu);
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

    public void fillPictures(){
        Btn_1 = (ImageButton) findViewById(R.id.Btn_1);
        Btn_2 = (ImageButton) findViewById(R.id.Btn_2);
        Btn_3 = (ImageButton) findViewById(R.id.Btn_3);
        Btn_4 = (ImageButton) findViewById(R.id.Btn_4);
        Btn_5 = (ImageButton) findViewById(R.id.Btn_5);

        dir1 = "/sdcard/" + GlobalClass.getCustomer_id().toString();
        dir2 = dir1 + "/netmap";
        dir3 = dir2 + "/" + GlobalClass.getGf_sec_id();

        file1 = new File(dir1);
        file2 = new File(dir2);
        file3 = new File(dir3);

        file1.mkdir();
        file2.mkdir();
        file3.mkdir();

        //on remplit les boutons avec les images trouvees
        Integer nbFiles = file3.listFiles().length;

        if(nbFiles >= 1) {
            Bitmap bmp1 = BitmapFactory.decodeFile(dir3 + "/1.png");
            Btn_1.setImageBitmap(bmp1);
            Btn_1.setTag(dir3 + "/1.png");
            Btn_1.setVisibility(View.VISIBLE);
        }

        if(nbFiles >= 2) {
            Bitmap bmp2 = BitmapFactory.decodeFile(dir3 + "/2.png");
            Btn_2.setImageBitmap(bmp2);
            Btn_2.setTag(dir3 + "/2.png");
            Btn_2.setVisibility(View.VISIBLE);
        }

        if(nbFiles >= 3) {
            Bitmap bmp3 = BitmapFactory.decodeFile(dir3 + "/3.png");
            Btn_3.setImageBitmap(bmp3);
            Btn_3.setTag(dir3 + "/3.png");
            Btn_3.setVisibility(View.VISIBLE);
        }

        if(nbFiles >= 4) {
            Bitmap bmp4 = BitmapFactory.decodeFile(dir3 + "/4.png");
            Btn_4.setImageBitmap(bmp4);
            Btn_4.setTag(dir3 + "/4.png");
            Btn_4.setVisibility(View.VISIBLE);
        }

        if(nbFiles >= 5) {
            Bitmap bmp5 = BitmapFactory.decodeFile(dir3 + "/5.png");
            Btn_5.setImageBitmap(bmp5);
            Btn_5.setTag(dir3 + "/5.png");
            Btn_5.setVisibility(View.VISIBLE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            contents = intent.getStringExtra("SCAN_RESULT");
            if(contents != null){
                String sub = contents.substring(0, 4);
                if(sub.equals("HTTP")) {
                    homeQR(contents);
                }else{
                    Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
                }
            }else{
                Bundle extras = intent.getExtras();
                //ici, on récupère l'image
                Bitmap imgBitmap = (Bitmap) extras.get("data");

                Integer nbFiles = file3.listFiles().length;
                if(nbFiles < 5) {
                    try {
                        String filename = String.valueOf(nbFiles + 1) + ".png";
                        File img = new File(dir3 + "/" + filename);
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(img));
                        imgBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                        os.flush();
                        os.close();

                        switch(nbFiles) {
                            case 0:
                                Btn_1.setImageBitmap(imgBitmap);
                                Btn_1.setVisibility(View.VISIBLE);
                                Btn_1.setTag(dir3 + "/1.png");
                                break;

                            case 1:
                                Btn_2.setImageBitmap(imgBitmap);
                                Btn_2.setVisibility(View.VISIBLE);
                                Btn_2.setTag(dir3 + "/2.png");
                                break;

                            case 2:
                                Btn_3.setImageBitmap(imgBitmap);
                                Btn_3.setVisibility(View.VISIBLE);
                                Btn_3.setTag(dir3 + "/3.png");
                                break;

                            case 3:
                                Btn_4.setImageBitmap(imgBitmap);
                                Btn_4.setVisibility(View.VISIBLE);
                                Btn_4.setTag(dir3 + "/4.png");
                                break;

                            case 4:
                                Btn_5.setImageBitmap(imgBitmap);
                                Btn_5.setVisibility(View.VISIBLE);
                                Btn_5.setTag(dir3 + "/5.png");
                                break;
                        }
                        Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(this, "You can take more than 5 pictures", Toast.LENGTH_SHORT).show();
                }
            }
        }catch(NullPointerException e){
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        };
    }

    public void zoom(View view){
        Intent myIntent = new Intent(this, ZoomActivity.class);

        //on peut paser des paramètres : Extra
        myIntent.putExtra("filepath", view.getTag().toString());
        startActivity(myIntent);
    }

    public void deletePicture(View view){
        Integer nbFiles = file3.listFiles().length;
        File myFile = new File(dir3 + "/" + nbFiles + ".png");
        myFile.delete();
        switch(nbFiles){
            case 1:
                Btn_1.setVisibility(View.GONE);
                break;

            case 2:
                Btn_2.setVisibility(View.GONE);
                break;

            case 3:
                Btn_3.setVisibility(View.GONE);
                break;

            case 4:
                Btn_4.setVisibility(View.GONE);
                break;

            case 5:
                Btn_5.setVisibility(View.GONE);
                break;
        }
        Toast.makeText(this, getString(R.string.file_deleted), Toast.LENGTH_SHORT).show();
    }
}
