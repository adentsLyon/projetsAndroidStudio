
package com.example.rartonne.appftur;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.UserDao;
import com.example.rartonne.appftur.model.User;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.IOException;


public class ManualLoginActivity extends GlobalViews {
    public SQLiteDatabase bdd;
    public TextView input_login;
    public TextView text_message;
    public TextView textUsername;
    private UserDao userDao;
    private User user;
    private Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_login);

        this.setRequestedOrientation(
             ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //on remplit le header
        textUsername = (TextView) findViewById(R.id.textUsername);
        textUsername.setText(GlobalClass.getLogin());

        //on lie les view aux variables
        input_login = (TextView) findViewById(R.id.input_login);
        text_message = (TextView) findViewById(R.id.text_message);
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
        userDao = new UserDao(this);
        count = userDao.count(input_login.getText().toString());

        if(count == 1) {
            text_message.setTextColor(Color.parseColor("#007a3d"));
            text_message.setText("Login Successful");
            GlobalClass.setLogin(input_login.getText().toString());
            textUsername.setText(input_login.getText());

            user = userDao.select(input_login.getText().toString());
            GlobalClass.setUserId(user.getUser_id());
        }else{
            text_message.setTextColor(Color.parseColor("#c60f13"));
            text_message.setText("Wrong login");
        }
    }
}
