package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class JobDataActivity extends GlobalViews {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_data);

        final Spinner spin = (Spinner) findViewById(R.id.spin_job);
        final EditText field_job = (EditText) findViewById(R.id.field_job);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
// hide selection text
                ((TextView) view).setText(null);

                String value = spin.getSelectedItem().toString();

                if(value.equals("None")) {
                    field_job.setText(null);
                } else {
                    field_job.setText(value);
                }
// if you want you can change background here
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    public void buttonOnClick (View v){
        //Action si on clique sur le boutton
        //Button button=(Button) v;

        ((TextView)findViewById(R.id.hello)).setText("Coucou");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
