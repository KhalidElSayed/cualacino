package com.alorma.cualacino.utils;

import android.content.Context;
import android.net.Uri;

/**
 * Created by Bernat on 11/02/14.
 */
public interface FileUtilities  {
    String mimeType(Context ctx, Uri uri);
    String path(Context context, Uri uri);
    String fileName(Context context, Uri uri);
    String fileName(String path);
}
