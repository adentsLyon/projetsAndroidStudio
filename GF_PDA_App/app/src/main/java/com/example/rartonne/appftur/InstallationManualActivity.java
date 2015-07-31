package com.example.rartonne.appftur;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.dao.TranslationDao;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.model.Translation;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class InstallationManualActivity extends GlobalViews {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation_manual);

        setHeader();
        LinearLayout ll_manual = (LinearLayout) findViewById(R.id.ll_manual);

        Integer i = 0;
        TranslationDao translationDao = new TranslationDao(this);
        ArrayList<Translation> translations = translationDao.select("3");
        for(Translation translation : translations){
            i++;
            LinearLayout linearLayout = new LinearLayout(this);
            TextView textView = new TextView(this);
            textView.setText(i + ". " + translation.getContent());
            /*GradientDrawable gd = new GradientDrawable();
            gd.setColor(Color.WHITE);
            gd.setCornerRadius(5);
            gd.setStroke(1, Color.BLACK);
            textView.setBackgroundDrawable(gd);*/

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 20);
            textView.setPadding(10, 10, 10, 10);
            ll_manual.addView(textView, layoutParams);

            try {
                InputStream inputStream = getAssets().open("manual/3-" + i + "0.jpeg");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                ll_manual.addView(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_installation_manual, menu);
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            String contents = intent.getStringExtra("SCAN_RESULT");
            homeQR(contents);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }
}
