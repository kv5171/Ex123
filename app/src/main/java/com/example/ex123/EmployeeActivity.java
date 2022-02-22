package com.example.ex123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {
    EditText lastName, firstName, company, id, phone;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        lastName = (EditText) findViewById(R.id.lastName);
        firstName = (EditText) findViewById(R.id.firstName);
        company = (EditText) findViewById(R.id.company);
        id = (EditText) findViewById(R.id.id);
        phone = (EditText) findViewById(R.id.phone);

        // all the numeric fields accepts just numbers
        id.setTransformationMethod(null);
        phone.setTransformationMethod(null);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
    }

    public void addEmployee(View view) {
        String lastNameString = lastName.getText().toString();
        String firstNameString = firstName.getText().toString();
        String companyString = company.getText().toString();
        String idString = id.getText().toString();
        String phoneString = phone.getText().toString();


        // all parameters != null ""
        if (lastNameString.equals("") || firstNameString.equals("") || companyString.equals("") || idString.equals("") || phoneString.equals(""))
        {
            Toast.makeText(EmployeeActivity.this, "Inputs must have a value!", Toast.LENGTH_SHORT).show();
        }
        else if(!goodId(idString)) { // not good id
            Toast.makeText(EmployeeActivity.this, "Wrong employee ID syntax", Toast.LENGTH_SHORT).show();
        }
        else if(checkInDB(phoneString, Employee.PHONE) || checkInDB(idString, Employee.EMPLOYEE_ID))
        {
            Toast.makeText(EmployeeActivity.this, "Data is in the db already!", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues cv = new ContentValues();

            cv.put(Employee.LAST_NAME, lastNameString);
            cv.put(Employee.FIRST_NAME, firstNameString);
            cv.put(Employee.COMPANY, companyString);
            cv.put(Employee.EMPLOYEE_ID, idString);
            cv.put(Employee.PHONE, phoneString);

            db = hlp.getWritableDatabase();
            db.insert(Employee.TABLE_EMPLOYEE, null, cv);
            db.close();
        }
    }

    private boolean checkInDB(String param, String paramType)
    {
        int crsrLen = 0;

        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, new String[]{paramType}, paramType+"=?", new String[]{param}, paramType+"=?", null, null, null);

        crsr.moveToFirst();
        crsrLen = crsr.getColumnCount();
        crsr.close();
        db.close();

        // if there is no elements returned - the param not in the db!
        return crsrLen != 0;
    }

    private boolean goodId(String id)
    {
        return true;
    }
}