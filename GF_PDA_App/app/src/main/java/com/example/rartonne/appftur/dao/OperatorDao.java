package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rartonne.appftur.model.Operator;
import com.example.rartonne.appftur.model.User;

import java.util.ArrayList;

/**
 * Created by rartonne on 29/06/2015.
 */
public class OperatorDao extends DaoBase<Operator> {
    public OperatorDao(Context context) {
        super(context);
    }

    public Operator select(Integer user_id){
        try {
            db = this.open();
            Operator operator = null;
            Cursor cursor =  db.rawQuery("SELECT operator_id FROM operator WHERE user_id = ?", new String[]{user_id.toString()});

            while(cursor.moveToNext()){
                operator = new Operator(cursor.getString(0));

                break;
            }
            cursor.close();

            this.close();
            return operator;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("FittingDao", e.getMessage());
            return null;
        }
    }
}
