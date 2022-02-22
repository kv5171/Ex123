package com.example.ex123;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.ex123.Employee.COMPANY;
import static com.example.ex123.Employee.EMPLOYEE_ID;
import static com.example.ex123.Employee.FIRST_NAME;
import static com.example.ex123.Employee.KEY_ID;
import static com.example.ex123.Employee.LAST_NAME;
import static com.example.ex123.Employee.PHONE;
import static com.example.ex123.Employee.TABLE_EMPLOYEE;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GYN.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+TABLE_EMPLOYEE;
        strCreate+=" ("+KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+LAST_NAME+" TEXT,";
        strCreate+=" "+FIRST_NAME+" TEXT,";
        strCreate+=" "+COMPANY+" TEXT,";
        strCreate+=" "+EMPLOYEE_ID+" TEXT,";
        strCreate+=" "+PHONE+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_EMPLOYEE;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
