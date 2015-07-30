package com.example.rartonne.appftur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;


public class InstallationData2Activity extends GlobalViews {
    RelativeLayout rel_product1;
    RelativeLayout rel_product2;
    RelativeLayout rel_product3;
    RelativeLayout rel_product4;
    EditText tvproduct1;
    EditText tvproduct2;
    EditText tvproduct3;
    EditText tvproduct4;
    EditText et_length1;
    EditText et_length2;
    EditText et_length3;
    EditText et_length4;
    SecIdDataDao secIdDataDao;
    SecIdData secIdData;
    View bar_product1;
    View bar_product2;
    View bar_product3;
    View bar_product4;
    RelativeLayout rel_btn_link2;
    RelativeLayout rel_btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation_data2);

        setHeader();
        setArticleHeader();
        fillInstallation2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_installation_data2, menu);
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
            String[] params = contents.split("\\?");
            params = params[1].split("&");
            params = params[1].split("ART=");
            String art_id = params[1];

            //on affiche les champs de liens
            if(GlobalClass.getE1().isEmpty()) {
                //rel_product1.setVisibility(View.VISIBLE);
                tvproduct1.setText(art_id);
                secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e1", art_id);
                GlobalClass.setE1(art_id);
            }else{
                if(GlobalClass.getE2().isEmpty()) {
                    //rel_product1.setVisibility(View.VISIBLE);
                    tvproduct1.setText(GlobalClass.getE1());
                    //rel_product2.setVisibility(View.VISIBLE);
                    tvproduct2.setText(art_id);
                    secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e2", art_id);
                    GlobalClass.setE2(art_id);
                }else{
                    if(GlobalClass.getE3().isEmpty()) {
                        //rel_product1.setVisibility(View.VISIBLE);
                        tvproduct1.setText(GlobalClass.getE1());
                        //rel_product2.setVisibility(View.VISIBLE);
                        tvproduct2.setText(GlobalClass.getE2());
                        //rel_product3.setVisibility(View.VISIBLE);
                        tvproduct3.setText(art_id);
                        secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e3", art_id);
                        GlobalClass.setE3(art_id);
                    }else{
                        if(GlobalClass.getE4().isEmpty()) {
                            //rel_product1.setVisibility(View.VISIBLE);
                            tvproduct1.setText(GlobalClass.getE1());
                            //rel_product2.setVisibility(View.VISIBLE);
                            tvproduct2.setText(GlobalClass.getE2());
                            //rel_product3.setVisibility(View.VISIBLE);
                            tvproduct3.setText(GlobalClass.getE3());
                            //rel_product4.setVisibility(View.VISIBLE);
                            tvproduct4.setText(art_id);
                            secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e4", art_id);
                            GlobalClass.setE4(art_id);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        }
    }

    public void fillInstallation2(){
        rel_product1 = (RelativeLayout) findViewById(R.id.rel_product1);
        rel_product2 = (RelativeLayout) findViewById(R.id.rel_product2);
        rel_product3 = (RelativeLayout) findViewById(R.id.rel_products3);
        rel_product4 = (RelativeLayout) findViewById(R.id.rel_products4);

        rel_btn_link2 = (RelativeLayout) findViewById(R.id.rel_btn_link2);
        rel_btn_confirm = (RelativeLayout) findViewById(R.id.rel_btn_confirm);

        tvproduct1 = (EditText) findViewById(R.id.tvproduct1);
        tvproduct2 = (EditText) findViewById(R.id.tv_product2);
        tvproduct3 = (EditText) findViewById(R.id.tv_product3);
        tvproduct4 = (EditText) findViewById(R.id.tv_product4);

        et_length1 = (EditText) findViewById(R.id.et_length1);
        et_length2 = (EditText) findViewById(R.id.et_product2);
        et_length3 = (EditText) findViewById(R.id.et_product3);
        et_length4 = (EditText) findViewById(R.id.et_product4);

        bar_product1 = findViewById(R.id.bar_product1);
        bar_product2 = findViewById(R.id.bar_product2);
        bar_product3 = findViewById(R.id.bar_product3);
        bar_product4 = findViewById(R.id.bar_product4);

        secIdDataDao = new SecIdDataDao(this);

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e1") > 0) {
            rel_product2.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e1");
            tvproduct1.setText(secIdData.getValue());
            tvproduct1.setEnabled(false);
            GlobalClass.setE1(secIdData.getValue());
        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e2") > 0) {
            rel_product3.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e2");
            tvproduct2.setText(secIdData.getValue());
            tvproduct2.setEnabled(false);
            GlobalClass.setE2(secIdData.getValue());
        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e3") > 0) {
            rel_product4.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e3");
            tvproduct3.setText(secIdData.getValue());
            tvproduct3.setEnabled(false);
            GlobalClass.setE3(secIdData.getValue());
        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "e4") > 0) {
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "e4");
            tvproduct4.setText(secIdData.getValue());
            tvproduct4.setEnabled(false);
            GlobalClass.setE4(secIdData.getValue());
        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "l1") > 0) {
            bar_product1.setBackgroundColor(Color.parseColor("#66c266"));
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "l1");
            et_length1.setEnabled(false);
            et_length1.setText(secIdData.getValue());
        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "l2") > 0) {
            bar_product2.setBackgroundColor(Color.parseColor("#66c266"));
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "l2");
            et_length2.setText(secIdData.getValue());
            et_length2.setEnabled(false);

        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "l3") > 0) {
            bar_product3.setBackgroundColor(Color.parseColor("#66c266"));
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "l3");
            et_length3.setText(secIdData.getValue());
            et_length3.setEnabled(false);
        }

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "l4") > 0) {
            bar_product4.setBackgroundColor(Color.parseColor("#66c266"));
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "l4");
            et_length4.setText(secIdData.getValue());
            et_length4.setEnabled(false);
            rel_btn_link2.setVisibility(View.GONE);
            rel_btn_confirm.setVisibility(View.GONE);
        }
    }

    public void deleteInstallation2(View view){
        for(int i = 1; i < 5 ; i++) {
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "e" + i);
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "l" + i);
        }


        tvproduct1.setText("");
        tvproduct2.setText("");
        tvproduct3.setText("");
        tvproduct4.setText("");
        et_length1.setText("");
        et_length2.setText("");
        et_length3.setText("");
        et_length4.setText("");
        GlobalClass.setE1("");
        GlobalClass.setE2("");
        GlobalClass.setE3("");
        GlobalClass.setE4("");
        //rel_product1.setVisibility(View.GONE);
        rel_product2.setVisibility(View.GONE);
        rel_product3.setVisibility(View.GONE);
        rel_product4.setVisibility(View.GONE);
        rel_btn_link2.setVisibility(View.VISIBLE);
        rel_btn_confirm.setVisibility(View.VISIBLE);
        bar_product1.setBackgroundColor(Color.parseColor("#1965a3"));
        bar_product2.setBackgroundColor(Color.parseColor("#1965a3"));
        bar_product3.setBackgroundColor(Color.parseColor("#1965a3"));
        bar_product4.setBackgroundColor(Color.parseColor("#1965a3"));

        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
    }

    public void confirmInstallation2(View view) {
        if (!et_length1.getText().toString().isEmpty()) {
            if (!tvproduct1.getText().toString().isEmpty()) {
                tvproduct1.setEnabled(false);
                et_length1.setEnabled(false);
                GlobalClass.setE1(tvproduct1.getText().toString());
                if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "e1") == 0) {
                    secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e1", tvproduct1.getText().toString());

                } else {
                    secIdDataDao.update(GlobalClass.getGf_sec_id(), "e1", tvproduct1.getText().toString());
                }
            }

            if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "l1") == 0) {
                secIdDataDao.insert(GlobalClass.getGf_sec_id(), "l1", et_length1.getText().toString());
                et_length1.setEnabled(false);
            } else {
                secIdDataDao.update(GlobalClass.getGf_sec_id(), "l1", et_length1.getText().toString());
            }

            bar_product1.setBackgroundColor(Color.parseColor("#66c266"));
            rel_product2.setVisibility(View.VISIBLE);
        }

        if (!et_length2.getText().toString().isEmpty()){
            tvproduct2.setEnabled(false);
            et_length2.setEnabled(false);
            if (!tvproduct2.getText().toString().isEmpty()) {
                GlobalClass.setE2(tvproduct2.getText().toString());
                if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "e2") == 0) {
                    secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e2", tvproduct2.getText().toString());
                } else {
                    secIdDataDao.update(GlobalClass.getGf_sec_id(), "e2", tvproduct2.getText().toString());
                }
            }

            if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "l2") == 0) {
                secIdDataDao.insert(GlobalClass.getGf_sec_id(), "l2", et_length2.getText().toString());
            } else {
                secIdDataDao.update(GlobalClass.getGf_sec_id(), "l2", et_length2.getText().toString());
            }

            bar_product2.setBackgroundColor(Color.parseColor("#66c266"));
            rel_product3.setVisibility(View.VISIBLE);
        }

        if(!et_length3.getText().toString().isEmpty()) {
            if (!tvproduct3.getText().toString().isEmpty()) {
                GlobalClass.setE1(tvproduct1.getText().toString());

                if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "e3") == 0) {
                    secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e3", tvproduct3.getText().toString());
                } else {
                    secIdDataDao.update(GlobalClass.getGf_sec_id(), "e3", tvproduct3.getText().toString());
                }
            }

            if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "l3") == 0) {
                    secIdDataDao.insert(GlobalClass.getGf_sec_id(), "l3", et_length3.getText().toString());
            } else {
                secIdDataDao.update(GlobalClass.getGf_sec_id(), "l3", et_length3.getText().toString());
            }

            bar_product3.setBackgroundColor(Color.parseColor("#66c266"));
            rel_product4.setVisibility(View.VISIBLE);
        }

        if(!et_length4.getText().toString().isEmpty()) {
            GlobalClass.setE1(tvproduct1.getText().toString());

            if (!tvproduct4.getText().toString().isEmpty()) {
                if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "e4") == 0) {
                    secIdDataDao.insert(GlobalClass.getGf_sec_id(), "e4", tvproduct4.getText().toString());
                } else {
                    secIdDataDao.update(GlobalClass.getGf_sec_id(), "e4", tvproduct4.getText().toString());
                }
            }

            if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "l4") == 0) {
                secIdDataDao.insert(GlobalClass.getGf_sec_id(), "l4", et_length4.getText().toString());
            } else {
                secIdDataDao.update(GlobalClass.getGf_sec_id(), "l4", et_length4.getText().toString());
            }

            bar_product4.setBackgroundColor(Color.parseColor("#66c266"));
            rel_btn_link2.setVisibility(View.GONE);
            rel_btn_confirm.setVisibility(View.GONE);
        }

        Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show();
    }

    public void dialogExplanation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("-\tScan a QR by using the button on the app\n" +
                "-\tScan a barecode by using the side button of the device\n" +
                "-\tInput manually the barecode\n")
                .setTitle("WHAT TO DO NOW?");

        builder.setPositiveButton("OK, Thanks", null);


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
