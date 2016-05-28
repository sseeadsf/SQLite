package com.example.admin.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.database.sqlite.SQLiteDatabase;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase database;
    private int id;
    private String firstname, lastname, birthday, contact, classs;
    private double gpa, cpa;
    private ArrayList<String> arr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //createDB();
        arr = allStudent();
        ListView list = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText id1 = (EditText) findViewById(R.id.id1);
                EditText lastname1 = (EditText) findViewById(R.id.lastname1);
                EditText firstname1 = (EditText) findViewById(R.id.firstname1);
                EditText contact1 = (EditText) findViewById(R.id.contact1);
                EditText classs1 = (EditText) findViewById(R.id.classs1);
                EditText birthday1 = (EditText) findViewById(R.id.birthday1);
                EditText gpa1 = (EditText) findViewById(R.id.gpa1);
                EditText cpa1 = (EditText) findViewById(R.id.cpa1);
                String[] student = arr.get(position).toString().split("\\s");
                id1.setText(student[0]);
                firstname1.setText(student[1]);
                lastname1.setText(student[2]);
                birthday1.setText(student[3]);
                contact1.setText(student[4]);
                classs1.setText(student[5]);
                gpa1.setText(student[6]);
                cpa1.setText(student[7]);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void createDB(){
        database = openOrCreateDatabase("sinhvien", MODE_PRIVATE, null);
        String sql = "CREATE TABLE school (";
        sql+= "id INTEGER primary key,";
        sql+= "firstname TEXT,";
        sql+= "lastname TEXT,";
        sql+= "birthday TEXT,";
        sql+= "contact TEXT,";
        sql+= "class TEXT,";
        sql+= "GPA DOUBLE,";
        sql+= "CPA DOUBLE)";
        database.execSQL(sql);
    }
    public ArrayList<String> allStudent(){
        database = openOrCreateDatabase("sinhvien", MODE_PRIVATE, null);
        Cursor c = database.rawQuery("select * from school", null);
        ArrayList<String> data = new ArrayList<String>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            id = c.getInt(0);
            firstname = c.getString(1);
            lastname = c.getString(2);
            birthday = c.getString(3);
            contact = c.getString(4);
            classs = c.getString(5);
            gpa = c.getDouble(6);
            cpa = c.getDouble(7);
            data.add( id + "\t" + firstname + "\t" + lastname + "\t" + birthday + "\t" + contact + "\t" + classs + "\t" + gpa + "\t" + cpa);
            c.moveToNext();
        }
        c.close();
        return data;
    }
    public void btn_create(View v) {
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }
    public void btn_refresh(View v){
        arr = allStudent();
        ListView list = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        list.setAdapter(adapter);
    }
    public void btn_edit(View v){
        database = openOrCreateDatabase("sinhvien", MODE_PRIVATE, null);
        EditText id1 = (EditText) findViewById(R.id.id1);
        EditText lastname1 = (EditText) findViewById(R.id.lastname1);
        EditText firstname1 = (EditText) findViewById(R.id.firstname1);
        EditText contact1 = (EditText) findViewById(R.id.contact1);
        EditText classs1 = (EditText) findViewById(R.id.classs1);
        EditText birthday1 = (EditText) findViewById(R.id.birthday1);
        EditText gpa1 = (EditText) findViewById(R.id.gpa1);
        EditText cpa1 = (EditText) findViewById(R.id.cpa1);
        ContentValues cv = new ContentValues();
        cv.put("id", Integer.parseInt(id1.getText().toString()));
        cv.put("firstname", firstname1.getText().toString());
        cv.put("lastname", lastname1.getText().toString());
        cv.put("birthday", birthday1.getText().toString());
        cv.put("contact", contact1.getText().toString());
        cv.put("class", classs1.getText().toString());
        cv.put("gpa", Double.parseDouble(gpa1.getText().toString()));
        cv.put("cpa", Double.parseDouble(cpa1.getText().toString()));
        database.update("school", cv, "id="+Integer.parseInt(id1.getText().toString()), null);
        Toast.makeText(this, "Update complete", Toast.LENGTH_SHORT).show();
        id1.setText("");
        lastname1.setText("");
        firstname1.setText("");
        contact1.setText("");
        classs1.setText("");
        birthday1.setText("");
        gpa1.setText("");
        cpa1.setText("");
    }
}
