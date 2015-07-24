package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.PdaSettings;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.tools.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rartonne on 22/07/2015.
 */
public class PdaSettingsDao extends DaoBase<PdaSettings> {
    public PdaSettingsDao(Context context) {
        super(context);
    }

    public PdaSettings select(String pda_id){
        try {
            db = this.open();
            PdaSettings pdaSettings = null;
            Cursor cursor =  db.rawQuery("SELECT * FROM pda_settings WHERE pda_id = ?", new String[]{pda_id});

            while(cursor.moveToNext()){
                pdaSettings = new PdaSettings(pda_id, cursor.getString(1), cursor.getString(2), cursor.getInt(3), new Date(cursor.getString(4)));
                break;
            }
            cursor.close();

            this.close();
            return pdaSettings;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("PdaSettingsDao", e.getMessage());
            return null;
        }
    }

    public boolean update(String last_update){
        try {
            db = this.open();
            db.execSQL("UPDATE pda_settings SET last_update = ?",
                    new Object[]{last_update});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("PdaSettingsDao", e.getMessage());
            return false;
        }
    }
}

