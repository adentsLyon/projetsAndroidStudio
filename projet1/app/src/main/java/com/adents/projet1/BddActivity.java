package com.adents.projet1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.adents.projet1.dao.UserDao;
import com.adents.projet1.dialogs.AddEditUserDialog;

import java.util.ArrayList;

import model.User;


public class BddActivity extends FragmentActivity {

    private UserDao uDao;
    private ArrayList<User> users;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdd);

        fillUserListView();

    }

    private void fillUserListView(){
        uDao = new UserDao(this);

        users = uDao.findAll();

        if (users!=null) {

            adapter = new ArrayAdapter<User>(
                    this,
                    android.R.layout.simple_list_item_1,
                    users
            );

            ListView lv_users = (ListView) findViewById(R.id.lv_liste_bdd);
            lv_users.setAdapter(adapter);


            lv_users.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, android.view.View view, final int position, long id) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(BddActivity.this);
                    builder.setMessage(R.string.confirm_message)
                            .setTitle(R.string.confirm_title);

                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            boolean b = uDao.delete(users.get(position).getUserId());
                            if (b){
                                users.remove(position); // on supprime de la vue
                                adapter.notifyDataSetChanged();//refresh listview
                            }

                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });


                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bdd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnAddUserClick(View v){
        //creéation d'un fragment
        AddEditUserDialog myDialog = new AddEditUserDialog();
        //affichage d'un fragment
        myDialog.show(getFragmentManager(), "addDialog");
    }

    public void updateUsersList(User u) {
        users.add(u);
        adapter.notifyDataSetChanged();
    }
}
