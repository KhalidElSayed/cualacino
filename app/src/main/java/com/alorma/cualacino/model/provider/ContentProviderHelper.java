package com.alorma.cualacino.model.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.alorma.cualacino.BuildConfig;
import com.alorma.cualacino.model.contract.FilesContract;
import com.alorma.cualacino.model.db.DbHelper;

/**
 * Created by Bernat on 11/02/14.
 */
public abstract class ContentProviderHelper extends ContentProvider {

    private SQLiteDatabase db;

    protected abstract String getTable();

    protected SQLiteDatabase getDb() {
        if (db == null) {
            db = getDbHelper().getWritableDatabase();
        }
        return db;
    }

    protected abstract SQLiteOpenHelper getDbHelper();

    public static Uri buildUri(String path) {
        Uri uri =  Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + getAuthority());
        return Uri.withAppendedPath(uri, path);
    }

    public static Uri buildUri(String path, long id) {
        Uri uri =  Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + getAuthority());
        uri = Uri.withAppendedPath(uri, path);
        return ContentUris.withAppendedId(uri, id);
    }

    protected static String getAuthority() {
        return  BuildConfig.PACKAGE_NAME + "." + BuildConfig.BUILD_TYPE;
    }
}
