package com.example.rartonne.appftur.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.rartonne.appftur.model.Fitting;
import com.example.rartonne.appftur.model.User;

/**
 * Created by rartonne on 29/06/2015.
 */
public class UserDao  extends DaoBase<User>{
    public UserDao(Context context) {
        super(context);
    }

    public User select(String login){
        try {
            db = this.open();
            User user = null;
            Cursor cursor =  db.rawQuery("SELECT user.user_id, installer_id " +
                    "FROM user " +
                    "LEFT JOIN operator ON operator.user_id = user.user_id " +
                    "WHERE login = ?", new String[]{login});

            while(cursor.moveToNext()){
                user = new User(cursor.getInt(0), login, cursor.getInt(1));
                break;
            }
            cursor.close();

            this.close();
            return user;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("FittingDao", e.getMessage());
            return null;
        }
    }

    public Integer count(String login){
        try{
            db = this.open();

            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM user WHERE login = ?", new String[]{login});

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
}
