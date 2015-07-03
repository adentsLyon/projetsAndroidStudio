package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.rartonne.appftur.model.OrdernrSites;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rartonne on 29/06/2015.
 */
public class ScanlogDao extends DaoBase<Scanlog> {
    public ScanlogDao(Context context) {
        super(context);
    }

    public Scanlog select(String gf_sec_id){
        try {
            db = this.open();
            Scanlog scanlog = null;
            Cursor cursor =  db.rawQuery("SELECT * FROM scan_log WHERE gf_sec_id = ?", new String[]{gf_sec_id});

            while(cursor.moveToNext()){
                scanlog = new Scanlog(gf_sec_id, cursor.getDouble(1), cursor.getDouble(2), new Date(cursor.getString(3)), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10), cursor.getString(11));
                break;
            }
            cursor.close();

            this.close();
            return scanlog;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("UserDao", e.getMessage());
            return null;
        }
    }

    public ArrayList<Scanlog> selectAll() {
        try {
            db = this.open();
            ArrayList<Scanlog> scanlogs = new ArrayList<>();

            SimpleDateFormat formater = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            Cursor cursor =  db.rawQuery("SELECT gf_sec_id, gps_lat, gps_long, scan_date, login, status_name, art_id, customer_order_nr, welding_sketch_nr, serial_wm_nr, fusion_nr, source" +
                    " FROM scan_log AS sl" +
                    " LEFT JOIN status_name AS stat ON stat.status_code = sl.status_code" +
                    " LEFT JOIN user ON user.user_id = sl.user_id",null);

            while(cursor.moveToNext()){
                scanlogs.add(new Scanlog(cursor.getString(0), cursor.getDouble(1), cursor.getDouble(2), new Date(cursor.getString(3)), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10), cursor.getString(11)));
            }
            cursor.close();

            this.close();
            return scanlogs;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return null;
        }
    }

    public boolean insert(Scanlog scanlog){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new SimpleDateFormat( format );
            Date date = new Date();

            db = this.open();
            db.execSQL("INSERT INTO scan_log (gf_sec_id, user_id, art_id, status_code, scan_date, source) VALUES(?,?,?, 200, ?, 'PDA')",
                    new Object[]{scanlog.getGf_sec_id(), scanlog.getUser_id().toString(), scanlog.getArt_id(), date});

            this.close();
            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }

    public boolean updateInstallation(String gf_sec_id, String sketch_number){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();

            db = this.open();
            db.execSQL("UPDATE scan_log SET welding_sketch_nr = ?, scan_date = ? WHERE gf_sec_id = ?",
                    new Object[]{sketch_number, date, gf_sec_id});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }

    public boolean updateScan(String gf_sec_id){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();

            db = this.open();
            db.execSQL("UPDATE scan_log SET scan_date = ? WHERE gf_sec_id = ?",
                    new Object[]{date, gf_sec_id});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }

    public boolean updateJob(String gf_sec_id, String customer_order_nr){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();

            db = this.open();
            db.execSQL("UPDATE scan_log SET customer_order_nr = ?, scan_date = ? WHERE gf_sec_id = ?",
                    new Object[]{customer_order_nr, date, gf_sec_id});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }

    public boolean updateGps(String gf_sec_id, Double gpsLat, Double gpsLong){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();

            db = this.open();
            db.execSQL("UPDATE scan_log SET gps_lat = ?, gps_long = ?, scan_date = ? WHERE gf_sec_id = ?",
                    new Object[]{gpsLat, gpsLong, date, gf_sec_id});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }

    public boolean updateWelding(String gf_sec_id, String wm, String fusion){
        try {
            String format = "yy/MM/dd HH:mm:ss";

            SimpleDateFormat formater = new java.text.SimpleDateFormat( format );
            Date date = new java.util.Date();

            db = this.open();
            db.execSQL("UPDATE scan_log SET serial_wm_nr = ?, fusion_nr = ?, scan_date = ? WHERE gf_sec_id = ?",
                    new Object[]{wm, fusion, date, gf_sec_id});

            this.close();

            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }

    public Integer count(){
        try {
            db = this.open();
            Cursor cursor =  db.rawQuery("SELECT COUNT(*) FROM scan_log", null);
            cursor.moveToFirst();
            Integer count = cursor.getInt(0);

            cursor.close();

            this.close();
            return count;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return null;
        }
    }

    public Integer count(String gf_sec_id){
        try {
            db = this.open();
            Cursor cursor =  db.rawQuery("SELECT COUNT(*) FROM scan_log WHERE gf_sec_id = ?", new String[]{gf_sec_id});
            cursor.moveToFirst();
            Integer count = cursor.getInt(0);

            cursor.close();

            this.close();
            return count;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return null;
        }
    }
}
