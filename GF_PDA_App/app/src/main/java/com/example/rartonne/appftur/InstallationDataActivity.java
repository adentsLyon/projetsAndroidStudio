package com.example.rartonne.appftur;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.ScanlogDao;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;


public class InstallationDataActivity extends GlobalViews {
    EditText field_sketch;
    EditText field_trench;
    View bar_sketch;
    View bar_trench;
    ScanlogDao scanlogDao;
    SecIdDataDao secIdDataDao;
    SecIdData secIdData;
    Scanlog scanlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation_data);

        setHeader();
        setArticleHeader();

        fillInstallation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_installation_data, menu);
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

    public void fillInstallation(){
        field_sketch = (EditText) findViewById(R.id.field_sketch);
        field_trench = (EditText) findViewById(R.id.field_trench);
        bar_sketch = findViewById(R.id.bar_sketch);
        bar_trench = findViewById(R.id.bar_trench);

        //si les données sont en bases, on remplit les champs
        scanlogDao = new ScanlogDao(this);
        secIdDataDao = new SecIdDataDao((this));

        if(scanlogDao.count(GlobalClass.getGf_sec_id()) > 0) {
            scanlog = scanlogDao.select(GlobalClass.getGf_sec_id());
            field_sketch.setText(scanlog.getWelding_sketch_nr());
            bar_sketch.setBackgroundColor(Color.parseColor("#66c266"));
        }

        if (secIdDataDao.count(GlobalClass.getGf_sec_id(), "td") > 0) {
            secIdData = secIdDataDao.select(GlobalClass.getGf_sec_id(), "td");
            field_trench.setText(secIdData.getValue());
            bar_trench.setBackgroundColor(Color.parseColor("#66c266"));
        }
    }

    public void confirmInstallation(View view){
        scanlogDao.updateInstallation(GlobalClass.getGf_sec_id(), field_sketch.getText().toString());

        if(secIdDataDao.count(GlobalClass.getGf_sec_id(), "td") > 0) {
            secIdDataDao.update(GlobalClass.getGf_sec_id(), "td", field_trench.getText().toString());
            Toast.makeText(this, "Data updated", Toast.LENGTH_LONG).show();
        }else{
            secIdDataDao.insert(GlobalClass.getGf_sec_id(), "td", field_trench.getText().toString());
            Toast.makeText(this, "Data inserted", Toast.LENGTH_LONG).show();
        }

        bar_sketch.setBackgroundColor(Color.parseColor("#66c266"));
        bar_trench.setBackgroundColor(Color.parseColor("#66c266"));
    }

    public void resetInstallation(View view){
        field_sketch.setText("");
        field_trench.setText("");
        bar_sketch.setBackgroundColor(Color.parseColor("#1965a3"));
        bar_trench.setBackgroundColor(Color.parseColor("#1965a3"));
    }
}
