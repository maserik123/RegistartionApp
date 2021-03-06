package com.androidtuts4u.arun.registartionapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidtuts4u.arun.registartionapp.adapter.UserDetailsAdapter;
import com.androidtuts4u.arun.registartionapp.database.UserDatabaseContract.UserDatabase;
import com.androidtuts4u.arun.registartionapp.database.UserDatabaseHelper;
import com.androidtuts4u.arun.registartionapp.model.UserDetails;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    UserDatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter userAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button btnRegister;

    List<UserDetails> userDetailsList;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new UserDatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        recyclerView = (RecyclerView) findViewById(R.id.rv_users);
        btnRegister = (Button) findViewById(R.id.bt_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userDetailsList = new ArrayList<UserDetails>();
        userDetailsList.clear();
        Cursor c1 = db.query(UserDatabase.TABLE_NAME, null, null, null, null, null, null);

        if (c1 != null && c1.getCount() != 0) {
            userDetailsList.clear();
            while (c1.moveToNext()) {
                UserDetails userDetailsItem = new UserDetails();

                userDetailsItem.setUserId(c1.getInt(c1.getColumnIndex(UserDatabase._ID)));
                userDetailsItem.setName(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL1)));
                userDetailsItem.setAddress(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL2)));
                userDetailsItem.setMobileNo(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL3)));
                userDetailsItem.setProfessiion(c1.getString(c1.getColumnIndex(UserDatabase.COLUMN_NAME_COL4)));
                userDetailsList.add(userDetailsItem);


            }


        }

        c1.close();
        layoutManager = new LinearLayoutManager(this);
        userAdapter = new UserDetailsAdapter(userDetailsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);


    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}
