package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.SecIdData;
import com.example.rartonne.appftur.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rartonne on 02/07/2015.
 */
public class SecIdDataDao extends DaoBase<Scanlog> {
    public SecIdDataDao(Context context) {
        super(context);
    }

    public ArrayList<SecIdData> selectAll() {
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new SimpleDateFormat(format);
            Date date = new Date();

            db = this.open();
            ArrayList<SecIdData> secIdDatas = new ArrayList<>();

            Cursor cursor =  db.rawQuery("SELECT type, value FROM pda_sec_id_data",null);

            while(cursor.moveToNext()){
                secIdDatas.add(new SecIdData(cursor.getString(0), cursor.getString(1), date, date));
            }
            cursor.close();

            this.close();
            return secIdDatas;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return null;
        }
    }

    public SecIdData select(String gf_sec_id, String type){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new SimpleDateFormat(format);
            Date date = new Date();

            db = this.open();
            SecIdData secIdData = null;
            Cursor cursor =  db.rawQuery("SELECT value FROM pda_sec_id_data WHERE gf_sec_id = ?AND type = ?", new String[]{gf_sec_id, type});

            while(cursor.moveToNext()){
                secIdData = new SecIdData(type, cursor.getString(0), date, date);
                break;
            }
            cursor.close();

            this.close();
            return secIdData;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("SecIdDataDao", e.getMessage());
            return null;
        }
    }

    public Integer count(String gf_sec_id, String type){
        try {
            db = this.open();
            Cursor cursor =  db.rawQuery("SELECT COUNT(*) FROM pda_sec_id_data WHERE type = ? AND gf_sec_id = ?", new String[]{type, gf_sec_id});
            cursor.moveToFirst();
            Integer count = cursor.getInt(0);

            cursor.close();

            this.close();
            return count;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("SecIdDataDao", e.getMessage());
            return null;
        }
    }

    public boolean insert(String gf_sec_id, String type, String value){
        try{
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new SimpleDateFormat(format);
            Date date = new Date();

            db = this.open();
            db.execSQL("INSERT INTO pda_sec_id_data (type, value, createdon, modifiedon, gf_sec_id) VALUES(?,?,?,?,?)",
                    new Object[]{type, value, date, date, gf_sec_id});

            this.close();
            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("SecIdDataDao", e.getMessage());
            return false;
        }
    }

    public boolean update(String gf_sec_id, String type, String value){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();

            db = this.open();
            db.execSQL("UPDATE pda_sec_id_data SET value = ?, modifiedon = ? WHERE gf_sec_id = ? AND type = ?",
                    new Object[]{value, date, gf_sec_id, type});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("SecIdDataDao", e.getMessage());
            return false;
        }
    }
}
