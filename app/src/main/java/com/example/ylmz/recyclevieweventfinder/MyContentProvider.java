package com.example.ylmz.recyclevieweventfinder;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {


    private static final String LOG_TAG = MyContentProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MyHelper mOpenHelper;

    // Codes for the UriMatcher //////
    private static final int EVENT= 100;
    private static final int DETAIL= 300;
    private static final int EVENT_WITH_ID = 200;
    ////////
    public static final Uri CONTENT_URI = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
            .appendPath("events").build();

    public static final Uri CONTENT_URI2 = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
            .appendPath("details").build();



    private static UriMatcher buildUriMatcher(){
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = "com.example.ylmz.recyclevieweventfinder";

        // add a code for each type of URI you want
        matcher.addURI(authority, "events", EVENT);
        matcher.addURI(authority, "details", DETAIL);
        matcher.addURI(authority, "events"+ "/#", EVENT_WITH_ID);


        return matcher;
    }




    @Override
    public boolean onCreate() {
        mOpenHelper = new MyHelper(getContext());

        return true;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            // All Flavors selected
            case EVENT:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                       "events",
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case DETAIL:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        "details",
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual flavor based on Id selected
            case EVENT_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        "events",
                        projection,
                        "id" + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            default:{
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case EVENT:{
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + "com.example.ylmz.recyclevieweventfinder" + "/" + "events";
            }
            case EVENT_WITH_ID:{
                return ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + "com.example.ylmz.recyclevieweventfinder" + "/" + "events";
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case EVENT: {
                long _id = db.insert("events", null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, _id);
                    Log.v("inserted1","inserted");
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }


            }
            break;
            case DETAIL: {
                long _id = db.insert("details", null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI2, _id);
                    Log.v("inserted2", "inserted");
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }

            }
            break;
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
