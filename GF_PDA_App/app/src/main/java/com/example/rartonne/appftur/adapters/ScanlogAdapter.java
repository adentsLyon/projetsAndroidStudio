package com.example.rartonne.appftur.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.rartonne.appftur.R;
import com.example.rartonne.appftur.model.Scanlog;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by rartonne on 30/06/2015.
 */
public class ScanlogAdapter extends BaseAdapter {
    private Context mContext;
    //public String[] filenames;
    public ArrayList<Scanlog> scanlogs;

    // Gets the context so it can be used later
    public ScanlogAdapter(Context c, ArrayList<Scanlog> s) {
        mContext = c;
        //filenames = s;
        scanlogs = s;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        //return filenames.length;
        return scanlogs.size();
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
            tv.setLayoutParams(new GridView.LayoutParams(100, 55));
            tv.setPadding(8, 8, 8, 8);
        }
        else {
            tv = (TextView) convertView;
        }

        tv.setText(scanlogs.get(position).getArt_id());
        tv.setTextColor(Color.WHITE);
        tv.setId(position);

        return tv;
    }
}
