package com.alorma.cualacino.model.bean;

import android.net.Uri;

import java.io.File;

/**
 * Created by Bernat on 11/02/14.
 */
public class AppFile {
    private int widgetId;
    private String fileName;
    private String mime;
    private Uri uri;

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getUri() {
        return uri != null ? uri : Uri.EMPTY;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppFile{");
        sb.append("widgetId=").append(widgetId);
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", mime='").append(mime).append('\'');
        sb.append(", uri=").append(uri);
        sb.append('}');
        return sb.toString();
    }
}
