package com.alorma.cualacino.model.contract;

import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * Created by Bernat on 11/02/14.
 */
public class FilesContract implements Contract {

    public static final String TABLE = "FILES";
    public static final String PATH_DIR = "FILES";
    public static final String PATH_ITEM = "FILES/#";
    public static final String CONTENT_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.android." + PATH_DIR;
    public static final String CONTENT_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.android." + PATH_DIR;

    public static class FilesColumns implements BaseColumns {
        public static final String PATH = "PATH";
        public static final String WIDGET_ID = "WIDGET_ID";
        public static final String MIME = "MIME";
        public static final String NAME = "NAME";
    }

    @Override
    public String create() {
        StringBuilder sb = new StringBuilder("Create table ");

        sb.append(TABLE);
        sb.append(" (");
        sb.append(FilesColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(FilesColumns.PATH + " TEXT, ");
        sb.append(FilesColumns.NAME + " TEXT, ");
        sb.append(FilesColumns.WIDGET_ID + " INTEGER, ");
        sb.append(FilesColumns.MIME + " TEXT");
        sb.append(");");

        return sb.toString();
    }

    @Override
    public String alter(int oldVersion, int newVersion) {
        return null;
    }



}
