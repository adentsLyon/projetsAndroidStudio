package com.example.rartonne.appftur;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rartonne.appftur.dao.SecIdDataDao;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.tools.GlobalClass;
import com.example.rartonne.appftur.tools.GlobalViews;

import java.io.File;


public class ReadCommentActivity extends GlobalViews {
    SecIdDataDao secIdDataDao;
    SecIdData secIdData;
    TextView tv_comment;
    ImageView imgComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comment);

        setHeader();
        setArticleHeader();
        fillReadComment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_comment, menu);
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

    public void fillReadComment(){
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        imgComment = (ImageView) findViewById(R.id.imgComment);
        secIdDataDao = new SecIdDataDao(this);
        Intent intent = getIntent();
        String data_id = intent.getStringExtra("data_id");
        String filepath = "/sdcard/" + GlobalClass.getCustomer_id().toString() + "/" + data_id + ".png";
        secIdData = secIdDataDao.select(data_id);
        tv_comment.setText(secIdData.getValue());
        Bitmap bmp = BitmapFactory.decodeFile(filepath);
        File file = new File(filepath);
        if(file.exists()) {
            imgComment.setImageBitmap(bmp);
            imgComment.setTag(filepath);
            imgComment.setVisibility(View.VISIBLE);
        }
    }
}
