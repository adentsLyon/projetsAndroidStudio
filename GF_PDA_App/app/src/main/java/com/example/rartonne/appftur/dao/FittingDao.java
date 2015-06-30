package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.Fitting;

import java.sql.SQLException;

/**
 * Created by rartonne on 29/06/2015.
 */
public class FittingDao  extends DaoBase<Fitting>{
    public FittingDao(Context context) {
        super(context);
    }

    public Fitting select(String art_id){
        try {
            db = this.open();
            Fitting fitting = null;
            Cursor cursor =  db.rawQuery("SELECT gf_art_name3_ln5, ddd_art_druck, ddd_art_sdr, ddd_art_dim, catalog_id " +
                    "FROM t_ddd_lab AS lab " +
                    "LEFT JOIN article_catalog AS ac ON ac.article_id = lab.ddd_art_id " +
                    "WHERE ddd_art_id = ?", new String[]{art_id});

            while(cursor.moveToNext()){
                fitting = new Fitting(art_id,
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );

                break;
            }
            cursor.close();

            this.close();
            return fitting;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("FittingDao",e.getMessage());
            return null;
        }
    }
}
