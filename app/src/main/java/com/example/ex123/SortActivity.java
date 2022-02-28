package com.example.ex123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Sort activity.
 */
public class SortActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView lv;
    Spinner options;
    ArrayAdapter<String> adp;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    String [] allOptions = {"show just ids", "show just names", "order Employees by id"};
    ArrayList<String> idsArray, namesArray, orderEmployeesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        options = (Spinner) findViewById(R.id.options);
        lv = (ListView) findViewById(R.id.lv);

        options.setOnItemSelectedListener(this);

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, allOptions);
        options.setAdapter(adp);

        hlp = new HelperDB(this);

        idsArray = new ArrayList<>();
        namesArray = new ArrayList<>();
        orderEmployeesArray = new ArrayList<>();

        getIds();
        getNames();
        getOrderedEmployees();
    }

    /**
     *  get all id employees from the db
     */
    private void getIds()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, new String[]{Employee.EMPLOYEE_ID}, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employee.EMPLOYEE_ID);

        crsr.moveToFirst();
        idsArray.add("id");
        while (!crsr.isAfterLast()) {
            idsArray.add(crsr.getString(col1));
            crsr.moveToNext();
        }
        crsr.close();
    }

    /**
     *  get all names employees from db
     */
    private void getNames()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, new String[]{Employee.FIRST_NAME, Employee.LAST_NAME}, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employee.FIRST_NAME);
        int col2 = crsr.getColumnIndex(Employee.LAST_NAME);

        crsr.moveToFirst();
        namesArray.add("firstName lastName");
        while (!crsr.isAfterLast()) {
            namesArray.add(crsr.getString(col1) + " " + crsr.getString(col2));
            crsr.moveToNext();
        }
        crsr.close();
    }

    /**
     *  get all the id employees from the db sorted
     */
    private void getOrderedEmployees()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, null, null, null, null, null, Employee.EMPLOYEE_ID, null);

        int col1 = crsr.getColumnIndex(Employee.EMPLOYEE_ID);
        int col2 = crsr.getColumnIndex(Employee.COMPANY);
        int col3 = crsr.getColumnIndex(Employee.FIRST_NAME);
        int col4 = crsr.getColumnIndex(Employee.LAST_NAME);
        int col5 = crsr.getColumnIndex(Employee.PHONE);
        int col6 = crsr.getColumnIndex(Employee.KEY_ID);

        crsr.moveToFirst();
        orderEmployeesArray.add("id | key | name | phone | company");
        while (!crsr.isAfterLast()) {
            orderEmployeesArray.add(crsr.getString(col1) + " | " + crsr.getString(col6) + " | " + crsr.getString(col3) + " " + crsr.getString(col4) + " | " + crsr.getString(col5) + " | " + crsr.getString(col2));
            crsr.moveToNext();
        }
        crsr.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        switch (pos) {
            case 0:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, idsArray);
                break;
            case 1:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, namesArray);
                break;
            case 2:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, orderEmployeesArray);
                break;
        }
        lv.setAdapter(adp);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        else if (id == R.id.employee)
        {
            Intent si = new Intent(this, EmployeeActivity.class);
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
        else if (id == R.id.home)
        {
            Intent si = new Intent(this, MainActivity.class);
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