package com.alorma.cualacino.utils;

import com.alorma.cualacino.R;

import java.util.HashMap;

/**
 * Created by Bernat on 11/02/14.
 */
public class MimeUtils {

    private static HashMap<String, Integer> mimeDrawables;

    public static int getMimeResource(String mime) {
        if (mimeDrawables == null) {
            createDrawables();
        }
        Integer draw = mimeDrawables.get(mime);
        if (draw == null) {
            draw = R.drawable._blank;
        }
        return draw;
    }

    private static void createDrawables() {
        mimeDrawables = new HashMap<String, Integer>();

        addApplicationMimeTypes();
        addImageMimeTypes();
        addTextMimeTypes();
        addMediaMimeTypes();
        addCompressedMimeTypes();
    }

    private static void addApplicationMimeTypes() {
        mimeDrawables.put("application/pdf", R.drawable.pdf);
        mimeDrawables.put("application/mswordf", R.drawable.doc);
        mimeDrawables.put("application/vnd.ms-excel", R.drawable.xls);
        mimeDrawables.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", R.drawable.xlsx);
        mimeDrawables.put("application/vnd.ms-powerpoint", R.drawable.ppt);
        mimeDrawables.put("application/x-msdos-program", R.drawable.exe);
        mimeDrawables.put("application/vnd.android.package-archive", R.drawable.android);
    }

    private static void addImageMimeTypes() {
        mimeDrawables.put("image/jpeg", R.drawable.jpg);
        mimeDrawables.put("image/png", R.drawable.png);
    }

    private static void addTextMimeTypes() {
        mimeDrawables.put("text/plain", R.drawable.txt);
        mimeDrawables.put("text/html", R.drawable.html);
        mimeDrawables.put("text/php", R.drawable.php);
        mimeDrawables.put("application/rss+xml", R.drawable.xml);
        mimeDrawables.put("text/x-python", R.drawable.py);
        mimeDrawables.put("text/x-java", R.drawable.java);
        mimeDrawables.put("application/javascript", R.drawable.js);
    }

    private static void addMediaMimeTypes() {
        mimeDrawables.put("audio/mpeg", R.drawable.mpg);
        mimeDrawables.put("video/mp4", R.drawable.mp4);
        mimeDrawables.put("video/3gpp", R.drawable.mpg);
    }

    private static void addCompressedMimeTypes() {
        mimeDrawables.put("application/x-7z-compressed", R.drawable.zip);
        mimeDrawables.put("application/zip", R.drawable.zip);
        mimeDrawables.put("application/rar", R.drawable.rar);
        mimeDrawables.put("application/x-gzip", R.drawable.tgz);
    }
}
