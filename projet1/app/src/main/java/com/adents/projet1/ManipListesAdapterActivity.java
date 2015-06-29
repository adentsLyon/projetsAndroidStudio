package com.adents.projet1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.PhotoMontage;
import com.adents.projet1.adapter.PhotoMontageAdapter;


public class ManipListesAdapterActivity extends ActionBarActivity {

    private ListView listView1;
    private List<PhotoMontage> lp;
    private PhotoMontageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manip_listes_adapter);

        remplirListView1();
    }

    //___________________________________
    private void remplirListView1(){
        listView1 = (ListView)findViewById(R.id.lv_liste_photo);

        lp = new ArrayList<>();
        lp.add(new PhotoMontage(1,"img/video-calories-2.jpg","Photo 1", new Date()));
        lp.add(new PhotoMontage(2, "img/video-calories-2.jpg", "Photo 2",new Date()));

        adapter = new PhotoMontageAdapter(
                this,
                android.R.layout.simple_list_item_1,
                lp
        );

        listView1.setAdapter(adapter);

        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

          @Override
          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
              PhotoMontage p3 = new PhotoMontage(3,"img/video-calories-2.jpg", "Photo3", new Date());
              lp.add(p3);
              adapter.addPhoto(p3);
              adapter.notifyDataSetChanged();
          }
      });

    }



    //___________________________





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manip_listes_adapter, menu);
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