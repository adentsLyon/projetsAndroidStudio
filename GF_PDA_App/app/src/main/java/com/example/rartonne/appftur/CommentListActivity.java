package com.example.rartonne.appftur;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rartonne.appftur.adapters.CommentAdapter;
import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.util.ArrayList;


public class CommentListActivity extends GlobalViews {
    private ListView listComments;
    private SecIdDataDao secIdDataDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        setHeader();
        setArticleHeader();
        fillListComment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment_list, menu);
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

    public void fillListComment(){
        secIdDataDao = new SecIdDataDao(this);
        listComments = (ListView) findViewById(R.id.listComments);
        ArrayList<SecIdData> items = secIdDataDao.selectAllComments();

        /*ArrayAdapter<SecIdData> adapter = new ArrayAdapter<SecIdData>(
                this,
                android.R.layout.simple_list_item_activated_1,
                items);*/
        CommentAdapter commentAdapter = new CommentAdapter(this, items);

        listComments.setAdapter(commentAdapter);

        listComments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ReadCommentActivity.class);
                intent.putExtra("data_id", view.getTag().toString());
                startActivity(intent);
            }
        });
    }
}
