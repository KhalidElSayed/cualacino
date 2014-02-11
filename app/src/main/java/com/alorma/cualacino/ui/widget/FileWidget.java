package com.alorma.cualacino.ui.widget;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.alorma.cualacino.R;
import com.alorma.cualacino.model.bean.AppFile;
import com.alorma.cualacino.model.contract.FilesContract;
import com.alorma.cualacino.model.cursor.AppFileCursor;
import com.alorma.cualacino.model.provider.CualacinoContentProvider;
import com.alorma.cualacino.ui.activity.FileWidgetConfigureActivity;
import com.alorma.cualacino.utils.MimeUtils;
import com.alorma.cualacino.utils.SchemeFileUtils;

import java.io.File;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link FileWidgetConfigureActivity FileWidgetConfigureActivity}
 */
public class FileWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Uri uri = CualacinoContentProvider.buildUri(FilesContract.PATH_DIR, appWidgetId);
            updateViews(context, appWidgetManager, uri);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        /*final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            // WidgetPrefsUtils.deleteTitlePref(context, appWidgetIds[i]);
        }*/
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateWidget(Context context, Uri uri) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        updateViews(context, manager, uri);
    }

    private static void updateViews(Context context, AppWidgetManager appWidgetManager, Uri uri) {
        Cursor cursor = context
                .getContentResolver()
                .query(uri, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            AppFile file = new AppFileCursor().readCursor(cursor);

            if (!TextUtils.isEmpty(file.getFilePath())) {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.file_widget);

                PendingIntent pendingIntent = getPendingIntent(context, file);

                remoteViews.setTextViewText(R.id.appwidget_text, "" + file.getFileName());

                if (file.getMime() != null) {
                    Integer draw = MimeUtils.getMimeResource(file.getMime());
                    if (draw != null) {
                        remoteViews.setImageViewResource(R.id.appwidget_image, draw);
                    } else {
                        remoteViews.setImageViewResource(R.id.appwidget_image, R.drawable.ic_launcher);
                    }
                    remoteViews.setOnClickPendingIntent(R.id.appwidget_image, pendingIntent);
                }

                remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

                appWidgetManager.updateAppWidget(file.getWidgetId(), remoteViews);
            }
        }
    }


    private static PendingIntent getPendingIntent(Context ctx, AppFile appFile) {
        String action = Intent.ACTION_VIEW;
        File file = new File(appFile.getFilePath());
        Uri fileUri = Uri.fromFile(file);

        Intent intent = new Intent(action);
        intent.setDataAndType(fileUri, appFile.getMime());

        return PendingIntent.getActivity(ctx, file.hashCode(), intent, 0);
    }

    public class WidgetLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    }
}


