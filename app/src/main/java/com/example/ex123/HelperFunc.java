package com.example.ex123;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HelperFunc {
    public static boolean checkInDB(String databaseName, String param, String paramType, HelperDB hlp)
    {
        int crsrLen = 0;

        SQLiteDatabase db=hlp.getReadableDatabase();
        Cursor crsr = db.query(databaseName, new String[]{paramType}, paramType+"=?", new String[]{param}, paramType+"=?", null, null, null);

        crsr.moveToFirst();
        crsrLen = crsr.getCount();
        crsr.close();
        db.close();

        // if there is no elements returned - the param not in the db!
        return crsrLen != 0;
    }
}
