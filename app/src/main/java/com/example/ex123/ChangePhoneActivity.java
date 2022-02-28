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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Change phone activity.
 */
public class ChangePhoneActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText phone;
    Spinner options;
    Button btn;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;

    String employeeId, currPhone;
    ArrayList<String> idsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);

        options = (Spinner) findViewById(R.id.options);
        phone = (EditText) findViewById(R.id.phone);
        btn = (Button) findViewById(R.id.btn);

        options.setOnItemSelectedListener(this);

        hlp = new HelperDB(this);
        idsArray = new ArrayList<>();
        getIds();

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, idsArray);
        options.setAdapter(adp);

        phone.setTransformationMethod(null);
    }

    /**
     * get all employees id from db
     */
    private void getIds()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, new String[]{Employee.EMPLOYEE_ID}, null, null, null, null, null, null);

        int col1 = crsr.getColumnIndex(Employee.EMPLOYEE_ID);

        crsr.moveToFirst();
        idsArray.add("Your Id");
        while (!crsr.isAfterLast()) {
            idsArray.add(crsr.getString(col1));
            crsr.moveToNext();
        }
        crsr.close();
    }

    /**
     * Apply the phone in db
     *
     * @param view the view
     */
    public void apply(View view) {
        String phoneString = phone.getText().toString();

        if (phoneString.equals(""))
        {
            Toast.makeText(this, "Phone cant be null", Toast.LENGTH_SHORT).show();
        }
        else if (currPhone.equals(phoneString))
        {
            Toast.makeText(this, "This is the same phone", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues cv = new ContentValues();
            db = hlp.getWritableDatabase();
            cv.put(Employee.PHONE, phoneString);
            db.update(Employee.TABLE_EMPLOYEE, cv, Employee.PHONE + "=?", new String[]{currPhone});
            db.close();
            Toast.makeText(this, "Phone has changed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0)
        {
            phone.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.INVISIBLE);
        }
        else
        {
            phone.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);

            employeeId = (String) adapterView.getAdapter().getItem(pos);
            getPhoneById();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * get the phone by employee id
     */
    private void getPhoneById()
    {
        db=hlp.getReadableDatabase();
        crsr = db.query(Employee.TABLE_EMPLOYEE, new String[]{Employee.PHONE}, Employee.EMPLOYEE_ID+"=?", new String[]{employeeId}, null, null, null, "1");

        crsr.moveToFirst();
        int col1 = crsr.getColumnIndex(Employee.PHONE);
        currPhone = crsr.getString(col1);
        phone.setText(currPhone);
        crsr.close();
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
        else if (id == R.id.sort)
        {
            Intent si = new Intent(this, SortActivity.class);
            startActivity(si);
        }
        else if (id == R.id.home)
        {
            Intent si = new Intent(this, MainActivity.class);
            startActivity(si);
        }

        return true;
    }
}