package com.example.ex123;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Keren Weintraub <kv5171@bs.amalnet.k12.il>
 * @version	1
 * @since  17/02/2022
 * The type Helper func.
 */
public class HelperFunc {
    /**
     * Check if the parameter is already in the db
     *
     * @param databaseName the database name
     * @param param        the param to chack in db
     * @param paramType    the param type
     * @param hlp          the helper to db
     * @return the boolean
     */
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
