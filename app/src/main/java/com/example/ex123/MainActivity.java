package com.example.ex123;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

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

    public void addNewEmployee(View view) {
        Intent si = new Intent(this, EmployeeActivity.class);
        startActivity(si);
    }
}