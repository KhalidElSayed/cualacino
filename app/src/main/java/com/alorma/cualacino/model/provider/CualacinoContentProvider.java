package com.alorma.cualacino.model.provider;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.alorma.cualacino.model.contract.FilesContract;
import com.alorma.cualacino.model.db.DbHelper;

public class CualacinoContentProvider extends ContentProviderHelper {

    private static final int WIDGETS_DIR = 0;
    private static final int WIDGETS_ITEM = 1;
    private UriMatcher uriMatcher;

    @Override
    protected String getTable() {
        return FilesContract.TABLE;
    }

    @Override
    protected SQLiteOpenHelper getDbHelper() {
        return new DbHelper(getContext());
    }

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(getAuthority(), FilesContract.PATH_DIR, WIDGETS_DIR);
        uriMatcher.addURI(getAuthority(), FilesContract.PATH_ITEM, WIDGETS_ITEM);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case WIDGETS_DIR:
                cursor = getDb().query(getTable(), projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case WIDGETS_ITEM:
                cursor = getDb().query(getTable(), projection, BaseColumns._ID + "=" + uri.getLastPathSegment(), null, null, null, sortOrder);
                break;

        }

        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri newUri = buildUri(FilesContract.PATH_DIR);
        switch (uriMatcher.match(uri)) {
            case WIDGETS_DIR:
                long id = getDb().insert(getTable(), null, values);
                newUri = buildUri(FilesContract.PATH_DIR, id);
                getContext().getContentResolver().notifyChange(newUri, null);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return newUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int sum = 0;
        switch (uriMatcher.match(uri)) {
            case WIDGETS_DIR:
                sum = getDb().update(getTable(), values, selection, selectionArgs);
                break;
            case WIDGETS_ITEM:
                sum = getDb().update(getTable(), values, BaseColumns._ID + "=?", new String[]{uri.getLastPathSegment()});
                break;

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return sum;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int sum = 0;
        switch (uriMatcher.match(uri)) {
            case WIDGETS_DIR:
                sum = getDb().delete(getTable(), selection, selectionArgs);
                break;
            case WIDGETS_ITEM:
                sum = getDb().delete(getTable(), BaseColumns._ID + "=?", new String[]{uri.getLastPathSegment()});
                break;

        }

        getContext().getContentResolver().notifyChange(uri, null);
        return sum;
    }

    @Override
    public String getType(Uri uri) {
        String type = FilesContract.CONTENT_DIR;
        if (uriMatcher.match(uri) == WIDGETS_ITEM) {
            type = FilesContract.CONTENT_ITEM;
        }
        return type;
    }
}
