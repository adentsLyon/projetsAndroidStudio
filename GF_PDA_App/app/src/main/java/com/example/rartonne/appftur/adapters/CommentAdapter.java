package com.example.rartonne.appftur.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.SecIdData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rartonne on 08/07/2015.
 */
public class CommentAdapter extends BaseAdapter  {
    private Context mContext;
    //public String[] filenames;
    public ArrayList<SecIdData> secIdDatas;

    // Gets the context so it can be used later
    public CommentAdapter(Context c, ArrayList<SecIdData> s) {
        mContext = c;
        secIdDatas = s;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        //return filenames.length;
        return secIdDatas.size();
    }

    // Require for structure, not really used in my code.
    public Object getItem(int position) {
        return null;
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position,
                        View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(mContext);
        }
        else {
            tv = (TextView) convertView;
        }

        String format = "yy/MM/dd HH:mm:ss";

        SimpleDateFormat formater = new SimpleDateFormat(format);
        Date date = new Date(secIdDatas.get(position).getCreatedon().toString());
        String formatedDate = formater.format(date);

        tv.setText(formatedDate);
        tv.setBackgroundColor(Color.parseColor("#66c266"));
        tv.setTextColor(Color.WHITE);
        tv.setHeight(80);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setId(position);
        tv.setTag(secIdDatas.get(position).getData_id().toString());

        return tv;
    }
}
