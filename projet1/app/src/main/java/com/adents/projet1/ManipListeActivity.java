package com.adents.projet1;

import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Article;


public class ManipListeActivity extends ActionBarActivity {

    private Spinner sp_liste;
    private ListView lv_liste;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manip_liste);


        fillSpinner1();

        fillLv_liste();
    }

    private void fillLv_liste() {
        lv_liste = (ListView) findViewById(R.id.lv_liste);


        List<Article> items = new ArrayList<>();
        for (int i = 1; i < 5; i++)
            items.add(new Article("Reference " + i, 300.0));

        ArrayAdapter<Article> adapter = new ArrayAdapter<Article>(this, android.R.layout.simple_list_item_1, items);
        lv_liste.setAdapter(adapter);

    }

    private void fillSpinner1(){
        sp_liste = (Spinner) findViewById(R.id.sp_liste);
        //List<String> items = new ArrayList<>();
       // for(int i=0; i<5; i++)
         //   items.add("item" + i);


        final String[] items = getResources().getStringArray(R.array.sp_liste_items);

        //Un spinner doit être rempli avec un array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_liste.setAdapter(adapter);

        sp_liste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Elt Selectionné :" + items[position],
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manip_liste, menu);
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
