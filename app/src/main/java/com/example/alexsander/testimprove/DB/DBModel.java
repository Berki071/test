package com.example.alexsander.testimprove.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.alexsander.testimprove.MainActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by alexsander on 19.03.18.
 */

public class DBModel {
    private static final DBModel ourInstance = new DBModel();
    public static DBModel getInstance() {
        return ourInstance;
    }
    private DBModel() {}


    DBHelper dbHelper;
    Context context;

    public void initDBHelper(Context context)
    {
        this.context=context;
        dbHelper=new DBHelper(this.context);
    }

    public void setData(Context context, UserData userData) {
        if(dbHelper==null)
        {
            initDBHelper(context);
        }

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.nameTabel, null, null);

        cv.put(DBHelper.EMAIL,userData.getEmail());
        cv.put(DBHelper.PHONE,userData.getPhone());
        cv.put(DBHelper.PASSWORD,userData.getPassword());
        cv.put(DBHelper.PHOTO,userData.getPhotoPath());

        long rowID =db.insert(DBHelper.nameTabel,null,cv);

        dbHelper.close();
    }

    public UserData getData()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.query(DBHelper.nameTabel,null,null,null,null,null,null);

        if(cursor.moveToFirst())
        {
            int indexPhoto=cursor.getColumnIndex(DBHelper.PHOTO);
            int indexEmail=cursor.getColumnIndex(DBHelper.EMAIL);
            int indexPhone=cursor.getColumnIndex(DBHelper.PHONE);
            int indexPassword=cursor.getColumnIndex(DBHelper.PASSWORD);

            UserData userData=new UserData(cursor.getString(indexPhoto),
                    cursor.getString(indexEmail),
                    cursor.getString(indexPhone),
                    cursor.getString(indexPassword)
                    );
            dbHelper.close();
            return userData;

        }
        else
        {
            Log.wtf(MainActivity.TAG,"found 0 rows");
            dbHelper.close();
            return null;
        }
    }

}
