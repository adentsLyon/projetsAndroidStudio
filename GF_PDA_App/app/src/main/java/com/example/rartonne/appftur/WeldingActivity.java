package com.example.rartonne.appftur;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.R;
import com.example.rartonne.appftur.dao.OperatorDao;
import com.example.rartonne.appftur.dao.OrdernrSitesDao;
import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.dao.WmSerialDao;
import com.example.rartonne.appftur.model.Operator;
import com.example.rartonne.appftur.model.OrdernrSites;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.model.WmSerial;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.util.ArrayList;
import java.util.List;

public class WeldingActivity extends GlobalViews {
    private EditText et_fusion;
    private EditText et_wm;
    private RelativeLayout rel_welding1;
    private RelativeLayout rel_welding2;
    private RelativeLayout rel_welding3;
    private RelativeLayout rel_welding4;
    private RelativeLayout rel_scanFlash;
    private RelativeLayout rel_confirm;
    private TextView tv_idWmSerial1;
    private TextView tv_idWmSerial2;
    private TextView tv_idWmSerial3;
    private TextView tv_idWmSerial4;
    private TextView tv_idWmFusion1;
    private TextView tv_idWmFusion2;
    private TextView tv_idWmFusion3;
    private TextView tv_idWmFusion4;
    private RelativeLayout rel_buttons;
    private RelativeLayout rel_inputEmpty;
    private ScanlogDao scanlogDao;
    private SecIdDataDao secIdDataDao;
    private SecIdData secIdData;
    private String contents;
    private Spinner spin_wm;
    private WmSerialDao wmSerialDao;
    private ArrayList<WmSerial> wmSerials;
    private ArrayAdapter<String> adapter;
    private List<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welding);

        setHeader();
        setArticleHeader();

        fillWelding();

        //spin_wm listener
        spin_wm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(null);

                //changeJobData();
                if (spin_wm.getSelectedItem().toString().equals("None")) {
                    et_wm.setText("");
                } else {
                    et_wm.setText(spin_wm.getSelectedItem().toString());
                }
                et_fusion.setText("");
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welding, menu);
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

    public void fillWelding(){
        et_fusion = (EditText) findViewById(R.id.et_fusion);
        et_wm = (EditText) findViewById((R.id.et_wm));
        rel_welding1 = (RelativeLayout) findViewById(R.id.rel_welding1);
        rel_welding2 = (RelativeLayout) findViewById(R.id.rel_welding2);
        rel_welding3 = (RelativeLayout) findViewById(R.id.rel_welding3);
        rel_welding4 = (RelativeLayout) findViewById(R.id.rel_welding4);
        rel_scanFlash = (RelativeLayout) findViewById(R.id.rel_scanFlash);
        rel_confirm = (RelativeLayout) findViewById(R.id.rel_confirm);
        tv_idWmSerial1 = (TextView) findViewById(R.id.tv_idWmSerial1);
        tv_idWmSerial2 = (TextView) findViewById(R.id.tv_idWmSerial2);
        tv_idWmSerial3 = (TextView) findViewById(R.id.tv_idWmSerial3);
        tv_idWmSerial4 = (TextView) findViewById(R.id.tv_idWmSerial4);
        tv_idWmFusion1 = (TextView) findViewById(R.id.tv_idWmFusion1);
        tv_idWmFusion2 = (TextView) findViewById(R.id.tv_idWmFusion2);
        tv_idWmFusion3 = (TextView) findViewById(R.id.tv_idWmFusion3);
        tv_idWmFusion4 = (TextView) findViewById(R.id.tv_idWmFusion4);
        rel_inputEmpty = (RelativeLayout) findViewById(R.id.rel_inputEmpty);
        rel_buttons = (RelativeLayout) findViewById(R.id.rel_buttonPart2);
        spin_wm = (Spinner) findViewById(R.id.spin_wm);

        //on remplit le spinner job
        spinnerArray =  new ArrayList<String>();
        spinnerArray.add("None");

        wmSerialDao = new WmSerialDao(this);
        wmSerials = wmSerialDao.selectAll();

        for(WmSerial wmSerial : wmSerials){
            spinnerArray.add(wmSerial.getSerial_number());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_wm.setAdapter(adapter);

        scanlogDao = new ScanlogDao(this);
        secIdDataDao = new SecIdDataDao(this);
        if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "wm1") > 0) {
            rel_welding1.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "wm1");
            tv_idWmSerial1.setText(secIdData.getValue());
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "fu1");
            tv_idWmFusion1.setText(secIdData.getValue());
        }

        if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "wm2") > 0) {
            rel_welding2.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "wm2");
            tv_idWmSerial2.setText(secIdData.getValue());
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "fu2");
            tv_idWmFusion2.setText(secIdData.getValue());
        }

        if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "wm3") > 0) {
            rel_welding3.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "wm3");
            tv_idWmSerial3.setText(secIdData.getValue());
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "fu3");
            tv_idWmFusion3.setText(secIdData.getValue());
        }

        if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "wm4") > 0) {
            rel_welding4.setVisibility(View.VISIBLE);
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "wm4");
            tv_idWmSerial4.setText(secIdData.getValue());
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "fu4");
            tv_idWmFusion4.setText(secIdData.getValue());
            rel_scanFlash.setVisibility(View.INVISIBLE);
            rel_confirm.setVisibility(View.INVISIBLE);
            rel_inputEmpty.setVisibility(View.INVISIBLE);
        }
    }

    public void confirmWelding(View view){
        if(!rel_welding1.isShown()) {
            rel_welding1.setVisibility(View.VISIBLE);
            tv_idWmSerial1.setText(et_wm.getText());
            tv_idWmFusion1.setText(et_fusion.getText());
            addSerial();
            //si le welder certificate n'existe pas en base, on l'enregistre
            if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "WCERT") == 0) {
                OperatorDao operatorDao = new OperatorDao(this);
                Operator operator = operatorDao.select(GlobalClass.getUserId());
                secIdDataDao.insert(GlobalClass.getGf_sec_id(), "WCERT", operator.getOperator_id());
            }
            scanlogDao.updateWelding(GlobalClass.getGf_sec_id(), et_wm.getText().toString(), et_fusion.getText().toString());
            updateSecIdData("wm1");
            updateSecIdData("fu1");
            et_wm.setText(spin_wm.getSelectedItem().toString());
            newFusion();
            GlobalClass.setCheckWelding(true);
        }else {
            if (!rel_welding2.isShown()) {
                rel_welding2.setVisibility(View.VISIBLE);
                tv_idWmSerial2.setText(et_wm.getText());
                tv_idWmFusion2.setText(et_fusion.getText());
                addSerial();
                updateSecIdData("wm2");
                updateSecIdData("fu2");
                et_wm.setText(spin_wm.getSelectedItem().toString());
                newFusion();
            }else{
                if (!rel_welding3.isShown()) {
                    rel_welding3.setVisibility(View.VISIBLE);
                    tv_idWmSerial3.setText(et_wm.getText());
                    tv_idWmFusion3.setText(et_fusion.getText());
                    addSerial();
                    updateSecIdData("wm3");
                    updateSecIdData("fu3");
                    et_wm.setText(spin_wm.getSelectedItem().toString());
                    newFusion();
                }else{
                    if (!rel_welding4.isShown()) {
                        rel_welding4.setVisibility(View.VISIBLE);
                        tv_idWmSerial4.setText(et_wm.getText());
                        tv_idWmFusion4.setText(et_fusion.getText());
                        addSerial();
                        updateSecIdData("wm4");
                        updateSecIdData("fu4");
                        rel_scanFlash.setVisibility(View.INVISIBLE);
                        rel_confirm.setVisibility(View.INVISIBLE);
                        rel_inputEmpty.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
    }

    public void updateSecIdData(String type) {
        if (type.substring(0, 2).equals("wm")) {
            if (secIdDataDao.count(GlobalClass.getGf_sec_id(), type) == 0) {
                if (secIdDataDao.insert(GlobalClass.getGf_sec_id(), type, et_wm.getText().toString()))
                    Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            } else {
                if (secIdDataDao.update(GlobalClass.getGf_sec_id(), type, et_wm.getText().toString()))
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            }
        }else{
            if (secIdDataDao.count(GlobalClass.getGf_sec_id(), type) == 0) {
                if (secIdDataDao.insert(GlobalClass.getGf_sec_id(), type, et_fusion.getText().toString()))
                    Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
            } else {
                if (secIdDataDao.update(GlobalClass.getGf_sec_id(), type, et_fusion.getText().toString()))
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            contents = intent.getStringExtra("SCAN_RESULT");
            String sub = contents.substring(0, 4);
            if(sub.equals("HTTP")) { 
                homeQR(contents);
            }else{
                et_wm.setText(contents);
            }
        }catch(NullPointerException e){
            Toast.makeText(this, getString(R.string.invalid_scan), Toast.LENGTH_LONG).show();
        };
    }

    public void resetWelding(View view){
        et_wm.setText("");
        et_fusion.setText("");

        if(rel_welding4.isShown()) {
            rel_welding4.setVisibility(View.GONE);
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "wm4");
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "fu4");
        }else if(rel_welding3.isShown()) {
            rel_welding3.setVisibility(View.GONE);
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "wm3");
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "fu3");
        }else if(rel_welding2.isShown()) {
            rel_welding2.setVisibility(View.GONE);
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "wm2");
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "fu2");
        }else if(rel_welding1.isShown()) {
            rel_welding1.setVisibility(View.GONE);
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "wm1");
            secIdDataDao.delete(GlobalClass.getGf_sec_id(), "fu1");
        }




        rel_inputEmpty.setVisibility(View.VISIBLE);
        rel_scanFlash.setVisibility(View.VISIBLE);
        rel_confirm.setVisibility(View.VISIBLE);

        scanlogDao.updateWelding(GlobalClass.getGf_sec_id(), "", "");
        GlobalClass.setCheckWelding(false);
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
    }

    public void addSerial(){
        if(wmSerialDao.count(et_wm.getText().toString()) == 0) {
            wmSerialDao.insert(et_wm.getText().toString());
            spinnerArray.add(et_wm.getText().toString());
            adapter.notifyDataSetChanged();
            spin_wm.setSelection(spin_wm.getAdapter().getCount());
        }
    }

    public void newFusion(){
        try {
            Integer newFusion = Integer.parseInt(et_fusion.getText().toString())+1;
            et_fusion.setText(newFusion.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
