
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.DataBaseHelper;
import com.example.rartonne.appftur.dao.PdaSettingsDao;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.dao.UserDao;
import com.example.rartonne.appftur.model.PdaSettings;
import com.example.rartonne.appftur.model.User;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ManualLoginActivity extends GlobalViews {
    public TextView input_login;
    public TextView text_message;
    public TextView textUsername;
    public TextView textCompany;
    private TextView textDate;
    private UserDao userDao;
    private User user;
    private Integer count;
    public RelativeLayout footer;
    public ImageView imageStatus;
    private String contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_login);

        setHeader();
        setArticleHeader();

        fillLogin();

        setFooter();
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
            if(GlobalClass.getLogin().isEmpty())
                footer.setVisibility(View.VISIBLE);

            text_message.setTextColor(Color.parseColor("#007a3d"));
            text_message.setText("Login Successful");
            GlobalClass.setLogin(input_login.getText().toString());
            textUsername.setText(input_login.getText());

            user = userDao.select(input_login.getText().toString());
            GlobalClass.setUserId(user.getUser_id());
            GlobalClass.setInstaller_id(user.getInstaller_id());
            GlobalClass.setInstallerName(user.getInstaller_name());
            GlobalClass.setCustomer_id(user.getCustomer_id());
            GlobalClass.setStatus("sign_scan_qr_expected");
            int id_icone = getResources().getIdentifier(GlobalClass.getStatus(), "drawable", getPackageName());
            imageStatus = (ImageView) findViewById(R.id.imageStatus);
            imageStatus.setImageResource(id_icone);
            textCompany.setText(user.getInstaller_name());

            //on récupère la date de dernière synchro
            PdaSettingsDao pdaSettingsDao  = new PdaSettingsDao(this);
            PdaSettings pdaSettings = pdaSettingsDao.select(GlobalClass.getSerialNumber());
            String format = "yyyy/MM/dd HH:mm:ss";
            SimpleDateFormat formater = new SimpleDateFormat(format);
            String date = formater.format(new Date(pdaSettings.getLast_update().toString()));
            GlobalClass.setLastUpdate(date);
            textDate.setText(date);

            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }else{
            text_message.setTextColor(Color.parseColor("#c60f13"));
            text_message.setText("Wrong login");
        }
    }

    public void setFooter(){
        if(GlobalClass.getLogin().isEmpty()){
            footer = (RelativeLayout) findViewById(R.id.footer);
            footer.setVisibility(View.GONE);
            RelativeLayout cancel = (RelativeLayout) findViewById(R.id.rel_cancel);
            cancel.setVisibility(View.GONE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            contents = intent.getStringExtra("SCAN_RESULT");
            String sub = contents.substring(0, 4);
            if(sub.equals("HTTP")) {
                homeQR(contents);
            }else{
                input_login.setText(contents);
            }
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }

    public void fillLogin(){
        //IMPORTANT
        //on remplit le header
        textUsername = (TextView) findViewById(R.id.textUsername);
        textCompany = (TextView) findViewById(R.id.textCompany);
        textDate = (TextView) findViewById(R.id.textDate);

        //on lie les view aux variables
        input_login = (TextView) findViewById(R.id.input_login);
        text_message = (TextView) findViewById(R.id.text_message);

        Intent recupIntent = getIntent();
        input_login.setText(recupIntent.getStringExtra("login"));
    }
}
