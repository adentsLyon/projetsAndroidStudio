package com.adents.projet1.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adents.projet1.BddActivity;
import com.adents.projet1.R;
import com.adents.projet1.dao.UserDao;

import model.Status;
import model.User;

/**
 * Created by amachado on 24/06/2015.
 */
public class AddEditUserDialog extends DialogFragment {

    private Activity activity;
    private EditText et_login;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_addedituser, container, false);
        getDialog().setTitle("Ajout d'un utilisateur");

        et_login = (EditText) v.findViewById(R.id.et_login);

        Button btnSave = (Button) v.findViewById(R.id.btn_Save);
        Button btnCancel = (Button) v.findViewById(R.id.btn_Cancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveClick(v);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelClick(v);
            }
        });
        return v;

    }

    protected void btnSaveClick(View v) {
    String login = et_login.getText().toString();

        User u = new User();
        u.setLogin(login);
        u.setPassword("Test");
        u.setCustomerId(1);
        u.setStatus(new Status(0,null,null));
        //insérer en bdd
        boolean b = new UserDao(activity).insert(u);

        if (b){

                    ((BddActivity) activity).updateUsersList(u);
           getDialog().dismiss();
        }

    }



    protected void btnCancelClick(View v) {
        getDialog().dismiss();
    }

}
