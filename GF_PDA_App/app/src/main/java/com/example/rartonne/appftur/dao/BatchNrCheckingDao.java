package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.util.Log;

import com.example.rartonne.appftur.model.BatchNrChecking;
import com.example.rartonne.appftur.model.Scanlog;
import com.example.rartonne.appftur.tools.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by rartonne on 10/07/2015.
 */
public class BatchNrCheckingDao extends DaoBase<BatchNrChecking>{
    public BatchNrCheckingDao(Context context) {
        super(context);
    }

    public boolean insert(){
        try {
            Date date = new Date();
            String id = UUID.randomUUID().toString();

            db = this.open();
            db.execSQL("INSERT INTO batch_nr_checking (createdon, modifiedon, gps_lat, gps_long, createdby, status_code, gf_sec_id, batch_nr_checking_id, batch_nr, article_id) VALUES(" +
                            "?, ?, 0, 0, ?, 400, ?, ?, ?, ?)",
                    new Object[]{date, date, GlobalClass.getUserId(), GlobalClass.getGf_sec_id(), id, GlobalClass.getBatch_nr(), GlobalClass.getArt_id()});

            this.close();
            return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("ScanlogDao", e.getMessage());
            return false;
        }
    }
}
