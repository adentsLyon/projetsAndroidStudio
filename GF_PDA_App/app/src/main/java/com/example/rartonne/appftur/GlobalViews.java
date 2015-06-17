package com.example.rartonne.appftur;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by rartonne on 17/06/2015.
 */
public class GlobalViews extends Activity {
    public void toActivity(View view) {
        String name = view.getTag().toString();
        String activity = "com.example.rartonne.appftur." + name;
        Class act;
        try {
            act = Class.forName(activity);

            Intent intent = new Intent(getApplicationContext(), act);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ClassNotFoundException e){

        }
    }
}
