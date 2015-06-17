
package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;


public class ManualLoginActivity extends Activity {
    public SQLiteDatabase bdd;
    public TextView input_login;
    public TextView text_message;
    public TextView textUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_login);

        //this.setRequestedOrientation(
        //     ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //on lie les view aux variables
        input_login = (TextView) findViewById(R.id.input_login);
        text_message = (TextView) findViewById(R.id.text_message);
        textUsername = (TextView) findViewById(R.id.textUsername);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manual_login, menu);
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

    public void gotoLogin(View view)
    {
        Intent intent = new Intent(ManualLoginActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public void checkLogin(View view){
        Integer count = 0;
        //on initalise la connexion à la base
        DataBaseHelper myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        myDbHelper.close();

        bdd = myDbHelper.getWritableDatabase();

        Cursor curseur = bdd.rawQuery("SELECT COUNT(*) FROM user WHERE login = '" + input_login.getText() + "'", null);

        curseur.moveToFirst();
        count = curseur.getInt(0);

        curseur.close();

        if(count == 1){
            text_message.setTextColor(Color.parseColor("#007a3d"));
            text_message.setText("Login Successful");
            //final GlobalClass globalLogin = (GlobalClass) ManualLoginActivity.this;
            //globalLogin.setLogin(input_login.getText().toString());
            textUsername.setText(input_login.getText());

        }else{
            text_message.setTextColor(Color.parseColor("#c60f13"));
            text_message.setText("Wrong login");
        }
    }
}
