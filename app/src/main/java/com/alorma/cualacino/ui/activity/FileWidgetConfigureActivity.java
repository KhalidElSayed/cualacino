package com.alorma.cualacino.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alorma.cualacino.R;
import com.alorma.cualacino.model.bean.AppFile;
import com.alorma.cualacino.model.contract.FilesContract;
import com.alorma.cualacino.model.cursor.AppFileCursor;
import com.alorma.cualacino.model.provider.CualacinoContentProvider;
import com.alorma.cualacino.ui.widget.FileWidget;
import com.alorma.cualacino.utils.FileManagerUtils;
import com.alorma.cualacino.utils.MimeUtils;
import com.bugsense.trace.BugSenseHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The configuration screen for the {@link FileWidget FileWidget} AppWidget.
 */
public class FileWidgetConfigureActivity extends Activity implements View.OnClickListener {

    private static final int REQUEST_GET_CONTENT = 870;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private TextView txtName;
    private ImageView image;
    private AppFile file;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        BugSenseHandler.initAndStartSession(FileWidgetConfigureActivity.this, "6262773b");

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.file_widget_configure);

        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.accept).setOnClickListener(this);

        txtName = (TextView) findViewById(R.id.name);
        image = (ImageView) findViewById(R.id.image);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        } else {
            Intent selectFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            selectFileIntent.setType("*/*");
            startActivityForResult(selectFileIntent, REQUEST_GET_CONTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_GET_CONTENT) {
            Uri uri = data.getData();
            String name = getNameFromUri(uri);
            String mime = getMimeFromUri(uri);

            file = new AppFile();

            file.setWidgetId(mAppWidgetId);
            file.setFileName(name);
            file.setMime(mime);
            file.setUri(uri);

            Log.i("TAG-FILE", file.toString());

            updateUi(file);
        }
    }

    private String getNameFromUri(Uri uri) {
        if (uri != null && uri.getScheme() != null) {
            if (uri.getScheme().equalsIgnoreCase(ContentResolver.SCHEME_CONTENT)) {
                Cursor cursor =
                        getContentResolver().query(uri, null, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

                    if (nameIndex > 0) {
                        return cursor.getString(nameIndex);
                    }
                }
            } else if (uri.getScheme().equalsIgnoreCase(ContentResolver.SCHEME_FILE)) {
                File file = new File(uri.getPath());
                return file.getName();
            }
        }

        return "";
    }

    private String getMimeFromUri(Uri uri) {
        String mime = getContentResolver().getType(uri);

        if (uri != null && uri.getScheme() != null &&
                uri.getScheme().equalsIgnoreCase(ContentResolver.SCHEME_FILE)) {
            String extensionUrl = MimeTypeMap.getFileExtensionFromUrl(String.valueOf(uri));
            mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extensionUrl);
        }
        return mime;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void updateUi(AppFile file) {
        String fileName = file.getFileName();

        if (TextUtils.isEmpty(fileName)) {
            txtName.setText(file.getFileName());
        } else {
            txtName.setText(fileName);
        }

        int drawable = MimeUtils.getMimeResource(file.getMime());
        Drawable resource = getResources().getDrawable(drawable);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            resource = getResources().getDrawableForDensity(drawable, DisplayMetrics.DENSITY_XXHIGH);
        }
        image.setImageDrawable(resource);
    }

    private Uri insertFile(AppFile file) {

        String fileNameTxt = file.getFileName();
        if (!TextUtils.isEmpty(txtName.getText())) {
            fileNameTxt = txtName.getText() != null ? txtName.getText().toString() : "";
        }

        file.setFileName(fileNameTxt);

        ContentValues cv = new AppFileCursor().setValues(file);
        Uri insertUri = CualacinoContentProvider.buildUri(FilesContract.PATH_DIR);

        return getContentResolver().insert(insertUri, cv);
    }

    private void finishWidgetConf(int result) {
        setResult(result);
        finish();
    }

    private void updateWidget(Uri insertUri) {
        FileWidget.updateWidget(this, insertUri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept:
                Uri insertUri = insertFile(file);
                updateWidget(insertUri);
                finishWidgetConf(RESULT_OK);
                break;
            case R.id.cancel:
                finishWidgetConf(RESULT_CANCELED);
                break;
        }
    }
}



