package com.example.ex123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Employee activity.
 */
public class EmployeeActivity extends AppCompatActivity {
    EditText lastName, firstName, company, id, phone;

    SQLiteDatabase db;
    HelperDB hlp;

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

    /**
     * Add employee to db.
     *
     * @param view the view
     */
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
        else if(HelperFunc.checkInDB(Employee.TABLE_EMPLOYEE, phoneString, Employee.PHONE, hlp) || HelperFunc.checkInDB(Employee.TABLE_EMPLOYEE, idString, Employee.EMPLOYEE_ID, hlp))
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

            Toast.makeText(EmployeeActivity.this, "Add Employee completed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean goodId(String id)
    {
        return true;
    }


    /**
     * Create the options menu
     *
     * @param menu the menu
     * @return true if success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Go where clicked
     *
     * @param item the item in menu that was clicked
     *  @return true if success
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.credits)
        {
            Intent si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }
        else if (id == R.id.company)
        {
            Intent si = new Intent(this, CompanyActivity.class);
            startActivity(si);
        }
        else if (id == R.id.home)
        {
            Intent si = new Intent(this, MainActivity.class);
            startActivity(si);
        }
        else if (id == R.id.order)
        {
            Intent si = new Intent(this, OrderActivity.class);
            startActivity(si);
        }
        else if (id == R.id.show)
        {
            Intent si = new Intent(this, ShowAllActivity.class);
            startActivity(si);
        }
        else if (id == R.id.sort)
        {
            Intent si = new Intent(this, SortActivity.class);
            startActivity(si);
        }
        else if (id == R.id.change)
        {
            Intent si = new Intent(this, ChangePhoneActivity.class);
            startActivity(si);
        }

        return true;
    }
}