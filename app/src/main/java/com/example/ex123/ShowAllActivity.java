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
 * The type Show all activity.
 */
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
        getCompanies();
        getMeals();
        getOrders();
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

    private void getCompanies()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Company.TABLE_COMPANY, null, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Company.COMPANY_NUMBER);
        int col2 = crsr.getColumnIndex(Company.NAME);
        int col3 = crsr.getColumnIndex(Company.FIRST_PHONE);
        int col4 = crsr.getColumnIndex(Company.SECOND_PHONE);

        crsr.moveToFirst();
        companiesArray.add("id | name | phone1 | phone2");
        while (!crsr.isAfterLast()) {
            companiesArray.add(crsr.getString(col1) + " | " + crsr.getString(col2) + " | " + crsr.getString(col3) + " | " + crsr.getString(col4));
            crsr.moveToNext();
        }
        crsr.close();
    }

    private void getMeals()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Meal.TABLE_MEAL, null, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Meal.MEAL_ID);
        int col2 = crsr.getColumnIndex(Meal.FIRST_MEAL);
        int col3 = crsr.getColumnIndex(Meal.MAIN_MEAL);
        int col4 = crsr.getColumnIndex(Meal.EXTRA);
        int col5 = crsr.getColumnIndex(Meal.DESSERT);
        int col6 = crsr.getColumnIndex(Meal.DRINK);

        crsr.moveToFirst();
        mealsArray.add("id | meal1 | meal2 | extra | dessert | drink");
        while (!crsr.isAfterLast()) {
            mealsArray.add(crsr.getString(col1) + " | " + crsr.getString(col2) + " | " + crsr.getString(col3) + " | " + crsr.getString(col4) + " | " + crsr.getString(col5) + " | " + crsr.getString(col6));
            crsr.moveToNext();
        }
        crsr.close();
    }

    private void getOrders()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Order.TABLE_ORDER, null, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Order.ORDER_DATE);
        int col2 = crsr.getColumnIndex(Order.ORDER_TIME);
        int col3 = crsr.getColumnIndex(Order.WORKER_ID);
        int col4 = crsr.getColumnIndex(Order.MEAL_ID);
        int col5 = crsr.getColumnIndex(Order.COMPANY);

        crsr.moveToFirst();
        ordersArray.add("date | time | worker | mealId | company");
        while (!crsr.isAfterLast()) {
            ordersArray.add(crsr.getString(col1) + " | " + crsr.getString(col2) + " | " + crsr.getString(col3) + " | " + crsr.getString(col4) + " | " + crsr.getString(col5));
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
                break;
            case 1:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, companiesArray);
                break;
            case 2:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, mealsArray);
                break;
            case 3:
                adp = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, ordersArray);
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

        if (id == R.id.home)
        {
            Intent si = new Intent(this, MainActivity.class);
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
        else if (id == R.id.credits)
        {
            Intent si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }

        return true;
    }
}