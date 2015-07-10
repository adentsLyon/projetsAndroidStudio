package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.BatchBlacklist;

/**
 * Created by rartonne on 09/07/2015.
 */
public class BatchBlacklistDao extends DaoBase<BatchBlacklist> {
    public BatchBlacklistDao(Context context) {
        super(context);
    }

    public Boolean isBlacklisted(String batch_nr, String article_id){
        try{
            db = this.open();

            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM batch_blacklist WHERE batch_nr = ? AND article_id = ?", new String[]{batch_nr, article_id});

            cursor.moveToFirst();
            Integer count = cursor.getInt(0);
            cursor.close();

            if(count == 0)
                return false;
            else
                return true;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("UserDao", e.getMessage());
            return null;
        }
    }
}
