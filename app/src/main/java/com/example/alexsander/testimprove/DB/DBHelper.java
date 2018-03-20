package com.example.alexsander.testimprove.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexsander on 18.03.18.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String nameDB="mDB1";
    public static final String nameTabel="mTable";

    public static final String PHOTO="photo";
    public static final String EMAIL="email";
    public static final String PHONE="phone";
    public static final String PASSWORD="password";


    public DBHelper(Context context)
    {
        super(context,nameDB,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+nameTabel+" ("
                + "id integer primary key autoincrement,"
                + EMAIL +" text,"
                + PHONE +" text,"
                + PASSWORD +" text,"
                + PHOTO +" BLOB"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
