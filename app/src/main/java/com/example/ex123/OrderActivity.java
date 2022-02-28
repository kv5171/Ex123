package com.example.ex123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Order activity.
 */
public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner workerID, companiesNames;
    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    AlertDialog.Builder adb;
    String companyID, workerId;

    ArrayList<String> workersID = new ArrayList<>();
    ArrayAdapter<String> idsAdp;
    HashMap<String, Integer> companies = new HashMap<>();
    ArrayAdapter<String> companiesAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        workerID = (Spinner) findViewById(R.id.workerID);
        companiesNames = (Spinner) findViewById(R.id.companiesNames);

        workerID.setOnItemSelectedListener(this);
        companiesNames.setOnItemSelectedListener(this);

        hlp = new HelperDB(this);

        getSpinnersData();
    }

    private void getSpinnersData()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, new String[]{Employee.EMPLOYEE_ID}, null, null, null, null, Employee.EMPLOYEE_ID + " DESC", null);

        int col1 = crsr.getColumnIndex(Employee.EMPLOYEE_ID);

        crsr.moveToFirst();
        workersID.add("your id");
        while (!crsr.isAfterLast()) {
            workersID.add(crsr.getString(col1));
            crsr.moveToNext();
        }
        crsr.close();

        crsr = db.query(Company.TABLE_COMPANY, new String[]{Company.COMPANY_NUMBER, Company.NAME}, null, null, null, null, Company.NAME + " DESC", null);
        col1 = crsr.getColumnIndex(Company.COMPANY_NUMBER);
        int col2 = crsr.getColumnIndex(Company.NAME);

        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            companies.put(crsr.getString(col2), crsr.getInt(col1));
            crsr.moveToNext();
        }
        crsr.close();
        db.close();

        idsAdp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, workersID);
        workerID.setAdapter(idsAdp);

        ArrayList<String> compniesNames = new ArrayList<>(companies.keySet());
        compniesNames.add(0, "company name");
        companiesAdp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, compniesNames);
        companiesNames.setAdapter(companiesAdp);
    }

    /**
     * Make order
     *
     * @param view the view
     */
    public void makeOrder(View view) {
        // if there are no workers or companies in db
        if ((companyID == null) || (workerId == null))
        {
            Toast.makeText(OrderActivity.this, "There are no workers or companies :(", Toast.LENGTH_SHORT).show();
        }
        else
        {
            adb = new AlertDialog.Builder(this);
            adb.setTitle("Meal Info");

            final EditText firstMeal = new EditText(this);
            final EditText mainMeal = new EditText(this);
            final EditText extra = new EditText(this);
            final EditText dessert = new EditText(this);
            final EditText drink = new EditText(this);

            firstMeal.setHint("first meal");
            mainMeal.setHint("main meal");
            extra.setHint("extra");
            dessert.setHint("dessert");
            drink.setHint("drink");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            layout.addView(firstMeal);
            layout.addView(mainMeal);
            layout.addView(extra);
            layout.addView(dessert);
            layout.addView(drink);

            adb.setView(layout);

            adb.setPositiveButton("buy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (TextUtils.isEmpty(firstMeal.getText().toString())
                        || TextUtils.isEmpty(mainMeal.getText().toString())
                        || TextUtils.isEmpty(extra.getText().toString())
                        || TextUtils.isEmpty(dessert.getText().toString())
                        || TextUtils.isEmpty(drink.getText().toString()))
                    {
                        Toast.makeText(OrderActivity.this, "Meal must have value", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        addOrderToDB(firstMeal.getText().toString(),
                                    mainMeal.getText().toString(),
                                    extra.getText().toString(),
                                    dessert.getText().toString(),
                                    drink.getText().toString());
                        Toast.makeText(OrderActivity.this, "Order completed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            adb.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            adb.show();
        }
    }

    /**
     *  add order to the db
     */
    private void addOrderToDB(String firstMeal, String mainMeal, String extra, String dessert, String drink)
    {
        // insert meal to db
        ContentValues cv = new ContentValues();

        cv.put(Meal.FIRST_MEAL, firstMeal);
        cv.put(Meal.MAIN_MEAL, mainMeal);
        cv.put(Meal.EXTRA, extra);
        cv.put(Meal.DESSERT, dessert);
        cv.put(Meal.DRINK, drink);

        db = hlp.getWritableDatabase();
        int mealId = (int) db.insert(Meal.TABLE_MEAL, null, cv);
        db.close();

        // insert order to db
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Use Israel's time zone to format the date in
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));
        String[] timeDate = df.format(date).split(" ");

        cv = new ContentValues();

        cv.put(Order.ORDER_DATE, timeDate[0]);
        cv.put(Order.ORDER_TIME, timeDate[1]);
        cv.put(Order.WORKER_ID, workerId);
        cv.put(Order.MEAL_ID, mealId);
        cv.put(Order.COMPANY, companyID);

        db = hlp.getWritableDatabase();
        db.insert(Order.TABLE_ORDER, null, cv);
        db.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long rowid) {
        if (adapterView.getAdapter().getItem(0).equals("your id"))
        {
            if (idsAdp.getItem(pos).equals("your id"))
                workerId = null;
            else
                workerId = idsAdp.getItem(pos);
        }
        else {
            if (companiesAdp.getItem(pos).equals("company name"))
                companyID = null;
            else
                companyID = String.valueOf(companies.get(companiesAdp.getItem(pos)));
        }
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
        else if (id == R.id.home)
        {
            Intent si = new Intent(this, MainActivity.class);
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