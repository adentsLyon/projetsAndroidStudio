package com.adents.projet1.dao;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import model.Status;
import model.User;

/**
 * Created by amachado on 24/06/2015.
 */
public class UserDao extends DaoBase<User>{

    public UserDao(Context context) {
        super(context);
    }

    public boolean insert(User u){
        try {
            db = this.open();

            db.execSQL("INSERT INTO user(login,password,email,lang_id,customer_id,status_code) "
                            +"VALUES(?,?,?,?,?,?)",
                    new Object[]{u.getLogin(), u.getPassword(),
                            u.getEmail(), u.getLangId(), u.getCustomerId(),
                            u.getStatus().getCode()});
            this.close();
            return true;
        }catch(SQLException e){
            //TODO Logs dans un fichier
            Log.e("UserDao",e.getMessage());
            return false;
        }
    }

    public ArrayList<User> findAll() {
        try {
            db = this.open();
            ArrayList<User> users = new ArrayList<>();
            Cursor cursor =  db.rawQuery("SELECT user_id, login,password,email,lang_id,user.status_code, status_name,customer_id "
                    + "FROM user  "
                    + "INNER JOIN status_name ON(user.status_code=status_name.status_code)",null);

            while(cursor.moveToNext()){
                users.add(new User(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                new Status(cursor.getInt(5),cursor.getString(6),null),
                                cursor.getInt(7)
                        )
                );
            }
            cursor.close();

            this.close();
            return users;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("UserDao",e.getMessage());
            return null;
        }
    }

    public User find(String login) {
        try {
            db = this.open();
            User user = null;
            Cursor cursor =  db.rawQuery("SELECT user_id, login,password,email,lang_id,user.status_code, status_name,customer_id "
                    + "FROM user  "
                    + "INNER JOIN status_name ON(user.status_code=status_name.status_code) "
                    + "WHERE login=?", new String[]{login});

            while(cursor.moveToNext()){
                new User(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        new Status(cursor.getInt(5),cursor.getString(6),null),
                        cursor.getInt(7)

                );
                break;
            }
            cursor.close();

            this.close();
            return user;
        }catch(Exception e){
            //TODO Logs dans un fichier
            Log.e("UserDao",e.getMessage());
            return null;
        }
    }

    public boolean execReq(String req){
        try {
            db = this.open();
            db.execSQL(req);
            this.close();
            return true;
        }catch(SQLException e){
            //TODO Logs dans un fichier
            Log.e("UserDao",e.getMessage());
            return false;
        }
    }



    public boolean delete(int userId){

        try {
            db = this.open();
            db.execSQL("DELETE FROM user WHERE user_id=?", new Object[]{userId});
            this.close();
            return true;
        }catch(SQLException e){
            //TODO Logs dans un fichier
            Log.e("UserDao",e.getMessage());
            return false;
        }
    }


}

