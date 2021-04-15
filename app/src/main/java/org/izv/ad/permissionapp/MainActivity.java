package org.izv.ad.permissionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int CONTACTS_PERMISSION = 1;

    /* lifecycle methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case CONTACTS_PERMISSION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    executeAction();
                } else {
                    finish();
                }
                break;
        }
    }

    /* methods */

    private void executeAction() {
        TextView tv = findViewById(R.id.tvText);
        tv.setText(readContacts(getContactsCursor()));
    }

    private void explainReason() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_title);
        builder.setMessage(R.string.permission_message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION);
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    private Cursor getContactsCursor() {
        ContentResolver contentResolver = getContentResolver();
        return contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, //url, consulta de contactos
                null, // *
                null, // where
                null, // parámetros
                null);
    }

    private void init() {
        if(isPermitted()) {
            executeAction();
        } else {
            requestPermission();
        }
    }

    private boolean isPermitted() {
        int result = PackageManager.PERMISSION_GRANTED;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            result = checkSelfPermission(Manifest.permission.READ_CONTACTS);
        }
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private String readContacts(Cursor cursor) {
        long _id;
        String id, name;
        StringBuffer content = new StringBuffer();

        int columnNumber = cursor.getColumnCount();
        for (int i = 0; i < columnNumber; i++) {
            //cursor.getColumnName(i);
        }

        String[] columns = cursor.getColumnNames();
        for (String column: columns) {
            //column;
        }

        int rows = cursor.getCount();

        while(cursor.moveToNext()) {
            _id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            content.append(_id + " " + id + " " + name + "\n");
        }
        return content.toString();
    }

    @SuppressLint("NewApi")
    private void requestPermission() {
        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            explainReason();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION);
        }
    }
}