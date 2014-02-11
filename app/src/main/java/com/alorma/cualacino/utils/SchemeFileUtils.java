package com.alorma.cualacino.utils;

import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

/**
 * Created by Bernat on 29/01/14.
 */
public class SchemeFileUtils implements FileUtilities {
    @Override
    public String mimeType(Context ctx, Uri uri) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(uri));
        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mime;
    }

    @Override
    public String path(Context context, Uri uri) {
        return uri.getPath();
    }

    @Override
    public String fileName(Context context, Uri uri) {
        String filePath = FileManagerUtils.removeExtension(path(context, uri));
        filePath = FileManagerUtils.removeDot(filePath);
        return filePath;
    }

    @Override
    public String fileName(String path) {
        String filePath = FileManagerUtils.removeExtension(MimeTypeMap.getFileExtensionFromUrl(path));
        filePath = FileManagerUtils.removeDot(filePath);
        return filePath;
    }

}
