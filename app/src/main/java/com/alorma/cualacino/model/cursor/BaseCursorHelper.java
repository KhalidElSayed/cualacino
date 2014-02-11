package com.alorma.cualacino.model.cursor;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe que ens permet treballar am els cursors.
 * <br />
 * Llegim cursor i el passem a K
 * D'un tipus de objecte K pasem a ContentValues
 */
public abstract class BaseCursorHelper<K> {

    public abstract ContentValues setValues(K k);

    public ContentValues[] setValuesArray(K[] ks) {
        return setValuesArray(Arrays.asList(ks));
    }

    public ContentValues[] setValuesArray(List<K> ks) {
        ContentValues[] vs = new ContentValues[ks.size()];

        for (int i = 0; i < ks.size(); i++) {
            vs[i] = setValues(ks.get(i));
        }

        return vs;
    }

    public abstract K readCursor(Cursor cursor);

    public List<K> readFullCursor(Cursor cursor) {
        List<K> ks = new ArrayList<K>();
        if (checkCursor(cursor)) {
            while (cursor.moveToNext()) {
                ks.add(readCursor(cursor));
            }
        }
        return ks;
    }

    protected boolean checkCursor(Cursor cursor) {
        return cursor != null && !cursor.isBeforeFirst() && !cursor.isAfterLast();
    }

    protected int columnIndex(Cursor cursor, String column) {
        return cursor.getColumnIndex(column);
    }
}
