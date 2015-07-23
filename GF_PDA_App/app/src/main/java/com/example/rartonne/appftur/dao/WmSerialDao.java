package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.model.OrdernrSites;
import com.example.rartonne.appftur.model.WmSerial;
import com.example.rartonne.appftur.tools.GlobalClass;

import java.util.ArrayList;

/**
 * Created by rartonne on 21/07/2015.
 */
public class WmSerialDao extends DaoBase<WmSerial> {
    public WmSerialDao(Context context) {
        super(context);
    }

    public ArrayList<WmSerial> selectAll() {
        try {
            db = this.open();
            ArrayList<WmSerial> wmserials = new ArrayList<>();
            Cursor cursor =  db.rawQuery("SELECT * FROM wm_serial", null);

            while(cursor.moveToNext()){
                wmserials.add(new WmSerial(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)));
            }
            cursor.close();

            this.close();
            return wmserials;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("UserDao", e.getMessage());
            return null;
        }
    }

    public boolean insert(String serialNumber){
        try {
            db = this.open();
            db.execSQL("INSERT INTO wm_serial (serial_number, installer_id) VALUES(?, ?)",
                    new Object[]{serialNumber, GlobalClass.getInstaller_id()});

            this.close();
            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("WmSerialDao", e.getMessage());
            return false;
        }
    }

    public Integer count(String serialNumber){
        try{
            db = this.open();

            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM wm_serial WHERE serial_number = ?", new String[]{serialNumber});

            cursor.moveToFirst();
            Integer count = cursor.getInt(0);
            cursor.close();
            return count;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("WmSerialDao", e.getMessage());
            return null;
        }
    }
}
