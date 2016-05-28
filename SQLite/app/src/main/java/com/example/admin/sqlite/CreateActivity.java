package com.example.admin.sqlite;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void btn_clear(View v){
        EditText id = (EditText) findViewById(R.id.id);
        EditText firstname = (EditText) findViewById(R.id.firstname);
        EditText lastname = (EditText) findViewById(R.id.lastname);
        EditText contact = (EditText) findViewById(R.id.contact);
        EditText classs = (EditText) findViewById(R.id.classs);
        EditText gpa = (EditText) findViewById(R.id.gpa);
        EditText cpa = (EditText) findViewById(R.id.cpa);
        EditText birthday = (EditText) findViewById(R.id.birthday);
        id.setText("");
        firstname.setText("");
        lastname.setText("");
        contact.setText("");
        classs.setText("");
        gpa.setText("");
        cpa.setText("");
        birthday.setText("");
    }
    public void btn_ok(View v) {
        EditText id = (EditText) findViewById(R.id.id);
        EditText firstname = (EditText) findViewById(R.id.firstname);
        EditText lastname = (EditText) findViewById(R.id.lastname);
        EditText contact = (EditText) findViewById(R.id.contact);
        EditText classs = (EditText) findViewById(R.id.classs);
        EditText gpa = (EditText) findViewById(R.id.gpa);
        EditText cpa = (EditText) findViewById(R.id.cpa);
        EditText birthday = (EditText) findViewById(R.id.birthday);
        if (id.getText().toString().equals("") || gpa.getText().toString().equals("") || cpa.getText().toString().equals("")) {
            AlertDialog.Builder b = new AlertDialog.Builder(CreateActivity.this);
            b.setTitle("Alert");
            b.setMessage("Some information can't be null");
            b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b.create().show();
        } else {
            SQLiteDatabase database = openOrCreateDatabase("sinhvien", MODE_PRIVATE, null);
            ContentValues values = new ContentValues();
            values.put("id", Integer.parseInt(id.getText().toString()));
            values.put("firstname", firstname.getText().toString());
            values.put("lastname", lastname.getText().toString());
            values.put("birthday", birthday.getText().toString());
            values.put("contact", contact.getText().toString());
            values.put("class", classs.getText().toString());
            values.put("GPA", Double.parseDouble(gpa.getText().toString()));
            values.put("CPA", Double.parseDouble(cpa.getText().toString()));
            String mess = "";
            if (database.insert("school", null, values) == -1) {
                mess = "Something went wrong";
            } else {
                mess = "Insert success";
            }
            Toast.makeText(this, mess, Toast.LENGTH_LONG).show();
        }
    }

}
