package com.alorma.cualacino.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

/**
 * Created by Bernat on 11/02/14.
 */
public class FileManagerUtils {

    public static String mimeType(Context ctx, Uri uri) {
        String type = null;
        String fileName = fileName(ctx, uri);
        String extension = MimeTypeMap.getFileExtensionFromUrl(fileName);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isDocumentUri(Context ctx, Uri uri) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(ctx, uri);
    }

    public static String fullFileName(Context context, Uri uri) {
        String fileName = "";

        try {
            if (uri != null) {
                if (uri.getScheme() != null) {
                    if (!TextUtils.isEmpty(uri.getScheme())) {
                        if (uri.getScheme().equalsIgnoreCase(ContentResolver.SCHEME_FILE)) {
                            fileName = uri.getPath();
                        } else if (uri.getScheme().equalsIgnoreCase(ContentResolver.SCHEME_CONTENT)) {
                            if (isDocumentUri(context, uri)) {
                                fileName = documentsFileName(context, uri);
                            } else {
                                fileName = noDocumentsFileName(context, uri);
                            }
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return fileName;
    }


    public static String fileName(Context context, Uri uri) {
        return fileName(filePath(context, uri));
    }

    public static String fileName(String path) {
        String filePath = removeExtension(path);

        filePath = removeDot(filePath);
        return filePath;
    }

    public static String filePath(Context context, Uri uri) {
        return uri.getPath();
    }

    public static String removeExtension(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            String[] parts = fileName.split("\\.");
            if (parts != null && parts.length > 0) {
                fileName = parts[0];
            }
        }
        return fileName;
    }

    public static String removeDot(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            fileName = fileName.replace(".", "");
        }

        return fileName;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String documentsFileName(Context context, Uri uri) throws ArrayIndexOutOfBoundsException {
        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(uri);
        String filePath = "";
        // Split at colon, use second item in the array
        String[] split = wholeID.split(":");

        String id = "";
        if (split != null && split.length > 0) {
            id = split[1];
        } else {
            throw new ArrayIndexOutOfBoundsException("Bad id");
        }

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{id}, null);


        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(column[0]);

            if (columnIndex != -1) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        return filePath;
    }

    private static String noDocumentsFileName(Context context, Uri uri) {
        String path = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            if (column_index != -1) {
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                cursor.close();
            }
        }
        return path;
    }

    public static String uriType(Uri uri) {

        String scheme = "noscheme";

        if (uri != null && uri.getScheme() != null) {
            scheme = uri.getScheme();
        }

        return scheme;
    }

    public static boolean isFile(Uri uri) {
        return uriType(uri).equalsIgnoreCase(ContentResolver.SCHEME_FILE);
    }

    public static boolean isContent(Uri uri) {
        return uriType(uri).equalsIgnoreCase(ContentResolver.SCHEME_CONTENT);
    }
}
