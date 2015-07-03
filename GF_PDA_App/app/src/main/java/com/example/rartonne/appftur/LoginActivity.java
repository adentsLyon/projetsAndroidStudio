package com.example.rartonne.appftur;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;


public class LoginActivity extends GlobalViews {
    private String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            contents = intent.getStringExtra("SCAN_RESULT");
            String sub = contents.substring(0, 4);
            if(sub.equals("HTTP")) {
                homeQR(contents);
            }else{
                Intent intent1 = new Intent(getApplicationContext(), ManualLoginActivity.class);
                intent1.putExtra("login", contents);
                startActivity(intent1);
            }
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }
}
