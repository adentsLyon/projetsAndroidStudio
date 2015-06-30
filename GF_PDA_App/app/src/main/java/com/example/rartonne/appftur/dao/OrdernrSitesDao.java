package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.OrdernrSites;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rartonne on 29/06/2015.
 */
public class OrdernrSitesDao extends DaoBase<OrdernrSites> {
    public OrdernrSitesDao(Context context) {
        super(context);
    }

    public String selectSite(String ordernr){
        try {
            db = this.open();
            OrdernrSites ordernrSites = null;
            Cursor cursor =  db.rawQuery("SELECT name FROM ordernr_sites LEFT JOIN construction_site AS cs ON cs.site_id = ordernr_sites.site_id WHERE ordernr = ?", new String[]{ordernr});

            cursor.moveToFirst();
            String name = cursor.getString(0);
            cursor.close();

            this.close();
            return name;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("FittingDao", e.getMessage());
            return null;
        }
    }

    public ArrayList<OrdernrSites> selectAll() {
        try {
            db = this.open();
            ArrayList<OrdernrSites> ordernrSites = new ArrayList<>();
            Cursor cursor =  db.rawQuery("SELECT ordernr, name FROM ordernr_sites LEFT JOIN construction_site AS cs ON cs.site_id = ordernr_sites.site_id", null);

            while(cursor.moveToNext()){
                ordernrSites.add(new OrdernrSites(cursor.getString(0), cursor.getString(1)));
            }
            cursor.close();

            this.close();
            return ordernrSites;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("UserDao", e.getMessage());
            return null;
        }
    }

    public Integer count(String ordernr){
        try{
            db = this.open();

            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM ordernr_sites WHERE ordernr = ?", new String[]{ordernr});

            cursor.moveToFirst();
            Integer count = cursor.getInt(0);
            cursor.close();
            return count;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("FittingDao", e.getMessage());
            return null;
        }
    }

    public boolean insert(String ordernr, Integer installer_id){
        try {
            db = this.open();
            db.execSQL("INSERT INTO ordernr_sites (ordernr, site_id, installer_id) VALUES(?, 0, ?)",
                    new Object[]{ordernr, installer_id});

            this.close();
            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("OrdernrSitesDao", e.getMessage());
            return false;
        }
    }
}
