package com.example.ex123;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.ex123.Company.COMPANY_NUMBER;
import static com.example.ex123.Company.FIRST_PHONE;
import static com.example.ex123.Company.NAME;
import static com.example.ex123.Company.SECOND_PHONE;
import static com.example.ex123.Company.TABLE_COMPANY;
import static com.example.ex123.Employee.COMPANY;
import static com.example.ex123.Employee.EMPLOYEE_ID;
import static com.example.ex123.Employee.FIRST_NAME;
import static com.example.ex123.Employee.KEY_ID;
import static com.example.ex123.Employee.LAST_NAME;
import static com.example.ex123.Employee.PHONE;
import static com.example.ex123.Employee.TABLE_EMPLOYEE;
import static com.example.ex123.Meal.DESSERT;
import static com.example.ex123.Meal.DRINK;
import static com.example.ex123.Meal.EXTRA;
import static com.example.ex123.Meal.FIRST_MEAL;
import static com.example.ex123.Meal.MAIN_MEAL;
import static com.example.ex123.Meal.TABLE_MEAL;
import static com.example.ex123.Meal.MEAL_ID;
import static com.example.ex123.Order.ORDER_DATE;
import static com.example.ex123.Order.TABLE_ORDER;
import static com.example.ex123.Order.ORDER_TIME;
import static com.example.ex123.Order.WORKER_ID;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Helper db.
 */
public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GYN.db";
    private static final int DATABASE_VERSION = 5;
    String strCreate, strDelete;

    /**
     * Instantiates a new Helper db.
     *
     * @param context the context
     */
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

        strCreate="CREATE TABLE "+TABLE_COMPANY;
        strCreate+=" ("+COMPANY_NUMBER+" INTEGER PRIMARY KEY,";
        strCreate+=" "+NAME+" TEXT,";
        strCreate+=" "+FIRST_PHONE+" TEXT,";
        strCreate+=" "+SECOND_PHONE+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_MEAL;
        strCreate+=" ("+MEAL_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+FIRST_MEAL+" TEXT,";
        strCreate+=" "+MAIN_MEAL+" TEXT,";
        strCreate+=" "+EXTRA+" TEXT,";
        strCreate+=" "+DESSERT+" TEXT,";
        strCreate+=" "+DRINK+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+TABLE_ORDER;
        strCreate+=" ("+ORDER_DATE+" TEXT,";
        strCreate+=" "+ORDER_TIME+" TEXT,";
        strCreate+=" "+WORKER_ID+" TEXT,";
        strCreate+=" "+Order.MEAL_ID+" INTEGER,";
        strCreate+=" "+Order.COMPANY+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+TABLE_EMPLOYEE;
        db.execSQL(strDelete);

        strDelete="DROP TABLE IF EXISTS "+TABLE_COMPANY;
        db.execSQL(strDelete);

        strDelete="DROP TABLE IF EXISTS "+TABLE_MEAL;
        db.execSQL(strDelete);

        strDelete="DROP TABLE IF EXISTS "+TABLE_ORDER;
        db.execSQL(strDelete);

        onCreate(db);
    }
}
