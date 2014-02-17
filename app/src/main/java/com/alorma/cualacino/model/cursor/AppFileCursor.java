package com.alorma.cualacino.model.cursor;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.alorma.cualacino.model.bean.AppFile;
import com.alorma.cualacino.model.contract.FilesContract;
import com.alorma.cualacino.utils.SchemeFileUtils;

import java.io.File;

/**
 * Created by Bernat on 11/02/14.
 */
public class AppFileCursor extends BaseCursorHelper<AppFile> {
    @Override
    public ContentValues setValues(AppFile appFile) {
        ContentValues values = new ContentValues(4);
        values.put(FilesContract.FilesColumns._ID, appFile.getWidgetId());
        values.put(FilesContract.FilesColumns.URI, String.valueOf(appFile.getUri()));
        values.put(FilesContract.FilesColumns.MIME, appFile.getMime());
        values.put(FilesContract.FilesColumns.NAME, appFile.getFileName());
        return values;
    }

    @Override
    public AppFile readCursor(Cursor cursor) {
        AppFile file = new AppFile();

        if (checkCursor(cursor)) {
            int idColumn = columnIndex(cursor, FilesContract.FilesColumns._ID);
            if (idColumn > -1) {
                int id = cursor.getInt(idColumn);
                file.setWidgetId(id);
            }

            int pathColumn = columnIndex(cursor, FilesContract.FilesColumns.URI);
            if (pathColumn > -1) {
                String path = cursor.getString(pathColumn);
                file.setUri(Uri.parse(path));
            }

            int mimeColumn = columnIndex(cursor, FilesContract.FilesColumns.MIME);
            if (mimeColumn > -1) {
                String mime = cursor.getString(mimeColumn);
                file.setMime(mime);
            }
            int nameColumn = columnIndex(cursor, FilesContract.FilesColumns.NAME);
            if (nameColumn > -1) {
                String name = cursor.getString(nameColumn);
                file.setFileName(name);
            }
        }

        return file;
    }
}
