package com.example.rartonne.appftur;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rartonne.appftur.R;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CommentActivity extends GlobalViews {
    private SecIdDataDao secIdDataDao;
    private EditText et_comment;
    private String contents;
    private ImageView imgComment;
    private File file;
    private String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        setHeader();
        setArticleHeader();
        fillComment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment, menu);
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

    public void fillComment(){
        secIdDataDao = new SecIdDataDao(this);
        et_comment = (EditText) findViewById(R.id.et_comment);
        imgComment = (ImageView) findViewById(R.id.imgComment);
        dir = "/sdcard/" + GlobalClass.getCustomer_id().toString();
        file = new File(dir);
        file.mkdir();
    }

    public void confirmComment(View view){
        boolean inserted = secIdDataDao.insert(GlobalClass.getGf_sec_id(), "comment", et_comment.getText().toString());
        if(inserted)
            Toast.makeText(this, getString(R.string.inserted), Toast.LENGTH_SHORT).show();
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

                    try {
                        Integer id = secIdDataDao.max() + 1;
                        String filename = id.toString() + ".png";
                        File img = new File(dir + "/" + filename);
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(img));
                        imgBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                        os.flush();
                        os.close();

                        imgComment.setImageBitmap(imgBitmap);
                        imgComment.setVisibility(View.VISIBLE);
                        imgComment.setTag(dir + "/1.png");

                        Toast.makeText(this, "File created", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
        }catch(NullPointerException e){
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        };
    }
}
