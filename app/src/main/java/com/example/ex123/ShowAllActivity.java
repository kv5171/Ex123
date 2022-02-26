package com.example.ex123;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowAllActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner options;
    ListView lv;

    ArrayAdapter<String> adp;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    String [] allOptions = {"employees", "companies", "meals", "orders"};

    ArrayList<String> employeesArray, companiesArray, mealsArray, ordersArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        options = (Spinner) findViewById(R.id.options);
        lv = (ListView) findViewById(R.id.lv);

        options.setOnItemSelectedListener(this);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, allOptions);
        options.setAdapter(adp);

        hlp = new HelperDB(this);

        employeesArray = new ArrayList<>();
        companiesArray = new ArrayList<>();
        mealsArray = new ArrayList<>();
        ordersArray = new ArrayList<>();

        getEmployees();
//        getCompanies();
//        getMeals();
//        getOrders();
    }

    private void getEmployees()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, null, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employee.EMPLOYEE_ID);
        int col2 = crsr.getColumnIndex(Employee.COMPANY);
        int col3 = crsr.getColumnIndex(Employee.FIRST_NAME);
        int col4 = crsr.getColumnIndex(Employee.LAST_NAME);
        int col5 = crsr.getColumnIndex(Employee.PHONE);
        int col6 = crsr.getColumnIndex(Employee.KEY_ID);

        crsr.moveToFirst();
        employeesArray.add("id | key | name | phone | company");
        while (!crsr.isAfterLast()) {
            employeesArray.add(crsr.getString(col1) + " | " + crsr.getString(col6) + " | " + crsr.getString(col3) + " " + crsr.getString(col4) + " | " + crsr.getString(col5) + " | " + crsr.getString(col2));
            crsr.moveToNext();
        }
        crsr.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos)
        {
            case 0:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, employeesArray);
                lv.setAdapter(adp);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}