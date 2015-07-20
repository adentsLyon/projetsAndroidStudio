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
            db = this.open();
            ArrayList<SecIdData> secIdDatas = new ArrayList<>();

            Cursor cursor =  db.rawQuery("SELECT type, value, createdon, modifiedon, data_id FROM pda_sec_id_data",null);

            while(cursor.moveToNext()){
                secIdDatas.add(new SecIdData(cursor.getInt(4), cursor.getString(0), cursor.getString(1),  new Date(cursor.getString(2)), new Date(cursor.getString(3))));
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

    public ArrayList<SecIdData> selectAllComments() {
        try {
            db = this.open();
            ArrayList<SecIdData> secIdDatas = new ArrayList<>();

            Cursor cursor =  db.rawQuery("SELECT type, value, createdon, modifiedon, data_id FROM pda_sec_id_data WHERE type = 'comment'",null);

            while(cursor.moveToNext()){
                secIdDatas.add(new SecIdData(cursor.getInt(4), cursor.getString(0), cursor.getString(1), new Date(cursor.getString(2)), new Date(cursor.getString(3))));
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
            db = this.open();
            SecIdData secIdData = null;
            Cursor cursor =  db.rawQuery("SELECT value, createdon, modifiedon, data_id FROM pda_sec_id_data WHERE gf_sec_id = ?AND type = ?", new String[]{gf_sec_id, type});

            while(cursor.moveToNext()){
                secIdData = new SecIdData(cursor.getInt(3), type, cursor.getString(0), new Date(cursor.getString(1)), new Date(cursor.getString(2)));
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

    public SecIdData select(String data_id){
        try {
            db = this.open();
            SecIdData secIdData = null;
            Cursor cursor =  db.rawQuery("SELECT type, value, createdon, modifiedon FROM pda_sec_id_data WHERE data_id = ?", new String[]{data_id});

            cursor.moveToFirst();
            secIdData = new SecIdData(Integer.parseInt(data_id), cursor.getString(0), cursor.getString(1), new Date(cursor.getString(2)), new Date(cursor.getString(3)));
            cursor.close();

            this.close();
            return secIdData;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("SecIdDataDao", e.getMessage());
            return null;
        }
    }

    public Integer max(){
        try {
            db = this.open();
            SecIdData secIdData = null;
            Cursor cursor =  db.rawQuery("SELECT MAX(data_id) FROM pda_sec_id_data", null);

            cursor.moveToFirst();
            Integer max = cursor.getInt(0);
            cursor.close();

            this.close();
            return max;
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
            String date = formater.format(new Date());

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

    public boolean delete(String gf_sec_id, String type){
        try{
            db = this.open();
            db.execSQL("DELETE FROM pda_sec_id_data WHERE gf_sec_id = ? AND type = ?",
                    new Object[]{gf_sec_id, type});

            this.close();
            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("SecIdDataDao", e.getMessage());
            return false;
        }
    }
}
