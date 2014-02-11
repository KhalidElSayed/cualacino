package com.alorma.cualacino.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alorma.cualacino.model.contract.FilesContract;

/**
 * Created by Bernat on 11/02/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cualaciono.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String filesTable = new FilesContract().create();
        db.execSQL(filesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
