package com.example.ex123;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    HelperDB hlp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
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

        return true;
    }

    /**
     * Add new employee.
     *
     * @param view the view
     */
    public void addNewEmployee(View view) {
        Intent si = new Intent(this, EmployeeActivity.class);
        startActivity(si);
    }

    /**
     * Add new company.
     *
     * @param view the view
     */
    public void addNewCompany(View view) {
        Intent si = new Intent(this, CompanyActivity.class);
        startActivity(si);
    }

    /**
     * Do order.
     *
     * @param view the view
     */
    public void doOrder(View view) {
        Intent si = new Intent(this, OrderActivity.class);
        startActivity(si);
    }
}