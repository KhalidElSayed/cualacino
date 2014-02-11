package com.alorma.cualacino.model.bean;

import java.io.File;

/**
 * Created by Bernat on 11/02/14.
 */
public class AppFile {
    private int widgetId;
    private String filePath;
    private String fileName;
    private String mime;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File file;

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
}
